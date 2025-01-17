package com.umc.refit.web.controller;

import com.umc.refit.domain.dto.clothe.GetClosetListResponseDto;
import com.umc.refit.domain.dto.clothe.RegisterClosetRequestDto;
import com.umc.refit.exception.ExceptionType;
import com.umc.refit.exception.member.TokenException;
import com.umc.refit.web.service.ClosetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/refit/clothe")
@RestController
public class ClotheController {

    private final ClosetService closetService;

    // 옷장 등록
    @PostMapping
    public ResponseEntity<Long> registerClothe(
            @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            Authentication authentication,
            HttpServletRequest request,
            @Valid @RequestBody RegisterClosetRequestDto requestDto
    ) {
        if (authentication == null) {
            ExceptionType exception = (ExceptionType) request.getAttribute("exception");
            throw new TokenException(exception, exception.getCode(), exception.getErrorMessage());
        }
        return new ResponseEntity<>(this.closetService.registerClothe(requestDto, multipartFile, authentication), HttpStatus.CREATED);
    }

    // 옷장 조회
    @GetMapping
    public ResponseEntity<List<GetClosetListResponseDto>> showClotheMain(
            @RequestParam Integer category,
            @RequestParam Integer season,
            @RequestParam String sort) {
        return new ResponseEntity<>(
                this.closetService.showClotheMain(category, season, sort), HttpStatus.OK);
    }
}
