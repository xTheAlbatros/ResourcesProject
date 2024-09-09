package org.example.resourcesproject.article;

import org.example.resourcesproject.article.dto.AddArticle;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ArticleMapper {

    Article mapToArticle(AddArticle addArticle);
}
