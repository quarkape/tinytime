-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lightme
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `control_func`
--

DROP TABLE IF EXISTS `control_func`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `control_func` (
  `show` tinyint(2) NOT NULL,
  PRIMARY KEY (`show`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `control_func`
--

LOCK TABLES `control_func` WRITE;
/*!40000 ALTER TABLE `control_func` DISABLE KEYS */;
INSERT INTO `control_func` VALUES (1);
/*!40000 ALTER TABLE `control_func` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `feedback` (
  `clientid` varchar(45) NOT NULL,
  `detail` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`clientid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginrecord`
--

DROP TABLE IF EXISTS `loginrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `loginrecord` (
  `id` varchar(13) NOT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginrecord`
--

LOCK TABLES `loginrecord` WRITE;
/*!40000 ALTER TABLE `loginrecord` DISABLE KEYS */;
INSERT INTO `loginrecord` VALUES ('1668938289531','2022-11-21 00:08:57','0'),('1668938289531','2022-11-21 00:19:25','0'),('1668938289531','2022-11-21 00:26:33','0'),('1668938289531','2022-11-21 00:27:02','0'),('1668938289531','2022-11-21 00:27:23','0'),('1668938289531','2022-11-21 00:28:41','0'),('1668938289531','2022-11-21 00:28:55','0'),('1668938289531','2022-11-21 00:29:24','0'),('1668938289531','2022-11-21 00:34:49','0'),('1668938289531','2022-11-21 00:36:15','0'),('1668938289531','2022-11-21 10:55:18','1'),('1668938289531','2022-11-21 13:08:54','2'),('1668938289531','2022-11-21 14:07:45','2'),('1668938289531','2022-11-21 14:08:26','2'),('1668938289531','2022-11-21 14:08:43','2'),('1668938289531','2022-11-21 14:08:50','2'),('1668938289531','2022-11-21 14:08:59','2'),('1668938289531','2022-11-21 14:09:15','2'),('1668938289531','2022-11-21 14:19:19','2'),('1668938289531','2022-11-21 14:19:30','2'),('1668938289531','2022-11-21 14:19:34','2'),('1668938289531','2022-11-21 14:19:37','2'),('1668938289531','2022-11-21 14:19:40','2'),('1668938289531','2022-11-21 14:19:43','2'),('1668938289531','2022-11-21 14:20:34','2'),('1668938289531','2022-11-21 14:21:00','2'),('1668938289531','2022-11-21 14:21:16','2'),('1668938289531','2022-11-21 14:23:00','2'),('1668938289531','2022-11-21 14:23:13','2'),('1668938289531','2022-11-21 14:26:03','2'),('1668938289531','2022-11-21 14:26:11','2'),('1668938289531','2022-11-21 14:26:42','2'),('1668938289531','2022-11-21 14:26:54','2'),('1668938289531','2022-11-21 14:28:17','2'),('1668938289531','2022-11-21 14:28:30','2'),('1668938289531','2022-11-21 14:28:41','2'),('1668938289531','2022-11-21 14:29:31','2'),('1668938289531','2022-11-21 14:30:14','2'),('1668938289531','2022-11-21 14:30:47','2'),('1668938289531','2022-11-21 14:31:08','2'),('1668938289531','2022-11-21 14:31:43','2'),('1668938289531','2022-11-21 14:32:23','2'),('1668938289531','2022-11-21 14:32:32','2'),('1668938289531','2022-11-21 14:32:50','2'),('1668938289531','2022-11-21 14:33:07','2'),('1668877810897','2022-11-21 14:37:55','2'),('1668938289531','2022-11-21 14:39:13','2'),('1668938289531','2022-11-21 14:39:39','2'),('1668938289531','2022-11-21 14:40:20','2'),('1668877810897','2022-11-21 15:04:15','2'),('1668877810897','2022-11-21 15:06:02','2'),('1668877810897','2022-11-21 15:08:00','2'),('1668877810897','2022-11-21 15:10:24','2'),('1668877810897','2022-11-21 15:14:07','2'),('1668877810897','2022-11-21 15:14:52','2'),('1668877810897','2022-11-21 15:16:01','2'),('1668877810897','2022-11-21 15:16:51','2'),('1668877810897','2022-11-21 15:19:06','2'),('1668877810897','2022-11-21 15:19:22','2'),('1668877810897','2022-11-21 15:19:38','2'),('1668877810897','2022-11-21 15:20:06','2'),('1668877810897','2022-11-21 15:20:26','2'),('1668877810897','2022-11-21 15:22:08','2'),('1668877810897','2022-11-21 15:22:34','2'),('1668877810897','2022-11-21 15:22:46','2'),('1668877810897','2022-11-21 15:27:18','2'),('1668877810897','2022-11-21 15:30:11','2'),('1668877810897','2022-11-21 15:31:26','2'),('1668877810897','2022-11-21 15:31:50','2'),('1668877810897','2022-11-21 15:32:19','2'),('1668877810897','2022-11-21 15:33:04','2'),('1668877810897','2022-11-21 15:33:50','2'),('1668877810897','2022-11-21 15:34:36','2'),('1668877810897','2022-11-21 15:34:59','2'),('1668877810897','2022-11-21 15:35:48','2'),('1668877810897','2022-11-21 15:37:37','2'),('1668877810897','2022-11-21 15:39:38','2'),('1668877810897','2022-11-21 15:40:44','2'),('1668877810897','2022-11-21 15:41:54','2'),('1668877810897','2022-11-21 15:42:15','2'),('1668877810897','2022-11-21 15:44:06','2'),('1668877810897','2022-11-21 15:44:17','2'),('1668877810897','2022-11-21 15:45:09','2'),('1668877810897','2022-11-21 15:45:33','2'),('1668877810897','2022-11-21 15:46:12','2'),('1668877810897','2022-11-21 15:46:43','2'),('1668877810897','2022-11-21 15:47:05','2'),('1668877810897','2022-11-21 15:47:27','2'),('1668877810897','2022-11-21 15:47:45','2'),('1668877810897','2022-11-21 15:48:01','2'),('1668877810897','2022-11-21 15:50:37','2'),('1668877810897','2022-11-21 15:50:41','2'),('1668877810897','2022-11-21 15:51:31','2'),('1668877810897','2022-11-21 15:52:10','2'),('1668877810897','2022-11-21 15:52:43','2'),('1668877810897','2022-11-21 16:07:49','2'),('1668877810897','2022-11-21 16:08:07','2'),('1668877810897','2022-11-21 16:09:27','2'),('1668877810897','2022-11-21 16:21:10','2'),('1668877810897','2022-11-21 16:26:17','2'),('1668877810897','2022-11-21 16:27:38','2'),('1668877810897','2022-11-21 16:29:09','2'),('1668877810897','2022-11-21 16:29:50','2'),('1668877810897','2022-11-21 16:30:44','2'),('1668877810897','2022-11-21 16:31:20','2'),('1668877810897','2022-11-21 16:32:28','2'),('1668877810897','2022-11-21 16:33:21','2'),('1668877810897','2022-11-21 16:33:31','2'),('1668877810897','2022-11-21 16:38:20','2'),('1668877810897','2022-11-21 16:40:25','2'),('1668877810897','2022-11-21 16:41:01','2'),('1668877810897','2022-11-21 16:41:12','2'),('1668877810897','2022-11-21 16:43:56','2'),('1668877810897','2022-11-21 16:44:25','2'),('1668877810897','2022-11-21 16:46:16','2'),('1668877810897','2022-11-21 16:46:29','2'),('1668877810897','2022-11-21 16:46:41','2'),('1668877810897','2022-11-21 16:47:25','2'),('1668877810897','2022-11-21 16:47:32','2'),('1668877810897','2022-11-21 16:47:58','2'),('1668877810897','2022-11-21 16:48:09','2'),('1668877810897','2022-11-22 17:51:29','2'),('1668877810897','2022-11-22 17:52:19','2'),('1668877810897','2022-11-22 20:11:00','2'),('1668877810897','2022-11-22 20:32:35','2');
/*!40000 ALTER TABLE `loginrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `postid` varchar(45) NOT NULL,
  `messageid` varchar(45) DEFAULT NULL,
  `rolea` varchar(45) DEFAULT NULL,
  `roleb` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新增一条当别人评论或者收藏时收到的消息提醒';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('16688778108971668932042672','16688778108971668937934779','1668877810897','undefined','1',0,'2022-11-20 17:52:14'),('16688778108971668932042672','16689382895311668938533956','1668938289531','undefined','1',0,'2022-11-20 18:02:13'),('16688778108971668932042672','16688778108971669018114536','1668877810897','undefined','1',0,'2022-11-21 16:08:34');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notify`
--

DROP TABLE IF EXISTS `notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notify` (
  `id` varchar(45) NOT NULL,
  `notifyid` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notify`
--

LOCK TABLES `notify` WRITE;
/*!40000 ALTER TABLE `notify` DISABLE KEYS */;
INSERT INTO `notify` VALUES ('1668877810897','16688778108971668877810897','注册成功通知','欢迎注册使用话题起源说。您可以随时发布话题和参与到话题讨论中，可以读到正能量和负能量句子并制作表情包，平衡忙碌的一天。希望你爱上思考与交流。',1,'2022-11-20 01:10:10'),('1668938289531','16689382895311668938289531','注册成功通知','欢迎注册使用话题起源说。您可以随时发布话题和参与到话题讨论中，可以读到正能量和负能量句子并制作表情包，平衡忙碌的一天。希望你爱上思考与交流。',1,'2022-11-20 17:58:09');
/*!40000 ALTER TABLE `notify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pic_count`
--

DROP TABLE IF EXISTS `pic_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pic_count` (
  `type` tinyint(2) NOT NULL COMMENT '0表示正能量表情包，1表示负能量表情包',
  `count` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='存储表情包的个数，便于后期直接增加表情包';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pic_count`
--

LOCK TABLES `pic_count` WRITE;
/*!40000 ALTER TABLE `pic_count` DISABLE KEYS */;
INSERT INTO `pic_count` VALUES (0,15),(1,15);
/*!40000 ALTER TABLE `pic_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `posts` (
  `postid` varchar(26) CHARACTER SET utf8 NOT NULL,
  `authorid` varchar(13) CHARACTER SET utf8 DEFAULT NULL,
  `type` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `anonymous` int(2) DEFAULT NULL,
  `title` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `imgtype` int(2) DEFAULT NULL,
  `imgurl` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `date` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES ('16688778108971668932042672','1668877810897','0',0,'图书馆座位',0,'/users/1668877810897/16688778108971668932042672.jpg','2022-11-20 16:14:02'),('16688778108971669018447541','1668877810897','0',0,'又是一年银杏落',0,'/users/1668877810897/16688778108971669018447541.jpg','2022-11-21 16:14:07'),('16688778108971669110858717','1668877810897','0',0,'Emotional Damage',0,'/users/1668877810897/16688778108971669110858717.jpg','2022-11-22 17:54:18');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postscomment`
--

DROP TABLE IF EXISTS `postscomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `postscomment` (
  `postid` varchar(26) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `clientid` varchar(45) DEFAULT NULL,
  `detail` varchar(200) DEFAULT NULL,
  `quote` varchar(200) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postscomment`
--

LOCK TABLES `postscomment` WRITE;
/*!40000 ALTER TABLE `postscomment` DISABLE KEYS */;
INSERT INTO `postscomment` VALUES ('16688778108971668932042672','0','1668877810897','评论','','2022-11-20 17:19:04'),('16688778108971668932042672','1','1668877810897','引用评论','回复内容：评论','2022-11-20 17:52:14'),('16688778108971668932042672','1','1668938289531','中肯的、极好的、一针见血的','回复内容：评论','2022-11-20 18:02:13'),('16688778108971668932042672','1','1668877810897','哈哈哈哈嗝','回复内容：中肯的、极好的、一针见血的','2022-11-21 16:08:34'),('16688778108971669018447541','0','1668877810897','做好自己就行了，很多事情都遵循一个道理：船到桥头自然直。','','2022-11-21 16:39:13');
/*!40000 ALTER TABLE `postscomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postscontent`
--

DROP TABLE IF EXISTS `postscontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `postscontent` (
  `postid` varchar(26) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postscontent`
--

LOCK TABLES `postscontent` WRITE;
/*!40000 ALTER TABLE `postscontent` DISABLE KEYS */;
INSERT INTO `postscontent` VALUES ('16688778108971668932042672','现在9点来图书馆算是晚的了！！！'),('16688778108971669018447541','时间如白驹过隙。转眼已经大二了，感觉做了一些事，又感觉什么事都没有做。每天都感觉很忙，但是好像每天也没事也没有做成。心中渐渐的有一些慌了，遇到一些棘手的问题总是选择逃避，但是又常常说服自己是在做更优化的安排。事实上我自己也不太明白。'),('16688778108971669110858717','人潮熙熙攘攘，去年的此时你在干些什么？明年的此时你又会在哪里？');
/*!40000 ALTER TABLE `postscontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postsinfomation`
--

DROP TABLE IF EXISTS `postsinfomation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `postsinfomation` (
  `postid` varchar(45) NOT NULL,
  `clientid` varchar(45) DEFAULT NULL,
  `view` int(2) DEFAULT NULL,
  `agree` int(2) DEFAULT NULL,
  `collect` int(2) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postsinfomation`
--

LOCK TABLES `postsinfomation` WRITE;
/*!40000 ALTER TABLE `postsinfomation` DISABLE KEYS */;
INSERT INTO `postsinfomation` VALUES ('16688778108971668932042672','1668877810897',1,0,0,'2022-11-20 17:18:47'),('16688778108971669110858717','1668877810897',1,0,0,'2022-11-22 17:54:30'),('16688778108971669018447541','1668877810897',1,0,0,'2022-11-21 16:38:22');
/*!40000 ALTER TABLE `postsinfomation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postsreport`
--

DROP TABLE IF EXISTS `postsreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `postsreport` (
  `postid` varchar(26) NOT NULL,
  `clientid` varchar(45) DEFAULT NULL,
  `detail` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postsreport`
--

LOCK TABLES `postsreport` WRITE;
/*!40000 ALTER TABLE `postsreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `postsreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` varchar(40) CHARACTER SET utf8 NOT NULL,
  `name` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mark` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatarurl` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1668877810897','加班未遂','2B3912B3D45282974A139533C859B699','quarkape@qq.com','NONE','/users/1668877810897/avatar.jpg'),('1668938289531','黄金切尔西','2B3912B3D45282974A139533C859B699','1773714090@qq.com','NONE','/wxbg/defaultavatar.png');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votesentence`
--

DROP TABLE IF EXISTS `votesentence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `votesentence` (
  `date` varchar(20) NOT NULL,
  `userid` varchar(13) DEFAULT NULL,
  `state` int(2) DEFAULT NULL,
  `type` int(2) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votesentence`
--

LOCK TABLES `votesentence` WRITE;
/*!40000 ALTER TABLE `votesentence` DISABLE KEYS */;
INSERT INTO `votesentence` VALUES ('2022-11-19','1668877810897',1,1),('2022-11-19','1668877810897',1,0),('2022-11-21','1668938289531',0,1),('2022-11-21','1668938289531',0,0),('2022-11-18','1668938289531',1,1);
/*!40000 ALTER TABLE `votesentence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'lightme'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-22 23:44:57
