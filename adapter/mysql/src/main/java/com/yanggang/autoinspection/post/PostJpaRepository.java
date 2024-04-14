package com.yanggang.autoinspection.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
