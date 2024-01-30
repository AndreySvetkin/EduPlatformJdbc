
package ru.svetkin.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Course;
import ru.svetkin.model.CourseStatus;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.Theme;
import ru.svetkin.model.ThemeUser;
import ru.svetkin.repository.CourseUserRepo;
import ru.svetkin.util.Util;

@Service
public class CourseUserService {
    
    @Autowired
    private CourseUserRepo courseUserRepo;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ThemeService themeService;
    
    @Autowired
    private ThemeUserService themeUserService;
    
    @Autowired
    private Util util;
    
    public Response<CourseUser> enroll(CourseUser courseUser){       
        try{
            if (null!=courseUserRepo.findByIdUserAndIdCourse(  
                courseUser.getIdUser(), courseUser.getIdCourse())){
                //System.out.println(courseUser);
                return new ResponseService(ServiceStatus.AlreadyExist);
            }
            //System.out.println(courseUser);
            courseUser.setStatus(CourseStatus.Executing);
            courseUser.setDateEnroll(new Date());
            courseUserRepo.save(courseUser);
            //System.out.println(courseUser);
            courseService.updateCourseWeight(courseUser.getIdCourse());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(ServiceStatus.Successful) ;
    }
    
    public Response<CourseStatus> complete(long idUser,long idCourse){
        List<Theme> themes=null;
        ThemeUser temp=null;
        CourseUser courseUser=null;
        
        try{
            
            courseUser=courseUserRepo.findByIdUserAndIdCourse(idUser, idCourse);
            
            if (courseUser.getStatus().equals(CourseStatus.Complete)){
                return new ResponseService(CourseStatus.Complete,ServiceStatus.Successful);
            }
            
            themes=themeService.findByIdCourse(idCourse).get();
            
            for(Theme theme:themes){
                temp=themeUserService.findByIdUserAndIdTheme(idUser, theme.getId()).get();
                if (temp==null){
                    return new ResponseService(CourseStatus.Executing,ServiceStatus.Successful);
                }
            }            
            
            courseUser.setStatus(CourseStatus.Complete);
            courseUser.setDateComplete(new Date());
            update(courseUser);
            
            courseService.updateCourseWeight(idCourse);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(CourseStatus.Complete,ServiceStatus.Successful);
    }
    
    public Response<CourseStatus> isComplete(CourseUser courseUser){    
        CourseUser courseUserDB=null;
        //System.out.println("{"+idUser+","+idTheme+","+idCourse+"}");
        try{
            courseUserDB=courseUserRepo.findByIdUserAndIdCourse(courseUser.getIdUser(), courseUser.getIdCourse());
            if (courseUserDB==null){
                return new ResponseService(CourseStatus.Executing,ServiceStatus.NotExist);
            }
            if (courseUserDB.getStatus().equals(CourseStatus.Complete)){
                return new ResponseService(CourseStatus.Complete,ServiceStatus.Successful);
            }           
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(CourseStatus.Executing,ServiceStatus.Successful);
    }
    
     public Response<Boolean> isEnroll(CourseUser courseUser){ 
        CourseUser courseUserDB=null; 
        try{
            courseUserDB=courseUserRepo.findByIdUserAndIdCourse(
                courseUser.getIdUser(),
                courseUser.getIdCourse());
            if (courseUserDB!=null){
                return new ResponseService(true,ServiceStatus.Successful);
            }
        }catch(Exception ex){
            return new ResponseService(false,ServiceStatus.SQLException);
        }
        return new ResponseService(false,ServiceStatus.Successful) ;
    }
    
    public Response<CourseUser> update(CourseUser courseUser){
        CourseUser courseUserDB=null;
        try{
            courseUserDB=courseUserRepo.findByIdUserAndIdCourse(
                courseUser.getIdUser(), 
                courseUser.getIdCourse());
            if (null==courseUserDB){
                return new ResponseService(ServiceStatus.NotExist);
            }
            
            courseUserRepo.deleteByIdUserAndIdCourse(courseUser.getIdUser(), courseUser.getIdCourse());
            courseUserRepo.save(courseUser);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(ServiceStatus.Successful) ;
    }
    
    public Response<List<CourseUser>> findByIdUser(long idUser){
        List<CourseUser> courseUsers=null;
        try{
            courseUsers=courseUserRepo.findByIdUser(idUser);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseUsers,ServiceStatus.Successful) ;
    }
    
//    public Response<List<CourseUser>> findByIdCourse(int idCourse){ 
//        List<CourseUser> courseUsers=null;
//        try{
//            courseUsers=courseUserRepo.;
//        }catch(Exception ex){
//            return new ResponseService(ServiceStatus.SQLException);
//        }
//        return new ResponseService(courseUsers,ServiceStatus.Successful) ;
//    }
    
    public Response<List<Long>> findByIdCourseWithoutUser(long idCourse,long idUser){
        List<Long> users=null;
        try{
            users=courseUserRepo.findByIdCourseWithoutUser(idCourse,idUser);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(users,ServiceStatus.Successful);
    }
    
    public Response<List<Long>> findByIdCategoryAndIdUser(long idCategory,long idUser){
        List<Long> favoriteCourses=null;
        try{
            favoriteCourses=courseUserRepo.findByIdCategoryAndIdUser(idCategory,idUser);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(favoriteCourses,ServiceStatus.Successful) ;
    }
    
    public Response<Long> findExecCountByIdCourse(long idCourse){
        long execCount;
        try{
            execCount=courseUserRepo.findExecCountByIdCourse(idCourse);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(execCount,ServiceStatus.Successful) ;
    }
    
    public Response<Long> findCompleteCountByIdCourse(long idCourse){
        long completeCount;
        try{
            completeCount=courseUserRepo.findCompleteCountByIdCourse(idCourse);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(completeCount,ServiceStatus.Successful) ;
    }
    
    public Response<CourseUser> findByIdUserAndIdCourse(long idUser,long idCourse){
        CourseUser courseUserDB=null;
        try{
            courseUserDB=courseUserRepo.findByIdUserAndIdCourse(idUser, idCourse);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseUserDB,ServiceStatus.Successful) ;
    }

}
