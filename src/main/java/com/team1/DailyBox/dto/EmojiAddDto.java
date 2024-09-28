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
public class EmojiAddDto {
	private Long id;
	private EmojiType emoji;
	private String name;
	private DayType day;
	private int count;
	private ColorType color;
	private LocalDate date;

	public Box toEntity() {
		Box box = new Box();
		box.setId(this.id);
		box.setEmoji(this.emoji);
		box.setName(this.name);
		box.setDay(this.day);
		box.setCount(this.count);
		box.setColor(this.color);
		box.setDate(this.date);
		return box;
	}
}
