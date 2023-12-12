package whizware.whizware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.user.UserRequest;
import whizware.whizware.service.UserService;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
        BaseResponse response = userService.getUserById(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody UserRequest request) {
        BaseResponse response = userService.addUser(request);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody UserRequest request) {
        BaseResponse response = userService.updateUser(id, request);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) {
        BaseResponse response = userService.deleteUser(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

}
