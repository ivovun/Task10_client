package com.websystique.springmvc.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.*;


/**
 * Adding @EnableWebMvc annotation to an @Configuration class imports the Spring MVC configuration from
 * To customize the imported configuration, implement the interface WebMvcConfigurer and override individual methods
 * addFormatters, addResourceHandlers
 */
public class AppConfig implements WebMvcConfigurer {

    public AppConfig() {
    }

    /**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
}

