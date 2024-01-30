
package ru.svetkin.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetkin.model.Response;
import ru.svetkin.model.ResponseService;
import ru.svetkin.model.Review;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.repository.ReviewRepo;

@Service
public class ReviewService {
    @Autowired 
    private ReviewRepo reviewRepo;
    
    @Autowired
    private CourseService courseService;
    
    public Response<Review> estimate(Review review){
        Review reviewDB=null;
        Response<Boolean> res;
        try{
            res=isEstimate(review);
            if(res.get()==true){
                return new ResponseService(reviewDB,ServiceStatus.AlreadyExist);
            }
            
            review.setDateCreate(new Date());
            reviewDB=reviewRepo.save(review);
            reviewRepo.updateCourseRating(review.getIdCourse());
            
            courseService.updateCourseWeight(review.getIdCourse());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(reviewDB,ServiceStatus.Successful);
    }
    
    public Response<Boolean> isEstimate(Review review){
        Review reviewDB=null;
        try{
            reviewDB=reviewRepo.findByIdUserAndIdCourse(review.getIdUser(), review.getIdCourse());
            if (reviewDB==null){
                return new ResponseService(false,ServiceStatus.Successful);
            } 
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(true,ServiceStatus.Successful);
    }
    
    public Response<Review> findById(long idUser,long idCourse){
        Review reviewDB=null;
        try{
            reviewDB=reviewRepo.findByIdUserAndIdCourse(idUser,idCourse);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(reviewDB,ServiceStatus.Successful);
    }
    
    public Response<List<Review>> findByIdCourse(long idCourse){
        List<Review> reviews=null;
        try{
            reviews=reviewRepo.findByIdCourse(idCourse);
        }catch(Exception ex){
            return new ResponseService(ServiceStatus.SQLException);
        }
        return new ResponseService(reviews,ServiceStatus.Successful);
    }

}
