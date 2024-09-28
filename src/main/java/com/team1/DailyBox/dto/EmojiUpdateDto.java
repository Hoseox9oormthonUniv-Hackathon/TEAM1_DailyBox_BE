package com.team1.DailyBox.dto;

import com.team1.DailyBox.domain.ColorType;
import com.team1.DailyBox.domain.EmojiType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiUpdateDto {
	private EmojiType emojiType;
	private String name;
	private DayOfWeek day;
	private int goalCount;
	private ColorType color;
}
