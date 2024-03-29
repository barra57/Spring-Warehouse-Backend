package whizware.whizware.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String username;
    private String password;
    private String role;
}
