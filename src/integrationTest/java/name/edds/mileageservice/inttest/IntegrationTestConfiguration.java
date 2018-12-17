package name.edds.mileageservice.inttest;

/*
   Configuration for integration testing
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableAutoConfiguration(exclude={name.edds.mileageservice.MileageConfiguration.class})
@EnableAutoConfiguration()
@ComponentScan(basePackages = "name.edds.mileageservice.inttest")
public class IntegrationTestConfiguration implements WebMvcConfigurer {


}

