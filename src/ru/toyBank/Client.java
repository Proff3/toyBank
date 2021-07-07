package ru.toyBank;

import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.enums.RequestTypes;

public class Client extends Thread{
    private final Request request;
    private final FrontBankSystem frontBankSystem;

    public Client(String name, RequestTypes requestType, int sum, FrontBankSystem frontBankSystem){
        super(name);
        request = new Request(name, sum, requestType);
        this.frontBankSystem = frontBankSystem;
    }

    @Override
    public void run() {
        frontBankSystem.add(request);
    }

}
