package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
	List<Alarm> findByReceiverId(int receiverId);
}
