package ru.toyBank;

import ru.toyBank.models.BackSystem;
import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.exceptions.NotEnoughMoney;

public class Handler extends Thread {

    private final FrontBankSystem frontBankSystem;
    private final BackSystem backSystem = new BackSystem();
    int bankAccount;

    Handler(FrontBankSystem frontBankSystem, String name){
        super(name);
        this.frontBankSystem = frontBankSystem;
    }

    @Override
    public void run() {
        try{
            while (!frontBankSystem.isEmpty()){
                Request handlingRequest = frontBankSystem.poolRequest();
                if (handlingRequest != null){
                    try {
                        handlingAction(handlingRequest);
                        System.out.println("Bank account: " + bankAccount + " - operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is successfully (Thread " + this.getName() + ")");
                    } catch (NotEnoughMoney notEnoughMoney) {
                        System.out.print("Operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is FAILED (Thread " + this.getName() + ")\n");

                    }
                }
            }
        } catch (Exception err){
            err.printStackTrace();
        }

    }

    private void handlingAction(Request request) throws NotEnoughMoney {
        int sum = request.getSum();
        switch(request.getRequestType()){
            case CREDIT:
                bankAccount = backSystem.creditAndGetAccount(sum);
                break;
            case PAYMENT:
                bankAccount = backSystem.paymentAndGetAccount(sum);
                break;
        }
    }
}
