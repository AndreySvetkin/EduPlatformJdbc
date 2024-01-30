/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.svetkin.controller;

import com.google.gson.Gson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.svetkin.model.Course;
import ru.svetkin.model.Response;
import ru.svetkin.model.Search;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.service.SearchService;


@RestController
public class SearchController {
    @Autowired
    SearchService searchService;
    
    @Autowired
    Gson gson;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/search",method=RequestMethod.GET)
    public ResponseEntity search(@RequestBody String request) {
        HttpStatus hs;
        Search search=gson.fromJson(request, Search.class);
        Response<List<Course>> res=searchService.search(search);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
}
