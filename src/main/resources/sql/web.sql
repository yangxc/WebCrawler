-- WEB采集表
DROP TABLE IF EXISTS `web`;
CREATE TABLE `web` (
  `crawlerId` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `crawlerName` varchar(500) NULL DEFAULT '' COMMENT '名称',
  `groupId` varchar(500) NULL DEFAULT '' COMMENT '组ID',
  `groupName` varchar(500) NULL DEFAULT '' COMMENT '组名称', 
  `state` varchar(500) NULL DEFAULT '' COMMENT '状态',  
  `createTime` datetime NULL COMMENT '创建时间',
  `updateTime` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`crawlerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WEB存储表';

-- 规则表
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `ruleId` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `crawlerId` varchar(50) NULL DEFAULT '' COMMENT '爬虫 ID', 
  `express` blob,
  PRIMARY KEY (`ruleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则表';

-- 元数据
DROP TABLE IF EXISTS `metadata`;
CREATE TABLE `metadata` (
  metadataId  VARCHAR(50) NOT NULL COMMENT '主键',  
  crawlerId  VARCHAR(50) NULL COMMENT '爬虫 ID',
  metadata     VARCHAR(4000) NULL COMMENT '爬虫数据',
  md      VARCHAR(2000) NULL COMMENT 'md5码',
  createTime	datetime NULL COMMENT '创建时间',
  updateTime	datetime NULL COMMENT '更新时间',
PRIMARY KEY (`metadataId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表';

-- 历史记录表
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  id  VARCHAR(50) NOT NULL COMMENT '主键',  
  crawlerId  VARCHAR(50) NULL COMMENT '爬虫 ID',
  version      int COMMENT '版本号',
  pageCrawledCount      int COMMENT '抓取数据的数量',
  hasException      int COMMENT '指示是否存在异常',
  exceptionMessage      VARCHAR(2000) NULL COMMENT '异常对应的信息',
  startDate      datetime NULL COMMENT '爬虫的启动时间',
  StopDate      datetime NULL COMMENT '爬虫的停止时间',
  createTime	datetime NULL COMMENT '创建时间',
  updateTime	datetime NULL COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史记录表';

-- 代理表
DROP TABLE IF EXISTS `proxy`;
CREATE TABLE `proxy` (
  proxyId  VARCHAR(50) NOT NULL COMMENT '主键',  
  crawlerId  VARCHAR(50) NULL COMMENT '爬虫 ID',
  hostName      VARCHAR(2000) NULL COMMENT '代理服务地址',
  port      int COMMENT '端口',
  createTime	datetime NULL COMMENT '创建时间',
  updateTime	datetime NULL COMMENT '更新时间',
PRIMARY KEY (`proxyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理表';