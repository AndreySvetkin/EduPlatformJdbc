
package ru.svetkin.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.Theme;
import java.util.List;

@Repository
public interface ThemeRepo extends ListCrudRepository<Theme,Long> {
    
    Theme findByNameAndIdCourse(String name,long idCourse);
            
    List<Theme> findByIdCourse(long idCourse);
}
