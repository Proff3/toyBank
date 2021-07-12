package ru.toyBank;

import ru.toyBank.models.BackSystem;
import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.enums.RequestTypes;

import java.util.*;
import java.util.concurrent.*;

class Bank {

    private final FrontBankSystem frontBankSystem = new FrontBankSystem();
    private final BackSystem backSystem = new BackSystem();

    void start() {
        WarmingUp warmingUp = new WarmingUp();
        ExecutorService warmingUpService = Executors.newSingleThreadExecutor();
        Future<Integer> futureOfWarmingUp = warmingUpService.submit(warmingUp);
        try {
            int result = futureOfWarmingUp.get();
            warmingUpService.shutdown();
            int bankAccount = backSystem.paymentAndGetAccount(result, "warmingUp");
            System.out.println("Bank account after warming-up: " + bankAccount);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ExecutorService clientsExecutorPool = Executors.newFixedThreadPool(2);
        Set<Callable<Client>> clients = createClients();
        for(Callable<Client> client: clients){
            clientsExecutorPool.submit(client);
        }

        ExecutorService handlersExecutorPool = Executors.newFixedThreadPool(2);
        Set<Callable<Handler>> handlers = createHandlers();
        try {
            handlersExecutorPool.invokeAll(handlers);
            clientsExecutorPool.shutdown();
            handlersExecutorPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Set<Callable<Client>> createClients(){
        Callable<Client> John = new Client("John", RequestTypes.PAYMENT, 89_000, frontBankSystem);
        Callable<Client> Michel = new Client("Michel", RequestTypes.CREDIT, 46_000, frontBankSystem);
        Callable<Client> Alex = new Client("Alex", RequestTypes.PAYMENT, 24_000, frontBankSystem);
        Callable<Client> Andrew = new Client("Andrew", RequestTypes.CREDIT, 63_000, frontBankSystem);
        Callable<Client> Eliza = new Client("Eliza", RequestTypes.PAYMENT, 78_000, frontBankSystem);
        return new HashSet<>(Arrays.asList(John, Michel, Alex, Andrew, Eliza));
    }

    private Set<Callable<Handler>> createHandlers(){
        Callable<Handler> handler1 = new Handler(frontBankSystem, backSystem, "Handler1");
        Callable<Handler> handler2 = new Handler(frontBankSystem, backSystem, "Handler2");
        return new HashSet<>(Arrays.asList(handler1, handler2));
    }
}
