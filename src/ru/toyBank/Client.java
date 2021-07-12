package ru.toyBank;

import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.enums.RequestTypes;

import java.util.concurrent.Callable;

public class Client implements Callable<Client> {
    private final Request request;
    private final FrontBankSystem frontBankSystem;

    Client(String name, RequestTypes requestType, int sum, FrontBankSystem frontBankSystem){
        request = new Request(name, sum, requestType);
        this.frontBankSystem = frontBankSystem;
    }

    @Override
    public Client call() {
        frontBankSystem.addRequest(request);
        return null;
    }
}
