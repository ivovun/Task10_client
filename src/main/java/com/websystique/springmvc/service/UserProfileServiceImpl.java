package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.UserProfile;
import org.springframework.web.client.RestTemplate;


@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
	private final RestTemplate restTemplate;

	@Value("${rest_server_api_url}")
	private String restServerUrl;

	@Autowired
	public UserProfileServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.basicAuthentication("my_rest_user", "my_pass").build();
	}
	
	public UserProfile findById(long id) {
		return restTemplate.getForObject(restServerUrl+"profiles/"+id, UserProfile.class);
	}
 }
