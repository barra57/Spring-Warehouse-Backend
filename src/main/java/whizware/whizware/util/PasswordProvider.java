package whizware.whizware.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordProvider {

    public String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
