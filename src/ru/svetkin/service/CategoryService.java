
package ru.svetkin.service;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Category;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.model.User;
import ru.svetkin.repository.CategoryRepo;
import ru.svetkin.util.Util;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private Util util;

    public Response<Category> add(Category category){
        Category categoryDB=null;
        try{
            categoryDB=categoryRepo.findByName(category.getName());
            if (categoryDB!=null){
                return new ResponseService(ServiceStatus.AlreadyExist);
            }
            categoryRepo.save(category);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(category,ServiceStatus.Successful);
    }
    public Response<List<Category>> findAll(){
        List<Category> categories=null; 
        try{
            categories=categoryRepo.findAll();
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }       
        return new ResponseService(categories,ServiceStatus.Successful);
    }
    
    public Response<List<Long>> findFavoriteCategoriesByIdUser(long idUser){
        List<Long> categories=null; 
        try{
            categories=categoryRepo.findFavoriteCategoriesByIdUser(idUser);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }       
        return new ResponseService(categories,ServiceStatus.Successful);
    }
    
    public Response<Category> findById(long idCategory){
        Category category=null; 
        try{
            category=categoryRepo.findById(idCategory).get();
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }       
        return new ResponseService(category,ServiceStatus.Successful);
    }

    public Response<Category> update(Category category){
        Category categoryDB=null;
        try{
            categoryDB=categoryRepo.findById(category.getId()).get();
            if (categoryDB==null){
                return new ResponseService(ServiceStatus.NotExist);
            }
            categoryRepo.save(category);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(category,ServiceStatus.Successful);
    }

    public Response<Category> delete(long id){
        Category categoryDB=null;
        try{
            categoryDB=categoryRepo.findById(id).get();
            if (categoryDB==null){
                return new ResponseService(ServiceStatus.NotExist);
            }
            categoryRepo.deleteById(id);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(ServiceStatus.Successful);
    }
}
