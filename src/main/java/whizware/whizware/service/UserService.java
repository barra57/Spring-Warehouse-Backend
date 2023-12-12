package whizware.whizware.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.user.UserRequest;
import whizware.whizware.dto.user.UserResponse;
import whizware.whizware.entity.User;
import whizware.whizware.repository.UserRepository;
import whizware.whizware.util.PasswordProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private PasswordProvider passwordProvider = new PasswordProvider();

    public BaseResponse getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserResponse> data = new ArrayList<>();
        for (User user : users) {
            data.add(UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build());
        }
        return BaseResponse.builder()
                .message("Berhasil")
                .data(data)
                .build();
    }

    public BaseResponse getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("gagal")
                    .build();
        }
        User user = userOptional.get();
        return BaseResponse.builder()
                .message("Berhasil")
                .data(UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build())
                .build();
    }

    public BaseResponse addUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordProvider.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponse data = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse updateUser(Long id, UserRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("gagal")
                    .build();
        }
        User user = userOptional.get();

        user.setUsername(request.getUsername());
        user.setPassword(passwordProvider.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponse data = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("gagal")
                    .build();
        }
        User user = userOptional.get();
        userRepository.delete(user);

        return BaseResponse.builder()
                .message("berhasil")
                .build();
    }

}
