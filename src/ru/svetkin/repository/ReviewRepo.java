
package ru.svetkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.Course;
import ru.svetkin.model.Review;

@Repository
public interface ReviewRepo extends ListCrudRepository<Review,Object[]>{
    @Modifying
    Review save(Review review);
    
    List<Review> findByIdCourse(long idCourse);
    
    Review findByIdUserAndIdCourse(long idUser,long idCourse);
    
    @Modifying
    @Query("update course "
            + "set rating=(select avg(grade) "
            + "from review) "
            + "where course.id=:idCourse")
    void updateCourseRating(long idCourse);
}
