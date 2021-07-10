package ru.toyBank;

import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.enums.RequestTypes;

import java.util.ArrayList;
import java.util.Arrays;

class Bank {

    private final FrontBankSystem frontBankSystem = new FrontBankSystem();

    void start() {
        ArrayList<Thread> clientsPool = createClientPool();
        clientsPool.forEach(client -> {
            try{
                client.join();
            } catch(InterruptedException err){
                err.printStackTrace();
            }
            client.start();
        });

        int numberOfRequests = clientsPool.size();
        ArrayList<Thread> handlersPool = createHandlersPool(numberOfRequests);
        handlersPool.forEach(handler -> {
            try{
                handler.join();
                handler.setDaemon(true);
                handler.start();
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Thread> createClientPool(){
        Thread John = new Client("John", RequestTypes.PAYMENT, 89_000, frontBankSystem);
        Thread Michel = new Client("Michel", RequestTypes.CREDIT, 46_000, frontBankSystem);
        Thread Michel2 = new Client("Alex", RequestTypes.PAYMENT, 24_000, frontBankSystem);
        Thread Michel3 = new Client("Andrew", RequestTypes.CREDIT, 63_000, frontBankSystem);
        Thread Michel4 = new Client("Eliza", RequestTypes.PAYMENT, 78_000, frontBankSystem);
        return new ArrayList<>(Arrays.asList(John, Michel, Michel2, Michel3, Michel4));
    }

    private ArrayList<Thread> createHandlersPool(int numberOfRequests){
        Thread handler1 = new Thread(new Handler(frontBankSystem), "Handler1");
        Thread handler2 = new Thread(new Handler(frontBankSystem), "Handler2");
        return new ArrayList<>(Arrays.asList(handler1, handler2));
    }
}
