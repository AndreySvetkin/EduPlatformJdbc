
package ru.svetkin.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetkin.model.Response;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.Theme;
import ru.svetkin.model.ThemeUser;
import ru.svetkin.service.ThemeService;
import ru.svetkin.service.ThemeUserService;

@RequestMapping("/course/{id}/theme")
@RestController
public class ThemeController {
    @Autowired
    ThemeService themeService;
    @Autowired 
    ThemeUserService themeUserService;
    @Autowired
    Gson gson;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/add",method=RequestMethod.POST)
    public ResponseEntity add(@RequestBody String request) {
        HttpStatus hs;
        Theme theme=gson.fromJson(request, Theme.class);
        Response<Theme> res=themeService.add(theme);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{idTheme}",method=RequestMethod.GET)
    public ResponseEntity themeById(@PathVariable String idTheme) {
        HttpStatus hs;
        Long id=Long.parseLong(idTheme);
        Response<Theme> res=themeService.findById(id);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{idTheme}/get",method=RequestMethod.GET)
    public ResponseEntity themeWithTasks(@PathVariable String idTheme) {
        HttpStatus hs;
        Long id=Long.parseLong(idTheme);
        Response<Theme> res=themeService.getThemeWithTasks(id);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.DELETE)
    @RequestMapping(path = "/{idTheme}/delete",method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String idTheme) {
        HttpStatus hs;
        Long id=Long.parseLong(idTheme);
        Response<Theme> res=themeService.deleteById(id);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/{idTheme}/complete",method=RequestMethod.POST)
    public ResponseEntity complete(@PathVariable String idTheme,@RequestBody String request) {
        HttpStatus hs;
        Long id=Long.parseLong(idTheme);
        Long idUser=Long.parseLong(request);
        ThemeUser themeUser=new ThemeUser();
        themeUser.setIdUser(idUser);
        themeUser.setIdTheme(id);
        Response<Boolean> res=themeUserService.isCompleted(themeUser);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
}
