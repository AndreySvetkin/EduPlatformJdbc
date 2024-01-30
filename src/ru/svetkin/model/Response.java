
package ru.svetkin.model;


public interface Response<T>{
    T get();
    ServiceStatus status();
}
