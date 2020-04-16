package com.jwts.JavaSecurity.repo;

import com.jwts.JavaSecurity.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Clients, Integer> {
    List<Clients> findByName(String name);
}
