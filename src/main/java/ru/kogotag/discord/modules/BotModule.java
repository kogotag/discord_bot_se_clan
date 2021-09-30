package ru.kogotag.discord.modules;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotModule extends ListenerAdapter {
    protected JDA jda;

    public BotModule(JDA jda) {
        jda.addEventListener(this);
        this.jda = jda;
    }
}
