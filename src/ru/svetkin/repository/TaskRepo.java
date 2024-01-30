
package ru.svetkin.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.Task;

import java.util.List;

@Repository
public interface TaskRepo extends ListCrudRepository<Task,Long>{
    List<Task> findByIdTheme(long idTheme);
}
