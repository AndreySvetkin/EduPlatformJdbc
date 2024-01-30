
package ru.svetkin.repository;

import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.CourseFile;
import ru.svetkin.model.FileInfo;

@Repository
public interface CourseFileRepo   extends ListCrudRepository<CourseFile,Object[]>{
    
    
    List<CourseFile> findByIdCourse(long idCourse);
}
