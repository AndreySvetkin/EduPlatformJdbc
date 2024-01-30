
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


public class Search {
    
    @Expose
    private String name;
    
    @Expose
    private long idCategory;
    
    @Expose
    private String author;
    
    @Expose
    private long idAuthor;
    
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    
    public long getIdCategory(){
        return idCategory;
    }

    public void setIdCategory(long idCategory){
        this.idCategory=idCategory;
    }
    
    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author=author;
    }

    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }
    
    
}
