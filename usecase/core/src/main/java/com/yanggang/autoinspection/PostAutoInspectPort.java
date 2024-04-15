package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.AutoInspectionResult;
import com.yanggang.autoinspection.content.post.model.Post;

public interface PostAutoInspectPort {

    AutoInspectionResult inspect(Post post, String categoryName);
}
