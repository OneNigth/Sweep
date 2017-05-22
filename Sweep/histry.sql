/*
MySQL Data Transfer
Source Host: localhost
Source Database: histry
Target Host: localhost
Target Database: histry
Date: 2016/12/29 21:18:12
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for histry
-- ----------------------------
DROP TABLE IF EXISTS `histry`;
CREATE TABLE `histry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(10) NOT NULL,
  `model` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `histry` VALUES ('1', '20', '困难');
