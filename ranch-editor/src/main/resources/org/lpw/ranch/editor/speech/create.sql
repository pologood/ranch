DROP TABLE IF EXISTS t_editor_speech;
CREATE TABLE t_editor_speech
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_user CHAR(36) NOT NULL COMMENT '用户',
  c_editor CHAR(36) DEFAULT NULL COMMENT '编辑器',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_width INT DEFAULT 0 COMMENT '宽度',
  c_height INT DEFAULT 0 COMMENT '高度',
  c_image VARCHAR(255) DEFAULT NULL COMMENT '预览图',
  c_data TEXT DEFAULT NULL COMMENT '数据',
  c_password VARCHAR(255) DEFAULT NULL COMMENT '密码',
  c_ws_url VARCHAR(255) DEFAULT NULL COMMENT 'WebSocket地址',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_user_time(c_user,c_time) USING BTREE,
  KEY k_time(c_time) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
