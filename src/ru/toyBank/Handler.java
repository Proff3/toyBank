package ru.toyBank;

import ru.toyBank.models.BackSystem;
import ru.toyBank.models.FrontBankSystem;
import ru.toyBank.models.Request;
import ru.toyBank.models.exceptions.NotEnoughMoney;

public class Handler implements Runnable {

    FrontBankSystem frontBankSystem;
    BackSystem backSystem = new BackSystem();

    public Handler(FrontBankSystem frontBankSystem){
        this.frontBankSystem = frontBankSystem;
    }

    @Override
    public void run() {
        while(!frontBankSystem.isEmpty()){
            Request handlingRequest = frontBankSystem.remove();
            try {
                handlingAction(handlingRequest);
                System.out.println("Operation " + handlingRequest.getRequestType() + " клиента " + handlingRequest.getClientName() + " произведжена успешно");
            } catch (NotEnoughMoney notEnoughMoney) {
                notEnoughMoney.printStackTrace();
            }
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
