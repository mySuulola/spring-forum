package com.suulola.forum.service;

import com.suulola.forum.dto.AuthenticationResponse;
import com.suulola.forum.dto.LoginRequest;
import com.suulola.forum.dto.RegisterRequest;
import com.suulola.forum.exception.ForumCustomException;
import com.suulola.forum.model.NotificationEmail;
import com.suulola.forum.model.User;
import com.suulola.forum.model.VerificationToken;
import com.suulola.forum.repository.UserRepository;
import com.suulola.forum.repository.VerificationTokenRepository;
import com.suulola.forum.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.suulola.forum.util.Constant.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    MailContentBuilder mailContentBuilder;

    @Autowired
    MailService mailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


  @Transactional
  public void signup(RegisterRequest registerRequest) {
   Optional<User> userExist = userRepository.findByUsername(registerRequest.getUsername());
      System.out.println(userExist);

      if(userExist.isPresent()) {
          throw new ForumCustomException("User already Exist");
      }

    User newUser = new User();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setEnabled(false);
    newUser.setCreated(Instant.now());

    userRepository.save(newUser);
//    log.info("User Registered Successfully, Sending Authentication Email");

    String token = generateVerificationToken(newUser);
    String message = mailContentBuilder.build("Thank you for signing up to the forum. Please click on the url below to activate your account: " + ACTIVATION_EMAIL+ "/" + token);
    mailService.sendMail(new NotificationEmail("Please Activate your account", newUser.getEmail(), message));
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);
//        verificationToken.setExpiryDate();
    verificationTokenRepository.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
    verificationTokenOptional.orElseThrow(() -> new ForumCustomException("Invalid Token"));
    fetchUserAndEnable(verificationTokenOptional.get());
  }

  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username).orElseThrow(() ->  new ForumCustomException("User Not found with id - " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
  }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
}
