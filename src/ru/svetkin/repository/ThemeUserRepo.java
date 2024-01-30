
package ru.svetkin.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.svetkin.model.ThemeUser;

import java.util.List;

public interface ThemeUserRepo  extends ListCrudRepository<ThemeUser,Object[]>{
    
    ThemeUser findByIdUserAndIdTheme(long idUser,long idTheme);
    
    List<ThemeUser> findByIdUser(long idUser);
}
