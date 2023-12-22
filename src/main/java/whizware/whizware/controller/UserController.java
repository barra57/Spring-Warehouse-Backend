package whizware.whizware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
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
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
