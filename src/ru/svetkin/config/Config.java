
package ru.svetkin.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.unit.DataSize;
import ru.svetkin.model.Course;
import ru.svetkin.model.DegreeHit;
import ru.svetkin.model.courseDegreeHit;
import ru.svetkin.util.Util;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    Gson gson(){
        return new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    }
    @Bean
    Util util(){
        return new Util();
    }
    
    @Bean
    DegreeHit<Course> courseDegreeHit(){
        return new courseDegreeHit();
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofGigabytes(1));
        factory.setMaxRequestSize(DataSize.ofGigabytes(1));
        return factory.createMultipartConfig();
    }
}
