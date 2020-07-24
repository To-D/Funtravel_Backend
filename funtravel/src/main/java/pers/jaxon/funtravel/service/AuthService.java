package pers.jaxon.funtravel.service;

import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.repository.UserRepository;
import pers.jaxon.funtravel.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Object register(RegisterRequest request) {
        User userByName = userRepository.findByUsername(request.getUsername());
        User userByEmail = userRepository.findUserByEmail(request.getEmail());

        if (userByName != null){
            return "username duplicated";
        }

        if( userByEmail!=null){
            return "email duplicated";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword().trim());
        User newUser = new User(
                request.getUsername(),
                encodedPassword,
                request.getEmail()
        );
        userRepository.save(newUser);

        return newUser;

    }

    /**
     * @param userIdentifier: username or email from the front end
     * @param password: password from the front end
     * @return return token info to the front end(call generateToken func)
     * return value sent to : AuthController
     */
    public Object login(String userIdentifier, String password) {
        // Get user
        User user = userRepository.findByUsername(userIdentifier);
        if(user == null){
            user = userRepository.findUserByEmail(userIdentifier);
        }

        if (user == null) {
            return "notFound";
        } else {
            String psw = user.getPassword();
            if (passwordEncoder.matches(password, psw)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return user;
            } else {
                return "badCredentials";
            }
        }
    }
}
