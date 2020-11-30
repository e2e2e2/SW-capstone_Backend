package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
	Picture findByEventId(int eventID);
}
