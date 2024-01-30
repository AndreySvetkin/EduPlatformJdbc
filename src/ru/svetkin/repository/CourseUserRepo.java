
package ru.svetkin.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.CourseUser;

@Repository
public interface CourseUserRepo extends ListCrudRepository<CourseUser,Object[]>{

    List<CourseUser> findByIdUser(long idUser);
    
    @Query("select count(id_user) " +
        "from course_user " +
        "where status='Executing'")
    Long findExecCountByIdCourse(long idUser);
    
    @Query("select count(id_user) " +
        "from course_user " +
        "where status='Complete'")
    Long findCompleteCountByIdCourse(long idUser);
   
    @Query("select id_course "+
	"from course_user join course "+
            "on Course_User.id_Course=Course.id "+
	"where id_category=:idCategory and id_User=:idUser")
    List<Long> findByIdCategoryAndIdUser(long idCategory,long idUser);
    
    @Query("select id_user " +
        "from course_user " +
        "where id_user!=:idUser")
    List<Long> findByIdCourseWithoutUser(long idCourse,long idUser);
    
    CourseUser findByIdUserAndIdCourse(long idUser,long idCourse);
    
    @Modifying
    @Query("delete course_user "+
        "where id_User=:idUser and id_Course=:idCourse")
    void deleteByIdUserAndIdCourse(long idUser,long idCourse);
    
    
}
