package com.yanggang.autoinspection.metadata;

import com.yanggang.autoinspection.MetadataPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetadataAdapter implements MetadataPort {

    private final MetadataClient metadataClient;

    public MetadataAdapter(MetadataClient metadataClient) {
        this.metadataClient = metadataClient;
    }

    @Override
    public String getCategoryNameByCategoryId(Long categoryId) {
        MetadataClient.CategoryResponse categoryResponse =
                metadataClient.getCategoryById(categoryId);

        if (categoryResponse == null) {
            return null;
        }

        return categoryResponse.getName();
    }

    @Override
    public String getUserNameByUserId(Long userId) {
        MetadataClient.UserResponse userResponse = metadataClient.getUserById(userId);

        if (userResponse == null) {
            return null;
        }

        return userResponse.getName();
    }

    @Override
    public List<Long> listFollowerIdsByUserID(Long userId) {
        return metadataClient.getFollowerIdsByUserId(userId);
    }
}
