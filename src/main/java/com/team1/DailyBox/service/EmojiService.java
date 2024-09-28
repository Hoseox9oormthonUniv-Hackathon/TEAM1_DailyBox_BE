package com.team1.DailyBox.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.team1.DailyBox.domain.Emoji;
import com.team1.DailyBox.domain.EmojiDay;
import com.team1.DailyBox.dto.EmojiAddDto;
import com.team1.DailyBox.dto.EmojiResponseDto;
import com.team1.DailyBox.dto.EmojiUpdateDto;
import com.team1.DailyBox.exception.CustomException;
import com.team1.DailyBox.exception.ErrorCode;
import com.team1.DailyBox.repository.JpaEmojiDayRepository;
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
	private final JpaEmojiDayRepository jpaEmojiDayRepository;

	// 이모지 추가
	@Transactional
	public void addEmoji(EmojiAddDto emojiAddDto){
		Emoji emoji = Emoji.builder()
				.emojiType(emojiAddDto.getEmojiType())
				.name(emojiAddDto.getName())
				.goalCount(emojiAddDto.getGoalCount())
				.color(emojiAddDto.getColor())
				.build();

		jpaEmojiRepository.save(emoji);

		List<DayOfWeek> days = emojiAddDto.getDays();
		for (DayOfWeek day : days) {
			EmojiDay emojiDay = EmojiDay.builder()
					.emoji(emoji)
					.day(day)
					.count(emojiAddDto.getGoalCount())
					.build();
			jpaEmojiDayRepository.save(emojiDay);
		}
	}

	// 이모지 삭제
	@Transactional
	public void deleteEmoji(Long emojiDayId){
		EmojiDay emojiDay = jpaEmojiDayRepository.findById(emojiDayId)
			.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));
		jpaEmojiDayRepository.delete(emojiDay);
	}

	// 이모지 수정
	@Transactional
	public void updateEmoji(EmojiUpdateDto emojiUpdateDto, Long emojiDayId) {
		// Retrieve the EmojiDay entity by its ID
		EmojiDay existingEmojiDay = jpaEmojiDayRepository.findById(emojiDayId)
				.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));

		// Get the associated Emoji
		Emoji emoji = existingEmojiDay.getEmoji();

		// Delete all existing EmojiDay entities for this Emoji
		List<EmojiDay> existingEmojiDays = jpaEmojiDayRepository.findAllByEmoji(emoji);
		jpaEmojiDayRepository.deleteAll(existingEmojiDays);

		// Create new EmojiDay entities with the updated days
		List<DayOfWeek> newDays = emojiUpdateDto.getDays();
		for (DayOfWeek day : newDays) {
			EmojiDay newEmojiDay = EmojiDay.builder()
					.emoji(emoji)
					.day(day)
					.count(emojiUpdateDto.getGoalCount())
					.build();
			jpaEmojiDayRepository.save(newEmojiDay);
		}

		// Update the Emoji entity with new details
		emoji.setName(emojiUpdateDto.getName());
		emoji.setGoalCount(emojiUpdateDto.getGoalCount());
		emoji.setColor(emojiUpdateDto.getColor());
		emoji.setEmojiType(emojiUpdateDto.getEmojiType());

		jpaEmojiRepository.save(emoji);
	}

	// 되돌리기(카운트 늘리기)
	@Transactional
	public void backCount(Long emojiDayId){
		EmojiDay emojiDay = jpaEmojiDayRepository.findById(emojiDayId)
				.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));
		Emoji emoji = jpaEmojiRepository.findById(emojiDay.getEmoji().getId())
				.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));


		// count가 goalCount를 넘어가지 않도록 조건 처리
		if (emojiDay.getCount() + 1 > emoji.getGoalCount())
			throw new CustomException(ErrorCode.COUNT_OVERFLOW);

		emojiDay.setCount(emojiDay.getCount() + 1);

		jpaEmojiDayRepository.save(emojiDay);
	}

	// 카운드 줄이기
	@Transactional
	public void downCount(Long emojiDayId){
		EmojiDay emojiDay = jpaEmojiDayRepository.findById(emojiDayId)
				.orElseThrow(() -> new CustomException(ErrorCode.EMOJI_NOT_FOUND));

		// count가 0 밑으로 내려가지 않도록 조건 처리
		if (emojiDay.getCount() - 1 < 0)
			throw new CustomException(ErrorCode.COUNT_ALREADY_DONE);

		emojiDay.setCount(emojiDay.getCount() - 1);

		jpaEmojiDayRepository.save(emojiDay);
	}

	// 새로운 주가 시작되면 ToDo 카운트 초기화
	@Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul") // 매주 월요일 00시에 실행
	@Transactional
	public void resetEmojiCounts() {
		List<EmojiDay> emojiDays = jpaEmojiDayRepository.findAll();
		for (EmojiDay emojiDay : emojiDays) {
			Emoji emoji = emojiDay.getEmoji();
			emojiDay.setCount(emoji.getGoalCount());
			jpaEmojiDayRepository.save(emojiDay);
		}
		log.info("ToDo counts reset completed.");
	}


	// 해당주 ToDo 보여주기 (전체 보여주기)
	@Transactional
	public List<EmojiResponseDto> showWeekEmoji() {
		List<Emoji> emojis = jpaEmojiRepository.findAll();
		return emojis.stream()
				.flatMap(emoji -> jpaEmojiDayRepository.findAllByEmoji(emoji).stream())
				.map(emojiDay -> new EmojiResponseDto(
						emojiDay.getEmoji().getId(),
						emojiDay.getId(),
						emojiDay.getDay(),
						emojiDay.getEmoji().getColor(),
						emojiDay.getEmoji().getGoalCount(),
						emojiDay.getCount(),
						emojiDay.getEmoji().getName(),
						emojiDay.getEmoji().getEmojiType()
				))
				.collect(Collectors.toList());
	}

	// 완료 되지 않은 ToDo 보여주기
	public List<EmojiResponseDto> getNotDoneEmojis() {
		List<EmojiDay> result = jpaEmojiDayRepository.findAllWithCountGreaterThanZero();
		return result.stream()
				.map(emojiDay -> new EmojiResponseDto(
						emojiDay.getEmoji().getId(),
						emojiDay.getId(),
						emojiDay.getDay(),
						emojiDay.getEmoji().getColor(),
						emojiDay.getEmoji().getGoalCount(),
						emojiDay.getCount(),
						emojiDay.getEmoji().getName(),
						emojiDay.getEmoji().getEmojiType()
				))
				.collect(Collectors.toList());
	}

	// 금일 ToDo 보여주기
	@Transactional
	public List<EmojiResponseDto> getTodayBox() {
		LocalDate date = LocalDate.now();
		DayOfWeek day = date.getDayOfWeek();
		List<EmojiDay> emojiDays = jpaEmojiDayRepository.findAllByDay(day);

		return emojiDays.stream()
				.map(emojiDay -> {
					Emoji emoji = emojiDay.getEmoji();
					return new EmojiResponseDto(
							emojiDay.getEmoji().getId(),
							emojiDay.getId(),
							emojiDay.getDay(),
							emoji.getColor(),
							emoji.getGoalCount(),
							emojiDay.getCount(),
							emoji.getName(),
							emoji.getEmojiType()
					);
				})
				.collect(Collectors.toList());
	}

}
