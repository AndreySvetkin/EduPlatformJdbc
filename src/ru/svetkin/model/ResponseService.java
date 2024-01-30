package ru.svetkin.model;


public class ResponseService<T> implements Response<T>{
    private T obj;
    private ServiceStatus ss;
    
    public ResponseService(T obj,ServiceStatus ss){
        this.obj=obj;
        this.ss=ss;
    }
    public ResponseService(ServiceStatus ss){
        this.ss=ss;
    }
    
    public T get(){
        return obj;
    }
    
    public ServiceStatus status(){
        return ss;
    }
}
