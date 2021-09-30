package ru.kogotag.discord.modules.seclan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.kogotag.discord.Config;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MoneyManager {
    private static MoneyManager moneyManager;
    private final String filePath;

    public static MoneyManager getMoneyManager() {
        if (moneyManager == null) {
            moneyManager = new MoneyManager();
        }
        return moneyManager;
    }

    private MoneyDatabase getMoneyDatabase() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        FileReader reader = new FileReader(filePath);
        return gson.fromJson(reader, MoneyDatabase.class);
    }

    private void save(MoneyDatabase newDatabase) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(newDatabase);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(json);
        fileWriter.close();
    }

    public MoneyManager() {
        filePath = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParent() + "/money.json";
    }

    public void addAccount(MoneyAccount account) throws IOException {
        MoneyDatabase database = getMoneyDatabase();
        database.addAccount(account);
        save(database);
    }

    public void changeAccountBalance(String name, float value) throws IOException {
        MoneyDatabase database = getMoneyDatabase();
        Optional<MoneyAccount> opt = database.getMoneyAccountList().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();
        opt.ifPresent(s -> s.setMoneyBalance(value));
        save(database);
    }

    public void changeAccountBalanceAdd(String name, float value) throws IOException {
        MoneyDatabase database = getMoneyDatabase();
        Optional<MoneyAccount> opt = database.getMoneyAccountList().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();
        opt.ifPresent(s -> {
            float v = s.getMoneyBalance();
            s.setMoneyBalance(v+value);
        });
        save(database);
    }

    public float getAccountBalance(String name) throws FileNotFoundException {
        MoneyDatabase database = getMoneyDatabase();
        Optional<MoneyAccount> opt = database.getMoneyAccountList().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();
        AtomicReference<Float> val = new AtomicReference<>(0f);
        opt.ifPresent(s -> val.set(s.getMoneyBalance()));
        return val.get();
    }

    public String getDatabaseInfo() throws FileNotFoundException {
        MoneyDatabase database = getMoneyDatabase();
        return database.getAsString();
    }
}
