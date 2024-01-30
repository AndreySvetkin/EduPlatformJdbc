
package ru.svetkin.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.Theme;
import ru.svetkin.model.ThemeUser;
import ru.svetkin.repository.CourseUserRepo;
import ru.svetkin.repository.ThemeUserRepo;
import ru.svetkin.util.Util;

@Service
public class ThemeUserService {
    @Lazy
    @Autowired
    private CourseUserService courseUserService;

    @Lazy
    @Autowired
    private ThemeService themeService;
    
    @Autowired
    private ThemeUserRepo themeUserRepo;
    
    @Autowired
    private Util util;
    
    
    public Response<Boolean> completed(ThemeUser themeUser){
        Theme theme=null;
        try{
            theme=themeService.findById(themeUser.getIdTheme()).get();
            if (null!=themeUserRepo.findByIdUserAndIdTheme(
                themeUser.getIdUser(), themeUser.getIdTheme())){
                return new ResponseService(ServiceStatus.AlreadyExist);
            }
            themeUser.setDateComplete(new Date());
            themeUserRepo.save(themeUser);
            courseUserService.complete(
                themeUser.getIdUser(),
                theme.getIdCourse());
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(ServiceStatus.Successful) ;
    }
    
    public Response<Boolean> isCompleted(ThemeUser themeUser){
        try{
            if (null!=themeUserRepo.findByIdUserAndIdTheme(
                themeUser.getIdUser(), themeUser.getIdTheme())){
                return new ResponseService(true,ServiceStatus.AlreadyExist);
            }
        }catch(Exception ex){
            return new ResponseService(false,ServiceStatus.SQLException);
        }
        return new ResponseService(false,ServiceStatus.Successful) ;
    }
    
     public Response<List<ThemeUser>> findByIdUser(long idUser){
        List<ThemeUser> themeUsers=null;
        try{
            themeUsers=themeUserRepo.findByIdUser(idUser);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(themeUsers,ServiceStatus.Successful) ;
    }
    
     public Response<ThemeUser> findByIdUserAndIdTheme(long idUser,long idTheme){
        ThemeUser themeUserDB=null;
        try{
            themeUserDB=themeUserRepo.findByIdUserAndIdTheme(idUser, idTheme);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(themeUserDB,ServiceStatus.Successful) ;
    }
}
