package ru.kogotag;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import ru.kogotag.discord.Config;
import ru.kogotag.discord.modules.seclan.BotModuleSEClan;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(
                Config.getConfig().getToken(),
                GatewayIntent.GUILD_MESSAGES)
                .build();
        BotModuleSEClan botModuleSEClan = new BotModuleSEClan(jda);
    }

}
