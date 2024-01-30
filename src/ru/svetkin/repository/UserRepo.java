
package ru.svetkin.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.svetkin.model.User;

@Repository
public interface UserRepo extends CrudRepository<User,Long>{
    @Query("select id,email,login,password from [user] where [user].email=:email")
    User findByEmail(@Param("email") String email);
    
    @Query("select id from [user] where [user].login=:login")
    User findByLogin(@Param("login") String login);
}
