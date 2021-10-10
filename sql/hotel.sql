-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `description`     varchar(255) DEFAULT NULL,
    `srcId`         int(11)      DEFAULT 0,
    `couponType`      int(11)      NOT NULL,
    `couponName`      varchar(255) NOT NULL,
    `target_money`    int(11)      DEFAULT NULL,
    `target_room_num` int(11)      DEFAULT NULL,
    `discount`        double       DEFAULT NULL,
    `status`          int(11)      DEFAULT NULL,
    `start_time`      datetime     DEFAULT NULL,
    `end_time`        datetime     DEFAULT NULL,
    `discount_money`  int(11)      DEFAULT NULL,
    `corporate_name`  varchar(255) DEFAULT NULL,
    `bizRegion`       varchar(255) DEFAULT NULL,
    `vipLevel`        int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--
BEGIN;
/*!40000 ALTER TABLE `Coupon`
    DISABLE KEYS */;
INSERT INTO `Coupon`
VALUES (2, '满500-100优惠', 2, 3, '满减优惠券', 500, NULL, 0, 1, NULL, NULL, 100, NULL, NULL, NULL);
/*!40000 ALTER TABLE `Coupon`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT,
    `hotelName`        varchar(255) NOT NULL,
    `hotelDescription` varchar(255) DEFAULT NULL,
    `address`          varchar(255) UNIQUE DEFAULT NULL,
    `bizRegion`        varchar(255) DEFAULT NULL,
    `hotelStar`        varchar(255) DEFAULT NULL,
    `phoneNum`         varchar(100)      DEFAULT NULL,
    `commentTime`      int(11)      DEFAULT 0,
    `points`           double       DEFAULT 5.0,
    `sanitation`       double       DEFAULT 5.0,
    `environment`      double       DEFAULT 5.0,
    `service`          double       DEFAULT 5.0,
    `equipment`        double       DEFAULT 5.0,
    `picture`          varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Answers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Answers`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `userId`     int(11) NOT NULL,
    `questionId` int(11) NOT NULL,
    `answer`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Collections`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Collections`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `userId`  int(11) NOT NULL,
    `hotelId` int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel`
    DISABLE KEYS */;
INSERT INTO `Hotel`
VALUES (1, '汉庭酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Three', 1829373819, 0, 5, 5, 5, 5, 5, NULL),
       (2, '如家酒店', '欢迎您入住', '南京市鼓楼区珠江路269号', 'XiDan', 'Four', 1829373819, 0, 5, 5, 5, 5, 5, NULL),
       (3, '桂圆酒店', '欢迎您入住', '南京市栖霞区珠江路270号', 'XiDan', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (4, '格林豪泰', '欢迎您入住', '南京市栖霞区珠江路271号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (5, '布丁酒店', '欢迎您入住', '南京市栖霞区珠江路272号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (6, '维也纳酒店', '欢迎您入住', '南京市栖霞区珠江路273号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (7, '凯利酒店', '欢迎您入住', '南京市栖霞区珠江路274号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (8, '莫泰酒店', '欢迎您入住', '南京市栖霞区珠江路275号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (9, '青年酒店', '欢迎您入住', '南京市栖霞区珠江路276号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (10, '凯悦酒店', '欢迎您入住', '南京市栖霞区珠江路277号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (11, '皇冠假日酒店', '欢迎您入住', '南京市栖霞区珠江路278号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (12, '豪生酒店', '欢迎您入住', '南京市栖霞区珠江路279号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (13, '法莱德酒店', '欢迎您入住', '南京市栖霞区珠江路280号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (14, '茂悦酒店', '欢迎您入住', '南京市栖霞区珠江路281号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (15, '艾美酒店', '欢迎您入住', '南京市栖霞区珠江路282号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
        (16, '索菲特酒店', '欢迎您入住', '南京市栖霞区珠江路283号', 'XiDan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (17, '留香酒店', '欢迎您入住', '苏州市姑苏区临顿路钮家巷5-1号', 'GuanQianStreet', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (18, '希尔顿欢朋酒店', '欢迎您入住', '苏州吴中区工业园区东环路88号', 'JinJiHu', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (19, '王府井希尔顿酒店', '欢迎您入住', '北京市王府井东街8号', 'WangFuJing', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (20, '北京首都宾馆', '欢迎您入住', '北京市前门东大街3号', 'WangFuJing', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (21, '未名酒店', '欢迎您入住', '北京市东城区南锣鼓巷20号', 'NanLuoGuXiang', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (22, '北京木棉花酒店', '欢迎您入住', '北京市东华门大街16号', 'TianAnMen', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (23, '北京南锣鼓巷亚朵酒店', '欢迎您入住', '北京市西扬威胡同甲1号', 'NanLuoGuXiang', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (24, '北京福庭酒店', '欢迎您入住', '北京市朝阳区雅宝路街甲15号', 'SanLiTun', 'Three', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (25, '上海静安宾馆', '欢迎您入住', '上海市华山路370号', 'JingAnSi', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (26, '上海外滩英迪格酒店', '欢迎您入住', '上海市中山东二路585号', 'WaiTan', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (27, '上海宝格丽酒店', '欢迎您入住', '上海市河南北路33号', 'WaiTan', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (28, '上海浦东假日酒店', '欢迎您入住', '上海市东方路899号', 'LuJiaZui', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (29, '深圳邻里公寓', '欢迎您入住', '深圳市滨河大道5022号联合广场酒店公寓区', 'HuaQiangBei', 'Three', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (30, '途次·尧歌里精品民宿', '欢迎您入住', '无锡滨湖区尧歌里容庄231号', 'SanGuoCheng', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (31, '无锡揽月阁客栈', '欢迎您入住', '无锡市马山环山西路8号', 'LingShanDaFo', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (32, '无锡忆蝉居客栈', '欢迎您入住', '无锡市马山古竹社区西头40号', 'LingShanDaFo', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (33, '长沙柏锐·美麓智能民宿', '欢迎您入住', '长沙市桔子洲街道岳麓公园内山顶登高路58号极目山庄', 'YueLuShuYuan', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (34, '逸晨客栈', '欢迎您入住', '长沙市天心阁火药局巷62号', 'TianXinGe', 'Three', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (35, '天津盛捷国际大厦服务公寓', '欢迎您入住', '天津市南京路75号', 'GuLou', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (36, '天津西海岸豪华酒店公寓', '欢迎您入住', '天津和平区荣业大街新汇华庭12号楼', 'GuLou', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (37, '天津多米小筑主题酒店公寓', '欢迎您入住', '天津市南市大街与福安大街交口天汇中心4号楼', 'GuLou', 'Four', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (38, '朵亚连锁酒店公寓', '欢迎您入住', '广州市花城大道16号铂林国际公寓C座', 'GuangZhouTa', 'Three', 1829553719, 0, 5, 5, 5, 5, 5, NULL),
       (39, '柏雅轩酒店式公寓', '欢迎您入住', '广州市兴安路15号2111房天空别墅', 'GuangZhouTa', 'Five', 1829553719, 0, 5, 5, 5, 5, 5, NULL);

/*!40000 ALTER TABLE `Hotel`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `userId`        int(11)        DEFAULT NULL,
    `hotelId`       int(11)        DEFAULT NULL,
    `hotelName`     varchar(255)   DEFAULT NULL,
    `hotelPhoneNum` varchar(255)   DEFAULT NULL,
    `checkInDate`   varchar(255)   DEFAULT NULL,
    `checkOutDate`  varchar(255)   DEFAULT NULL,
    `roomType`      varchar(255)   DEFAULT NULL,
    `roomNum`       int(255)       DEFAULT NULL,
    `peopleNum`     int(255)       DEFAULT NULL,
    `haveChild`     tinytext,
    `createDate`    varchar(255)   DEFAULT NULL,
    `price`         decimal(65, 2) DEFAULT NULL,
    `clientName`    varchar(255)   DEFAULT NULL,
    `phoneNumber`   varchar(255)   DEFAULT NULL,
    `orderState`    varchar(255)   DEFAULT NULL,
    `comments`      varchar(255)   DEFAULT NULL,
    `points`        int(5)         DEFAULT NULL,
    `sanitation`    int(5)         DEFAULT NULL,
    `environment`   int(5)         DEFAULT NULL,
    `service`       int(5)         DEFAULT NULL,
    `equipment`     int(5)         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

/*!40000 ALTER TABLE `OrderList`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderList`
    ENABLE KEYS */;

BEGIN;
/*!40000 ALTER TABLE `OrderList`
    DISABLE KEYS */;
INSERT INTO `OrderList`
VALUES (1, 1, 1, '汉庭酒店', 12312312312, '2020-06-01', '2020-06-02', 'BigBed', '1', 1, 0, '2020-06-01', 100, 'zzy',
        '110', '未入住', '你的野区我养猪', 5, 5, 5, 5, 5);
/*!40000 ALTER TABLE `OrderList`
    ENABLE KEYS */;
COMMIT;

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `price`     double      DEFAULT NULL,
    `curNum`    int(11)     DEFAULT NULL,
    `total`     int(11)     DEFAULT NULL,
    `hotel_id`  int(11)     DEFAULT NULL,
    `roomType`  varchar(50) DEFAULT NULL,
    `bedType`   varchar(50) DEFAULT NULL,
    `breakfast` varchar(50) DEFAULT NULL,
    `peopleNum` int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room`
    DISABLE KEYS */;
INSERT INTO `Room`
VALUES (2, 199, 20, 20, 1, 'BigBed', 'BigBed', 'None', 1),
       (3, 299, 30, 30, 1, 'DoubleBed', 'DoubleBed', 'None', 2),
       (4, 399, 10, 10, 1, 'Family', 'Family', 'Porridge', 3),
       (5, 399, 10, 10, 2, 'Family', 'Family', 'Porridge', 3),
       (6, 199, 20, 20, 3, 'BigBed', 'BigBed', 'None', 1),
       (7, 199, 20, 20, 4, 'BigBed', 'BigBed', 'None', 1),
       (8, 199, 20, 20, 5, 'BigBed', 'BigBed', 'None', 1),
       (9, 199, 20, 20, 6, 'BigBed', 'BigBed', 'None', 1),
       (10, 199, 20, 20, 7, 'BigBed', 'BigBed', 'None', 1),
       (11, 199, 20, 20, 8, 'BigBed', 'BigBed', 'None', 1),
       (12, 199, 20, 20, 9, 'BigBed', 'BigBed', 'None', 1),
       (13, 199, 20, 20, 10, 'BigBed', 'BigBed', 'None', 1),
       (14, 199, 20, 20, 11, 'BigBed', 'BigBed', 'None', 1),
       (15, 199, 20, 20, 12, 'BigBed', 'BigBed', 'None', 1),
       (16, 199, 20, 20, 13, 'BigBed', 'BigBed', 'None', 1),
       (17, 199, 20, 20, 14, 'BigBed', 'BigBed', 'None', 1),
       (18, 199, 20, 20, 15, 'BigBed', 'BigBed', 'None', 1),
       (19, 199, 20, 20, 16, 'BigBed', 'BigBed', 'None', 1),
       (20, 199, 20, 20, 17, 'BigBed', 'BigBed', 'None', 1),
       (21, 199, 20, 20, 18, 'BigBed', 'BigBed', 'None', 1),
       (22, 199, 20, 20, 19, 'BigBed', 'BigBed', 'None', 1),
       (23, 199, 20, 20, 20, 'BigBed', 'BigBed', 'None', 1),
       (24, 199, 20, 20, 21, 'BigBed', 'BigBed', 'None', 1),
       (25, 199, 20, 20, 22, 'BigBed', 'BigBed', 'None', 1),
       (26, 199, 20, 20, 23, 'BigBed', 'BigBed', 'None', 1),
       (27, 199, 20, 20, 24, 'BigBed', 'BigBed', 'None', 1),
       (28, 199, 20, 20, 25, 'BigBed', 'BigBed', 'None', 1),
       (29, 199, 20, 20, 26, 'BigBed', 'BigBed', 'None', 1),
       (30, 199, 20, 20, 27, 'BigBed', 'BigBed', 'None', 1),
       (31, 199, 20, 20, 28, 'BigBed', 'BigBed', 'None', 1),
       (32, 199, 20, 20, 29, 'BigBed', 'BigBed', 'None', 1),
       (33, 199, 20, 20, 30, 'BigBed', 'BigBed', 'None', 1),
       (34, 199, 20, 20, 31, 'BigBed', 'BigBed', 'None', 1),
       (35, 199, 20, 20, 32, 'BigBed', 'BigBed', 'None', 1),
       (36, 199, 20, 20, 33, 'BigBed', 'BigBed', 'None', 1),
       (37, 199, 20, 20, 34, 'BigBed', 'BigBed', 'None', 1),
       (38, 199, 20, 20, 35, 'BigBed', 'BigBed', 'None', 1),
       (39, 199, 20, 20, 36, 'BigBed', 'BigBed', 'None', 1),
       (40, 199, 20, 20, 37, 'BigBed', 'BigBed', 'None', 1),
       (41, 199, 20, 20, 38, 'BigBed', 'BigBed', 'None', 1),
       (42, 199, 20, 20, 39, 'BigBed', 'BigBed', 'None', 1);

/*!40000 ALTER TABLE `Room`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `email`       varchar(255) UNIQUE DEFAULT NULL,
    `password`    varchar(11) NOT NULL,
    `username`    varchar(255) DEFAULT NULL,
    `phonenumber` varchar(255) DEFAULT NULL,
    `credit`      double       DEFAULT 100.0,
    `usertype`    varchar(255) DEFAULT NULL,
    `birthday`    varchar(255) DEFAULT NULL,
    `corporation` varchar(255) DEFAULT NULL,
    `annulTime`   int(5)       DEFAULT 3,
    `jobNumber`   int(11)      DEFAULT NULL,
    `hotelID`     int(11)      DEFAULT NULL,
    `portrait`    varchar(255) DEFAULT NULL,
    `vipType`     varchar(255) DEFAULT 'Normal',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User`
    DISABLE KEYS */;
INSERT INTO `User`
VALUES (4, '1012681@qq.com', 'e10adc3949b', '测试一号', '12345678919', 100, 'Client', NULL, NULL, 3, NULL, NULL, NULL,
        'Normal'),
       (5, '123@qq.com', 'e10adc3949b', '测试二号', '12345678911', 100, 'Client', NULL, NULL, 3, NULL, NULL, NULL,
        'Normal'),
       (6, '333@qq.com', 'e10adc3949b', '工作人员1', NULL, NULL, 'HotelManager', NULL, NULL, 3, NULL, 1, NULL, 'Normal'),
       (7, '444@qq.com', 'e10adc3949b', '营销人员1', '13651731541', NULL, 'SalesPerson', NULL, NULL, 3, NULL, NULL, NULL, 'Normal'),
       (8, '555@qq.com', 'e10adc3949b', '管理人员1', NULL, NULL, 'Admin', NULL, NULL, 3, NULL, NULL, NULL, 'Normal');
/*!40000 ALTER TABLE `User`
    ENABLE KEYS */;
COMMIT;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;

--
-- Table structure for table `Collections`
--

DROP TABLE IF EXISTS `Collections`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Collections`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `userID`  int(11) NOT NULL,
    `hotelID` int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Questions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Questions`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `userID`    int(11) NOT NULL,
    `userName`  varchar(255) DEFAULT NULL,
    `hotelID`   int(11) NOT NULL,
    `question`  varchar(255) DEFAULT NULL,
    `available` bool         DEFAULT TRUE,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Answers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Answers`
(
    `answerId`   int(11) NOT NULL AUTO_INCREMENT,
    `userId`     int(11) NOT NULL,
    `userName`   varchar(255) DEFAULT NULL,
    `questionId` int(11) NOT NULL,
    `answer`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`answerId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Credits`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credits`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `userId`     int(11) NOT NULL,
    `changeDate` varchar(255) DEFAULT NULL,
    `change`     int(11)      DEFAULT 0,
    `now`        double       DEFAULT NULL,
    `reason`     varchar(255) DEFAULT NULL,
    `status`     int(5)       DEFAULT 0,
    `argue`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABlE IF EXISTS `VIP`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIP`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `type`            varchar(255)        DEFAULT NULL,
    `userId`          int(11)             DEFAULT NULL,
    `corporationName` varchar(255) UNIQUE DEFAULT NULL,
    `level`           int(11)             DEFAULT NULL,
    `consumption`     int(11)             DEFAULT 0,
    `status`          int(11)             DEFAULT 1,
    `reduction`       double              DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABlE IF EXISTS `VIPLevel`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIPLevel`
(
    `level`              int(11) NOT NULL,
    `type`               varchar(255) NOT NULL,
    `requestConsumption` int(11) NOT NULL,
    `reduction`          double NOT NULL,
    PRIMARY KEY (`level`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viplevel`
--

BEGIN;
/*!40000 ALTER TABLE `VIPLevel`
    DISABLE KEYS */;
INSERT INTO `VIPLevel`
VALUES (1, 'Client',0, 0.01),
       (2, 'Client',1000, 0.02),
       (3, 'Client',2500, 0.03),
       (4, 'Client',5000, 0.04),
       (5, 'Client',10000, 0.05),
       (1, 'Corporation', 0, 0.01),
       (2, 'Corporation', 20000, 0.02),
       (3, 'Corporation', 30000, 0.03);
/*!40000 ALTER TABLE `VIPLevel`
    ENABLE KEYS */;
COMMIT;