package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.user.UserRequest;
import whizware.whizware.dto.user.UserResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.User;
import whizware.whizware.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    private User user1;
    private User user2;

    private UserResponse userResponse1;
    private UserResponse userResponse2;


    @BeforeEach
    void setUp() {
        this.user1 = new User(1L, "admin", "admin", "ADMIN");
        this.user2 = new User(2L, "warehouse", "warehouse", "WAREHOUSE");

        this.userResponse1 = new UserResponse(1L, "admin", "admin");
        this.userResponse2 = new UserResponse(2L, "warehouse", "warehouse");
    }

    @Test
    void getAllUsers() {

        List<UserResponse> data = new ArrayList<>();
        data.add(userResponse1);
        data.add(userResponse2);

        String expectedMessage = "Success";

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(data)
                .build();

        when(userService.getAllUser()).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = userController.getAll();

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getUserById() {
        String expectedMessage = "Success";
        UserResponse expectedData = userResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(userService.getUserById(user1.getId())).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = userController.getById(user1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

}