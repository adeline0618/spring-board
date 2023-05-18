package com.study.springboard.repository;

import com.study.springboard.config.JpaConfig;
import com.study.springboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class )
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select T")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //G

        //W
        List<Article> articles = articleRepository.findAll();
        //T
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert T")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        //G
        long previousCount = articleRepository.count();
        Article article = Article.of("insertT","insertT","#insertT");
        //W
        Article savedArticle = articleRepository.save(article);
        //T
        assertThat(
                articleRepository.count()
        )
                .isEqualTo(previousCount+1);

    }

    @DisplayName("update T")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        //G
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#updateT";
        article.setHashtag(updatedHashtag);
        //W
        Article savedArticle = articleRepository.saveAndFlush(article);
        //T
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashtag);

    }
    @DisplayName("delete T")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        //G
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();

        int deletedCommentSize=article.getArticleComments().size();

        //W
        articleRepository.delete(article);

        //T
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount-deletedCommentSize);

    }
}