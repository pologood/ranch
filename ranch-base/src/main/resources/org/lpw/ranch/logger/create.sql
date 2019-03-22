DROP TABLE IF EXISTS t_logger;
CREATE TABLE t_logger
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '键',
  c_p0 VARCHAR(255) DEFAULT NULL COMMENT '参数0',
  c_p1 VARCHAR(255) DEFAULT NULL COMMENT '参数1',
  c_p2 VARCHAR(255) DEFAULT NULL COMMENT '参数2',
  c_p3 VARCHAR(255) DEFAULT NULL COMMENT '参数3',
  c_p4 VARCHAR(255) DEFAULT NULL COMMENT '参数4',
  c_p5 VARCHAR(255) DEFAULT NULL COMMENT '参数5',
  c_p6 VARCHAR(255) DEFAULT NULL COMMENT '参数6',
  c_p7 VARCHAR(255) DEFAULT NULL COMMENT '参数7',
  c_p8 VARCHAR(255) DEFAULT NULL COMMENT '参数8',
  c_p9 VARCHAR(255) DEFAULT NULL COMMENT '参数9',
  c_state INT DEFAULT 0 COMMENT '状态',
  c_time DATETIME NOT NULL COMMENT '时间',

  PRIMARY KEY pk(c_id) USING HASH,
  KEY k_key(c_key) USING HASH,
  KEY k_p0(c_p0) USING HASH,
  KEY k_p1(c_p1) USING HASH,
  KEY k_p2(c_p2) USING HASH,
  KEY k_p3(c_p3) USING HASH,
  KEY k_p4(c_p4) USING HASH,
  KEY k_p5(c_p5) USING HASH,
  KEY k_p6(c_p6) USING HASH,
  KEY k_p7(c_p7) USING HASH,
  KEY k_p8(c_p8) USING HASH,
  KEY k_p9(c_p9) USING HASH,
  KEY k_state(c_state) USING BTREE,
  KEY k_time(c_time) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
