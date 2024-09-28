package com.team1.DailyBox.domain;

import java.time.LocalDate;
import java.util.Date;

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
public class Box {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private DayType day;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private ColorType color;

	private int count;

	private String name;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private EmojiType emoji;

	private LocalDate date;

}
