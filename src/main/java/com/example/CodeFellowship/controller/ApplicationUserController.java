package com.example.CodeFellowship.controller;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
         List<ApplicationUser> applicationUser = applicationUserRepository.findAll();
            m.addAttribute("applicationUser", applicationUser);
        }
        return "home.html";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName,
                                   String lastName,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
                                   String bio) {
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

    @GetMapping("/myprofile")
    public String getUserProfile(Model model, Principal p)  {

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            List<Post> posts = applicationUser.getApplicationUserList();
            model.addAttribute("applicationUser", applicationUser);
            model.addAttribute("posts" , posts);
        }
        return "user-info.html";
    }

    @GetMapping("/user/{id}")
    public String editUserInfo_Mapping_Obj(Model m , @PathVariable(value = "id") Long id) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);

        m.addAttribute("applicationUser" , applicationUser.get());
        return "user-edit";
    }

    @PostMapping("/users")
    public RedirectView editUserInfo(@ModelAttribute("user") ApplicationUser applicationUser ,
                                     Principal p , RedirectAttributes redirectAttributes){
        if ((p != null) &&
                (p.getName().equals(applicationUser.getUsername()))) {
            applicationUserRepository.save(applicationUser);
        }else {
            redirectAttributes.addFlashAttribute("errorMessage" , "you cant edit another user information");
            return new RedirectView("/user/" +applicationUser.getId());
        }

        return new RedirectView("/myprofile");
    }

    public void authWithHttpServletRequest(String username, String password) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException{
        ResourceNotFoundException(String message){
            super(message);
        }
    }
}
