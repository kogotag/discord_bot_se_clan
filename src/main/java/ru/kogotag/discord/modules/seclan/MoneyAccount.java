package ru.kogotag.discord.modules.seclan;

public class MoneyAccount {
    private String name;
    private String discordId;
    private float moneyBalance;

    public MoneyAccount(String name, String discordId, float moneyBalance) {
        this.name = name;
        this.discordId = discordId;
        this.moneyBalance = moneyBalance;
    }

    public String getName() {
        return name;
    }

    public String getDiscordId() {
        return discordId;
    }

    public float getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(float moneyBalance) {
        this.moneyBalance = moneyBalance;
    }
}
