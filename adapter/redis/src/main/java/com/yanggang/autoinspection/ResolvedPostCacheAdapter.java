package com.yanggang.autoinspection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Component
public class ResolvedPostCacheAdapter implements ResolvedPostCachePort {

    private static final String CACHE_KEY_PREFIX = "resolved_post:v1:";
    private static final Long CACHE_EXPIRE_SECONDS = 60 * 60 * 24 * 7L; // 일주일

    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    public ResolvedPostCacheAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(ResolvedPost resolvedPost) {
        String jsonString = null;
        try {
            jsonString = customObjectMapper.writeValueAsString(resolvedPost);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        redisTemplate.opsForValue().set(
                generateCacheKey(resolvedPost.getId()),
                jsonString,
                Duration.ofSeconds(CACHE_EXPIRE_SECONDS));
    }

    @Override
    public ResolvedPost get(Long postId) {
        String jsonString = redisTemplate.opsForValue().get(generateCacheKey(postId));

        if (jsonString == null) {
            return null;
        }

        try {
            return customObjectMapper.readValue(jsonString, ResolvedPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long postId) {
        redisTemplate.delete(generateCacheKey(postId));
    }

    @Override
    public List<ResolvedPost> multiGet(List<Long> postIds) {
        List<String> jsonStringList = redisTemplate.opsForValue().multiGet(postIds.stream().map(this::generateCacheKey).toList());
        if (jsonStringList == null) {
            return List.of();
        }

        return jsonStringList.stream().filter(Objects::nonNull).map(jsonString -> {
            try {
                return customObjectMapper.readValue(jsonString, ResolvedPost.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private String generateCacheKey(Long postId) {
        return CACHE_KEY_PREFIX + postId;
    }
}
