/*
Navicat MySQL Data Transfer

Source Server         : xlauncher
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : tiangong_dim

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-07-31 16:49:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dim_channel
-- ----------------------------
DROP TABLE IF EXISTS `dim_channel`;
CREATE TABLE `dim_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自动递增，数据库维护',
  `channel_id` varchar(32) NOT NULL COMMENT '真实通道编号 UUID，32位唯一标识，关键字',
  `channel_name` varchar(50) NOT NULL COMMENT '真实通道名称',
  `channel_source_id` varchar(32) NOT NULL COMMENT '真实通道资源编号，UUID通道所属设备编号（外键），真实设备表中的device_id',
  `channel_number` int(10) DEFAULT NULL COMMENT '真实设备取流使用的通道序号',
  `channel_grid_id` varchar(50) NOT NULL COMMENT '网格编号',
  `channel_location` varchar(51) DEFAULT NULL COMMENT '真实通道行政地理位置信息',
  `channel_longitude` varchar(50) DEFAULT NULL COMMENT 'GIS信息经度，具体标准可调整',
  `channel_latitude` varchar(50) DEFAULT NULL COMMENT 'GIS信息维度，具体标准可调整',
  `channel_handler` varchar(52) DEFAULT NULL COMMENT '真实通道负责人',
  `channel_handler_phone` varchar(53) DEFAULT NULL COMMENT '真实通道负责人联系电话',
  `channel_pod_status` int(10) DEFAULT '0' COMMENT '默认为：0；链接状态：1；不可达：0',
  `channel_status` varchar(50) NOT NULL DEFAULT '在用' COMMENT '真实通道使用状态：在用，停用，删除',
  `channel_create_time` datetime NOT NULL COMMENT '真实通道添加时间：yyyy-MM-dd HH:mm:ss',
  `channel_update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '真实通道更新时间：yyyy-MM-dd HH:mm:ss',
  `channel_fault_time` datetime DEFAULT NULL COMMENT '通道故障产生时间',
  `channel_fault_code` int(10) DEFAULT '0' COMMENT '真实通道故障编码',
  `channel_fault` varchar(50) DEFAULT '正常运行' COMMENT '真实通道故障信息',
  `channel_model` varchar(20) DEFAULT NULL COMMENT '通道型号',
  `channel_vendor` varchar(50) DEFAULT NULL COMMENT '通道厂商',
  `channel_thread_id` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dim_channel
-- ----------------------------
INSERT INTO `dim_channel` VALUES ('2', '4978523fe0fd41b79a7acede12dd13e9', '周二DIM通道测试001号', '3c9ceb34e40749ef88026d5e9e10f9ce', '1', '1001', '杭州市西湖区西湖街道', '117.29', '41.05', 'launcher01', '17826808950', '0', '在用', '2018-06-22 10:43:03', '1970-01-01 00:00:00', null, '0', '正常', '2018XH', '海康威视', '0');
INSERT INTO `dim_channel` VALUES ('5', '60f9ba40acc7468fab653f9ddade43b3', '周二DIM通道测试002号', '3c9ceb34e40749ef88026d5e9e10f9ce', '1', '1002', '杭州市西湖区西湖街道', '117.29', '41.15', 'launcher01', '17826808950', '0', '在用', '2018-05-09 14:10:25', '1970-01-01 00:00:00', null, '0', '正常', '2018XH', '海康威视', '0');
INSERT INTO `dim_channel` VALUES ('6', 'db684861d9b34252a380b4e4af63caaa', '周二DIM通道测试003号', '3c9ceb34e40749ef88026d5e9e10f9ce', '1', '1002', '杭州市西湖区西湖街道', '117.29', '41.25', 'launcher01', '17826808950', '0', '在用', '2018-05-09 14:10:25', '1970-01-01 00:00:00', null, '0', '正常', '2018XH', '海康威视', '0');

-- ----------------------------
-- Table structure for dim_device
-- ----------------------------
DROP TABLE IF EXISTS `dim_device`;
CREATE TABLE `dim_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动递增，数据库维护',
  `device_id` varchar(32) NOT NULL COMMENT '真实设备编号，UUID随机生成32位字符唯一标识',
  `device_name` varchar(50) DEFAULT NULL COMMENT '真实设备备注名称',
  `device_ip` varchar(20) NOT NULL COMMENT '真实设备ip地址',
  `device_port` varchar(20) NOT NULL COMMENT '真实设备port端口',
  `device_user_name` varchar(50) NOT NULL COMMENT '真实设备登录用户名',
  `device_user_password` varchar(50) NOT NULL COMMENT '真实设备登录密码',
  `device_type` varchar(50) DEFAULT NULL COMMENT '真实设备类型',
  `device_model` varchar(20) DEFAULT NULL COMMENT '真实设备型号',
  `device_vendor` varchar(50) DEFAULT NULL COMMENT '设备厂商',
  `device_fault_code` int(10) DEFAULT '0' COMMENT '真实设备故障编号，1表示正常运行，2,3,4等表示各设备故障信息',
  `device_fault` varchar(50) DEFAULT '正常运行' COMMENT '真实设备故障信息',
  `device_fault_time` datetime DEFAULT NULL COMMENT '真实设备故障产生时间',
  `device_channel_count` int(10) DEFAULT NULL COMMENT '真实设备支持的通道总数',
  `device_status` varchar(50) DEFAULT '在用' COMMENT '真实设备使用状态，（1在用，0停用，-1删除）',
  `device_org_id` VARCHAR (32) NOT NULL COMMENT '真实设备所属组织编号',
  `device_division_id` bigint(20) NOT NULL COMMENT '真实设备所属行政区划编号',
  `device_create_time` datetime NOT NULL COMMENT '真实设备添加时间，yyyy-MM-dd HH:mm:ss',
  `device_update_time` datetime DEFAULT NULL COMMENT '真实设备更新时间，yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dim_device
-- ----------------------------
INSERT INTO `dim_device` VALUES ('1', '3c9ceb34e40749ef88026d5e9e10f9ce', '录制测试设备20号', '8.11.0.46', '8000', 'admin', '1qaz2wsx', 'IPcamera', 'HAVISION3000', '海康威视', '0', '正常运行', null, '1', '在用', '57', '110101001013', '2018-06-22 10:45:00', '2018-07-31 16:45:34');
