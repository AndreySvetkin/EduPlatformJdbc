
package ru.svetkin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.svetkin.model.CourseFile;
import ru.svetkin.model.CourseStatus;
import ru.svetkin.model.CourseUser;
import ru.svetkin.model.FileInfo;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.ThemeFile;
import ru.svetkin.repository.CourseFileRepo;
import ru.svetkin.repository.CourseUserRepo;
import ru.svetkin.repository.FileInfoRepo;
import ru.svetkin.repository.ThemeFileRepo;

@Service
public class FileService {
    
    @Autowired
    private FileInfoRepo fileInfoRepo;
    
    @Autowired
    private CourseFileRepo courseFileRepo;
    
    @Autowired
    private ThemeFileRepo themeFileRepo;
    
    public FileInfo upload(Resource resource) throws Exception{
        FileInfo fileInfo=new FileInfo();
        File file;
        file=new File("src/resources/"+resource.getFilename());
        FileOutputStream output=new FileOutputStream(file);
        output.write(resource.getContentAsByteArray());
        output.close();
        
        fileInfo.setName(file.getName());
        fileInfo.setPath("src/resources/"+file.getName());
        fileInfo=fileInfoRepo.save(fileInfo);
        return fileInfo;
    }
    
    public Response<ThemeFile> uploadThemeFile(long idTheme,Resource resource){
        FileInfo fileInfo;
        ThemeFile themeFile;
        if(!resource.exists()){
            return new ResponseService(ServiceStatus.NotExist);
        }
        try{
            fileInfo=upload(resource);
            themeFile=new ThemeFile();
            themeFile.setIdTheme(idTheme);
            themeFile.setIdFile(fileInfo.getId());
            themeFileRepo.save(themeFile);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(themeFile,ServiceStatus.Successful) ;
    }
    
    public Response<CourseFile> uploadCourseFile(long idCourse,Resource resource){
        FileInfo fileInfo;
        CourseFile courseFile;
        try{
            fileInfo=upload(resource);
            courseFile=new CourseFile();
            courseFile.setIdCourse(idCourse);
            courseFile.setIdFile(fileInfo.getId());
            courseFileRepo.save(courseFile);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseFile,ServiceStatus.Successful) ;
    }
    
    public Response<InputStreamResource> download(long idFile){
        FileInfo fileInfo;
        File file;
        InputStreamResource input;
        try{
            fileInfo=fileInfoRepo
                .findById((long)idFile)
                .get();
            
            file=new File(fileInfo.getPath());
            input=new InputStreamResource(new FileInputStream(file));
            //System.out.println(file.length());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(input,ServiceStatus.Successful) ;
    }
    
     public Response<List<ThemeFile>> downloadThemeFilesId(long idTheme){
        List<ThemeFile> themeFiles=new ArrayList<>();
        try{
            themeFiles=themeFileRepo.findByIdTheme(idTheme);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(themeFiles,ServiceStatus.Successful) ;
    }
     
    public Response<List<CourseFile>> downloadCourseFilesId(long idCourse){
        List<CourseFile> courseFiles=new ArrayList<>();
        try{
            courseFiles=courseFileRepo.findByIdCourse(idCourse);
            //System.out.println(courseFiles);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(courseFiles,ServiceStatus.Successful) ;
    }
}
