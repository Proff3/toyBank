package ru.toyBank;

import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.enums.RequestTypes;

import java.util.ArrayList;
import java.util.Arrays;

class Bank {

    private final FrontBankSystem frontBankSystem = new FrontBankSystem();

    void start() {
        ArrayList<Thread> clientsPool = createClientPool();
        clientsPool.forEach(Thread::start);

        ArrayList<Thread> handlersPool = createHandlersPool();
        handlersPool.forEach(Thread::start);
    }

    private ArrayList<Thread> createClientPool(){
        Thread John = new Client("John", RequestTypes.PAYMENT, 89_000, frontBankSystem);
        Thread Michel = new Client("Michel", RequestTypes.CREDIT, 46_000, frontBankSystem);
        Thread Alex = new Client("Alex", RequestTypes.PAYMENT, 24_000, frontBankSystem);
        Thread Andrew = new Client("Andrew", RequestTypes.CREDIT, 63_000, frontBankSystem);
        Thread Eliza = new Client("Eliza", RequestTypes.PAYMENT, 78_000, frontBankSystem);
        return new ArrayList<>(Arrays.asList(John, Michel, Alex, Andrew, Eliza));
    }

    private ArrayList<Thread> createHandlersPool(){
        Thread handler1 = new Thread(new Handler(frontBankSystem, "Handler1"));
        Thread handler2 = new Thread(new Handler(frontBankSystem, "Handler2"));
        return new ArrayList<>(Arrays.asList(handler1, handler2));
    }
}
