package com.team1.DailyBox.dto;

import java.time.LocalDate;

import com.team1.DailyBox.domain.Box;

import com.team1.DailyBox.domain.ColorType;
import com.team1.DailyBox.domain.DayType;
import com.team1.DailyBox.domain.EmojiType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoxAddDto {
	private EmojiType emoji;
	private String name;
	private DayType day;
	private int count;
	private ColorType color;
	private LocalDate date;

	public Box toEntity() {
		return Box.builder()
			.emoji(this.emoji)
			.name(this.name)
			.day(this.day)
			.count(this.count)
			.color(this.color)
			.date(this.date)
			.build();
	}
}
