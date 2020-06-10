package com.suulola.forum.service;

import com.suulola.forum.dto.RegisterRequest;
import com.suulola.forum.exception.ForumCustomException;
import com.suulola.forum.model.NotificationEmail;
import com.suulola.forum.model.User;
import com.suulola.forum.model.VerificationToken;
import com.suulola.forum.repository.UserRepository;
import com.suulola.forum.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.suulola.forum.util.Constant.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;


  @Transactional
  public void signup(RegisterRequest registerRequest) {
   User userExist = userRepository.findByUsername(registerRequest.getUsername()).get();

    if(userExist.getUsername().isEmpty()) {
      new ForumCustomException("User already Exist");
   }

    User newUser = new User();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setEnabled(false);
    newUser.setCreated(Instant.now());

    userRepository.save(newUser);

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
    System.out.println("hfdsjk");
    fetchUserAndEnable(verificationTokenOptional.get());
    System.out.println("got here 1");
  }

  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    System.out.println("got here 2");
    System.out.println(username);
    User user = userRepository.findByUsername(username).orElseThrow(() ->  new ForumCustomException("User Not found with id - " + username));
    System.out.println(user.toString());
    System.out.println("got here 3");

    user.setEnabled(true);
    System.out.println("got here 4");

    userRepository.save(user);
    System.out.println("got here 5");

  }
}
