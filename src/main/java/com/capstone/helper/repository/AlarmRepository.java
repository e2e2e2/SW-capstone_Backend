package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

}
