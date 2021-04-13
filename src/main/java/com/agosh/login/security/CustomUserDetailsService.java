package com.agosh.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.repository.LoginUserRepository;
import com.agosh.login.web.rest.errors.ResourceNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	LoginUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {
		LoginUser user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
		if (!UserStatus.ACTIVE.equals(user.getUserStatus())) {
			throw new UsernameNotFoundException("User is inactive with email: " + email);
		}

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(String id) {
		LoginUser user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(user);
	}
}