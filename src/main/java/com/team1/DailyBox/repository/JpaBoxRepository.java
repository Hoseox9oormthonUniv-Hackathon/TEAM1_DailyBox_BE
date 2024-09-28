package com.team1.DailyBox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team1.DailyBox.domain.Box;

@Repository
public interface JpaBoxRepository extends JpaRepository<Box,Long> {
	List<Box> findAllByUserId(Long userId);

}
