CREATE DATABASE movie_management;

CREATE TABLE movies (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        director VARCHAR(255) NOT NULL,
                        year INT NOT NULL
);

CREATE OR REPLACE PROCEDURE add_movie(p_title VARCHAR, p_director VARCHAR, p_year INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO movies (title, director, year) VALUES (p_title, p_director, p_year);
END;
$$;

CREATE OR REPLACE FUNCTION list_movies()
    RETURNS TABLE(id INT, title VARCHAR, director VARCHAR, year INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT m.id, m.title, m.director, m.year FROM movies m;
END;
$$;

CREATE OR REPLACE PROCEDURE update_movie(p_id INT, p_title VARCHAR, p_director VARCHAR, p_year INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE movies SET title = p_title, director = p_director, year = p_year WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_movie(p_id INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM movies WHERE id = p_id;
END;
$$;