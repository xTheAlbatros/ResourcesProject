package org.example.resourcesproject.article;

import lombok.RequiredArgsConstructor;

import org.example.resourcesproject.article.dto.AddArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping("/article")
    public Article addArticle(@RequestBody AddArticle addArticle){
        Article addedArticle = articleService.saveArticle(addArticle);
        return addedArticle;
    }

    @GetMapping("/article/{id}")
    public Article findArticle(@PathVariable Long id){
        return articleService.findArticle(id);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }


}

