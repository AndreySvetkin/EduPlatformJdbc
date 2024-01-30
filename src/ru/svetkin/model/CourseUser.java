
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;




public class CourseUser implements Persistable<Object[]>{
    @Transient
    private boolean persisted;
    
    @Expose
    private long idUser;
    
    @Expose
    private long idCourse;
    
    @Expose
    private CourseStatus status;
    
    @Expose
    private Date dateEnroll;
    
    @Expose
    private Date dateComplete;
    
    public long getIdUser(){
        return idUser;
    }

    public void setIdUser(long idUser){
        this.idUser=idUser;
    }
    
    public long getIdCourse(){
        return idCourse;
    }

    public void setIdCourse(long idCourse){
        this.idCourse=idCourse;
    }
    
    public CourseStatus getStatus(){
        return status;
    }

    public void setStatus(CourseStatus status){
        this.status=status;
    }

    public Date getDateEnroll() {
        return dateEnroll;
    }
    public void setDateEnroll(Date dateEnroll) {
        this.dateEnroll = dateEnroll;
    }

    public Date getDateComplete() {
        return dateComplete;
    }
    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }
    
    
    public String toString(){
        return idUser+";"+idCourse;
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
