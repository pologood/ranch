DROP TABLE IF EXISTS t_editor;
CREATE TABLE t_editor
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_template INT DEFAULT 0 COMMENT '模板：0-否；1-模板；2-范文',
  c_type VARCHAR(255) NOT NULL COMMENT '类型',
  c_sort INT DEFAULT 0 COMMENT '顺序',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_label VARCHAR(255) DEFAULT NULL COMMENT '标签',
  c_keyword TEXT DEFAULT NULL COMMENT '关键词',
  c_summary TEXT DEFAULT NULL COMMENT '摘要',
  c_width INT DEFAULT 0 COMMENT '宽度',
  c_height INT DEFAULT 0 COMMENT '高度',
  c_image VARCHAR(255) DEFAULT NULL COMMENT '预览图',
  c_screenshot VARCHAR(255) DEFAULT NULL COMMENT '主截图',
  c_group VARCHAR(255) DEFAULT NULL COMMENT '分组',
  c_price INT DEFAULT 0 COMMENT '价格，单位：分',
  c_vip_price INT DEFAULT 0 COMMENT 'VIP价格，单位：分',
  c_limited_price INT DEFAULT 0 COMMENT '限时价格，单位：分',
  c_limited_time DATETIME DEFAULT NULL COMMENT '限时时间',
  c_total INT DEFAULT 0 COMMENT '总根节点数',
  c_modified INT DEFAULT 0 COMMENT '已修改根节点数',
  c_state INT DEFAULT 0 COMMENT '状态：0-待审核；1-审核通过；2-审核拒绝；3-已上架；4-已下架；5-已删除',
  c_json TEXT DEFAULT NULL COMMENT '扩展属性集',
  c_source VARCHAR(255) DEFAULT NULL COMMENT '来源',
  c_used INT DEFAULT 0 COMMENT '被使用次数',
  c_create DATETIME DEFAULT NULL COMMENT '创建时间',
  c_modify DATETIME DEFAULT NULL COMMENT '修改时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_template(c_type) USING HASH,
  KEY k_type(c_type) USING HASH,
  KEY k_state(c_state) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
