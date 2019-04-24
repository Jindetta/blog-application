package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@RestController
@RequestMapping("/api")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeRepository likeRepository;

    @GetMapping("blogs/{id:\\d+}")
    public Article getArticle(@PathVariable int id) {
        return blogRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find article with id:" + id));
    }

    @GetMapping("blogs")
    public Iterable<Article> getArticles() {
        return blogRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("blogs/{id:\\d+}")
    public ResponseEntity<Void> removeArticle(@PathVariable int id, Authentication auth) {
        try {
            User author = userRepository.findUserByUsername(auth.getName())
                    .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

            if(author.isAdmin()) {
                blogRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            throw new UserNotAdminException("Forbidden action, user with username " +  auth.getName() + " is not admin");


        } catch (EmptyResultDataAccessException e) {
            throw new CannotFindTargetException(id, "Cannot find article with id:" + id);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("blogs")
    public ResponseEntity<Void> addArticle(String title, String content, Authentication auth) {
        User author = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        if(author.isAdmin()) {
            Article article = new Article(title, content, author);
            blogRepository.save(article);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        throw new UserNotAdminException("Forbidden action, user with id " + auth.getName() + " is not a admin");
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("blogs/edit/{id:\\d+}")
    public ResponseEntity<Void> editBlog(@PathVariable int id, String newTitle, String newContent, Authentication authentication, UriComponentsBuilder builder) {
        Article article = blogRepository.findById(id).orElseThrow(
                () -> new CannotFindTargetException(id, "Couldn't modify id " + id + " because it doesn't exist"));
        User user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(
                () -> new CannotFindTargetException(0, "Couldn't find user " + authentication.getName()));

        if (user.isAdmin()) {
            article.setTitle(newTitle);
            article.setContent(newContent);
            blogRepository.save(article);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        throw new UserNotAdminException("Forbidden action, user " + authentication.getName() + " is not a admin");
    }

    @GetMapping("blogs/search/{value}")
    public Iterable<Article> searchPost(@PathVariable String value) {
        return blogRepository.findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByDateAsc(value,value);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("blogs/comments/{commentId:\\d+}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId, Authentication auth) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CannotFindTargetException(commentId, "Cannot find comment with id: " + commentId));

        User user = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        if (user.isAdmin() || user.equals(comment.getAuthor())) {
            commentRepository.delete(comment);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("blogs/comments")
    public ResponseEntity<Void> addComment(@RequestParam String comment, @RequestParam int articleId, Authentication auth) {
        User author = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        Article article = blogRepository.findById(articleId)
                .orElseThrow(() -> new CannotFindTargetException(articleId, "Cannot find article with id: " + articleId));

        commentRepository.save(new Comment(author,article,comment));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("blogs/{articleId:\\d+}/comments")
    public Iterable<CommentLikeResponse> getBlogComments(@PathVariable int articleId, Authentication auth) {
        Optional<User> user = userRepository.findUserByUsername(auth.getName());
        Iterable<Comment> comments = commentRepository.findByArticleId(articleId);
        Iterable<CommentLikeResponse> commentLikeResponses = new ArrayList<>();

        comments.forEach(comment ->((ArrayList<CommentLikeResponse>) commentLikeResponses).add(createCommentLikeResponse(comment, user)));
        return commentLikeResponses;
    }

    @GetMapping("blogs/comments/{commentId:\\d+}")
    public CommentLikeResponse getComment(@PathVariable int commentId, Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CannotFindTargetException(0, "Cannot find comment with id: " + commentId));
        Optional<User> user = userRepository.findUserByUsername(auth.getName());

        return createCommentLikeResponse(comment,user);
    }

    private CommentLikeResponse createCommentLikeResponse(Comment comment, Optional<User> user) {
        CommentLikeResponse response = new CommentLikeResponse();

        List<LikeStatus> likes = likeRepository.findLikeStatusesByCommentId(comment.getId());

        response.setComment(comment);
        response.setLikes(likes.size());

        if(user.isPresent()) {
            for (LikeStatus like : likes) {
                if (user.get().getId() == like.getLikerId()) {
                    response.setHasLiked(true);
                    break;
                }
            }
        } else {
            response.setHasLiked(false);
        }

        return response;
    }

    @GetMapping("blogs/comments")
    public Iterable<Comment> getComments() {
        return commentRepository.findAll();
    }


    @GetMapping("blogs/comments/likes")
    public Iterable<LikeStatus> getLikes() {
        return likeRepository.findAll();
    }

    @GetMapping("blogs/comments/likes/{commentId:\\d+}")
    public boolean hasLiked(@PathVariable int commentId,  Authentication auth) {
        User user = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        Optional<LikeStatus> status = likeRepository.findByLikerIdAndCommentId(commentId, user.getId());

        return status.isPresent();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("blogs/comments/likes/{commentId:\\d+}")
    public ResponseEntity<Void> postLike(@PathVariable int commentId, Authentication auth) {
        User user = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        commentRepository.findById(commentId).orElseThrow(() -> new CannotFindTargetException(0, "Cannot find comment with id: " + commentId));

        likeRepository.save(new LikeStatus(user.getId(), commentId));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("blogs/comments/likes/{commentId:\\d+}")
    public  ResponseEntity<Void> deleteLike(@PathVariable int commentId, Authentication auth) {
        User liker = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        LikeStatus status = likeRepository.findByLikerIdAndCommentId(liker.getId(), commentId)
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find comment with id: " + commentId));

        likeRepository.delete(status);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private int getLikeCount(int commentId) {
        return likeRepository.findLikeStatusesByCommentId(commentId).size();
    }
}
