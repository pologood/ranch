DROP TABLE IF EXISTS t_push_aliyun;
CREATE TABLE t_push_aliyun
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_app_code VARCHAR(255) NOT NULL COMMENT 'APP编码',
  c_key_id VARCHAR(255) DEFAULT NULL COMMENT 'KEY ID',
  c_key_secret VARCHAR(255) DEFAULT NULL COMMENT 'KEY密钥',
  c_app_key VARCHAR(255) DEFAULT NULL COMMENT 'APP KEY',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  UNIQUE KEY uk_app_code(c_app_code) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
