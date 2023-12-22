package whizware.whizware.service;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import whizware.whizware.controller.AuthController;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.login.LoginRequest;
import whizware.whizware.dto.login.LoginResponse;
import whizware.whizware.dto.register.RegisterRequest;
import whizware.whizware.dto.register.RegisterResponse;
import whizware.whizware.entity.User;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @InjectMocks
    AuthService authService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @Mock
    UserRepository userRepository;

    @Mock
    HttpServletRequest refreshTokenRequest;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;

    private User user;


    @BeforeEach
    void setUp() {
        this.loginRequest = new LoginRequest("admin", "admin");
        this.loginResponse = new LoginResponse("asdf", "1234");

        this.registerRequest = new RegisterRequest("admin", "admin", "ADMIN");
        this.registerResponse = new RegisterResponse(1L, "admin", "admin", "ADMIN");

        this.user = new User(1L, "admin", "admin", "ADMIN");
    }

    @Test
    void testLogin() {
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("asdf");
        when(jwtService.generateRefreshToken(user)).thenReturn("1234");

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Login success")
                .data(loginResponse)
                .build();

        BaseResponse actualResponse = authService.login(loginRequest);
        LoginResponse actualData = (LoginResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedResponse.getData(), actualResponse.getData());
        Assertions.assertEquals(loginResponse.getAccessToken(), actualData.getAccessToken());
        Assertions.assertEquals(loginResponse.getRefreshToken(), actualData.getRefreshToken());
    }

    @Test
    void testLoginWithInvalidUsername() {
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    void testRegister() {
        when(userRepository.save(any())).thenReturn(user);
        when(bCryptPasswordEncoder.encode(registerRequest.getPassword())).thenReturn("admin");

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Register success")
                .data(registerResponse)
                .build();

        BaseResponse actualResponse = authService.register(registerRequest);
        RegisterResponse actualData = (RegisterResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedResponse.getData(), actualResponse.getData());
        Assertions.assertEquals(registerResponse.getId(), actualData.getId());
        Assertions.assertEquals(registerResponse.getUsername(), actualData.getUsername());
        Assertions.assertEquals(registerResponse.getPassword(), actualData.getPassword());
        Assertions.assertEquals(registerResponse.getRole(), actualData.getRole());
    }

    @Test
    void testRefreshToken() {
        when(refreshTokenRequest.getHeader("Authorization")).thenReturn("Bearer asdf");
        when(jwtService.extractUsername("asdf")).thenReturn("admin");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("asdf", user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("asdf");

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success refreshing token")
                .data(loginResponse)
                .build();

        BaseResponse actualResponse = authService.refreshToken(refreshTokenRequest);
        LoginResponse actualData = (LoginResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(loginResponse.getAccessToken(), actualData.getAccessToken());
    }

}