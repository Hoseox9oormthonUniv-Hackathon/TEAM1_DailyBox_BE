package com.team1.DailyBox.repository;

import com.team1.DailyBox.domain.Emoji;
import com.team1.DailyBox.domain.EmojiDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface JpaEmojiDayRepository extends JpaRepository<EmojiDay, Long> {
    List<EmojiDay> findAllByDay(DayOfWeek day);

    @Query("SELECT e FROM EmojiDay e WHERE e.count > 0")
    List<EmojiDay> findAllWithCountGreaterThanZero();

    List<EmojiDay> findAllByEmoji(Emoji emoji);
}
