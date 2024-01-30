
package ru.svetkin.service;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Course;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.User;
import ru.svetkin.repository.CourseRepo;
import ru.svetkin.repository.UserRepo;
import ru.svetkin.util.Util;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Util util;        
    
    
    public Response<User> login(User user){
        User userDB=null;
        try{
            userDB=userRepo.findByEmail(user.getEmail());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        if (userDB==null) return new ResponseService(ServiceStatus.NotExist);
        if (!userDB.equals(user)){
            return new ResponseService(ServiceStatus.PasswordIncorrect);
        }
        return new ResponseService(userDB,ServiceStatus.Successful);
    }
    
    public Response<User> register(User user){
        User userDB=null;
        try{
            if (user.equals(userRepo.findByEmail(user.getEmail()))) 
                return new ResponseService(ServiceStatus.AlreadyExist);
            user.setDateRegister(new Date());
            userDB=userRepo.save(user);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(userDB,ServiceStatus.Successful);
    }
    
    public Response<User> findByLogin(String login){
        User user=null;
        try{
            user=userRepo.findByLogin(login);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(user,ServiceStatus.Successful);
    }
    
    public Response<User> findById(long idUser){
        User user=null;
        try{
            user=userRepo.findById(idUser).get();
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(user,ServiceStatus.Successful);
    }
    
    public Response<User> getUser(long id){
        User user=null;
        try{
            user=userRepo.findById(id).get();
            user.setCreateCourses(courseService.findCoursesByIdAuthor(id).get());
            user.setCurrCourses(courseService.findByUserLearn(id).get());
            user.setCompleteCourses(courseService.findByUserLearn(id).get());
            System.out.println(user.getCurrCourses());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(user,ServiceStatus.Successful);
    }
    
    
}
