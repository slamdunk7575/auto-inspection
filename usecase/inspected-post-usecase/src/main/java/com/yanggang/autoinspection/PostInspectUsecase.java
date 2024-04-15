package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import com.yanggang.autoinspection.content.post.model.Post;

public interface PostInspectUsecase {

    InspectedPost inspectAndGetIfValid(Post post);
}
