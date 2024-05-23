package com.yanggang.autoinspection.contentsearch;

import com.yanggang.autoinspection.PostSearchPort;
import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostSearchAdapter implements PostSearchPort {

    private final PostSearchRepository postSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public PostSearchAdapter(PostSearchRepository postSearchRepository,
                             ElasticsearchOperations elasticsearchOperations) {
        this.postSearchRepository = postSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void indexPost(InspectedPost post) {
        postSearchRepository.save(toDocument(post));
    }

    @Override
    public void deletePost(Long id) {
        postSearchRepository.deleteById(id);
    }

    @Override
    public List<Long> searchPostIdsByKeyword(String keyword, int pageNumber, int pageSize) {
        if (keyword == null || keyword.isBlank() || pageNumber < 0 || pageSize < 0) {
            return List.of();
        }

        Query query = buildQuery(keyword, pageNumber, pageSize);
        SearchHits<PostDocument> searchResult = elasticsearchOperations.search(query, PostDocument.class);

        List<Long> searchedPostIds = searchResult.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(PostDocument::getId)
                .toList();

        return searchedPostIds;
    }

    private Query buildQuery(String keyword, int pageNumber, int pageSize) {
        Criteria criteria = new Criteria("title").matches(keyword)
                .or(new Criteria("content").matches(keyword))
                .or(new Criteria("categoryName").is(keyword))
                .or(new Criteria("tags").is(keyword));

        return new CriteriaQuery(criteria)
                .setPageable(PageRequest.of(pageNumber, pageSize));
    }

    public PostDocument toDocument(InspectedPost inspectedPost) {
        Post post = inspectedPost.getPost();
        return PostDocument.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .categoryName(inspectedPost.getCategoryName())
                .tags(inspectedPost.getAutoGeneratedTags())
                .indexedDate(LocalDateTime.now())
                .build();
    }
}