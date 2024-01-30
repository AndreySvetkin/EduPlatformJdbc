
package ru.svetkin.model;


public class courseDegreeHit implements DegreeHit<Course>{

    public int calcDegreeHit(Course course, Search search) {
        int degree=0;
        if (search.getIdAuthor()==course.getIdAuthor()) degree++;
        if (search.getIdCategory()==course.getIdCategory()) degree++;
        if (search.getName().equals(course.getName())) degree++;
        return degree;
    }
    
}
