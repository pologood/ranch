DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_password CHAR(32) DEFAULT NULL COMMENT '密码',
  c_secret CHAR(32) DEFAULT NULL COMMENT '安全密码',
  c_idcard VARCHAR(255) DEFAULT NULL COMMENT '身份证号',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '姓名',
  c_nick VARCHAR(255) DEFAULT NULL COMMENT '昵称',
  c_mobile VARCHAR(255) DEFAULT NULL COMMENT '手机号',
  c_email VARCHAR(255) DEFAULT NULL COMMENT 'Email地址',
  c_portrait VARCHAR(255) DEFAULT NULL COMMENT '头像',
  c_gender INT DEFAULT 0 COMMENT '性别：0-未知；1-男；2-女',
  c_birthday DATE DEFAULT NULL COMMENT '出生日期',
  c_inviter CHAR(36) DEFAULT NULL COMMENT '邀请人',
  c_invite_count INT DEFAULT 0 COMMENT '邀请人数',
  c_code CHAR(8) NOT NULL COMMENT '唯一编码',
  c_register DATETIME DEFAULT NULL COMMENT '注册时间',
  c_grade INT DEFAULT 0 COMMENT '等级：<50为用户；>=50为管理员；99为超级管理员',
  c_state INT DEFAULT 0 COMMENT '状态：0-正常；1-禁用',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_mobile(c_mobile) USING HASH,
  UNIQUE KEY uk_code(c_code) USING HASH,
  KEY k_register(c_register) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO t_user(c_id,c_password,c_code,c_grade) VALUES('00000000-0000-0000-0000-000000000000','','00000000',99);
INSERT INTO t_user(c_id,c_password,c_code,c_grade) VALUES('99999999-9999-9999-9999-999999999999','','99999999',99);
