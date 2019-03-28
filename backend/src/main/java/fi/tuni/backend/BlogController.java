package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("blogs/{id:\\d}")
    public Article getArticle(@PathVariable int id) {
        return blogRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find article with id:" + id));
    }

    @GetMapping("blogs")
    public Iterable<Article> getArticles() {
        return blogRepository.findAll();
    }

    @DeleteMapping("blogs/{id:\\d+}&{userId:\\d+}")
    public ResponseEntity<Void> removeArticle(@PathVariable int id, @PathVariable int userId) {
        try {
            Optional<User> user = userRepository.findById(userId);

            if(user.isPresent()) {
                if(user.get().isAdmin()) {
                    blogRepository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                throw new UserNotAdminException("Forbidden action, user with id " + userId + " is not admin");
            } else {
                throw new CannotFindTargetException(userId, "Cannot find user with id " + userId);
            }

        } catch (EmptyResultDataAccessException e) {
            throw new CannotFindTargetException(id, "Cannot find article with id:" + id);
        }
    }

    @PostMapping("blogs")
    public ResponseEntity<Void> addArticle(@RequestParam String title,
                           @RequestParam int authorId,
                           @RequestParam String content,
                           UriComponentsBuilder builder) {
        Article article = new Article(title,content,authorId);
        blogRepository.save(article);

        return getVoidResponseEntity(builder, article, HttpStatus.CREATED);
    }

    private ResponseEntity<Void> getVoidResponseEntity(UriComponentsBuilder builder, Article article, HttpStatus status) {

        UriComponents uriComponents = builder.path("blogs/{id}").buildAndExpand(article.getId());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, status);
    }

    @PostMapping("blogs/edit/{id:\\d}")
    public ResponseEntity<Void> editBlog(@PathVariable int id,
                                        @RequestParam String title,
                                        @RequestParam int authorId,
                                        @RequestParam String content,
                                        UriComponentsBuilder builder) {
        Optional<Article> optionalArticle = blogRepository.findById(id);
        if(optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setTitle(title);
            article.setAuthor(authorId);
            article.setContent(content);
            blogRepository.save(article);
            return getVoidResponseEntity(builder, article, HttpStatus.CREATED);
        } else {
            throw new CannotFindTargetException(id, "Couldn't modify id " + id + " because it doesn't exist");
        }
    }

    @GetMapping("blogs/search/{value}")
    public Iterable<Article> searchPost(@PathVariable String value) {
        return blogRepository.findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(value,value);
    }
}
