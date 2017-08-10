/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : dbworkdayhr

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-08-11 00:20:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tblgetworkers
-- ----------------------------
DROP TABLE IF EXISTS `tblgetworkers`;
CREATE TABLE `tblgetworkers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employeeid` varchar(20) DEFAULT NULL,
  `userid` varchar(20) DEFAULT NULL,
  `formattedname` varchar(100) DEFAULT NULL,
  `countryname` varchar(100) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `maritalstatus` varchar(100) DEFAULT NULL,
  `ethnicity` varchar(100) DEFAULT NULL,
  `citizenshipstatuscode` varchar(100) DEFAULT NULL,
  `socialsecuritynumber` varchar(50) DEFAULT NULL,
  `passportid` varchar(50) DEFAULT NULL,
  `positionid` varchar(50) DEFAULT NULL,
  `positiontitle` varchar(50) DEFAULT NULL,
  `businesstitle` varchar(50) DEFAULT NULL,
  `workertype` varchar(50) DEFAULT NULL,
  `startdate` varchar(40) DEFAULT NULL,
  `timetype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
