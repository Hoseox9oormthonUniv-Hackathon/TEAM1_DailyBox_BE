package com.team1.DailyBox.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Emoji {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private DayOfWeek day;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private ColorType color;

	private int goalCount;

	private int count;

	private String name;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private EmojiType emojiType;
}
