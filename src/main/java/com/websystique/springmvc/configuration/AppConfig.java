package com.websystique.springmvc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.config.annotation.*;

import com.websystique.springmvc.converter.RoleToUserProfileConverter;

/**
 * Adding @EnableWebMvc annotation to an @Configuration class imports the Spring MVC configuration from
 *To customize the imported configuration, implement the interface WebMvcConfigurer and override individual methods
 *  addFormatters, addResourceHandlers
 */
//@Configuration
public class AppConfig implements WebMvcConfigurer {
//    @Value("${user_name_for_rest_authentication}")
//    private String user_name;
//
//    @Value("${password_rest_authentication}")
//    private String password;

    private RoleToUserProfileConverter roleToUserProfileConverter;

	public AppConfig(RoleToUserProfileConverter roleToUserProfileConverter) {
	    this.roleToUserProfileConverter = roleToUserProfileConverter;
    }

    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values[Roles] to UserProfiles in registration.jsp
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleToUserProfileConverter);
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

//    @Bean
//    RestOperations restTemplateBuilder(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder.basicAuthentication(user_name, password).build();
//    }
}

