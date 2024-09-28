package com.team1.DailyBox.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.team1.DailyBox.domain.Emoji;
import com.team1.DailyBox.dto.EmojiAddDto;
import com.team1.DailyBox.dto.EmojiUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team1.DailyBox.repository.JpaEmojiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmojiService {
	private final JpaEmojiRepository jpaEmojiRepository;

	// 이모지 추가
	@Transactional
	public boolean addEmoji(EmojiAddDto emojiAddDto){
		List<DayOfWeek> days = emojiAddDto.getDays();
		for (DayOfWeek day : days) {
			Emoji emoji = Emoji.builder()
					.emojiType(emojiAddDto.getEmojiType())
					.name(emojiAddDto.getName())
					.day(day)
					.goalCount(emojiAddDto.getGoalCount())
					.count(emojiAddDto.getGoalCount())
					.color(emojiAddDto.getColor())
					.build();
			jpaEmojiRepository.save(emoji);
		}
		return true;
	}

	// 이모지 삭제
	@Transactional
	public boolean deleteEmoji(Long emojiId){
		Emoji emoji = jpaEmojiRepository.findById(emojiId)
			.orElseThrow(() -> new IllegalArgumentException("Box not found with id: " + emojiId));
		jpaEmojiRepository.delete(emoji);

		return true;
	}

	// 이모지 수정
	@Transactional
	public boolean updateEmoji(EmojiUpdateDto emojiUpdateDto, Long emojiId){
		Emoji emoji = jpaEmojiRepository.findById(emojiId)
			.orElseThrow(() -> new IllegalArgumentException("Emoji의 아이디를 찾을 수 없습니다. : " + emojiId));

		emoji.setName(emojiUpdateDto.getName());
		emoji.setGoalCount(emojiUpdateDto.getGoalCount());
		emoji.setColor(emojiUpdateDto.getColor());

		jpaEmojiRepository.save(emoji);

		return true;
	}

	// 되돌리기(카운트 늘리기)
	@Transactional
	public boolean backCount(Long id){
		Emoji emoji = jpaEmojiRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Box의 아이디를 찾을 수 없습니다. : " + id));
		emoji.setCount(emoji.getCount() + 1);

		// count가 goalCount를 넘어가지 않도록 조건 처리 필요

		jpaEmojiRepository.save(emoji);

		return true;
	}

	// 카운드 줄이기
	@Transactional
	public boolean downCount(Long id){
		Emoji emoji = jpaEmojiRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Box의 아이디를 찾을 수 없습니다. : " + id));
		emoji.setCount(emoji.getCount() - 1);

		// count가 0 밑으로 내려가지 않도록 조건 처리 필요

		jpaEmojiRepository.save(emoji);

		return true;
	}


	// 해당주 ToDo 보여주기 (전체 보여주기)
	@Transactional
	public List<Emoji> showWeekEmoji() {
		return jpaEmojiRepository.findAll();
	}

	// 완료 되지 않은 ToDo 보여주기
	public List<Emoji> getNotDoneEmojis() {
		return jpaEmojiRepository.findAllWithCountGreaterThanZero();
	}

	// 금일 ToDo 보여주기
	@Transactional
	public List<Emoji> getTodayBox() {
		LocalDate date = LocalDate.now();
		DayOfWeek day = date.getDayOfWeek();
		return jpaEmojiRepository.findAllByDay(day);
	}

}
