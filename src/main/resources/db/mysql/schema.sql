SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS user_auth;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS auth;

SET FOREIGN_KEY_CHECKS = 1;

create table auth
(
    auth_id bigint auto_increment primary key,
    auth_code varchar(20) not null unique comment '권한 코드',
    auth_name varchar(20) not null unique comment '권한 이름',
    created_at datetime comment '생성일',
    modified_at datetime comment '수정일'
) engine=InnoDB default charset=utf8mb4 comment '권한 테이블';

create table user_info
(
    user_id bigint auto_increment primary key,
    login_id varchar(100) NOT NULL unique comment '로그인 아이디 OR 이메일',
    login_pw varchar(100) comment '로그인 비밀번호',
    user_name varchar(20) not null comment '사용자 이름',
    created_at datetime comment '생성일',
    modified_at datetime comment '수정일',
    index (user_name)
) engine=InnoDB default charset=utf8mb4 comment '사용자 정보 테이블';

create table user_auth
(
    user_auth_id bigint auto_increment primary key,
    user_id bigint not null,
    auth_id bigint not null,
    created_at datetime comment '생성일',
    modified_at datetime comment '수정일',
    index (user_id, auth_id),
    foreign key (user_id) references user_info(user_id),
    foreign key (auth_id) references auth(auth_id)
) engine=InnoDB default charset=utf8mb4 comment '사용자 권한 테이블';

create table test
(
    id bigint auto_increment primary key,
    name bigin t not null,
    auth_id bigint not null,
    created_at datetime comment '생성일',
    modified_at datetime comment '수정일'
)
