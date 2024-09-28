package com.team1.DailyBox.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team1.DailyBox.domain.Emoji;

@Repository
public interface JpaEmojiRepository extends JpaRepository<Emoji,Long> {
//	Emoji findById(Long emojiId);

//	List<Emoji> findAllByDay(DayOfWeek day);
//
//	@Query("SELECT e FROM Emoji e WHERE e.count > 0")
//	List<Emoji> findAllWithCountGreaterThanZero();

}
