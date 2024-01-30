
package ru.svetkin.controller;

import com.google.gson.Gson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetkin.model.Course;
import ru.svetkin.model.Response;
import ru.svetkin.model.Review;
import ru.svetkin.model.ServiceStatus;
import ru.svetkin.service.ReviewService;

@RestController
@RequestMapping("/course/{id}/review")
public class ReviewController {
    
    @Autowired
    private Gson gson;
            
    @Autowired
    private ReviewService reviewService;
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
    @RequestMapping(path = "/add",method=RequestMethod.POST)
    public ResponseEntity estimate(@RequestBody String request){
        HttpStatus hs;
        Review review=gson.fromJson(request, Review.class);
        Response<Review> res=reviewService.estimate(review);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.CREATED;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/isEstimate",method=RequestMethod.GET)
    public ResponseEntity isEstimate(@RequestBody String request) {
        HttpStatus hs;
        Review review=gson.fromJson(request, Review.class);
        Response<Boolean> res=reviewService.isEstimate(review);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/all",method=RequestMethod.GET)
    public ResponseEntity courseReviews(@PathVariable String id){
        HttpStatus hs;
        Long idCourse=Long.parseLong(id);
        Response<List<Review>> res=reviewService.findByIdCourse(idCourse);
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
    
    @CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
    @RequestMapping(path = "/get",method=RequestMethod.GET)
    public ResponseEntity findById(@RequestBody String request){
        HttpStatus hs;
        Review review=gson.fromJson(request, Review.class);
        Response<Review> res=reviewService.findById(
                review.getIdUser(),
                review.getIdCourse());
        if (res.status().equals(ServiceStatus.Successful)) 
            hs=HttpStatus.OK;
        else
            hs=HttpStatus.CONFLICT;
        return new ResponseEntity<>(res.get(),hs);
    }
}
