package com.yanggang.autoinspection;

import java.util.List;

public interface MetadataPort {

    String getCategoryNameByCategoryId(Long categoryId);
    String getUserNameByUserId(Long userId);
    List<Long> listFollowerIdsByUserID(Long userId);
}
