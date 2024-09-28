package com.team1.DailyBox.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiUpdateDto {
	private Long id;
	private String emoji;
	private String name;
	private String day;
	private int count;
	private String color;

}
