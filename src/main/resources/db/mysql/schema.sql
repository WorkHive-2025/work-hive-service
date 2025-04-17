SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS member_role;

SET FOREIGN_KEY_CHECKS = 1;

create table auth
(
    auth_id     bigint auto_increment primary key,
    auth_code   varchar(20) not null unique comment '권한 코드',
    auth_name   varchar(20) not null unique comment '권한 이름',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일'
) engine = InnoDB
  default charset = utf8mb4 comment '권한 테이블';

create table member
(
    member_id   bigint auto_increment primary key,
    username    varchar(64) not null comment '사용자 로그인 ID',
    password    varchar(255) comment '사용자 비밀번호',
    name        varchar(64) comment '사용자 이름',
    is_active   boolean     not null default true comment '활성화 여부',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일',
    constraint member_username_unique unique (username)
) engine = InnoDB
  default charset = utf8mb4 comment '사용자 테이블';

create table role
(
    role_id     bigint auto_increment primary key,
    role_code   varchar(20) not null comment '역할 코드',
    role_name   varchar(20) not null comment '역할 이름',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일',
    constraint role_code_unique unique (role_code)
) engine = InnoDB
  default charset = utf8mb4 comment '역할 테이블';

create table member_role
(
    member_role_id bigint auto_increment primary key,
    member_id      bigint not null,
    role_id        bigint not null,
    created_at     datetime comment '생성일',
    modified_at    datetime comment '수정일',
    constraint member_role_member_id_role_id_unique unique (member_id, role_id),
    key idx_member_role_member_id (member_id),
    key idx_member_role_id (role_id)
) engine = InnoDB
  default charset = utf8mb4 comment '사용자 역할 테이블';

create table oauth2SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS member_role;

SET FOREIGN_KEY_CHECKS = 1;

create table auth
(
    auth_id     bigint auto_increment primary key,
    auth_code   varchar(20) not null unique comment '권한 코드',
    auth_name   varchar(20) not null unique comment '권한 이름',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일'
) engine = InnoDB
  default charset = utf8mb4 comment '권한 테이블';

create table member
(
    member_id   bigint auto_increment primary key,
    username    varchar(64) not null comment '사용자 로그인 ID',
    password    varchar(255) comment '사용자 비밀번호',
    name        varchar(64) comment '사용자 이름',
    is_active   boolean     not null default true comment '활성화 여부',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일',
    constraint member_username_unique unique (username)
) engine = InnoDB
  default charset = utf8mb4 comment '사용자 테이블';

create table role
(
    role_id     bigint auto_increment primary key,
    role_code   varchar(20) not null comment '역할 코드',
    role_name   varchar(20) not null comment '역할 이름',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일',
    constraint role_role_code_unique unique (role_code)
) engine = InnoDB
  default charset = utf8mb4 comment '역할 테이블';

create table member_role
(
    member_role_id bigint auto_increment primary key,
    member_id      bigint not null,
    role_id        bigint not null,
    created_at     datetime comment '생성일',
    modified_at    datetime comment '수정일',
    constraint member_role_member_id_role_id_unique unique (member_id, role_id),
    key idx_member_role_member_id (member_id),
    key idx_member_role_id (role_id)
) engine = InnoDB
  default charset = utf8mb4 comment '사용자 역할 테이블';

create table oauth2
(
    oauth2_id   bigint auto_increment primary key,
    member_id   bigint       not null,
    platform    varchar(20)  not null comment 'OAuth2 플랫폼',
    `key`       varchar(255) not null comment 'OAuth2 키',
    args        varchar(255) comment 'OAuth2 인자',
    created_at  datetime comment '생성일',
    modified_at datetime comment '수정일',
    constraint oauth2_key_platform_unique unique (platform, `key`),
    constraint oauth2_member_id_unique unique (member_id)
) engine = InnoDB
  default charset = utf8mb4 comment 'OAuth2 테이블';



