package ru.toyBank.models;
import ru.toyBank.models.enums.RequestTypes;

public class Request {
    private String clientName;
    private int sum;
    private RequestTypes requestType;

    public Request(String name, int sum, RequestTypes requestType){
        this.clientName = name;
        this.sum = sum;
        this.requestType = requestType;
    }

    public String getClientName() {
        return clientName;
    }

    public int getSum() {
        return sum;
    }

    public RequestTypes getRequestType() {
        return requestType;
    }

}
