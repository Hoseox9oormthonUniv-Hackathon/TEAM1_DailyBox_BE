package com.team1.DailyBox.dto;

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
public class EmojiUpdateDto {
	private Long id;
	private EmojiType emoji;
	private String name;
	private DayType day;
	private int count;
	private ColorType color;

}
