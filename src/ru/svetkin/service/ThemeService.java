
package ru.svetkin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Course;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.Theme;
import ru.svetkin.repository.CourseRepo;
import ru.svetkin.repository.ThemeRepo;
import ru.svetkin.util.Util;

@Service
public class ThemeService {
    @Lazy
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ThemeRepo themeRepo;
    
    @Autowired
    private Util util;
    
    
    
    public Response<Theme> add(Theme theme){
        Theme themeDB=null;
        try{
            if (theme.equals(themeRepo.findByNameAndIdCourse(theme.getName(),theme.getIdCourse())))
                return new ResponseService(ServiceStatus.AlreadyExist); 
            theme.setDateCreate(new Date());
            themeDB=themeRepo.save(theme);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException); 
        }
        return new ResponseService(themeDB,ServiceStatus.Successful);
    }
    
    public Response<Theme> deleteById(long id){
        try{
            themeRepo.deleteById(id);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.NotExist);
        }
        return new ResponseService(ServiceStatus.Successful);
    }
    
    public Response<Theme> findById(long id){
        Theme theme=null;
        try{
            theme=themeRepo.findById(id).get();
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.NotExist);
        }
        return new ResponseService(theme,ServiceStatus.Successful);
    }
    
    public Response<Theme> getThemeWithTasks(long id){
        Theme theme=null;
        try{
            theme=themeRepo.findById(id).get();
            theme.setTasks(taskService.findByIdTheme(id).get());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(theme,ServiceStatus.Successful);
    }
    
      public Response<List<Theme>> getThemesByIdTheme(long idTheme){
        List<Theme> themes=new ArrayList<>();
        long idCourse;
        try{
            idCourse=themeRepo.findById(idTheme)
                .get()
                .getIdCourse();
            themes=themeRepo.findByIdCourse(idCourse);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(themes,ServiceStatus.Successful);
    }

    public Response<List<Theme>> findByIdCourse(long id){
        List<Theme> themes=null;
        try{
            themes=themeRepo.findByIdCourse(id);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.NotExist);
        }
        return new ResponseService(themes,ServiceStatus.Successful);
    }

}
