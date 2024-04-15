package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.AutoInspectionResult;
import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PostInspectService implements PostInspectUsecase {

    private final MetadataPort metadataPort;
    private final PostAutoInspectPort postAutoInspectPort;

    public PostInspectService(MetadataPort metadataPort,
                              PostAutoInspectPort postAutoInspectPort) {
        this.metadataPort = metadataPort;
        this.postAutoInspectPort = postAutoInspectPort;
    }

    @Override
    public InspectedPost inspectAndGetIfValid(Post post) {
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        AutoInspectionResult inspectedResult = postAutoInspectPort.inspect(post, categoryName);

        if (inspectedResult.isBadStatus()) {
            return InspectedPost.generate(
                    post,
                    categoryName,
                    Arrays.asList("all"));
        }

        return InspectedPost.generate(
                post,
                categoryName,
                Arrays.asList(inspectedResult.getTags())
        );
    }
}
