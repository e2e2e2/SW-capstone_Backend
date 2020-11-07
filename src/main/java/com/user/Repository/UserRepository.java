package com.user.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.user.Entity.User;
public interface UserRepository extends JpaRepository<User, Integer>{
}
