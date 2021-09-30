package ru.kogotag.discord.modules.seclan;

import ru.kogotag.discord.Config;

import javax.annotation.Nonnull;
import java.util.List;

public class MoneyDatabase {
    private List<MoneyAccount> moneyAccountList;

    public MoneyDatabase(List<MoneyAccount> moneyAccountList) {
        this.moneyAccountList = moneyAccountList;
    }

    public List<MoneyAccount> getMoneyAccountList() {
        return moneyAccountList;
    }

    public void addAccount(@Nonnull MoneyAccount account) {
        moneyAccountList.add(account);
    }

    public String getAsString() {
        StringBuilder b = new StringBuilder();
        for (MoneyAccount account :
                moneyAccountList) {
            b.append("name: ")
                    .append(account.getName())
                    .append(", balance: ")
                    .append(account.getMoneyBalance())
                    .append(" ")
                    .append(Config.getConfig().getCurrency())
                    .append("\n");
        }
        return b.toString().trim();
    }
}
