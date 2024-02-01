package com.battlecruisers.yanullja.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDto {

    @Schema(description = "이메일", example = "mockuser@mock.com")
    private String email;
    @Schema(description = "비밀번호", example = "mockuser")
    private String password;
}
