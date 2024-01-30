
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


public class Theme {
    @Id
    @Expose
    private long id;
    
    @Expose
    private long idCourse;
    
    @Expose
    private String name;
    
    @Expose
    private String description;
    
    @Expose
    private Date dateCreate;
    
    @Expose
    @Transient
    private List<Task> tasks;
    
    
    
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }
    
    public long getIdCourse(){
        return idCourse;
    }

    public void setIdCourse(long idCourse){
        this.idCourse=idCourse;
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
    
    public List<Task> getTasks(){
        return tasks;
    }
    public void setTasks(List<Task> tasks){
        this.tasks=tasks;
    }

    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String toString(){
        return "("+id+","+idCourse+","+name+","+description+")";        
    }
    
    public boolean equals(Object obj){
        if (obj==null) return false;
        if (this==obj) return true;
        if (!(obj instanceof Theme)) return false;
        Theme theme=(Theme)obj;
        if (theme.getName().equals(this.getName())
            && theme.idCourse==this.idCourse) return true;
        return false;
    }
}
