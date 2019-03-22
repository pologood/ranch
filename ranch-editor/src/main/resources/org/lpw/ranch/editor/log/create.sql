DROP TABLE IF EXISTS t_editor_log;
CREATE TABLE t_editor_log
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_editor CHAR(36) NOT NULL COMMENT '编辑器',
  c_parent CHAR(36) DEFAULT NULL COMMENT '父元素',
  c_element CHAR(36) NOT NULL COMMENT '元素',
  c_sort INT DEFAULT 0 COMMENT '顺序',
  c_json LONGTEXT DEFAULT NULL COMMENT '扩展属性集',
  c_create DATETIME DEFAULT NULL COMMENT '创建时间',
  c_modify BIGINT DEFAULT 0 COMMENT '修改时间',
  c_operation INT DEFAULT 0 COMMENT '操作：0-新增；1-修改；2-删除',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_editor(c_editor) USING HASH,
  KEY k_element(c_element) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
