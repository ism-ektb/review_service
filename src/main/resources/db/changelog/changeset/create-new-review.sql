--liquibase formatted sql
--changeset ism:create-new-users

INSERT INTO reviews (authorId, username, title, content_text, create_time, eventId) VALUES (1, 'ivan', 'title', 'good event', '2022-06-16 16:37:23', 1);
