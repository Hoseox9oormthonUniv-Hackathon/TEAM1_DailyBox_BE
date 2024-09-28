package com.team1.DailyBox.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team1.DailyBox.Service.EmojiService;
import com.team1.DailyBox.domain.Emoji;
import com.team1.DailyBox.dto.ApiResponse;
import com.team1.DailyBox.dto.EmojiAddDto;
import com.team1.DailyBox.dto.EmojiUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emoji")
@RequiredArgsConstructor
public class EmojiController {
	private final EmojiService emojiService;


	// 이모지 추가 API
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<String>> addEmoji(@RequestBody EmojiAddDto emojiAddDto) {
		boolean result = emojiService.addEmoji(emojiAddDto);

		if (result) {
			return ResponseEntity.ok(new ApiResponse<>("이모지가 성공적으로 추가되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse<>("이모지 추가에 실패했습니다."));
		}
	}

	// 이모지 삭제
	@PostMapping("/delete")
	public ResponseEntity<ApiResponse<String>> deleteEmoji(@PathVariable Long id){
		boolean result = emojiService.deleteEmoji(id);
		if (result) {
			return ResponseEntity.ok(new ApiResponse<>("이모지가 성공적으로 삭제되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse<>("이모지 삭제에 실패했습니다."));
		}
	}

	// 이모지 수정 API
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<String>> updateEmoji(@PathVariable Long id, @RequestBody EmojiUpdateDto emojiUpdateDto) {
		boolean result = emojiService.updateEmoji(emojiUpdateDto, id);

		if (result) {
			return ResponseEntity.ok(new ApiResponse<>("이모지가 성공적으로 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse<>("이모지 수정에 실패했습니다."));
		}
	}

	// 이모지 카운트 되돌리기 (1 증가)
	@PutMapping("/back-count/{id}")
	public ResponseEntity<ApiResponse<String>> backCount(@PathVariable Long id) {
		boolean result = emojiService.backCount(id);

		if (result) {
			return ResponseEntity.ok(new ApiResponse<>("이모지 카운트가 1 증가되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse<>("이모지 카운트 증가에 실패했습니다."));
		}
	}

	// 이모지 카운트 줄이기
	@PutMapping("/down-count/{id}")
	public ResponseEntity<ApiResponse<String>> downCount(@PathVariable Long id) {
		boolean result = emojiService.downCount(id);

		if (result) {
			return ResponseEntity.ok(new ApiResponse<>("이모지 카운트가 1 감소되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse<>("이모지 카운트 감소에 실패했습니다."));
		}
	}

	// 해당주 ToDo 보여주기 (전체 보여주기)
	@GetMapping("/week-emoji")
	public ResponseEntity<ApiResponse<List<Emoji>>> showWeekEmoji() {
		List<Emoji> weekEmojis = emojiService.showWeekEmoji();
		return ResponseEntity.ok(new ApiResponse<>(weekEmojis, "해당 주의 이모지 목록이 조회되었습니다."));
	}

	// 완료 되지 않은 ToDo 보여주기
	@GetMapping("/NotDone-emojis")
	public ResponseEntity<ApiResponse<List<Emoji>>> showNotDoneEmojis(){
		List<Emoji> notDoneEmojis = emojiService.getNotDoneEmojis();
		return ResponseEntity.ok(new ApiResponse<>(notDoneEmojis, "완료 되지 않은 이모지 목록이 조회되었습니다."));
	}

	// 금일 ToDo 보여주기
	@GetMapping("/today-emoji")
	public ResponseEntity<ApiResponse<List<Emoji>>> showTodayEmoji(){
		List<Emoji> todayEmoji = emojiService.getTodayBox();
		return ResponseEntity.ok(new ApiResponse<>(todayEmoji,"금일 이모지가 조회되었습니다."));
	}

}
