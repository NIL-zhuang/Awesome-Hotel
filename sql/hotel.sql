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
    `hotelId`         int(11)      DEFAULT '-1',
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
VALUES (2, '满500-100优惠', 2, 3, '满减优惠券', 500, NULL, 0, 1, NULL, NULL, 100, NULL);
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
    `address`          varchar(255) DEFAULT NULL,
    `bizRegion`        varchar(255) DEFAULT NULL,
    `hotelStar`        varchar(255) DEFAULT NULL,
    `phoneNum`         int(11)      DEFAULT NULL,
    `rate`             double       DEFAULT NULL,
    `manager_id`       int(11)      DEFAULT NULL,
    `commentTime`      int(11)      DEFAULT 0,
    `points`           double       DEFAULT 5,
    `sanitation`       double       DEFAULT 5,
    `environment`      double       DEFAULT 5,
    `service`          double       DEFAULT 5,
    `equipment`        double       DEFAULT 5,
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
VALUES (1, '汉庭酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Three', 1829373819, 4.8, 1, 0, 5, 5, 5, 5, 5, NULL),
       (2, '如家酒店', '欢迎您入住', '南京市鼓楼区珠江路268号', 'XiDan', 'Four', 1829373819, 4.8, 2, 0, 5, 5, 5, 5, 5, NULL),
       (3, '桂圆酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Five', 1829553719, 4.8, 6, 0, 5, 5, 5, 5, 5, NULL);
#        (4, '格林豪泰', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (5, '布丁酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (6, '维也纳酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (7, '凯利酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (8, '莫泰酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (9, '青年酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (10, '凯悦酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (11, '皇冠假日酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (12, '豪生酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (13, '法莱德酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (14, '茂悦酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (15, '艾美酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6),
#        (16, '索菲特酒店', '欢迎您入住', '南京市栖霞区珠江路268号', 'XiDan', 'Four', 1829553719, 4.8, 6);;
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
    `price`         decimal(65, 0) DEFAULT NULL,
    `clientName`    varchar(255)   DEFAULT NULL,
    `phoneNumber`   varchar(255)   DEFAULT NULL,
    `orderState`    varchar(255)   DEFAULT NULL,
    `comments`      varchar(255)   DEFAULT NULL,
    `points`        int(5)         DEFAULT 5,
    `sanitation`    int(5)         DEFAULT 5,
    `environment`   int(5)         DEFAULT 5,
    `service`       int(5)         DEFAULT 5,
    `equipment`     int(5)         DEFAULT 5,
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
VALUES (1, 1, 1, '国际会议中心', 12312312312, '2020-06-01', '2020-06-02', 'BigBed', '1', 1, 0, '2020-06-01', 100, 'zzy',
        '110', '未入住', '你的野区我养猪', 5, 5, 5, 5, 5);
/*!40000 ALTER TABLE `OrderList`
    ENABLE KEYS */;
COMMIT;

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
       (5, 399, 10, 10, 2, 'Family', 'Family', 'Porridge', 3);
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
    `email`       varchar(255)   DEFAULT NULL,
    `password`    varchar(11) NOT NULL,
    `username`    varchar(255)   DEFAULT NULL,
    `phonenumber` varchar(255)   DEFAULT NULL,
    `credit`      double(255, 0) DEFAULT NULL,
    `usertype`    varchar(255)   DEFAULT NULL,
    `birthday`    varchar(255)   DEFAULT NULL,
    `corporation` varchar(255)   DEFAULT NULL,
    `annulTime`   int(5)         DEFAULT 0,
    `jobNumber`   int(11)        DEFAULT NULL,
    `hotelID`     int(11)        DEFAULT NULL,
    `portrait`    varchar(255)   DEFAULT NULL,
    `vipType`     varchar(255)   DEFAULT 'Normal',
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
       (7, '444@qq.com', 'e10adc3949b', '营销人员1', NULL, NULL, 'SalesPerson', NULL, NULL, 3, NULL, NULL, NULL, 'Normal'),
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
-- Table structure for table `Likes`
--

DROP TABLE IF EXISTS `Likes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Likes`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `userID`  int(11) NOT NULL,
    `hotelID` int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `userId`     int(11) NOT NULL,
    `questionId` int(11) NOT NULL,
    `answers`    varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `Credits`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credits`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT,
    `userID` int(11) NOT NULL,
    `change` int(11) DEFAULT 0,
    `now`    int(11) DEFAULT NULL,
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
    `level`           int(11) DEFAULT NULL,
    `consumption`     int (11) DEFAULT 0,
    `status`          int(11) DEFAULT 1,
    `reduction`       double DEFAULT NULL,
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
    `level`              int(11) UNIQUE NOT NULL,
    `requestConsumption` int(11)        NOT NULL,
    PRIMARY KEY (`level`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;