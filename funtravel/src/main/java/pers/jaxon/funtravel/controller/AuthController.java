package pers.jaxon.funtravel.controller;

import pers.jaxon.funtravel.domain.TokenProcessor;
import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.exception.UsernameHasBeenRegisteredException;
import pers.jaxon.funtravel.exception.EmailHasBeenRegisteredException;
import pers.jaxon.funtravel.security.jwt.JwtTokenUtil;
import pers.jaxon.funtravel.service.AuthService;
import pers.jaxon.funtravel.controller.request.LoginRequest;
import pers.jaxon.funtravel.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private AuthService authService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Object res = authService.register(request);
//        if(res.equals("username duplicated")){
//            throw new UsernameHasBeenRegisteredException(request.getUsername());
//        }
//        if(res.equals("email duplicated")){
//            throw new EmailHasBeenRegisteredException(request.getEmail());
//        }
        if(res.equals("username duplicated") || res.equals("email duplicated")){
            return ResponseEntity.ok((String)res);
        }


        User user = (User)res;
        String token = jwtTokenUtil.generateToken(user);
        TokenProcessor t = new TokenProcessor();
        t.setToken(token);
        return ResponseEntity.ok(t.getToken());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenProcessor> login(@RequestBody LoginRequest request) {
        Object msg = authService.login(request.getUserIdentifier(), request.getPassword());
        if(msg.equals("notFound")) {
            throw new UsernameNotFoundException(request.getUserIdentifier());
        }else if (msg.equals("badCredentials")){
            throw new BadCredentialsException(request.getPassword());
        } else{
            User user = (User)msg;
            String token = jwtTokenUtil.generateToken(user);
            TokenProcessor t = new TokenProcessor();
            t.setToken(token);
            return ResponseEntity.ok(t);
        }
    }
}
