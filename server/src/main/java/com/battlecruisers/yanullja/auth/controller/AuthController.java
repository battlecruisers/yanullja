package com.battlecruisers.yanullja.auth.controller;

import com.battlecruisers.yanullja.auth.dto.LoginDto;
import com.battlecruisers.yanullja.member.MemberService;
import com.battlecruisers.yanullja.member.domain.SecurityMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "Get Current User", description = "Retrieves the current authenticated user's details. User must be authenticated.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the current user's details.")
    @ApiResponse(responseCode = "401", description = "User not logged in - Unauthorized access.")
    public ResponseEntity<SecurityMember> getCurrentUserDetails(
        @AuthenticationPrincipal SecurityMember member) {
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    @Operation(summary = "Login",
        description = "Performs user login.",
        requestBody = @RequestBody(
            description = "Login credentials",
            required = true,
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "login",
                        value = """
                            {
                                "email": "mockuser@mock.com",
                                "password": "mockuser"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "login fail",
                        value = """
                            {
                                "email": "you cannot",
                                "password": "login"
                            }
                            """
                    )

                },
                schema = @Schema(implementation = LoginDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
        }
    )
    public void login(@RequestBody LoginDto loginDto) {
        throw new RuntimeException("Login is handled by Spring Security");
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Performs user logout.")
    @ApiResponse(responseCode = "200", description = "Successfully logged out")
    public void logout() {
        throw new RuntimeException("Logout is handled by Spring Security");
    }
}
