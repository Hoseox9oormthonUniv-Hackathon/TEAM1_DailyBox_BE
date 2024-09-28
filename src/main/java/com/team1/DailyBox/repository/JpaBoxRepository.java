package com.team1.DailyBox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team1.DailyBox.domain.Box;

@Repository
public interface JpaBoxRepository extends JpaRepository<Box,Long> {
}
