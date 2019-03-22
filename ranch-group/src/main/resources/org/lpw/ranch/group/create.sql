DROP TABLE IF EXISTS t_group;
CREATE TABLE t_group
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_owner CHAR(36) NOT NULL COMMENT '所有者ID',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_portrait VARCHAR(255) DEFAULT NULL COMMENT '头像',
  c_note VARCHAR(255) DEFAULT NULL COMMENT '公告',
  c_member INT DEFAULT 0 COMMENT '成员数',
  c_audit INT DEFAULT 0 COMMENT '新成员是否需要审核：0-否；1-是',
  c_create DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_owner(c_owner) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
