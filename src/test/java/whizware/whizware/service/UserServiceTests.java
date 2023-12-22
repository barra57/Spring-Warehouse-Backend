package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.user.UserResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.User;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.ConflictException;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    LocationRepository locationRepository;

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
    void testGetAllUsers() {
        List<User> mockData = new ArrayList<>( );
        mockData.add(user1);
        mockData.add(user2);

        String expectedMessage = "Success";
        List<UserResponse> expectedData = new ArrayList<>( );
        expectedData.add(userResponse1);
        expectedData.add(userResponse2);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(userRepository.findAll()).thenReturn(mockData);

        BaseResponse actualResponse = userService.getAllUser();
        List<UserResponse> actualData = (List<UserResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void testGetAllUsersWithEmptyList() {
        List<User> mockData = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(mockData);

        Assertions.assertThrows(NoContentException.class, () -> userService.getAllUser()); // This will throw
    }

    @Test
    void testGetUserWithValidId() {
        String expectedMessage = "Success";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(userResponse1)
                .build();
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        BaseResponse actualResponse = userService.getUserById(user1.getId());
        UserResponse actualData = (UserResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(userResponse1.getId(), actualData.getId());
    }

    @Test
    void testGetUserWithInvalidId() {
        Long id = user1.getId();
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> userService.getUserById(id));
    }
}
