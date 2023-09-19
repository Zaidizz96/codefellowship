package com.example.CodeFellowship.controller;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
    public String getLoginPage() {
        return "login.html";
    }


    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup.html";
    }


    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth, String bio) {
        try {
            String encryptedPassword = passwordEncoder.encode(password);
            ApplicationUser applicationUser = new ApplicationUser(username, encryptedPassword, firstName, lastName, dateOfBirth, bio);

            applicationUserRepository.save(applicationUser);

            authWithHttpServletRequest(username, password);

            System.out.println("User created successfully: " + username);


        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here, and possibly return an error response
            // Redirect to an error page or display an error message to the user
        }
        return new RedirectView("/");
    }


    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("applicationUser", applicationUser);
        }

        return "home.html";
    }

    public void authWithHttpServletRequest(String username, String password) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
