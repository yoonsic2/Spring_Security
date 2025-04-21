package com.security.spring_security.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private String username;
    private String password;
    private String role;
    private String email;
}
