-- post 테이블 생성
CREATE TABLE post
(
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT '콘텐츠 id',
    title       VARCHAR(100) NOT NULL COMMENT '콘텐츠 제목',
    content     TEXT COMMENT '콘텐츠 내용',
    user_id     INT COMMENT '콘텐츠 작성자 user id',
    category_id INT COMMENT '콘텐츠 카테고리 id',
    created_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '콘텐츠 생성일시',
    updated_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '콘텐츠 수정일시',
    deleted_date  TIMESTAMP COMMENT '콘텐츠 삭제일시(미삭제 시 NULL)'
);
