package whizware.whizware.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.login.LoginRequest;
import whizware.whizware.dto.login.LoginResponse;
import whizware.whizware.dto.register.RegisterRequest;
import whizware.whizware.dto.register.RegisterResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.service.AuthService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTests {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthService authService;

    @Mock
    private HttpServletRequest refreshTokenRequest;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;


    @BeforeEach
    void setUp() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();

        this.loginRequest = new LoginRequest("admin", "admin");
        this.loginResponse = new LoginResponse("asdf", "1234");

        this.registerRequest = new RegisterRequest("admin", "admin", "ADMIN");
        this.registerResponse = new RegisterResponse(1L, "admin", bCryptPasswordEncoder.encode("admin"), "ADMIN");
    }

    @Test
    void testLogin() {

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Login Success")
                .data(loginResponse)
                .build();
        when(authService.login(loginRequest)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = authController.login(loginRequest);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void testRegister() {
        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Register Success")
                .data(registerResponse)
                .build();
        when(authService.register(registerRequest)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = authController.register(registerRequest);

        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void testRefreshToken() {

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success refreshing token")
                .data(loginResponse)
                .build();
        when(authService.refreshToken(refreshTokenRequest)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = authController.refreshToken(refreshTokenRequest);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse, actualResponse.getBody());
    }

}