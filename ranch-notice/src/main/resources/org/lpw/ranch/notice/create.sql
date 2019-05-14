DROP TABLE IF EXISTS t_notice;
CREATE TABLE t_notice
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_user CHAR(36) NOT NULL COMMENT '用户',
  c_type VARCHAR(255) DEFAULT NULL COMMENT '类型',
  c_subject VARCHAR(255) DEFAULT NULL COMMENT '标题',
  c_content TEXT DEFAULT NULL COMMENT '内容',
  c_link TEXT DEFAULT NULL COMMENT '链接',
  c_read INT DEFAULT 0 COMMENT '已读：0-否；1-是',
  c_marker INT DEFAULT 0 COMMENT '标记',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_user(c_user) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
