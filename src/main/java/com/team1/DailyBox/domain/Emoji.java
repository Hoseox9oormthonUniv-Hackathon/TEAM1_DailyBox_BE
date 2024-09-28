package com.team1.DailyBox.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
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

//	@Enumerated(EnumType.STRING)
//	@Column(columnDefinition = "varchar(255)")
//	private DayOfWeek day;

	@OneToMany(mappedBy = "emoji")
	private List<EmojiDay> days;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private ColorType color;

	private int goalCount;

//	private int count;

	private String name;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
	private EmojiType emojiType;
}
