package pers.jaxon.funtravel.controller;

import pers.jaxon.funtravel.controller.request.*;
import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.security.jwt.JwtTokenUtil;
import pers.jaxon.funtravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Object res = userService.register(request);
        if(res.equals("username duplicated") || res.equals("email duplicated")){
            return ResponseEntity.ok((String)res);
        }

        User user = (User)res;
        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequest request) {
        Object msg = userService.login(request.getUserIdentifier(), request.getPassword());
        if(msg.equals("notFound")) {
            throw new UsernameNotFoundException(request.getUserIdentifier());
        }else if (msg.equals("badCredentials")){
            throw new BadCredentialsException(request.getPassword());
        } else{
            User user = (User)msg;
            String token = jwtTokenUtil.generateToken(user);
            Map<String,String> response = new HashMap<>();
            response.put("token",token);
            response.put("username",user.getUsername());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/setView")
    public ResponseEntity<String> setView(@RequestBody SetViewRequest request) {
        userService.setView(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/searchUser")
    public ResponseEntity<Map> searchUser(@RequestBody SearchUserRequest request) {
        Map<String,Object> res = userService.searchUser(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestBody AddNewFriendRequest request) {
        String res = userService.addFriend(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/hasMessage")
    public ResponseEntity<String> hasMessage(@RequestBody GetMyPicturesRequest request) {
        String res = userService.hasMessage(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getMessage")
    public ResponseEntity<Map> getMessage(@RequestBody GetMyPicturesRequest request) {
        Map<String,Object> res = userService.getMessage(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/allow")
    public ResponseEntity<String> allowFriendApplication(@RequestBody AllowOrRefuseApplicationRequest request) {
        userService.allowFriendApplication(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/refuse")
    public ResponseEntity<String> refuseFriendApplication(@RequestBody AllowOrRefuseApplicationRequest request) {
        userService.refuseFriendApplication(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/alreadyRead")
    public ResponseEntity<String> alreadyRead(@RequestBody AllowOrRefuseApplicationRequest request) {
        userService.alreadyRead(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/getView")
    public ResponseEntity<Boolean> getView(@RequestBody GetMyPicturesRequest request) {
        Boolean res = userService.getView(request);
        return ResponseEntity.ok(res);
    }
}
