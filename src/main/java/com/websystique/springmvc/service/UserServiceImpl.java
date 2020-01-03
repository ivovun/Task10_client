package com.websystique.springmvc.service;

import java.util.*;

import com.websystique.springmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.User;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	private final RestTemplate restTemplate;

	@Value("${rest_server_api_url}")
	private String restServerUrl;

	@Autowired
	@Lazy
	public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.basicAuthentication("my_rest_user", "my_pass").build();
	}

	public User findById(long id) {
		return restTemplate.getForObject(restServerUrl+"users/"+id, User.class);
	}

	public User findBySsoId(String ssoId) {
		return restTemplate.getForObject(restServerUrl+"users/byssoid/"+ssoId, User.class);
	}

	public void save(User user) {
 		restTemplate.postForObject(restServerUrl+"save/" , new HttpEntity<>(user), User.class);
 	}

	public void deleteUserBySsoId(String ssoId) {
		restTemplate.delete(restServerUrl+"delete/{id}", ssoId);
	}

	public List<User> findAll() {
		return restTemplate.getForObject(restServerUrl+"/list", List.class);
	}

	public boolean isUserSSOUnique(Long id, String sso) {
		User user = findBySsoId(sso);
		return ( user == null || ((id != null) && (user.getId().equals(id))));
	}

	@Override
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		return findBySsoId(ssoId);
	}
}
