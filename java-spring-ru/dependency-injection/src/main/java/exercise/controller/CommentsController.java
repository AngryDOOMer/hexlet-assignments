package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getPosts() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment getPost(@PathVariable long id){
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createPost(@RequestBody Comment page){
        commentRepository.save(page);
        return page;
    }

    @PutMapping("/{id}")
    public Comment updatePost(@RequestBody Comment page, @PathVariable long id){
        var post = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
        post.setBody(page.getBody());
        post.setPostId(page.getPostId());
        return commentRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable long id){
        commentRepository.deleteById(id);
    }
}
// END
