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
import java.util.Set;

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
            String username = p.getName();
         List<ApplicationUser> applicationUser = applicationUserRepository.findByUsernameNot(username);
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
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setUsername(username);
            applicationUser.setPassword(encryptedPassword);
            applicationUser.setFirstName(firstName);
            applicationUser.setLastName(lastName);
            applicationUser.setDateOfBirth(dateOfBirth);
            applicationUser.setBio(bio);
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

    @GetMapping("/my-profile")
    public String getUserProfile(Model model, Principal p)  {

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            Set<Post> posts = applicationUser.getPosts();
            model.addAttribute("applicationUser", applicationUser);
            model.addAttribute("posts" , posts);
        }
        return "user-info.html";
    }

    @GetMapping("/user/{id}")
    public String getUserInfo(Model m , @PathVariable(value = "id") Long id) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);

        m.addAttribute("applicationUser" , applicationUser.get());
        return "user-edit";
    }

    @PostMapping("/users")
    public RedirectView editUser(@ModelAttribute("user") ApplicationUser applicationUser ,
                                     Principal p , RedirectAttributes redirectAttributes){
        if ((p != null) &&
                (p.getName().equals(applicationUser.getUsername()))) {
            applicationUserRepository.save(applicationUser);
        }else {
            redirectAttributes.addFlashAttribute("errorMessage" , "you cant edit another user information");
            return new RedirectView("/user/" +applicationUser.getId());
        }

        return new RedirectView("/my-profile");
    }

    @GetMapping("/follow/{userId}")
    public RedirectView followUser(Model m,Principal p, @PathVariable(value = "userId") Long userId){
        if (p != null){
            String username = p.getName();
            ApplicationUser myApplicationUser = applicationUserRepository.findByUsername(username);
            ApplicationUser followedApplicationUser = applicationUserRepository.findById(userId).orElseThrow();
            myApplicationUser.getFollow().add(followedApplicationUser);
            applicationUserRepository.save(myApplicationUser);
        }
        return new RedirectView("/userProfile/" + userId);
    }

    @GetMapping("/userProfile/{userId}")
    public String getUserProfile(Model m , Principal p , @PathVariable(value = "userId") Long userId){
        if (p != null){
            String username = p.getName();
            ApplicationUser myApplicationUser = applicationUserRepository.findByUsername(username);
            ApplicationUser applicationUser = applicationUserRepository.findById(userId).orElseThrow();

            boolean isFollowed = myApplicationUser.getFollow().stream().anyMatch(user -> user.getId() == userId) ;

            Set<Post> posts = applicationUser.getPosts();
            m.addAttribute("applicationUser" , applicationUser);
            m.addAttribute("posts" , posts);
            m.addAttribute("isFollowed" , isFollowed);
        }
        return "user-profile.html";
    }

    @GetMapping("/feed")
    public String getFeedPage(Principal p , Model m){
        if (p != null){
            String username = p.getName();
            ApplicationUser myApplicationUser = applicationUserRepository.findByUsername(username);
            Set<ApplicationUser> followedUser = myApplicationUser.getFollow();
            m.addAttribute("followedUser" , followedUser);
        }
        return "feed.html";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException{
        ResourceNotFoundException(String message){
            super(message);
        }
    }

    public void authWithHttpServletRequest(String username, String password) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
