package whizware.whizware.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.login.LoginRequest;
import whizware.whizware.dto.login.LoginResponse;
import whizware.whizware.dto.register.RegisterRequest;
import whizware.whizware.dto.register.RegisterResponse;
import whizware.whizware.entity.User;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.exception.UnauthorizedException;
import whizware.whizware.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public BaseResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("There's no such user"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return BaseResponse.builder()
                .message("Login success")
                .data(LoginResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build())
                .build();

    }

    public BaseResponse register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);
        return BaseResponse.builder()
                .message("Register success")
                .data(RegisterResponse.builder()
                        .id(savedUser.getId())
                        .username(savedUser.getUsername())
                        .password(savedUser.getPassword())
                        .role(savedUser.getRole())
                        .build())
                .build();
    }


    public BaseResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String token = jwtService.generateToken(user);
        return BaseResponse.builder()
                .message("Success refreshing token")
                .data(LoginResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build())
                .build();
    }
}
