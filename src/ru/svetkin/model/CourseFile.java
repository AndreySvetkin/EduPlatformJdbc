
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;


public class CourseFile implements Persistable<Object[]>{
    
    @Transient
    private boolean persisted;
    
    @Expose
    private long idCourse;
    
    @Expose
    private long idFile;

    
    public long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(long idCourse) {
        this.idCourse = idCourse;
    }

    public long getIdFile() {
        return idFile;
    }

    public void setIdFile(long idFile) {
        this.idFile = idFile;
    }
    
    @Nullable
    @Override
    public Object[] getId() {
        return new Object[]{idCourse,idFile};
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
