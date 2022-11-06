DROP TABLE
    IF
    EXISTS zy_user;
CREATE TABLE zy_user
(
    id          BIGINT ( 20 ) NOT NULL COMMENT '用户id',
    nick_name   VARCHAR(30) NOT NULL COMMENT '昵称',
    account     VARCHAR(20) NOT NULL COMMENT '账号',
    `password`  VARCHAR(100) DEFAULT NULL COMMENT '密码',
    phonenumber VARCHAR(11)  DEFAULT NULL COMMENT '手机号码',
    email       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    gender      TINYINT NULL DEFAULT 0 COMMENT '(0 男，1 女，2 未知)',
    avatar      VARCHAR(100) DEFAULT NULL COMMENT '头像地址',
    address     VARCHAR(100) DEFAULT NULL COMMENT '地址',
    `status`    TINYINT      DEFAULT '1' COMMENT '状态 0：停用 1：正常',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    login_ip    VARCHAR(128) DEFAULT NULL COMMENT '最后登录ip',
    login_date  datetime COMMENT '最后登录时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE (account),
    UNIQUE (nick_name)
) ENGINE = INNODB COMMENT = '用户信息表';
--
--
-- 角色管理表
DROP TABLE
    IF
    EXISTS zy_role;
CREATE TABLE zy_role
(
    id          BIGINT NOT NULL COMMENT 'id',
    user_id     BIGINT COMMENT '用户id',
    role        VARCHAR(20)  DEFAULT 'user' COMMENT '角色权限 (user，admin)',
    `status`    TINYINT      DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY         role_user_id ( user_id )
) ENGINE = INNODB COMMENT = '角色管理表';
--
--
-- 播客订阅表
DROP TABLE
    IF
    EXISTS zy_audio_rss;
CREATE TABLE zy_audio_rss
(
    id          BIGINT       NOT NULL COMMENT 'id',
    feed_id     VARCHAR(100) NOT NULL COMMENT 'rss id，rss基本信息id',
    href        VARCHAR(200) NOT NULL COMMENT 'rss 地址链接',
    title       VARCHAR(100) DEFAULT NULL COMMENT '播客节目名',
    image       VARCHAR(500) DEFAULT NULL COMMENT '播客节目logo',
    owner_name  VARCHAR(100) DEFAULT NULL COMMENT '播客作者',
    `status`    TINYINT      DEFAULT '0' COMMENT '状态（0正常，1停用）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE (feed_id)
) ENGINE = INNODB COMMENT = '播客rss表';
--
--
-- 播客订阅表
DROP TABLE
    IF
    EXISTS zy_audio_subscribe;
CREATE TABLE zy_audio_subscribe
(
    id          BIGINT       NOT NULL COMMENT 'id',
    user_id     BIGINT       NOT NULL COMMENT '用户id',
    feed_id     VARCHAR(100) NOT NULL COMMENT 'rss id，rss基本信息id',
    `status`    TINYINT      DEFAULT '0' COMMENT '状态（0正常，1停用）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY         audio_subscribe_user_id ( user_id ),
    KEY         audio_subscribe_feed_id ( feed_id )
) ENGINE = INNODB COMMENT = '播客订阅表';
--
--
-- 每期节目信息表
DROP TABLE
    IF
    EXISTS zy_audio_item_house;
CREATE TABLE zy_audio_item_house
(
    id               BIGINT       NOT NULL COMMENT 'id',
    feed_id          VARCHAR(100) NOT NULL COMMENT 'rss id，rss基本信息id',
    program_id       VARCHAR(100) NOT NULL COMMENT '节目id',
    title            VARCHAR(100)  DEFAULT NULL COMMENT '播客节目名',
    subtitle         VARCHAR(100)  DEFAULT NULL COMMENT '副标题',
    image            VARCHAR(500) COMMENT '播客节目图片',
    author           VARCHAR(200)  DEFAULT NULL COMMENT '作者',
    audio_type       VARCHAR(20)   DEFAULT NULL COMMENT '音频类型',
    description      VARCHAR(3000) DEFAULT NULL COMMENT '描述',
    description_type VARCHAR(20)   DEFAULT NULL COMMENT '描述类型',
    episode          VARCHAR(200)  DEFAULT NULL COMMENT '插话',
    pub_date         datetime COMMENT '发布时间',
    duration         VARCHAR(50)   DEFAULT NULL COMMENT '音频时长',
    explicit         VARCHAR(200)  DEFAULT NULL COMMENT '详述',
    audio_url        VARCHAR(500)  DEFAULT NULL COMMENT '音频地址',
    resource_link    VARCHAR(500)  DEFAULT NULL COMMENT '资源链接',
    `status`         TINYINT       DEFAULT '0' COMMENT '状态（0正常，1停用）',
    deleted          TINYINT       DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by        VARCHAR(64)   DEFAULT '' COMMENT '创建者',
    create_time      datetime COMMENT '创建时间',
    update_by        VARCHAR(64)   DEFAULT '' COMMENT '更新者',
    update_time      datetime COMMENT '更新时间',
    remark           VARCHAR(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY              item_house_feed_id ( feed_id ),
    KEY              item_house_program_id ( program_id ),
    UNIQUE (audio_url)
) ENGINE = INNODB COMMENT = '每期节目信息表';
--
--
-- 播客收藏表
DROP TABLE
    IF
    EXISTS zy_audio_collects;
CREATE TABLE zy_audio_collects
(
    id          BIGINT       NOT NULL COMMENT 'id',
    user_id     BIGINT       NOT NULL COMMENT '用户id',
    part_id     VARCHAR(100) NOT NULL COMMENT '节目id',
    in_queue    TINYINT      DEFAULT '0' COMMENT '在播放队列（0 存在，1不存在）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY         audio_collects_user_id ( user_id ),
    UNIQUE (part_id)
) ENGINE = INNODB COMMENT = '播客收藏表';
--
--
-- 播放列表表
DROP TABLE
    IF
    EXISTS zy_audio_playlist;
CREATE TABLE zy_audio_playlist
(
    id          BIGINT       NOT NULL COMMENT 'id',
    user_id     BIGINT       NOT NULL COMMENT '用户id',
    part_id     VARCHAR(100) NOT NULL COMMENT '节目id',
    `status`    TINYINT      DEFAULT '0' COMMENT '状态（0未收听，1 完成收听）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE (part_id)
) ENGINE = INNODB COMMENT = '播放列表表';
--
--
-- 收听历史表
DROP TABLE
    IF
    EXISTS zy_listen_history;
CREATE TABLE zy_listen_history
(
    id              BIGINT NOT NULL COMMENT 'id',
    user_id         BIGINT NOT NULL COMMENT '用户id',
    feed_id         VARCHAR(50)  DEFAULT NULL COMMENT 'rss id',
    audio_id        VARCHAR(50)  DEFAULT NULL COMMENT '音频 id',
    listen_duration VARCHAR(20)  DEFAULT NULL COMMENT '收听时长',
    deleted         TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by       VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time     datetime COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time     datetime COMMENT '更新时间',
    remark          VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY             audio_history_user_id ( user_id ),
    KEY             audio_history_feed_id ( feed_id )
) ENGINE = INNODB COMMENT = '收听历史表';
--
--
-- 播客收件箱表
DROP TABLE
    IF
    EXISTS zy_audio_inbox;
CREATE TABLE zy_audio_inbox
(
    id          BIGINT       NOT NULL COMMENT 'id',
    user_id     BIGINT       NOT NULL COMMENT '用户id',
    sub_id      VARCHAR(100) NOT NULL COMMENT '节目id',
    finish      TINYINT      DEFAULT '0' COMMENT '完成收听（0 未完成，1已经完成）',
    collect     TINYINT      DEFAULT '0' COMMENT '收藏（0 未收藏，1已收藏）',
    in_queue    TINYINT      DEFAULT '0' COMMENT '在播放队列（0 存在，1不存在）',
    `status`    TINYINT      DEFAULT '0' COMMENT '状态（0 未读，1 已读）',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY         audio_collects_user_id ( user_id ),
    UNIQUE (sub_id)
) ENGINE = INNODB COMMENT = '播客收件箱表';
--
--
-- 管理员信息表
DROP TABLE
    IF
    EXISTS zy_admin;
CREATE TABLE zy_admin
(
    id          BIGINT ( 20 ) NOT NULL COMMENT 'id',
    username    VARCHAR(30) NOT NULL COMMENT '用户名',
    `password`  VARCHAR(100) DEFAULT NULL COMMENT '密码',
    phonenumber VARCHAR(11)  DEFAULT NULL COMMENT '手机号码',
    email       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    avatar      VARCHAR(100) DEFAULT NULL COMMENT '头像地址',
    `status`    TINYINT      DEFAULT '1' COMMENT '状态 0：停用 1：正常',
    deleted     TINYINT      DEFAULT '0' COMMENT '删除（0 存在，1 删除）',
    login_date  datetime COMMENT '最后登录时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE (phonenumber)
) ENGINE = INNODB COMMENT = '管理员信息表';