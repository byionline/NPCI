package com.ncpi.bank.security.services;

import com.ncpi.bank.mongo.MongoConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncpi.bank.models.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MongoConnector mongoConnector;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = mongoConnector.findOneByUserId(username,User.class);
		if(user==null)
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		return UserDetailsImpl.build(user);
	}

}
