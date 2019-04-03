package ru.myphotos.model;

public interface AsyncOperation<T> {

    long getTimeOutInMillis();

    void onSuccess(T result);

    void onFailed(Throwable throwable);
}