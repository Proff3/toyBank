package ru.toyBank.models;

import java.util.LinkedList;
import java.util.Queue;

public class FrontBankSystem {
    private final Queue<Request> requestsQueue = new LinkedList<>();

    public synchronized void add(Request request) {
        while (requestsQueue.size() > 1) {
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestsQueue.add(request);
        System.out.println("Added: " + request.getClientName());
    }

    public synchronized Request remove(){
        Request removedRequest = requestsQueue.remove();
        notifyAll();
        return removedRequest;
    }

    public Boolean isEmpty(){
        return requestsQueue.isEmpty();
    }
}
