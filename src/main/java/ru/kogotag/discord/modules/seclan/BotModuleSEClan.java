package ru.kogotag.discord.modules.seclan;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import ru.kogotag.discord.Config;
import ru.kogotag.discord.modules.BotModule;
import ru.kogotag.discord.utils.DiscordUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class BotModuleSEClan extends BotModule {
    Config config = Config.getConfig();
    MoneyManager moneyManager = MoneyManager.getMoneyManager();
    String prefix = config.getPrefix();

    public BotModuleSEClan(JDA jda) {
        super(jda);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        String content = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();
        String memberId = Objects.requireNonNull(event.getMember()).getId();
        if (!content.startsWith(prefix)) {
            return;
        }
        String cmd = content.substring(prefix.length());
        String[] args = cmd.split("\\s+");
        try {
            switch (args[0]) {
                case "show":
                    cmdShow(args, channel);
                    break;
                case "balance":
                    cmdBalance(args, channel);
                    break;
                case "addmoney":
                    cmdAddMoney(args, channel, memberId);
                    break;
                case "addaccount":
                    cmdAddAccount(args, channel, memberId);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cmdShow(String[] args, TextChannel channel) throws FileNotFoundException {
        DiscordUtils.getDiscordUtils().sendBigTextMessage(channel, moneyManager.getDatabaseInfo());
    }

    private void cmdBalance(String[] args, TextChannel channel) throws FileNotFoundException {
        if (args.length != 2) {
            channel.sendMessage("Usage: " + prefix + "balance `name`").queue();
            return;
        }
        String name = args[1];
        channel.sendMessage(name + " now has " + moneyManager.getAccountBalance(name) + " "
                + config.getCurrency()).queue();
    }

    private void cmdAddMoney(String[] args, TextChannel channel, String memberId) throws IOException {
        if (!memberId.equals(config.getOwner())) {
            channel.sendMessage("Недостаточно прав").queue();
            return;
        }
        if (args.length != 3) {
            channel.sendMessage("Usage: " + prefix + "addmoney `name` `value`").queue();
            return;
        }
        String name = args[1];
        String value = args[2];
        float valueFloat = 0f;
        try {
            valueFloat = Float.parseFloat(value);
        } catch (Exception e) {
            channel.sendMessage(e.toString()).queue();
            return;
        }
        moneyManager.changeAccountBalanceAdd(name, valueFloat);
        channel.sendMessage("Изменения сохранены!").queue();
        String[] balanceArgs = {args[0], args[1]};
        cmdBalance(balanceArgs, channel);

    }

    private void cmdAddAccount(String[] args, TextChannel channel, String memberId) throws IOException {
        if (!memberId.equals(config.getOwner())) {
            channel.sendMessage("Недостаточно прав").queue();
            return;
        }
        if (args.length != 3) {
            channel.sendMessage("Usage: " + prefix + "addaccount `name` `discordId`").queue();
            return;
        }
        String name = args[1];
        String discordId = args[2];
        long discordIdLong = 0;
        try {
            discordIdLong = Long.parseLong(discordId);
        } catch (Exception e) {
            channel.sendMessage(e.toString()).queue();
            return;
        }
        moneyManager.addAccount(new MoneyAccount(name, discordId, 0f));
        channel.sendMessage("Аккаунт добавлен!").queue();
    }
}
