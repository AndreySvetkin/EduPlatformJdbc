
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import org.springframework.data.annotation.Id;


public class Category {
    @Id
    @Expose
    private long id;
    
    @Expose
    private String name;
    
    public long getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
}
