package com.jwts.JavaSecurity;

import com.jwts.JavaSecurity.models.AuthenticateRequest;
import com.jwts.JavaSecurity.models.AuthenticationResponse;
import com.jwts.JavaSecurity.models.Clients;
import com.jwts.JavaSecurity.repo.ClientRepo;
import com.jwts.JavaSecurity.services.MyUserDetailsService;
import com.jwts.JavaSecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Statement;
import java.util.List;

@RestController
public class HelloResource {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping( "/hello" )
    public String hello() {
        return "Hello World";
    }

    @PostMapping(value = "/add")
    public List<Clients> persist(@RequestBody final Clients clients) {
        clients.setPass(passwordEncoder.encode(clients.getPass()));
        clientRepo.save(clients);
//        System.out.println("-----------" + clientRepo.findByName(clients.getName()));
        return clientRepo.findAll();
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticateRequest.getUsername(),
                            authenticateRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                authenticateRequest.getUsername()
        );

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
