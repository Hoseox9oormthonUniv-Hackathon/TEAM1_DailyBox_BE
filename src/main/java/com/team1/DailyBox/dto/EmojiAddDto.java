package com.team1.DailyBox.dto;

import java.time.DayOfWeek;
import java.util.List;

import com.team1.DailyBox.domain.ColorType;
import com.team1.DailyBox.domain.EmojiType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiAddDto {
	private EmojiType emojiType;
	private String name;
	private List<DayOfWeek> days;
	private int goalCount;
	private ColorType color;
}
