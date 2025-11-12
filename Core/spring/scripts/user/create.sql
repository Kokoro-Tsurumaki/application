create table user (
                     id int unsigned primary key auto_increment comment 'ID',
                     phone int not null unique comment '手机号',
                     password varchar(64) default null comment '密码',
                     name varchar(16) not null comment '昵称'
) comment '用户表';