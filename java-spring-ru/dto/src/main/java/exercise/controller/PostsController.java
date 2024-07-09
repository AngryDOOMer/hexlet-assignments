package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getPosts(){
        var posts = postRepository.findAll();

        var result = posts.stream().map(this::postToDTO).toList();

        result.forEach(postDTO -> postDTO.setComments(
                commentRepository.findByPostId(postDTO.getId()).stream().map(this::commToDTO).toList()));

        return result;
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable long id){
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id "+id+" not found"));

        var comments = commentRepository.findByPostId(post.getId());

        var postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setBody(post.getBody());
        postDto.setComments(comments.stream().map(this::commToDTO).toList());
        postDto.setTitle(post.getTitle());

        return postDto;
    }

    private PostDTO postToDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        return dto;
    }

    private CommentDTO commToDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());

        return dto;
    }
}
// END
