package ru.toyBank.models;

import ru.toyBank.models.exceptions.NotEnoughMoney;

public class BackSystem {
    int bankAccount;

    public synchronized void credit(int sum) throws NotEnoughMoney {
        try {
            System.out.println("Operation credit is processing");
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (bankAccount - sum < 0){
            throw new NotEnoughMoney();
        } else {
           bankAccount -= sum;
        }
    }

    public synchronized void payment(int sum){
        try {
            System.out.println("Operation payment is processing");
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bankAccount += sum;
    }
}
