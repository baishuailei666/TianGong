/*
Navicat MySQL Data Transfer

Source Server         : xlauncher
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : es

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-08-06 15:09:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for es_alert_event
-- ----------------------------
DROP TABLE IF EXISTS `es_alert_event`;
CREATE TABLE `es_alert_event` (
  `event_id` int(25) NOT NULL AUTO_INCREMENT,
  `event_starttime` datetime DEFAULT NULL,
  `channel_id` varchar(32) DEFAULT NULL COMMENT '事件所对应的摄像头：\\r\\n如：拍摄到事件所对应的摄像头编号或者ID',
  `type_id` int(10) DEFAULT NULL COMMENT '事件类型：\r\n如：01表示非法挖沙；02表示非法钓鱼；',
  `event_source` varchar(1024) DEFAULT NULL COMMENT '事件资源：\r\n如：图片或者短视频',
  `event_push_status` varchar(20) DEFAULT '未推送' COMMENT '事件推送状态：\\r\\n如：刚接收到事件是未推送，成功转发给CMS可以将状态置为已推送。',
  `event_push_suntec_status` varchar(20) NOT NULL DEFAULT '未推送',
  `order_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for es_event_type
-- ----------------------------
DROP TABLE IF EXISTS `es_event_type`;
CREATE TABLE `es_event_type` (
  `type_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '事件类型编号',
  `type_description` varchar(255) NOT NULL COMMENT '事件类型描述：\r\n如：01表示危险事件；02表示严重事件；03表示一般事件；04表示警告事件\r\n',
  `type_startTime` varchar(25) DEFAULT NULL COMMENT '某告警事件类型推送事件段开始时间，时间戳毫秒数，默认每一天的零点。',
  `type_endTime` varchar(25) DEFAULT NULL COMMENT '某告警事件类型推送事件段结束时间，时间戳毫秒数，默认每一天的24点。',
  `type_pushStatus` varchar(25) DEFAULT NULL COMMENT '告警事件类型是否重复推送，',
  `type_delete` int(10) DEFAULT '0' COMMENT '用于数据库维护，0,1两个状态表示，未删除和删除。',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_event_type
-- ----------------------------
INSERT INTO `es_event_type` VALUES ('1', '非法船只入侵报警', '-28800000', '57540000', '是', '0');
INSERT INTO `es_event_type` VALUES ('2', '非法钓鱼报警', '-28800000', '57540000', '是', '0');
INSERT INTO `es_event_type` VALUES ('3', '垃圾污染报警', '-28800000', '57540000', '是', '0');
INSERT INTO `es_event_type` VALUES ('4', '人员非法入侵', '-28800000', '57540000', '是', '0');
INSERT INTO `es_event_type` VALUES ('5', '检测到河面垃圾', '-28800000', '57540000', '是', '0');
