package com.ssafy.wcc.domain.member.presentation;


import com.ssafy.wcc.domain.member.application.dto.request.EmailVerifyRequest;
import com.ssafy.wcc.domain.member.application.dto.request.MemberRequest;
import com.ssafy.wcc.domain.member.application.dto.response.MemberLoginResponse;
import com.ssafy.wcc.domain.member.application.service.EmailService;
import com.ssafy.wcc.domain.member.application.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "Member 컨트롤러")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final EmailService emailService;

    @PostMapping("/join")
    @ApiOperation(value = "회원 가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 404, message = "회원가입 실패"),
    })
    public ResponseEntity<?> signUp(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) MemberRequest signupInfo
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        memberService.memberSignUp(signupInfo);
        resultMap.put("isSuccess", true);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/{email}")
    @ApiOperation(value = "email 인증 중복 검사 및 인증번호 전송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이메일 인증 메일 전송 성공"),
            @ApiResponse(code = 404, message = "사용 불가능한 이메일"),
    })
    public ResponseEntity<?> confirmEmail(@PathVariable String email) throws MessagingException, UnsupportedEncodingException {
        Map<String, Object> resultMap = new HashMap<>();

        // 이메일 중복 검사
        if (memberService.checkEmail(email)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // 인증 메일 전송
        try {
            emailService.sendMessage(email);
        } catch (IllegalArgumentException e) {
            resultMap.put("isSuccess", false);
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }

        resultMap.put("isSuccess", true);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/code")
    @ApiOperation(value = "email 인증 번호 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이메일 인증 성공"),
            @ApiResponse(code = 404, message = "이메일 인증 실패"),
    })
    public ResponseEntity<?> verifyEmail(
            @RequestBody @ApiParam(value = "이메일 인증 정보", required = true) EmailVerifyRequest emailVerifyRequest
            ) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            emailService.verifyEmail(emailVerifyRequest);
        } catch (ChangeSetPersister.NotFoundException e) {
            resultMap.put("isSuccess", false);
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }
        resultMap.put("isSuccess", false);
        return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 401, message = "잘못된 비밀번호"),
            @ApiResponse(code = 404, message = "존재하지 않는 사용자")
    })
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "로그인 정보") MemberRequest loginInfo
    ) {
        Map<String, Object> res = new HashMap<>();

        // 비밀번호 일치 여부 파악
        Optional<MemberLoginResponse> loginMemberInfo = memberService.memberLogin(loginInfo);
        if(loginMemberInfo.isPresent()){
            res.put("isSuccess",true);
            res.put("data", loginMemberInfo.get());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

//        if (loginMemberInfo != null) { // 비밀번호 일치
//            res.put("isSuccess", true);
//            res.put("data", loginMemberInfo);
//            return new ResponseEntity<>(res, HttpStatus.OK);
//        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
