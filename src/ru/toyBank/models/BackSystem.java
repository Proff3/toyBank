package ru.toyBank.models;

import ru.toyBank.models.exceptions.NotEnoughMoney;

public class BackSystem {
    private volatile int bankAccount;

    public synchronized void credit(int sum) throws NotEnoughMoney {
        System.out.println("Operation credit is processing");
        if (bankAccount - sum < 0) {
            throw new NotEnoughMoney();
        } else {
            bankAccount -= sum;
        }
    }

    public synchronized int creditAndGetAccount(int sum) throws NotEnoughMoney {
        System.out.println("Operation credit is processing");
        if (bankAccount - sum < 0) {
            throw new NotEnoughMoney();
        } else {
            bankAccount -= sum;
        }
        return bankAccount;
    }

    public synchronized void payment(int sum){
        System.out.println("Operation payment is processing");
        bankAccount += sum;
    }

    public synchronized int paymentAndGetAccount(int sum){
        System.out.println("Operation payment is processing");
        bankAccount += sum;
        return bankAccount;
    }
}
