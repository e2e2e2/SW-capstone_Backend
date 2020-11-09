package com.SWcapstone_Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SWcapstone_Backend.model.User;
public interface UserRepository extends JpaRepository<User, Integer>{
}
