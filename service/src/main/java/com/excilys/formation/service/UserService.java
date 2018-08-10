package com.excilys.formation.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.model.User;
import com.excilys.formation.persistence.UserDaoHibernate;

@Service
public class UserService implements UserDetailsService {

	
	@Autowired
    private UserDaoHibernate userDaoHibernate;

    

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Objects.requireNonNull(name);
        return userDaoHibernate.findUserWithName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}