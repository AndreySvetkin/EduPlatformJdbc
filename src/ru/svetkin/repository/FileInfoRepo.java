
package ru.svetkin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.FileInfo;

@Repository
public interface FileInfoRepo  extends ListCrudRepository<FileInfo,Long>{

}
