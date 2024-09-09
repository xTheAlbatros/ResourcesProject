package org.example.resourcesproject.article.validator;

import org.example.resourcesproject.article.dto.AddArticle;
import org.example.resourcesproject.article.exception.ArticleBadRequestException;

public class ArticleValidator {

    public static void check(AddArticle addArticle) {

        if (addArticle.getName() == null || addArticle.getName().trim().isEmpty()) {
            throw new ArticleBadRequestException("Name cannot be null or empty");
        }

        if (addArticle.getContent() == null || addArticle.getContent().trim().isEmpty()) {
            throw new ArticleBadRequestException("Content cannot be null or empty");
        }

        if (addArticle.getCategory() == null || addArticle.getCategory().trim().isEmpty()) {
            throw new ArticleBadRequestException("Category cannot be null or empty");
        }

    }
}
