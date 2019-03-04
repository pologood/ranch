DROP TABLE IF EXISTS t_weixin_reply;
CREATE TABLE t_weixin_reply
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '引用KEY',
  c_sort INT DEFAULT 0 COMMENT '顺序',
  c_receive_type VARCHAR(255) DEFAULT NULL COMMENT '接收类型',
  c_receive_message VARCHAR(255) DEFAULT NULL COMMENT '接收消息',
  c_send_type VARCHAR(255) DEFAULT NULL COMMENT '发送类型',
  c_send_message TEXT DEFAULT NULL COMMENT '发送消息',
  c_state INT DEFAULT 0 COMMENT '状态：0-待使用；1-使用中',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_key(c_key) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;