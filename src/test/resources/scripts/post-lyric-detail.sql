-- Delete Anonymous Comments
delete from lyric_anonymous_comment where id = 1011 or id = 1111;

--Delete Comments
delete from lyric_comment where id = 1012 or id = 1112;

-- Delete Lyric of User 1
delete from lyric where id = 101 or id = 102 or id = 103 or id = 104;

-- Delete Lyric of User 2
delete from lyric where id = 111 or id = 112 or id = 113;