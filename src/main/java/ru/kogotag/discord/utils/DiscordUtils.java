package ru.kogotag.discord.utils;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiscordUtils {
    private static DiscordUtils discordUtils;
    private static final int messageContentSymbolLimit = 2000;

    public static DiscordUtils getDiscordUtils() {
        if (discordUtils == null) {
            discordUtils = new DiscordUtils();
        }
        return discordUtils;
    }

    public void sendBigTextMessage(TextChannel channel, String text) {
        if (text == null || text.length() <= 0) {
            return;
        }
        if (text.length() < messageContentSymbolLimit) {
            channel.sendMessage(text).queue();
            return;
        }
        List<String> splited = SplitMessages(text, messageContentSymbolLimit);
        if (splited == null) {
            return;
        }
        for (String element : splited) {
            if (element.replaceAll("\\s+", "").isEmpty()) {
                continue;
            }
            channel.sendMessage(element).queue();
        }
    }

    private List<String> SplitMessages(String fullmsg, int symbolLimit) {
        if (fullmsg == null) {
            return null;
        }
        if (fullmsg.length() <= symbolLimit) {
            return Collections.singletonList(fullmsg);
        }
        List<String> byWords = Arrays.asList(fullmsg.split("\\s+"));
        int l = 0;
        int i = 0;
        ArrayList<String> splited = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (String word : byWords) {
            if (l + word.length() + 1 >= symbolLimit) {
                splited.add(builder.toString());
                builder = new StringBuilder();
                l = 0;
            }
            builder.append(word).append(" ");
            if (i == byWords.size() - 1) {
                splited.add(builder.toString());
            }
            l += word.length() + 1;
            i++;
        }
        return splited;
    }
}
