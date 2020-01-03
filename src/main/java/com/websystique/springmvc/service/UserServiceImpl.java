package com.websystique.springmvc.service;

import java.util.List;
import java.util.Optional;

import com.websystique.springmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.User;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class UserServiceImpl implements UserService{
//	@Value("${user_name_for_rest_authentication}")
//	private String user_name;
//
//	@Value("${password_rest_authentication}")
//	private String password;


	private PasswordEncoder passwordEncoder;

	private UserRepository userRepository;

	private final RestTemplate restTemplate;

	@Value("${rest_server_api_url}")
	private String restServerUrl;

	@Autowired
	@Lazy
	public UserServiceImpl(PasswordEncoder passwordEncoder,
						   UserRepository userRepository,
						   RestTemplateBuilder restTemplateBuilder
						   ) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.restTemplate = restTemplateBuilder.basicAuthentication("my_rest_user", "my_pass")
				.build();

	}

	public User findById(long id) {
		return this.restTemplate.getForObject(restServerUrl+"users/"+id, User.class);
	}

	public User findBySsoId(String ssoId) {
		return this.restTemplate.getForObject(restServerUrl+"users/"+ssoId, User.class);
	}

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		Optional<User> entity = userRepository.findById(user.getId());
		if(entity.isPresent()){
			User ent = entity.get();
			ent.setSsoId(user.getSsoId());
			if(!user.getPassword().equals(ent.getPassword())){
				ent.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			ent.setFirstName(user.getFirstName());
			ent.setLastName(user.getLastName());
			ent.setEmail(user.getEmail());
			ent.setUserProfiles(user.getUserProfiles());
		}
	}

	public void deleteUserBySsoId(String sso) {
		userRepository.deleteBySsoId(sso);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public boolean isUserSSOUnique(Long id, String sso) {
		User user = findBySsoId(sso);
		return ( user == null || ((id != null) && (user.getId().equals(id))));
	}

	@Override
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		return userRepository.findBySsoId(ssoId);
	}
}
