
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;


public class ThemeUser implements Persistable<Object[]>{
    @Transient
    private boolean persisted;
    
    @Expose
    private long idUser;
    
    @Expose
    private long idTheme;

    @Expose
    private Date dateComplete;
    
    public long getIdUser(){
        return idUser;
    }

    public void setIdUser(long idUser){
        this.idUser=idUser;
    }
    
    public long getIdTheme(){
        return idTheme;
    }

    public void setIdTheme(long idTheme){
        this.idTheme=idTheme;
    }

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }
    
    public String toString(){
        return idUser+";"+idTheme;
    }

    public Object[] getId() {
        return new Object[]{idUser,idTheme};
    }

    public boolean isNew() {
        return !persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}

