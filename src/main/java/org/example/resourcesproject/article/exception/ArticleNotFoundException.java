package org.example.resourcesproject.article.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException() {
        super("Article not found");
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}

