DROP TABLE IF EXISTS t_facebook;
CREATE TABLE t_facebook
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '引用key',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_app_id VARCHAR(255) DEFAULT NULL COMMENT 'APP ID',
  c_secret VARCHAR(255) DEFAULT NULL COMMENT '密钥',
  c_version VARCHAR(255) DEFAULT NULL COMMENT '版本号',

  PRIMARY KEY pk(c_id) USING HASH,
  UNIQUE KEY uk_key(c_key) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
