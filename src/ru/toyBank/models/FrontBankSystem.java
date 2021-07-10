package ru.toyBank.models;

import java.util.LinkedList;
import java.util.Queue;

public class FrontBankSystem {
    private final Queue<Request> requestsQueue = new LinkedList<>();
    public synchronized void addRequest(Request request) {
        while (requestsQueue.size() > 1) {
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestsQueue.add(request);
        notifyAll();
        System.out.println("Added: " + request.getClientName());
    }

    public synchronized Request poolRequest(){
        Request removedRequest = requestsQueue.poll();
        notifyAll();
        return removedRequest;
    }

    public Boolean isEmpty(){
        return requestsQueue.isEmpty();
    }
}
