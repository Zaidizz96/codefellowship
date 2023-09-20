package com.example.CodeFellowship.repositories;

import com.example.CodeFellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post , Long> {

}
