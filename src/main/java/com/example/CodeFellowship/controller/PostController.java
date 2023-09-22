package com.example.CodeFellowship.controller;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import com.example.CodeFellowship.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/addNew-Post")
    public String addNewPost(){
        return "new-post";
    }

    @PostMapping("/savePost")
    public RedirectView savePost( Principal p , String body){
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            LocalDate createdAt = LocalDate.now();
            Post post = new Post(body , createdAt , applicationUser );
            postRepository.save(post);
        }
    return new RedirectView("/myprofile");
    }
}
