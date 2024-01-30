
package ru.svetkin.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.Category;

@Repository
public interface CategoryRepo extends ListCrudRepository<Category,Long>{
    
    @Query("select id_Category "+
	"from course_user join course "+
		"on course_user.id_Course=course.id "+
	"where id_User=:idUser "+
	"group by id_category "+
        "order by count(id_Category) desc")
    List<Long> findFavoriteCategoriesByIdUser(long idUser);

	Category findByName(String name);
}
