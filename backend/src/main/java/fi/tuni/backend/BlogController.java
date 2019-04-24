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
 * Controller for getting blog data.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@RestController
@RequestMapping("/api")
public class BlogController {

    /**
     * Repository for accessing blog database.
     */
    @Autowired
    BlogRepository blogRepository;

    /**
     * Repository for accessing users database.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository for accessing comments database.
     */
    @Autowired
    CommentRepository commentRepository;

    /**
     * Repository for accessing comments likes database.
     */
    @Autowired
    LikeRepository likeRepository;

    /**
     * Return blog with id given in URL.
     *
     * @param id Id of blog to return.
     * @return Blog post with given Id.
     */
    @GetMapping("blogs/{id:\\d+}")
    public Article getArticle(@PathVariable int id) {
        return blogRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find article with id:" + id));
    }

    /**
     * Gets all blog posts from database.
     * @return All blog posts in database.
     */
    @GetMapping("blogs")
    public Iterable<Article> getArticles() {
        return blogRepository.findAll();
    }

    /**
     * Removes article with given Id.
     *
     * @param id Id of blog to delete.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Status NO_CONTENT if successful, throws UserNotAdminException if user was not an admin or
     * CannotFindTargetException if target was not found.
     */
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

    /**
     * Adds article to blog database.
     *
     * @param title Title of article.
     * @param content Content of article.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Status CREATED if successful. Throws exception if user is not an admin.
     */
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

    /**
     * Edits blog with given id.
     *
     * @param id Id of blog to be edited.
     * @param newTitle Title to replace old title.
     * @param newContent Content to replace old content.
     * @param authentication Authentication provided by Spring. Used to determine user.
     * @return Status CREATED if successful. Throws UserNotAdminException if user is not admin.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("blogs/edit/{id:\\d+}")
    public ResponseEntity<Void> editBlog(@PathVariable int id, String newTitle, String newContent, Authentication authentication) {
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

    /**
     * Returns all blog posts containing given parameter in posts title or content.
     *
     * @param value String to search blog posts with.
     * @return All blog posts containing given parameter.
     */
    @GetMapping("blogs/search/{value}")
    public Iterable<Article> searchPost(@PathVariable String value) {
        return blogRepository.findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByDateAsc(value,value);
    }

    /**
     * Deletes comment from repository.
     *
     * @param commentId Comments Id to remove.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Status NO_CONTENT if successful, throws UserNotAdminException if user was not an admin or
     * CannotFindTargetException if target was not found.
     */
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

    /**
     * Adds comment to comment database.
     *
     * @param comment Content of comment.
     * @param articleId Id of article to add comment to.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Status CREATED if successful. Throws CannotFindTargetException if author or blog post cannot be found.
     */
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

    /**
     * Return all blog posts comments.
     *
     * @param articleId blog posts comments to get.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Comments of blog post with id provided in paramater.
     */
    @GetMapping("blogs/{articleId:\\d+}/comments")
    public Iterable<CommentLikeResponse> getBlogComments(@PathVariable int articleId, Authentication auth) {
        Optional<User> user = auth != null ? userRepository.findUserByUsername(auth.getName()) : Optional.empty();
        Iterable<Comment> comments = commentRepository.findByArticleId(articleId);
        Iterable<CommentLikeResponse> commentLikeResponses = new ArrayList<>();

        comments.forEach(comment ->((ArrayList<CommentLikeResponse>) commentLikeResponses).add(createCommentLikeResponse(comment, user)));
        return commentLikeResponses;
    }

    /**
     * Return comment of a blog containing information if user has liked the comment and amount of likes.
     *
     * @param commentId Comment id to get.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Content of comment and information if user has liked the comment and amount of likes.
     */
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

    /**
     * Gets all comments in comment repository.
     *
     * @return All comments in comment database.
     */
    @GetMapping("blogs/comments")
    public Iterable<Comment> getComments() {
        return commentRepository.findAll();
    }


    /**
     * Return all likes in like repository.
     *
     * @return Likes in like database.
     */
    @GetMapping("blogs/comments/likes")
    public Iterable<LikeStatus> getLikes() {
        return likeRepository.findAll();
    }

    /**
     * Returns true if user has liked a blog post. False if not. User is determined by Authentication provided by Spring.
     *
     * @param commentId Id of comment to check if user has liked the comment.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return True if has liked. False if not.
     */
    @GetMapping("blogs/comments/likes/{commentId:\\d+}")
    public boolean hasLiked(@PathVariable int commentId,  Authentication auth) {
        User user = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        Optional<LikeStatus> status = likeRepository.findByLikerIdAndCommentId(commentId, user.getId());

        return status.isPresent();
    }

    /**
     * Adds like to a comment.
     *
     * @param commentId Id of comment to like.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return Status CREATED if successful. Throws CannotFindTargetException if authentication user or comment cannot be found.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("blogs/comments/likes/{commentId:\\d+}")
    public ResponseEntity<Void> postLike(@PathVariable int commentId, Authentication auth) {
        User user = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new CannotFindTargetException(0, "Cannot find user with username: " + auth.getName()));

        commentRepository.findById(commentId).orElseThrow(() -> new CannotFindTargetException(0, "Cannot find comment with id: " + commentId));

        likeRepository.save(new LikeStatus(user.getId(), commentId));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Removes like from a comment.
     *
     * @param commentId Id of comment to remove like.
     * @param auth Authentication provided by Spring. Used to determine user.
     * @return
     */
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
}
