
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import org.springframework.data.annotation.Id;


public class FileInfo {
    @Id
    @Expose
    private long id;
    
    @Expose
    private String name;
    
    @Expose
    private String path;
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
