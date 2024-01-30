
package ru.svetkin.controller;

import com.google.gson.Gson;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.svetkin.model.CourseFile;
import ru.svetkin.model.Response;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.ThemeFile;
import ru.svetkin.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private Gson gson;

    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/upload/theme",method=RequestMethod.POST)
    public ResponseEntity uploadThemeFile(@RequestPart String id,@RequestPart MultipartFile file){
        HttpStatus hs;
        if (file.isEmpty()){
            hs=HttpStatus.CONFLICT;
            return new ResponseEntity<>("{}",hs);
        }
        Long idTheme=Long.parseLong(id);
        Response<ThemeFile> res=fileService.uploadThemeFile(idTheme,file.getResource());
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/upload/course",method=RequestMethod.POST)
    public ResponseEntity uploadCourseFile(@RequestPart String id,@RequestPart MultipartFile file){
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        if (file.isEmpty()){
            hs=HttpStatus.CONFLICT;
            return new ResponseEntity<>("{}",hs);
        }
        Response<CourseFile> res=fileService.uploadCourseFile(idCourse,file.getResource());
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/{id}",method=RequestMethod.GET)
    public ResponseEntity  download(@PathVariable String id){
        HttpStatus hs;
        Long idFile=Long.parseLong(id);
        Response<InputStreamResource> res=fileService.download(idFile);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/download/theme/{id}",method=RequestMethod.GET)
    public ResponseEntity  downloadThemeFiles(@PathVariable String id){
        HttpStatus hs;
        Long idTheme=Long.parseLong(id);
        Response<List<ThemeFile>> res=fileService.downloadThemeFilesId(idTheme);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        if (res.get().isEmpty()){
            return new ResponseEntity<>("{}",hs);
        }
        return new ResponseEntity<>(res.get().get(0),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/download/course/{id}",method=RequestMethod.GET)
    public ResponseEntity  downloadCourseFiles(@PathVariable String id){
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        Response<List<CourseFile>> res=fileService.downloadCourseFilesId(idCourse);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        if (res.get().isEmpty()){
            return new ResponseEntity<>("{}",hs);
        }
        return new ResponseEntity<>(res.get().get(0),hs);
    }
}
