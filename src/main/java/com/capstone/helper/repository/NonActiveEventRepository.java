package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.NonActiveEvent;


public interface NonActiveEventRepository extends JpaRepository<NonActiveEvent, Integer>{

}
