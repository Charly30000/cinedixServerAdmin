package com.cinedix.server.admin.app;

import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cinedix.server.admin.app.models.service.UploadFileServiceImpl;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/error_403").setViewName("errors/error_403");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations("file:/" + UploadFileServiceImpl.UPLOADS_FOLDER.replace("\\", "/") + "/");
	}
	
}
