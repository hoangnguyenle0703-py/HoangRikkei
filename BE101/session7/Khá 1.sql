CREATE TABLE book(
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(100),
    genre VARCHAR(50),
    price DECIMAL(10,2),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX genre_index ON book(genre);

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX author_index ON book USING gin(author gin_trgm_ops);

EXPLAIN ANALYZE
SELECT * FROM book WHERE genre = 'Fantasy';

-- Tạo index hỗ trợ tìm kiếm toàn văn (Full-text search)
CREATE INDEX idx_book_description_fts ON book
USING gin(to_tsvector('english', description));

-- Truy vấn ví dụ:
EXPLAIN
SELECT title FROM book
WHERE to_tsvector('english', description) @@ to_tsquery('magic & dragon');

CLUSTER book USING genre_index;

-- Kiểm tra lại hiệu năng
EXPLAIN ANALYZE
SELECT * FROM book WHERE genre = 'Fantasy';