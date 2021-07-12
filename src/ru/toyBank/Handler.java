package ru.toyBank;

import ru.toyBank.models.BackSystem;
import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.exceptions.NotEnoughMoney;

import java.util.concurrent.Callable;

public class Handler implements Callable<Handler> {

    private final FrontBankSystem frontBankSystem;
    private final BackSystem backSystem;
    private final String name;
    int bankAccount;

    Handler(FrontBankSystem frontBankSystem, BackSystem backSystem, String name){
        this.name = name;
        this.backSystem = backSystem;
        this.frontBankSystem = frontBankSystem;
    }

    private void handlingAction(Request request) throws NotEnoughMoney {
        int sum = request.getSum();
        String clientName = request.getClientName();
        switch(request.getRequestType()){
            case CREDIT:
                bankAccount = backSystem.creditAndGetAccount(sum, clientName);
                break;
            case PAYMENT:
                bankAccount = backSystem.paymentAndGetAccount(sum, clientName);
                break;
        }
    }

    @Override
    public Handler call() {
        try{
            while (!frontBankSystem.isEmpty()){
                Request handlingRequest = frontBankSystem.pollRequest();
                if (handlingRequest != null){
                    try {
                        handlingAction(handlingRequest);
                        System.out.println("Bank account: " + bankAccount + " - operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is successfully (Thread " + name + ")");
                    } catch (NotEnoughMoney notEnoughMoney) {
                        System.out.print("Operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is FAILED (Thread " + name + ")\n");

                    }
                }
            }
        } catch (Exception err){
            err.printStackTrace();
        }
        return null;
    }
}
