package ru.toyBank;

import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.enums.RequestTypes;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final FrontBankSystem frontBankSystem = new FrontBankSystem();

    public void start() {
        Client John = new Client("John", RequestTypes.PAYMENT, 100_000, frontBankSystem);
        Client Michel = new Client("Michel", RequestTypes.CREDIT, 50_000, frontBankSystem);
        Client Michel2 = new Client("Michel2", RequestTypes.PAYMENT, 50_000, frontBankSystem);
        Client Michel3 = new Client("Michel3", RequestTypes.CREDIT, 50_000, frontBankSystem);
        Client Michel4 = new Client("Michel4", RequestTypes.PAYMENT, 50_000, frontBankSystem);
        ArrayList<Client> clientsPool = new ArrayList<>(List.of(John, Michel, Michel2, Michel3, Michel4));
        clientsPool.forEach(Thread::start);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread handler1 = new Thread(new Handler(frontBankSystem), "Handler1");
        Thread handler2 = new Thread(new Handler(frontBankSystem), "Handler2");
        ArrayList<Thread> handlersPool = new ArrayList<>(List.of(handler1, handler2));
        handlersPool.forEach(Thread::start);
    }
}
