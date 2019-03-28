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

    @DeleteMapping("blogs/{id:\\d+}")
    public ResponseEntity<Void> removeArticle(@PathVariable int id, @RequestParam int userId) {
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
    public ResponseEntity<Void> addArticle(Article article, UriComponentsBuilder builder) {
        Optional<User> user = userRepository.findById(article.getAuthor());

        if(user.isPresent()) {
            if(user.get().isAdmin()) {
                blogRepository.save(article);
                return getVoidResponseEntity(builder, article, HttpStatus.CREATED);
            }

            throw new UserNotAdminException("Forbidden action, user with id " + article.getAuthor() + " is not a admin");
        }
        throw new CannotFindTargetException(article.getAuthor(), "Cannot find user with id:" + article.getAuthor());
    }

    private ResponseEntity<Void> getVoidResponseEntity(UriComponentsBuilder builder, Article article, HttpStatus status) {

        UriComponents uriComponents = builder.path("blogs/{id}").buildAndExpand(article.getId());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, status);
    }

    @PostMapping("blogs/edit/{id:\\d}")
    public ResponseEntity<Void> editBlog(@PathVariable int id, Article newArticle, @RequestParam int editorId, UriComponentsBuilder builder) {

        Optional<Article> optionalArticle = blogRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(newArticle.getAuthor());
        Optional<User> optionalEditor = userRepository.findById(editorId);

        if(optionalUser.isPresent()) {
            if(optionalEditor.isPresent()) {
                if (optionalEditor.get().isAdmin()) {
                    if (optionalArticle.isPresent()) {

                        Article article = optionalArticle.get();

                        article.setTitle(newArticle.getTitle());
                        article.setAuthor(newArticle.getAuthor());
                        article.setContent(newArticle.getContent());

                        blogRepository.save(article);

                        return getVoidResponseEntity(builder, article, HttpStatus.CREATED);
                    }

                    throw new CannotFindTargetException(id, "Couldn't modify id " + id + " because it doesn't exist");
                }

                throw new UserNotAdminException("Forbidden action, user with id " + editorId + " is not a admin");
            }

            throw new CannotFindTargetException(editorId, "Cannot find user with id " + editorId);
        }

        throw new CannotFindTargetException(newArticle.getAuthor(), "Cannot find user with id" + newArticle.getAuthor());
    }

    @GetMapping("blogs/search/{value}")
    public Iterable<Article> searchPost(@PathVariable String value) {
        return blogRepository.findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(value,value);
    }
}
