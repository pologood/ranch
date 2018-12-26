DROP TABLE IF EXISTS t_milestone;
CREATE TABLE t_milestone
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_user CHAR(36) NOT NULL COMMENT '用户',
  c_type VARCHAR(255) DEFAULT NULL COMMENT '类型',
  c_json TEXT DEFAULT NULL COMMENT '自定义属性集',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  UNIQUE KEY uk_user_type(c_user,c_type) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;