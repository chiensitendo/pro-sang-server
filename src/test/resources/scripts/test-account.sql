-- Create User 1
insert into account (id, created_date, email, is_deleted, role, updated_date, username) VALUES
(100, now(), 'test_account@gmail.com', false, 2, now(), 'test_account');
insert into account_detail (id, created_date, first_name, is_deleted, updated_date, account_id, last_name, middle_name, address, birthday, country, description, phone, state_or_province, website) VALUES
(1001,now(),'First Name', false, now(), 100, 'Last Name', 'Middle Name', '1 street', '1995-01-01', 1, 'I am test account', '000-0000-0000', 1, 'http://test-account.com');
insert into account_auth (id, created_date, is_deleted, password, updated_date, password_expired_time, refresh_token, refresh_token_expired_time, token, token_expired_time, account_id) VALUES
(1002,now(),false,'Test123456',now(),now() + interval '3 MONTHS', null, null ,null , null, 100);

-- Create User 2
insert into account (id, created_date, email, is_deleted, role, updated_date, username) VALUES
    (101, now(), 'test_account_2@gmail.com', false, 2, now(), 'test_account_2');
insert into account_detail (id, created_date, first_name, is_deleted, updated_date, account_id, last_name, middle_name, address, birthday, country, description, phone, state_or_province, website) VALUES
    (1011,now(),'First Name 2', false, now(), 101, 'Last Name 2', 'Middle Name 2', '2 street', '1995-02-01', 1, 'I am test account 2', '000-0000-0000', 1, 'http://test-account-2.com');
insert into account_auth (id, created_date, is_deleted, password, updated_date, password_expired_time, refresh_token, refresh_token_expired_time, token, token_expired_time, account_id) VALUES
    (1012,now(),false,'Test123456',now(),now() + interval '3 MONTHS', null, null ,null , null, 101);
