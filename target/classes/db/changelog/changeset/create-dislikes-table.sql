CREATE TABLE dislikes(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    review_id BIGINT REFERENCES reviews(id),
    user_id BIGINT NOT NULL
)