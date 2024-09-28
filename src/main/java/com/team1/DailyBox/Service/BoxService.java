package com.team1.DailyBox.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team1.DailyBox.domain.Box;
import com.team1.DailyBox.dto.EmojiAddDto;
import com.team1.DailyBox.dto.EmojiUpdateDto;
import com.team1.DailyBox.repository.JpaBoxRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoxService {
	private final JpaBoxRepository jpaBoxRepository;

	// 이모지 추가
	@Transactional
	public boolean addEmoji(EmojiAddDto emojiAddDto){
		Box box = emojiAddDto.toEntity();
		jpaBoxRepository.save(box);

		return true;
	}

	// 이모지 삭제
	@Transactional
	public boolean deleteEmoji(Long id){
		Box box = jpaBoxRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Box not found with id: " + id));
		jpaBoxRepository.delete(box);

		return true;
	}

	// 이모지 수정
	@Transactional
	public boolean updateEmoji(EmojiUpdateDto emojiUpdateDto){
		Box box = jpaBoxRepository.findById(emojiUpdateDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("Box의 아이디를 찾을 수 없습니다. : " + emojiUpdateDto.getId()));

		box.setEmoji(emojiUpdateDto.getEmoji());
		box.setName(emojiUpdateDto.getName());
		box.setDay(emojiUpdateDto.getDay());
		box.setCount(emojiUpdateDto.getCount());
		box.setColor(emojiUpdateDto.getColor());

		jpaBoxRepository.save(box);

		return true;

	}

	// 되돌리기(카운트 늘리기)
	@Transactional
	public boolean backCount(Long id){
		Box box = jpaBoxRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Box의 아이디를 찾을 수 없습니다. : " + id));
		box.setCount(box.getCount() + 1);
		jpaBoxRepository.save(box);

		return true;
	}

	// 카운드 줄이기
	@Transactional
	public boolean downCount(Long id){
		Box box = jpaBoxRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Box의 아이디를 찾을 수 없습니다. : " + id));
		box.setCount(box.getCount() - 1);
		jpaBoxRepository.save(box);

		return true;
	}


	// 해당주 ToDo 보여주기
	@Transactional
	public List<Box> showWeekEmoji(Long id, LocalDate selectedDate) {
		// 1. 주어진 id로 Box 리스트를 조회
		List<Box> userBoxes = jpaBoxRepository.findAllByUserId(id);

		// 2. 해당 주의 시작(월요일)과 끝(일요일) 날짜 계산
		LocalDate startOfWeek = selectedDate.with(DayOfWeek.MONDAY); // 선택된 주의 월요일
		LocalDate endOfWeek = selectedDate.with(DayOfWeek.SUNDAY);   // 선택된 주의 일요일

		// 3. 날짜 필터링: 선택한 주에 해당하는 Box들만 필터링
		return userBoxes.stream()
			.filter(box -> !box.getDate().isBefore(startOfWeek) && !box.getDate().isAfter(endOfWeek))
			.collect(Collectors.toList());
	}

}
