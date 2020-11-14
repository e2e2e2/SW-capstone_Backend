package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.FallEvent;

public interface FallEventRepository extends JpaRepository<FallEvent, Integer> {

}
