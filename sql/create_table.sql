# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists ren_search;

-- 切换库
use ren_search;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

INSERT INTO post (title, content, tags, thumbNum, favourNum, userId, createTime, updateTime, isDelete) VALUES
                                                                                                           ('Java 高并发优化技巧', '在高并发场景下，我们可以采用缓存、异步处理、限流等方式优化系统性能。', '["Java", "高并发", "优化"]', 45, 20, 1001, '2025-03-29 10:00:00', '2025-03-29 10:00:00', 0),
                                                                                                           ('Vue3 组合式 API 详解', 'Vue3 采用组合式 API 提高了代码复用性，让开发更加灵活...', '["Vue3", "前端", "组件化"]', 30, 15, 1002, '2025-03-29 11:00:00', '2025-03-29 11:00:00', 0),
                                                                                                           ('生活小技巧：如何提高工作效率', '合理规划时间，减少干扰，使用番茄工作法...', '["效率", "时间管理", "生活技巧"]', 50, 25, 1003, '2025-03-29 12:00:00', '2025-03-29 12:00:00', 0),
                                                                                                           ('最新 AI 技术发展趋势', '人工智能正在不断进步，从大模型到 AIGC...', '["AI", "深度学习", "科技"]', 100, 40, 1004, '2025-03-29 13:00:00', '2025-03-29 13:00:00', 0),
                                                                                                           ('旅行攻略：日本关西自由行', '京都、大阪、奈良，自由行攻略...', '["旅行", "日本", "自由行"]', 70, 30, 1005, '2025-03-29 14:00:00', '2025-03-29 14:00:00', 0),
                                                                                                           ('Kubernetes 实战经验分享', '在实际项目中使用 K8s 需要注意这些关键点...', '["Kubernetes", "微服务", "DevOps"]', 55, 22, 1006, '2025-03-29 15:00:00', '2025-03-29 15:00:00', 0);

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';
