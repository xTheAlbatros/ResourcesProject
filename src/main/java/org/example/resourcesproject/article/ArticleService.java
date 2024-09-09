package org.example.resourcesproject.article;

import lombok.RequiredArgsConstructor;
import org.example.resourcesproject.article.dto.AddArticle;
import org.example.resourcesproject.article.exception.ArticleNotFoundException;
import org.example.resourcesproject.article.validator.ArticleValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }


    public Article saveArticle(AddArticle addArticle){
        ArticleValidator.check(addArticle);
        Article addedArticle = articleMapper.mapToArticle(addArticle);
        articleRepository.save(addedArticle);
        return addedArticle;
    }

    public void deleteArticle(Long id){
        Optional<Article> foundArticle = articleRepository.findById(id);
        if(foundArticle.isPresent()){
            articleRepository.deleteById(id);
        }
        else{
            throw new ArticleNotFoundException();
        }
    }

    public Article findArticle(long id){
        Optional<Article> foundArticle = articleRepository.findById(id);
        if(foundArticle.isPresent()){
            return foundArticle.get();
        }
        else{
            throw new ArticleNotFoundException();
        }
    }
}