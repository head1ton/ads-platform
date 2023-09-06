package platform.ads.api.v1.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    @NotNull(message = "email 항목은 필수입니다.")
    @Email(message = "email 형식에 맞춰 작성해주세요.", regexp = "^[A-Za-z0-9._%*-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;
    @NotNull(message = "password 항목은 필수입니다.")
    private String password;
    @NotNull(message = "name 항목은 필수입니다.")
    private String name;
    @NotNull(message = "nickname 항목은 필수입니다.")
    private String nickname;
    private String birth;

    public void passwordEncoder(BCryptPasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

}
