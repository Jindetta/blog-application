package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/blogs/comments")
    public ResponseEntity<Void> addComment(@RequestParam Comment comment) {
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/blogs/comments")
    public Iterable<Comment> getComments() {
        return commentRepository.findAll();
    }
}
