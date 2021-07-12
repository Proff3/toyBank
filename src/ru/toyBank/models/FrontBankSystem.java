package ru.toyBank.models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FrontBankSystem {
    private final Queue<Request> requestsQueue = new ConcurrentLinkedQueue<>();//used ConcurrentLinkedQueue, cause it`s a non-blocking queue
    Lock lock = new ReentrantLock();
    Condition cond = lock.newCondition();

    public void addRequest(Request request) {
        lock.lock();
        try {
            while (requestsQueue.size() > 1) cond.await();
            requestsQueue.offer(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Request pollRequest(){
        Request removedRequest;
        lock.lock();
        try {
            removedRequest = requestsQueue.poll();
            cond.signalAll();
        } finally {
            lock.unlock();
        }
        return removedRequest;
    }

    public Boolean isEmpty(){
        return requestsQueue.isEmpty();
    }
}
