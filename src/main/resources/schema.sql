USE
yh;
CREATE TABLE members
(
    member_id       varchar(20) PRIMARY KEY,
    member_password varchar(20) NOT NULL
);
CREATE TABLE posts
(
    post_id    int PRIMARY KEY,
    member_id  varchar(20)   NOT NULL,
    post_type  varchar(20)   NOT NULL,
    title      varchar(20)   NOT NULL,
    contents   varchar(1000) NOT NULL,
    updated_at datetime      NOT NULL DEFAULT current_timestamp(),
    created_at datetime      NOT NULL DEFAULT current_timestamp(),
    foreign key (member_id) references members (member_id)
);
