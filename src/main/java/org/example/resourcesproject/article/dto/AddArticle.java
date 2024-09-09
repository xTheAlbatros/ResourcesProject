package org.example.resourcesproject.article.dto;

import lombok.Data;

@Data
public class AddArticle {
    private String name;

    private String content;

    private String category;
}
