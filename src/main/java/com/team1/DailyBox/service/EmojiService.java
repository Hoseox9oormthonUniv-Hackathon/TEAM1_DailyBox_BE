package com.team1.DailyBox.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.team1.DailyBox.domain.Emoji;
import com.team1.DailyBox.dto.EmojiAddDto;
import com.team1.DailyBox.dto.EmojiUpdateDto;
import com.team1.DailyBox.exception.CustomException;
import com.team1.DailyBox.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team1.DailyBox.repository.JpaEmojiRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmojiService {
	private final JpaEmojiRepository jpaEmojiRepository;

	// 이모지 추가
	@Transactional
	public void addEmoji(EmojiAddDto emojiAddDto){
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
	}

	// 이모지 삭제
	@Transactional
	public void deleteEmoji(Long emojiId){
		Emoji emoji = jpaEmojiRepository.findById(emojiId)
			.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));
		jpaEmojiRepository.delete(emoji);
	}

	// 이모지 수정
	@Transactional
	public void updateEmoji(EmojiUpdateDto emojiUpdateDto, Long emojiId){
		Emoji emoji = jpaEmojiRepository.findById(emojiId)
			.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));

		emoji.setName(emojiUpdateDto.getName());
		emoji.setDay(emojiUpdateDto.getDay());
		emoji.setGoalCount(emojiUpdateDto.getGoalCount());
		emoji.setColor(emojiUpdateDto.getColor());

		jpaEmojiRepository.save(emoji);
	}

	// 되돌리기(카운트 늘리기)
	@Transactional
	public void backCount(Long id){
		Emoji emoji = jpaEmojiRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));

		// count가 goalCount를 넘어가지 않도록 조건 처리
		if (emoji.getCount() + 1 > emoji.getGoalCount())
			throw new CustomException(ErrorCode.COUNT_OVERFLOW);

		emoji.setCount(emoji.getCount() + 1);

		jpaEmojiRepository.save(emoji);
	}

	// 카운드 줄이기
	@Transactional
	public void downCount(Long id){
		Emoji emoji = jpaEmojiRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));

		// count가 0 밑으로 내려가지 않도록 조건 처리
		if (emoji.getCount() - 1 < 0)
			throw new CustomException(ErrorCode.COUNT_ALREADY_DONE);

		emoji.setCount(emoji.getCount() - 1);

		jpaEmojiRepository.save(emoji);
	}

	// 새로운 주가 시작되면 ToDo 카운트 초기화
	@Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul") // 매주 월요일 00시에 실행
	@Transactional
	public void resetEmojiCounts() {
		List<Emoji> emojis = jpaEmojiRepository.findAll();
		for (Emoji emoji : emojis) {
			emoji.setCount(emoji.getGoalCount());
			jpaEmojiRepository.save(emoji);
		}
		log.info("ToDo counts reset completed.");
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
