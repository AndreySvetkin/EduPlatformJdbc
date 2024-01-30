
package ru.svetkin.service;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Course;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.Task;
import ru.svetkin.model.Theme;
import ru.svetkin.model.ThemeUser;
import ru.svetkin.repository.TaskRepo;
import ru.svetkin.util.Util;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ThemeUserService themeUserService;
    @Autowired
    private Util util;
    
    public Response<Task> add(Task task){       
        Task taskDB=null;
        try{
            taskDB=taskRepo.save(task);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(taskDB,ServiceStatus.Successful) ;
    }
    
    public Response<Task> delete(long id){
        try{
            taskRepo.deleteById(id);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(ServiceStatus.Successful) ;
    }
    
    public Response<List<Task>> findByIdTheme(long id){
        List<Task> tasks=null;
        try{
            tasks=taskRepo.findByIdTheme(id);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(tasks,ServiceStatus.Successful) ;
    }
    
    public Response<List<Task>> check(long idUser,List<Task> check){
        if (check.isEmpty()){
            return new ResponseService(ServiceStatus.AnswersIncorrect);
        }
        try{
            for(Task task:check){
                if (!task.equals(taskRepo.findById(task.getId()).get()))
                    return new ResponseService(ServiceStatus.AnswersIncorrect);
            }
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        ThemeUser tu=new ThemeUser();
        tu.setIdUser(idUser);
        tu.setIdTheme(check.get(0).getIdTheme());
        themeUserService.completed(tu);
        return new ResponseService(ServiceStatus.Successful) ;
    }
    
//    public Response<Integer> getUserLearnTask(Task task){
//        Theme theme=null;
//        Course course=null;
//        try{
//            theme=themeService.findById(task.getIdTheme()).get();
//            course=courseService.
//        }catch(Exception ex){
//        }
//    }
}
