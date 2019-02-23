-- 模特信息表
CREATE TABLE crawler.`t_beauty_model` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `org` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '机构',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '标题',
  `model_name` VARCHAR(50) COLLATE utf8mb4_unicode_ci DEFAULT '' NOT NULL COMMENT '模特名称',
  `pic_count` INT COLLATE utf8mb4_unicode_ci DEFAULT 0 NOT NULL COMMENT '图片数量',
  `entrance_url` VARCHAR(300) COLLATE utf8mb4_unicode_ci DEFAULT '' NOT NULL COMMENT '入口地址',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模特信息表';

-- 模特图片表
CREATE TABLE crawler.`t_beauty_model_pic` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `model_id` BIGINT COLLATE utf8mb4_unicode_ci DEFAULT 0 COMMENT '模特信息表主键',
  `pic_url` VARCHAR(300) COLLATE utf8mb4_unicode_ci DEFAULT '' NOT NULL COMMENT '图片地址',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模特信息表';