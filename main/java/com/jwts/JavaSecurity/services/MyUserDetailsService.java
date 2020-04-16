package com.jwts.JavaSecurity.services;

import com.jwts.JavaSecurity.models.Clients;
import com.jwts.JavaSecurity.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        List<Clients> person = clientRepo.findByName(userName);
//        if (passwordEncoder.matches(loadUserByUsername(userName).getPassword(), person.get(0).getPass())) {
//            return new User(person.get(0).getName(), loadUserByUsername(userName).getPassword(), new ArrayList<>());
//        }
//        System.out.println("------------------------------" + loadUserByUsername(userName).getPassword());
        return new User(person.get(0).getName(), person.get(0).getPass(), new ArrayList<>());
    }
}
