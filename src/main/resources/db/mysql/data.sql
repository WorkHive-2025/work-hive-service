insert into auth (auth_id, auth_code, auth_name, created_at, modified_at) values
(1, 'ROLE_SUPER', '수퍼 관리자',  now(), now()),
(2, 'ROLE_ADMIN', '회사 관리자', now(), now()),
(3, 'ROLE_USER', '일반 사용자', now(), now());