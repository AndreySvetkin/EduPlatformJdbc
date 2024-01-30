
package ru.svetkin.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.Course;


@Repository
public interface CourseRepo extends ListCrudRepository<Course,Long>{
    
    Course findByName(String name);

    List<Course> findByIdAuthor(long idAuthor);
    
    @Query("select top(6) id,id_Category,id_Author,name,duration,description,rating,weight "+
        "from Course "+
        "order by weight desc")
    List<Course> findFamousCourses();
    
    @Query("select id,id_Category,id_Author,name,duration,description,rating,weight "+
        "from Course "+
        "where name=:name or id_category=:idCategory or id_Author=:idAuthor "+
        "order by weight desc")
    List<Course> findByParams(String name,long idCategory,long idAuthor);
    
//    @Query("select id,id_Category,id_Author,name,duration,description,rating,weight "+
//        "from Course "+
//        "order by weight desc")
//    List<Course> findFamousCourses();
}
