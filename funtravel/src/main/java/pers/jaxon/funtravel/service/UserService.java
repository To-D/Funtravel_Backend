package pers.jaxon.funtravel.service;

import pers.jaxon.funtravel.controller.request.*;
import pers.jaxon.funtravel.domain.Message;
import pers.jaxon.funtravel.domain.User;
import pers.jaxon.funtravel.repository.MessageRepository;
import pers.jaxon.funtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
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
        Date time = new Date();
        newUser.setRegisterTime(time);
        newUser.setView(1);
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

    public void setView(SetViewRequest request) {
        String username = request.getUsername();
        int view = request.getView();
        User user = userRepository.findByUsername(username);
        user.setView(view);
        userRepository.save(user);
    }

    public Map<String,Object> searchUser(SearchUserRequest request) {
        Map<String,Object> res = new HashMap<>();

        String username = request.getUsername();
        String searchName = request.getSearchName();
        User user = userRepository.findByUsername(username);
        User searchUser = userRepository.findByUsername(searchName);

        Set<User> friends = user.getFriends();
        for(User friend:friends){
            if(friend == searchUser){
                res.put("alreadyFriend",true);
            }
        }

        res.put("searchResult",searchUser);

        return res;
    }

    public String addFriend(AddNewFriendRequest request) {
        String username = request.getUsername();
        String friendName = request.getFriendName();
        User friend = userRepository.findByUsername(friendName);

        // Check for duplicate request
        Set<Message> messages = friend.getMessages();
        for(Message message : messages){
            if(message.getSourceName().equals(username)){
                return "duplicate";
            }
        }

        // Create new message
        Message message = new Message();
        message.setSourceName(username);
        Date time = new Date();
        message.setSent_time(time);
        String content = "Hey, "+username + " sent a friend application message to you~";
        message.setContent(content);
        message.setType("application");

        // Build relationship
        message.setTarget(friend);
        friend.getMessages().add(message);
        userRepository.save(friend);
        messageRepository.save(message);
        return "success";
    }

    public String hasMessage(GetMyPicturesRequest request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        if(user.getMessages().size()>0){
            return "yes";
        }else{
            return "no";
        }
    }

    public Map<String,Object> getMessage(GetMyPicturesRequest request) {
        Map<String,Object> res = new HashMap<>();

        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        res.put("user",user);
        Set<Message> messages = user.getMessages();
        Set<User> friends = user.getFriends();
        res.put("friends",friends);
        if(messages.size()>0){
            res.put("messages",user.getMessages());
            res.put("hasMessage",true);
        }else{
            res.put("hasMessage",false);
        }
        return res;
    }

    public void allowFriendApplication(AllowOrRefuseApplicationRequest request) {
        Long id = request.getId();
        Message message = messageRepository.findById(id).get();
        String sourceName = message.getSourceName();
        User source = userRepository.findByUsername(sourceName);
        User target = message.getTarget();

        // Build friend relation
        source.getFriends().add(target);
        target.getFriends().add(source);

        // Notify source
        Message notify = new Message();
        notify.setSourceName(target.getUsername());
        Date time = new Date();
        notify.setSent_time(time);
        String content = target.getUsername() +" has accepted your friend application";
        notify.setContent(content);
        notify.setType("notify");
        notify.setTarget(source);
        source.getMessages().add(notify);

        userRepository.save(source);
        userRepository.save(target);
        messageRepository.delete(message);
        messageRepository.save(notify);
    }


    public void refuseFriendApplication(AllowOrRefuseApplicationRequest request) {
        Long id = request.getId();
        // Get source and target
        Message message = messageRepository.findById(id).get();
        String sourceName = message.getSourceName();
        User source = userRepository.findByUsername(sourceName);
        User target = message.getTarget();

        // Notify source
        Message notify = new Message();
        notify.setSourceName(target.getUsername());
        Date time = new Date();
        notify.setSent_time(time);
        String content = target.getUsername() +" has refused your friend application";
        notify.setContent(content);
        notify.setType("notify");
        notify.setTarget(source);
        source.getMessages().add(notify);

        userRepository.save(source);
        messageRepository.delete(message);
        messageRepository.save(notify);
    }

    public void alreadyRead(AllowOrRefuseApplicationRequest request) {
        Long id = request.getId();
        Message message = messageRepository.findById(id).get();
        messageRepository.delete(message);
    }

    public Boolean getView(GetMyPicturesRequest request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        if(user.getView() == 1){
            return true;
        }else{
            return false;
        }
    }
}
