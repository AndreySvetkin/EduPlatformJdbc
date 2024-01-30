
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

@Component
public class User {
    @Id
    @Expose
    private long id;
    
    @Expose
    private String email;
    
    @Expose
    private String login;
    
    @Expose
    private String password;
    
    @Expose
    private Date dateRegister;
    
    @Expose
    @Transient
    private List<Course> createCourses;
    
    @Expose
    @Transient
    private List<Course> currCourses;
    
    @Expose
    @Transient
    private List<Course> completeCourses;
    
    public long getId(){
        return id;
    }
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
    
    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login=login;
    }
    
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
    
    public List<Course> getCreateCourses(){
        return createCourses;
    }
    public void setCreateCourses(List<Course> createCourses){
        this.createCourses=createCourses;
    }
    
    public List<Course> getCurrCourses(){
        return currCourses;
    }
    public void setCurrCourses(List<Course> currCourses){
        this.currCourses=currCourses;
    }

    public List<Course> getCompleteCourses() {
        return completeCourses;
    }

    public void setCompleteCourses(List<Course> completeCourses) {
        this.completeCourses = completeCourses;
    }

    public Date getDateRegister() {
        return dateRegister;
    }
    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }
    
    
 
    public String toString(){
        return "("+email+","+login+","+password+")";        
    }
    
    public boolean equals(Object obj){
        if (obj==null) return false;
        if (this==obj) return true;
        if (!(obj instanceof User)) return false;
        User user=(User)obj;
        if (user.getEmail().equals(this.getEmail()) && 
            user.getPassword().equals(this.getPassword())) return true;
        return false;
    }
}
