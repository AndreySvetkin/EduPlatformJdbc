
package ru.svetkin.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;
import ru.svetkin.model.*;
import ru.svetkin.repository.CourseRepo;
import ru.svetkin.util.Util;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.util.Date;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class CourseService {
    
    private static final double COEF_VALUE=10;
    
    
    @Lazy
    @Autowired
    private CourseUserService courseUserService;
    
    @Autowired
    private ThemeService themeService;
    
    @Autowired
    private CourseRepo courseRepo;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private Util util;
    
    public Response<List<Course>> famous(long idUser){
        List<Course> courses=new ArrayList<>();
        try{
            courses=courseRepo.findFamousCourses();
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courses,ServiceStatus.Successful);
    }
    
    public Response<List<Course>> recoms(long idUser){
        List<Long> recomIdCourses=new ArrayList<>();
        List<Course> recomCourses=new ArrayList<>();
        List<Long> favoriteCourses=null;
        List<Long> categories=null;
        List<Long> tempCourses=null;
        List<Long> tempUsers=null;
        Long favoriteCategory;
        try{
            categories=categoryService
                .findFavoriteCategoriesByIdUser(idUser)
                .get();
            
            if (categories==null && categories.isEmpty()){
                return new ResponseService(recomCourses,ServiceStatus.NotExist);
            }
            
            favoriteCategory=categories.get(0);
            
            //System.out.println(favoriteCategory);
            
            favoriteCourses=courseUserService
                .findByIdCategoryAndIdUser(favoriteCategory, idUser)
                .get();
            
            //System.out.println(favoriteCourses);
            
            for(Long idCourse:favoriteCourses){
                tempUsers=courseUserService
                    .findByIdCourseWithoutUser(idCourse, idUser)
                    .get();
                //System.out.println(tempUsers);
                for(Long idUserTemp:tempUsers){
                    tempCourses=courseUserService
                        .findByIdCategoryAndIdUser(favoriteCategory,idUserTemp)
                        .get();
                    recomIdCourses.addAll(tempCourses);
                }
            }
            
            recomIdCourses=recomIdCourses.stream()
                .distinct()
                .toList();
            
            for(Long idCourse:recomIdCourses){
                recomCourses.add(courseRepo
                    .findById(idCourse)
                    .get());
            }
            //System.out.println(recomCourses);
            
            recomCourses.stream()
                .sorted(Comparator.comparing(course->course.getWeight()))
                .close();
            
            //System.out.println(recomCourses);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(recomCourses,ServiceStatus.Successful);
    }
    
    public Response<Course> add(Course course){    
        Course courseDB=null;
        try{
            if (course.equals(courseRepo.findByName(course.getName())))
                return new ResponseService(ServiceStatus.AlreadyExist); 
            course.setDateCreated(new Date());
            courseDB=courseRepo.save(course);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseDB,ServiceStatus.Successful);
    }

    public Response<List<Course>> findByParams(String name,long idCategory,long idAuthor){
        List<Course> courses=null;
        try{
            courses=courseRepo.findByParams(name,idCategory,idAuthor);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courses,ServiceStatus.Successful);
    }
    
    public Response<Course> findByName(String name){
        Course course=null;
        try{
            course=courseRepo.findByName(name);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(course,ServiceStatus.Successful);
    }
    
    public Response<Course> findById(long idCourse){
        Course course=null;
        try{
            course=courseRepo.findById(idCourse).get();
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(course,ServiceStatus.Successful);
    }
    
    public Response<Course> getCourseWithThemes(long idCourse){
        Course course=null;
        try{
            course=courseRepo.findById(idCourse).get();
            course.setThemes(themeService.findByIdCourse(idCourse).get());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(course,ServiceStatus.Successful);
    }
    
    public Response<List<Course>> findCoursesByIdAuthor(long idUser){
        List<Course> courses=null;
        
        try{            
            courses=courseRepo.findByIdAuthor(idUser);            
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        
        return new ResponseService(courses,ServiceStatus.Successful);
    }
    
    public Response<List<Course>> findByUserLearn(long idUser){
        List<Course> courses=new ArrayList<>();
        List<CourseUser> userCourses=null;
        try{
            userCourses = courseUserService.findByIdUser(idUser).get();
            for(CourseUser cu:userCourses){
                courses.add(courseRepo.findById((long)cu.getIdCourse()).get());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courses,ServiceStatus.Successful);
    }

    public Response<Course> update(Course course){
        Course courseDB=null;
        try{
            courseDB=courseRepo.findById((long)course.getId()).get();
            if (courseDB==null) {
                return new ResponseService(ServiceStatus.NotExist);
            }
            courseRepo.save(course);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseDB,ServiceStatus.Successful);
    }

    public Response<Course> delete(long idCourse){
        Course courseDB=null;
        try{
            courseDB=courseRepo.findById(idCourse).get();
            if (courseDB==null) {
                return new ResponseService(ServiceStatus.NotExist);
            }
            courseRepo.deleteById(idCourse);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseDB,ServiceStatus.Successful);
    }

    public Response<Double> updateCourseWeight(long idCourse) {
        double weight;
        long countExec,countComplete;
        Course course=null;
        try{
            course=courseRepo.findById(idCourse).get();
            
            countExec=courseUserService
                .findExecCountByIdCourse(course.getId())
                .get();
            
            countComplete=courseUserService
                .findCompleteCountByIdCourse(course.getId())
                .get();
            
            weight=countExec*COEF_VALUE+countComplete*course.getRating();

            course.setWeight(weight);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        
        return new ResponseService(course,ServiceStatus.Successful);
    }


}
