
package ru.svetkin.controller;

import com.google.gson.Gson;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetkin.model.Course;
import ru.svetkin.model.CourseStatus;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.Response;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.service.CourseService;
import ru.svetkin.service.CourseUserService;


@RestController
@RequestMapping("/course")
public class CourseController{
    @Autowired
    CourseUserService courseUserService;
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    Gson gson;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/enroll",method=RequestMethod.POST)
    public ResponseEntity enroll(@RequestBody String request) {
        HttpStatus hs;
        CourseUser courseUser=gson.fromJson(request, CourseUser.class);
        Response<CourseUser> res=courseUserService.enroll(courseUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}/isEnroll",method=RequestMethod.GET)
    public ResponseEntity isEnroll(@RequestBody String request) {
        HttpStatus hs;
        CourseUser courseUser=gson.fromJson(request, CourseUser.class);
        Response<Boolean> res=courseUserService.isEnroll(courseUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}/isComplete",method=RequestMethod.GET)
    public ResponseEntity isComplete(@RequestBody String request) {
        HttpStatus hs;
        CourseUser courseUser=gson.fromJson(request, CourseUser.class);
        Response<CourseStatus> res=courseUserService.isComplete(courseUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get().name(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/famous",method=RequestMethod.GET)
    public ResponseEntity famous(@RequestBody String request){
        HttpStatus hs;
        Long idUser=Long.parseLong(request);
        Response<List<Course>> res=courseService.famous(idUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/recoms",method=RequestMethod.GET)
    public ResponseEntity recoms(@RequestBody String request){
        HttpStatus hs;
        Long idUser;
        try{
            idUser=Long.parseLong(request);
        }catch(Exception ex){
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>("parseIntError",hs);
        }
        Response<List<Course>> res=courseService.recoms(idUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/add",method=RequestMethod.POST)
    public ResponseEntity add(@RequestBody String request) {
        HttpStatus hs;
        Course course=gson.fromJson(request, Course.class);
        Response<Course> res=courseService.add(course);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(course,hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}",method=RequestMethod.GET)
    public ResponseEntity courseById(@PathVariable String id) {
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        Response<Course> res=courseService.findById(idCourse);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}/get",method=RequestMethod.GET)
    public ResponseEntity courseWithThemes(@PathVariable String id) {
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        Response<Course> res=courseService.getCourseWithThemes(idCourse);
        if (res.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.DELETE)
    @RequestMapping(path = "/{id}/delete",method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        Response<Course> res=courseService.delete(idCourse);
        if (res.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.PATCH)
    @RequestMapping(path = "/{id}/update",method=RequestMethod.PATCH)
    public ResponseEntity update(@RequestBody String request) {
        HttpStatus hs;
        Course course=gson.fromJson(request, Course.class);
        Response<Course> res=courseService.update(course);
        if (res.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(res.get(),hs);
    }
}
