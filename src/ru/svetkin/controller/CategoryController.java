
package ru.svetkin.controller;


import java.util.List;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetkin.model.Category;
import ru.svetkin.model.Response;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.service.CategoryService;


@RestController
@RequestMapping("category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Gson gson;

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/all",method=RequestMethod.GET)
    public ResponseEntity getCategories(@RequestBody String request) {
        HttpStatus hs;
        Response<List<Category>> categories=categoryService.findAll();
        if (categories.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.CREATED;
        else 
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(categories.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}",method=RequestMethod.GET)
    public ResponseEntity categoryById(@PathVariable String id) {
        HttpStatus hs;
        Long idCategory=Long.parseLong(id);
        Response<Category> category=categoryService.findById(idCategory);
        if (category.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(category.get(),hs);
    }

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.PATCH)
    @RequestMapping(path = "/{id}/update",method=RequestMethod.PATCH)
    public ResponseEntity update(@RequestBody String request) {
        HttpStatus hs;
        Category category=gson.fromJson(request, Category.class);
        Response<Category> response=categoryService.update(category);
        if (response.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(response.get(),hs);
    }

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.DELETE)
    @RequestMapping(path = "/{id}/delete",method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        HttpStatus hs;
        Long idCategory=Long.parseLong(id);
        Response<Category> response=categoryService.delete(idCategory);
        if (response.status().equals(ServiceStatus.Successful))
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(response.get(),hs);
    }
}
