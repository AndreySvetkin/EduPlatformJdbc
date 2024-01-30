
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import org.springframework.data.annotation.Id;


public class Task {
    @Id
    @Expose
    private long id;
    
    @Expose
    private long idTheme;
    
    @Expose
    private String name;
    
    @Expose
    private String description;
    
    @Expose
    private String answer;
    
    public long getId(){
        return id;
    }
    
    public long getIdTheme(){
        return idTheme;
    }

    public void setIdTheme(long idTheme){
        this.idTheme=idTheme;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    
    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }

    public boolean equals(Object obj){
        if (obj==null) return false;
        if (this==obj) return true;
        if (!(obj instanceof Task)) return false;
        Task task=(Task)obj;
        if (task.getAnswer().equals(this.getAnswer())) return true;
        return false;
    }
    
    public String toString(){
        return "{"+id+","+idTheme+","+answer+"}";
    }
}
