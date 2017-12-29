DROP TABLE IF EXISTS t_form_template;
CREATE TABLE t_form_template
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_user CHAR(36) NOT NULL COMMENT '用户',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_memo VARCHAR(255) DEFAULT NULL COMMENT '说明',
  c_type INT DEFAULT 0 COMMENT '类型：0-私有；1-公开',
  c_create DATETIME DEFAULT NULL COMMENT '创建时间',
  c_modify DATETIME DEFAULT NULL COMMENT '修改时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_user(c_user) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;