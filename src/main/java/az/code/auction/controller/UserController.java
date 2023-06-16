package az.code.auction.controller;

import az.code.auction.dto.UserLoginDTO;
import az.code.auction.entity.User;
import az.code.auction.service.MailService;
import az.code.auction.service.MainService;
import az.code.auction.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MainService ms;
    private final BCryptPasswordEncoder encoder;

    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id).get();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user, HttpServletRequest request){
        return userService.findEmail(user.getEmail()).map(prof->{
            if (encoder.matches(user.getPassword(),prof.getPassword())){
                    ms.securedTest(prof.getId(),user.getCokie(),request);
//                prof.setIgnoreCode(user.getCokie());
                return ResponseEntity.ok(userService.save(prof));
            }else
                return ResponseEntity.status(403).build();
        }).orElse(ResponseEntity.status(401).build());
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return userService.findEmail(user.getEmail())
                .map(item->ResponseEntity.status(409).build())
                .orElseGet(()->{
                    user.setPassword(encoder.encode(user.getPassword()));
                    return ResponseEntity.ok(userService.save(user));
                });
    }
}
