package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capstone.helper.model.User;
public interface UserRepository extends JpaRepository<User, Integer>{
}
