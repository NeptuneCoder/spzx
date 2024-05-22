package com.travel.spzx.tour.guide;

//import com.travel.spzx.common.anno.EnableTourUserLoginAuthInterceptor;
//import com.travel.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.travel.spzx.common.anno.EnableUserTokenFeignInterceptor;
import com.travel.spzx.tour.guide.properties.FileServiceProperties;
import com.travel.spzx.tour.guide.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

//@EnableTourUserLoginAuthInterceptor
@EnableUserTokenFeignInterceptor
//@EnableCaching
//@EnableScheduling
@ComponentScan(basePackages = {"com.travel.spzx"})
@SpringBootApplication
@EnableConfigurationProperties(value = {FileServiceProperties.class, UserAuthProperties.class})
public class TourGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourGuideApplication.class, args);
    }
}
