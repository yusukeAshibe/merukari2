package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.User;

/**
 * EmailがDBと一致していればログイン権限を与える
 * @author ashibe
 *
 */
@Service
public class UDSImplement implements UserDetailsService {
	
	@Autowired
	private com.example.repository.UserRepository UserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		User user = UserRepository.findByEmail(email);

	Collection<GrantedAuthority> authorityList = new ArrayList<>();
	authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
	System.out.println(authorityList);
	
	return new LoginUser(user,authorityList);

	}
}
