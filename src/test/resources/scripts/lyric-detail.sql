-- Create Lyric of User 1
insert into lyric(id, content, created_date, description, is_deleted, stars, status, title, updated_date, account_id) values
(101, 'Lyric content 1', now(), 'Lyric Description 1', false, 4, 1, 'Lyric Title 1 of User 1', now(), 100);
insert into lyric(id, content, created_date, description, is_deleted, stars, status, title, updated_date, account_id) values
(102, 'Lyric content 2', now(), 'Lyric Description 2', false, 3.5, 0, 'Lyric Title 2 of User 1', now(), 100);
insert into lyric(id, content, created_date, description, is_deleted, stars, status, title, updated_date, account_id) values
(103, 'Lyric content 3', now(), 'Lyric Description 3', false, 2.5, 1, 'Lyric Title 3 of User 1', now(), 100),
(104, 'Lyric content 4', now(), 'Lyric Description 4', true, 0., null, 'Deleted Lyric Title of User 1', now(), 100);


-- Create Lyric of User 2
insert into lyric(id, content, created_date, description, is_deleted, stars, status, title, updated_date, account_id) values
    (111, 'Lyric content 1', now(), 'Lyric Description 1', false, 4.4, 1, 'Lyric Title 1 of User 2', now(), 101);
insert into lyric(id, content, created_date, description, is_deleted, stars, status, title, updated_date, account_id) values
    (112, 'Lyric content 2', now(), 'Lyric Description 2', false, 3.6, 0, 'Lyric Title 2 of User 2', now(), 101),
    (113, 'Lyric content 3', now(), 'Lyric Description 3', true, 3.3, 0, 'Lyric Title 3 of User 2', now(), 101);

-- Add Anonymous Comments
insert into lyric_anonymous_comment(id, comment_name, content, created_date, is_deleted, stars, updated_date, lyric_id, likes) values
(1011, 'No Name 1', 'This is great!! from No Name 1', '2022-06-13 12:00:20', false, null, now(), 101, null),
(1111, 'NO Name 2', 'This is great!! from No Name 2', now(), false, 4, now(), 111, 1);

--Add Comments
insert into lyric_comment(id, account_id, content, created_date, is_deleted, likes, stars, updated_date, lyric_id) values
(1012, 101, 'Hi! I am User 2', '2022-06-13 14:05:01', false, 1, null, now(), 101),
(1112, 100, 'Hi! I am User 1', now(), false, 1, null, now(), 111);