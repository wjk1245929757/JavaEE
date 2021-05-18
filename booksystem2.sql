/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : booksystem2

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 18/05/2021 10:15:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('eqY922', '牛虻 ', '艾捷尔·丽莲·伏尼契', '《牛虻》（The Gadfly）是爱尔兰女作家艾捷尔·丽莲·伏尼契创作的长篇小说，该书描写了意大利革命党人牛虻的一生。主人公单纯幼稚的爱国青年亚瑟因被革命同志误解，佯装投河自尽，奔赴南美。13年后，当他带着一身伤残重回故乡时，苦难的经历已把他磨练成一个坚定的革命者。他参与了反对奥地利统治者、争取国家独立统一的斗争，最后为之献出了生命。小说涉及了斗争、信仰、牺牲这些色彩浓重的主题。', 'public/eqY922牛虻.txt');
INSERT INTO `book` VALUES ('v53qqv', '呼啸山庄', '艾米莉·勃朗特', '小说描写吉卜赛弃儿希斯克利夫被山庄老主人收养后，因受辱和恋爱不遂，外出致富。回来后，对与其女友凯瑟琳结婚的地主林顿及其子女进行报复的故事。全篇充满强烈的反压迫、争幸福的斗争精神，又始终笼罩着离奇、紧张的浪漫气氛。此作品多次被改编成电影作品。', 'public/v53qqv呼啸山庄.txt');
INSERT INTO `book` VALUES ('w6rk36', '追风筝的人', '卡勒德·胡赛尼', '全书围绕风筝与阿富汗的两个少年之间展开，一个富家少年与家中仆人关于风筝的故事，关于人性的背叛与救赎。', 'public/w6rk36追风筝的人.txt');
INSERT INTO `book` VALUES ('y79vma', '死魂灵', '果戈理', '小说描写专营骗术的商人乞乞科夫来到某偏僻省城，以其天花乱坠的吹捧成为当地官僚的座上客，并上门去向地主收购死农奴，企图以此作为抵押，买空卖空，牟取暴利。丑事败露后，他便逃之夭夭。', 'public/y79vma死魂灵.txt');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (NULL, '001@qq.com', '$2a$10$RTh/TurpWANr6PVVGwB2ReObnzND.vx6Bj.3ujLf12Qx31vMNwmXi', NULL, 'ROLE_ADMIN');
INSERT INTO `user` VALUES (NULL, '2314721442@qq.com', '$2a$10$q5dtArTUyAHgVVg.lBhRieQuD9z2rncmrhWpSnwYZxPStcmVmYCQW', NULL, 'ROLE_USER');

SET FOREIGN_KEY_CHECKS = 1;
