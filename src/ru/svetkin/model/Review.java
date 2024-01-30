
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;


public class Review implements Persistable<Object[]>{
    
    @Transient
    private boolean persisted;
    
    @Expose
    private long idUser;

    @Expose
    private long idCourse;
    
    @Expose
    private long grade;
    
    @Expose
    private String text;
   
    @Expose
    private Date dateCreate;
    
    
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(long idCourse) {
        this.idCourse = idCourse;
    }
    
    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    
    
    @Nullable
    @Override
    public Object[] getId() {
        return new Object[]{idUser,idCourse};
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
