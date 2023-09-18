package com.example.CodeFellowship.controller;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class ApplicationUserController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/")
    public String getHomePage(){
        return "home.html";
    }

    @GetMapping("/signUp")
    public String getSignUpPage(){
        return "signup.html";
    }


    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, String bio) {

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);

        String encryptedPassword = passwordEncoder.encode(password);
        applicationUser.setPassword(encryptedPassword);

        applicationUserRepository.save(applicationUser);

        authWithHttpServletRequest(username,password);

        return new RedirectView("/");
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m){

        if(p != null){
            String username = p.getName();
            ApplicationUser applicationUser= applicationUserRepository.findByUsername(username);
            m.addAttribute("applicationUser", applicationUser);
        }

        return "home.html";
    }
    public void authWithHttpServletRequest(String username, String password){

        try {
            request.login(username, password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
}
