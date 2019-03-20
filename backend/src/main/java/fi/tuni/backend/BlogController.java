package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("blogs/{id:\\d}")
    public Article getArticle(@PathVariable int id) {
        return blogRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find article with id:" + id));
    }

    @GetMapping("blogs")
    public Iterable<Article> getArticles() {
        return blogRepository.findAll();
    }

    @DeleteMapping("blogs/{id:\\d}")
    public ResponseEntity<Void> removeArticle(@PathVariable int id) {
        try {
            blogRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

        UriComponents uriComponents = builder.path("blogs/{id}").buildAndExpand(article.getId());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, HttpStatus.CREATED);
    }
}
