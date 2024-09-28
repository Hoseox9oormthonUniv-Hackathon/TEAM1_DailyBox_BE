package com.team1.DailyBox.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmojiDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emoji_id")
    private Emoji emoji;

    @Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255)")
    private DayOfWeek day;

    private int count;
}
