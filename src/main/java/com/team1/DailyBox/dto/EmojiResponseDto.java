package com.team1.DailyBox.dto;

import com.team1.DailyBox.domain.ColorType;
import com.team1.DailyBox.domain.EmojiType;
import lombok.*;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiResponseDto {
    private Long emojiId;
    private Long emojiDayId;
	private DayOfWeek day;
    private ColorType color;
    private int goalCount;
	private int count;
    private String name;
    private EmojiType emojiType;
}
