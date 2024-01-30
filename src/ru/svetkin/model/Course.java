
package ru.svetkin.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


public class Course {
    @Id
    @Expose
    private long id;
    
    @Expose
    private long idCategory;
    
    @Expose
    private long idAuthor;
    
    @Expose
    private String name;
    
    @Expose
    private long duration;
    
    @Expose
    private String description;
    
    @Expose
    private Date dateCreate;
    
    @Expose
    @Transient
    private List<Theme> themes;
    
    @Expose
    private double rating;
    
    private double weight;
    
    public long getId(){
        return id;
    }
    
    public long getIdCategory(){
        return idCategory;
    }

    public void setIdCategory(long idCategory){
        this.idCategory=idCategory;
    }
    
    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    
    public long getDuration(){
        return duration;
    }

    public void setDuration(long duration){
        this.duration=duration;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    
    public List<Theme> getThemes(){
        return themes;
    }
    public void setThemes(List<Theme> themes){
        this.themes=themes;
    }
    
    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating=rating;
    }
    
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreated(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    
    
    public boolean equals(Object obj){
        if (obj==null) return false;
        if (this==obj) return true;
        if (!(obj instanceof Course)) return false;
        Course course=(Course)obj;
        if (course.getName().equals(this.getName())) return true;
        return false;
    }
}
