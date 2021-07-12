package ru.toyBank.models;

import ru.toyBank.models.exceptions.NotEnoughMoney;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BackSystem {
    private int bankAccount;
    Lock lock = new ReentrantLock();

    public void credit(int sum) throws NotEnoughMoney {
        lock.lock();
        try {
            System.out.println("Operation credit is processing");
            if (bankAccount - sum < 0) {
                throw new NotEnoughMoney();
            } else {
                bankAccount -= sum;
            }
        } finally {
            lock.unlock();
        }
    }

    public int creditAndGetAccount(int sum, String clientName) throws NotEnoughMoney {
        lock.lock();
        try {
            System.out.println("Operation credit of " + clientName + " is processing");
            if (bankAccount - sum < 0) {
                throw new NotEnoughMoney();
            } else {
                bankAccount -= sum;
            }
        } finally {
            lock.unlock();
        }
        return bankAccount;
    }

    public void payment(int sum){
        lock.lock();
        try {
            System.out.println("Operation payment is processing");
            bankAccount += sum;
        } finally {
            lock.unlock();
        }
    }

    public int paymentAndGetAccount(int sum, String clientName){
        lock.lock();
        try {
            System.out.println("Operation payment of " + clientName + " is processing");
            bankAccount += sum;
        } finally {
            lock.unlock();
        }
        return bankAccount;
    }
}
