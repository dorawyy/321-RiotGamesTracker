package com.example.riotgamestracker.models;

public class DataWrapper<T> {
    private T data;
    private String error;

    public DataWrapper(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
