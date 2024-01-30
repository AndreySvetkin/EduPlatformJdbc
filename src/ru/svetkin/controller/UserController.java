
package ru.svetkin.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.svetkin.model.Response;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.User;
import ru.svetkin.service.CourseService;
import ru.svetkin.service.UserService;

@RestController
public class UserController {
    
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    Gson gson;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/login",method=RequestMethod.POST)
    public ResponseEntity login(@RequestBody String request) {
        HttpStatus hs;
        User user=gson.fromJson(request, User.class);
        Response<User> res=userService.login(user);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else 
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
        
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/register",method=RequestMethod.POST)
    public ResponseEntity register(@RequestBody String request){
        HttpStatus hs;
        User user=gson.fromJson(request, User.class);
        Response<User> res=userService.register(user);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;     
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/getName",method=RequestMethod.POST)
    public ResponseEntity getName(@RequestBody String request){
        HttpStatus hs;
        Long id=Long.parseLong(request);
        Response<User> res=userService.findById(id);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;     
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/getUser",method=RequestMethod.POST)
    public ResponseEntity getUser(@RequestBody String request){
        HttpStatus hs;
        Long id=Long.parseLong(request);
        Response<User> res=userService.getUser(id);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;     
        return new ResponseEntity<>(res.get(),hs);
    }
}
