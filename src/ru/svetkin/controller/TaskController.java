
package ru.svetkin.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
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
import ru.svetkin.model.Task;
import ru.svetkin.model.User;
import ru.svetkin.service.TaskService;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private Gson gson;
    @Autowired
    private TaskService taskService;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/add",method=RequestMethod.POST)
    public ResponseEntity add(@RequestBody String request) {
        HttpStatus hs;
        Task task=gson.fromJson(request, Task.class);
        Response<Task> res=taskService.add(task);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else 
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/check",method=RequestMethod.GET)
    public ResponseEntity check(@RequestBody String request) {
        HttpStatus hs;
        Map<String,Object> items=convertJsonToMapWithList(request);
        Long idUser=Long.parseLong((String)items.get("idUser"));
        List<Task> lst=(List<Task>)items.get("tasks");
        Response<List<Task>> res=taskService.check(idUser,lst);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else 
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.status(),hs);
    }
    
    private Map<String,Object> convertJsonToMapWithList(String json){
        Map<String,Object> res=new HashMap<>();
        Type itemsMapType=new TypeToken<Map<String,Object>>(){}.getType();
        Map<String,Object> items=gson.fromJson(json,itemsMapType);
        Type itemsListType = new TypeToken<ArrayList<Task>>(){}.getType();
        List<Task> lst=gson.fromJson(items.get("tasks").toString(), itemsListType);
        res.remove("tasks");
        res.put("idUser",items.get("idUser"));
        res.put("tasks", lst);
        return res;
    }
}
