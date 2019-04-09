-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: tiangong_dim
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dim_channel`
--

DROP TABLE IF EXISTS `dim_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dim_channel`
--

LOCK TABLES `dim_channel` WRITE;
/*!40000 ALTER TABLE `dim_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `dim_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dim_device`
--

DROP TABLE IF EXISTS `dim_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `device_org_id` int(10) NOT NULL COMMENT '真实设备所属组织编号',
  `device_division_id` bigint(20) NOT NULL COMMENT '真实设备所属行政区划编号',
  `device_create_time` datetime NOT NULL COMMENT '真实设备添加时间，yyyy-MM-dd HH:mm:ss',
  `device_update_time` datetime DEFAULT NULL COMMENT '真实设备更新时间，yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dim_device`
--

LOCK TABLES `dim_device` WRITE;
/*!40000 ALTER TABLE `dim_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `dim_device` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-31 17:24:52
