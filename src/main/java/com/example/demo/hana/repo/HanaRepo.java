package com.example.demo.hana.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.hana.entity.User;


public interface HanaRepo extends JpaRepository<User,Integer>{
}
