package whizware.whizware.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.user.UserResponse;
import whizware.whizware.entity.User;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public BaseResponse getAllUser() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new NoContentException("User is empty");
        }

        List<UserResponse> data = new ArrayList<>();
        for (User user : users) {
            data.add(UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build());
        }
        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with ID %d not found", id)));

        return BaseResponse.builder()
                .message("Success")
                .data(UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build())
                .build();
    }

}
