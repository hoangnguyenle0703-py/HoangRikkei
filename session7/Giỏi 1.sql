CREATE TABLE post(
    post_id SERIAL PRIMARY KEY ,
    user_id INT NOT NULL ,
    content TEXT,
    tags TEXT[],
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_public BOOLEAN DEFAULT TRUE
);

CREATE TABLE post_like(
    user_id INT NOT NULL ,
    post_id INT NOT NULL ,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id,post_id)
);

EXPLAIN ANALYSE
SELECT * FROM post
WHERE is_public = TRUE AND content ILIKE '%du lịch%';

CREATE INDEX idx_post_content ON post(LOWER(content));

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_post_tags_gin ON post USING gin (tags);

EXPLAIN ANALYZE
SELECT * FROM post WHERE tags @> ARRAY['travel'];

CREATE INDEX idx_post_recent_public
ON post(created_at DESC)
WHERE is_public = TRUE;

EXPLAIN ANALYSE
SELECT * FROM post
WHERE is_public = TRUE AND created_at > now() - INTERVAL '7 days';