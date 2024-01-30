
package ru.svetkin.repository;

import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.FileInfo;
import ru.svetkin.model.ThemeFile;

@Repository
public interface ThemeFileRepo   extends ListCrudRepository<ThemeFile,Object[]>{
    
    List<ThemeFile> findByIdTheme(long idTheme);
}
