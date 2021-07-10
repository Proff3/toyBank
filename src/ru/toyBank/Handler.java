package ru.toyBank;

import ru.toyBank.models.BackSystem;
import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.exceptions.NotEnoughMoney;

public class Handler extends Thread {

    private FrontBankSystem frontBankSystem;
    private BackSystem backSystem = new BackSystem();

    Handler(FrontBankSystem frontBankSystem){
        this.frontBankSystem = frontBankSystem;
    }

    @Override
    public void run() {
        try{
            while (!frontBankSystem.isEmpty()){
                Request handlingRequest = frontBankSystem.poolRequest();
                try {
                    handlingAction(handlingRequest);
                    System.out.println("Operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is successfully");
                } catch (NotEnoughMoney notEnoughMoney) {
                    System.out.println("Operation " + handlingRequest.getRequestType() + " of client " + handlingRequest.getClientName() + " is FAILED");
                } finally {
                    System.out.println("Bank account: " + backSystem.getBankAccount());
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
                backSystem.credit(sum);
                break;
            case PAYMENT:
                backSystem.payment(sum);
                break;
        }
    }
}
