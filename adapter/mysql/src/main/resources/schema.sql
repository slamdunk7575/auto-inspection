-- content 테이블 생성
CREATE TABLE content
(
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT '콘텐츠의 id',
    title       VARCHAR(100) NOT NULL COMMENT '콘텐츠의 제목',
    content     TEXT COMMENT '콘텐츠의 내용',
    user_id     INT COMMENT '콘텐츠의 작성자 user id',
    category_id INT COMMENT '콘텐츠의 카테고리 id',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '콘텐츠의 생성일시',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '콘텐츠의 수정일시',
    deleted_at  TIMESTAMP COMMENT '콘텐츠 삭제일시(미삭제 시 NULL)'
);
