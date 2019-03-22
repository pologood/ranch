DROP TABLE IF EXISTS t_push_ios;
CREATE TABLE t_push_ios
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_app_code VARCHAR(255) NOT NULL COMMENT 'APP编码',
  c_p12 TEXT DEFAULT NULL COMMENT '证书，BASE64编码',
  c_password VARCHAR(255) DEFAULT NULL COMMENT '证书密码',
  c_destination INT DEFAULT 0 COMMENT '目的地：0-开发；1-正式',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  UNIQUE KEY uk_app_code_destination(c_app_code,c_destination) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
