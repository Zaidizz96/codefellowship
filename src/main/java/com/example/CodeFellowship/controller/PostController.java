package com.example.CodeFellowship.controller;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import com.example.CodeFellowship.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/addNew-Post/{id}")
    public String addNewSong(Model model , @PathVariable(value = "id") Long id){

        Optional<ApplicationUser> applicationUser =
                Optional.of(applicationUserRepository.findById(id).orElseThrow());

        Post userPost = new Post();
        userPost.setApplicationUser(applicationUser.get());
        model.addAttribute("userPost" , userPost);

        return "new-post";
    }

    @PostMapping("/savePost")
    public RedirectView savePost(@ModelAttribute(value = "post") Post post){
        postRepository.save(post);
    return new RedirectView("/getUser-posts");
    }

    @GetMapping("/getUser-posts")
    public String getUserPosts(Model model ,  Principal p) {
        String userName = null;
        if (p != null) {
            userName = p.getName();
        }
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(userName);
        Optional<Post> posts = postRepository.findById(applicationUser.getId());
        model.addAttribute("posts" , posts);
        return "user-info";

    }
}
