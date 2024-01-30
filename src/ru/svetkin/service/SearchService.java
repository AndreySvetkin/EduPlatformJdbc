
package ru.svetkin.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;
import ru.svetkin.model.Course;
import ru.svetkin.model.DegreeHit;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.Search;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.User;

@Service
public class SearchService {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DegreeHit<Course> courseDegreeHit;
    
    public Response<List<Course>> search(Search search){    
        List<Course> res=new ArrayList<>();
        List<Course> courses;
        List<Integer> hitDegrees;
        Map<Integer,List<Course>> courseHitDegreesMap;
        User author;
        try{
            author=userService.findByLogin(search.getAuthor()).get();
            if (author!=null){
                search.setIdAuthor(author.getId());
            }
            
            courses=courseService.findByParams(
                search.getName(),
                search.getIdCategory(),
                search.getIdAuthor())
                .get();
            
            System.out.println(courses);
            
            courseHitDegreesMap=courses.stream()
                .collect(Collectors.groupingBy((course)->courseDegreeHit.calcDegreeHit(course,search)));
                
            System.out.println(courseHitDegreesMap);
            
            hitDegrees=courseHitDegreesMap
                .keySet()
                .stream()
                .sorted(Comparator.comparing((x)->((Integer)x).intValue()).reversed())
                .toList();
                
            System.out.println(hitDegrees);
            
            for(Integer hitDegree:hitDegrees){
                res.addAll(courseHitDegreesMap.get(hitDegree));
            }
            System.out.println(res);
            
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(res,ServiceStatus.Successful);
    }
    
}
