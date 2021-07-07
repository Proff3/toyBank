package ru.toyBank.models.exceptions;

public class NotEnoughMoney extends Exception{
    public NotEnoughMoney(){
        super("Not enough money");
    }
}
