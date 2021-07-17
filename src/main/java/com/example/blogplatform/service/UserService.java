package com.example.blogplatform.service;

import com.example.blogplatform.model.User;
import com.example.blogplatform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.example.blogplatform.security.Role.USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    public void add(String username, String email, String password){
        User user = new User(username, email, password, null, null, false, USER, LocalDateTime.now(), UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.existsByEmail(email)){
            throw new IllegalStateException("Email taken");
        }
        userRepository.save(user);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void setActivation(User user){
        if (user.getActivationCode() != null){
            user.setActivationCode(null);
            user.setActivated(true);
        }
    }

    @Transactional
    public void update(User user, String userName, String password, String avatar, String about){
        if (userName != null && !user.getUserName().equals(userName)){
            user.setUserName(userName);
        }
        if (password != null && !user.getPassword().equals(password)){
            user.setPassword(passwordEncoder.encode(password));
        }
        if (avatar != null && !user.getAvatar().equals(avatar)){
            user.setAvatar(avatar);
        }
        if (about != null && !user.getAbout().equals(about)){
            user.setAvatar(avatar);
        }
    }

    public void sendMessage(User user) {
        if (user.getActivationCode() != null){
            String message = String.format("Hello, %s. Welcome to blog-platform application.\n" +
                    "Please, activate your account by clicking by http://localhost:8080/api/v1/activate/%s",
                    user.getUserName(), user.getActivationCode());
            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
