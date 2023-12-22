package whizware.whizware.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Please enter username")
    private String username;
    @NotBlank(message = "Please enter password") //not null
    private String password;
}
