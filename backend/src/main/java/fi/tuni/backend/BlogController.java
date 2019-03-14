package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.DateTimeException;
import java.time.LocalDate;

@RestController
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @RequestMapping(value = "blogs/{id}", method = RequestMethod.GET)
    public Article getArticle(@PathVariable int id) {
        return blogRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find article with id:" + id));
    }

    @RequestMapping(value = "blogs", method = RequestMethod.GET)
    public Iterable<Article> getArticles() {
        return blogRepository.findAll();
    }

    @RequestMapping(value = "blogs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeArticle(@PathVariable int id) {
        try {
            blogRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            throw new CannotFindTargetException(id, "Cannot find article with id:" + id);
        }
    }

    @RequestMapping(value = "blogs", method = RequestMethod.POST)
    public ResponseEntity<Void> addArticle(@RequestParam("date") String dateCreated,
                           @RequestParam("title") String title,
                           @RequestParam("authorID") int authorID,
                           @RequestParam("content") String content,
                           UriComponentsBuilder builder) {
        LocalDate date = LocalDate.parse(dateCreated);
        Article article = new Article(date,title,content,authorID);

        blogRepository.save(article);

        UriComponents uriComponents = builder.path("blogs/{id}").buildAndExpand(article.getId());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, HttpStatus.CREATED);
    }
}
