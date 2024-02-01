package com.battlecruisers.yanullja.auth.controller;

import com.battlecruisers.yanullja.auth.dto.LoginDto;
import com.battlecruisers.yanullja.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Principal> auth(Principal principal) {

        if (principal == null) {
            throw new RuntimeException("User not logged in!");
        }
        return ResponseEntity.ok(principal);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDto loginDto,
        HttpServletRequest request) {
        throw new RuntimeException("Login is handled by Spring Security");
    }

    @PostMapping("/logout")
    public void logout() {
        throw new RuntimeException("Logout is handled by Spring Security");
    }
}
