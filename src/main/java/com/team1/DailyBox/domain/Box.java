package com.team1.DailyBox.domain;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Box {
	@Id
	private Long id;
	@Enumerated(EnumType.STRING)
	private String day;
	@Enumerated(EnumType.STRING)
	private String color;
	private int count;
	private String name;
	@Enumerated(EnumType.STRING)
	private String emoji;
	private LocalDate date;

}
