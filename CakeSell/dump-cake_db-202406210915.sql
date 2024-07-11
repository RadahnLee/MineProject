-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: cake_db
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL,
  `uid` int NOT NULL,
  `cakename` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `cakenum` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `cakeprice` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,'原神,仰望星空,巧克力蛋糕,奶酪芝士,奥巧芝士','1,10,2,5,8','648,120,1000,39,33');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT '123456',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('lisi','123456'),('zhangsan','123456');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `userId` int NOT NULL,
  `oid` int NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `goods_num` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `total_price` int NOT NULL,
  `odate` date NOT NULL,
  `goods_id` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'巧克力蛋糕,原神','1,1',1648,'2022-01-01','1,8'),(2,2,'原神','1',648,'2022-01-10','8'),(2,3,'原神','1',648,'2022-01-11','8'),(3,4,'原神','1',648,'2022-01-12','8'),(1,5,'原神','1',648,'2022-01-13','8'),(1,6,'原神','1',648,'2022-01-14','8'),(1,7,'原神','1',648,'2022-01-15','8'),(1,8,'原神','1',648,'2024-06-19','8'),(1,9,'原神','1',648,'2024-06-19','8'),(1,13,'原神','1',648,'2024-06-19','8'),(1,14,'巧克力蛋糕','1',1000,'2024-06-19','1'),(1,15,'仰望星空','1',120,'2024-06-20','7');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_cake`
--

DROP TABLE IF EXISTS `tbl_cake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_cake` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `state` int NOT NULL DEFAULT '1',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '这个商品太懒了，什么也没有留下',
  `stock` int NOT NULL DEFAULT '25',
  `pic_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_cake`
--

LOCK TABLES `tbl_cake` WRITE;
/*!40000 ALTER TABLE `tbl_cake` DISABLE KEYS */;
INSERT INTO `tbl_cake` VALUES (1,'巧克力蛋糕',1000,1,'这个商品太懒了，什么也没有留下',24,'https://www.wdmcake.cn/images/upload/Image/20180719167.jpg'),(2,'撒旦最爱',1500,2,'这个商品太懒了，什么也没有留下',25,'https://image.pearvideo.com/cont/20190420/cont-1545428-11931099.png'),(3,'奶酪芝士',39,1,'这个商品太懒了，什么也没有留下',25,'https://ts1.cn.mm.bing.net/th/id/R-C.4b108186b67302b3baa05ebc45b6d2da?rik=kUStw4EKuNM%2bow&riu=http%3a%2f%2fwww.yg-hz.com%2fuploads%2fallimg%2f200318%2f1631352506-0.jpg&ehk=B7WYpxu%2biCgT9YqtpTVDdQJ9Iim%2b6iHEgv6SQ0wBEyQ%3d&risl=&pid=ImgRaw&r=0'),(4,'熔岩巧克力',45,1,'这个商品太懒了，什么也没有留下',25,'https://image.pearvideo.com/cont/20201122/10899851-155043-1.png'),(5,'奥巧芝士',33,1,'这个商品太懒了，什么也没有留下',25,'https://img14.360buyimg.com/pop/jfs/t1/166511/3/25247/227791/61c9a166E55a98057/ee602351e868545f.jpg'),(6,'蓝纹奶酪',111,1,'这个商品太懒了，什么也没有留下',25,'https://img.meidouya.com/2019/11/Stilton-Shropshire1500-56a126853df78cf77267dde7.jpg'),(7,'仰望星空',120,1,'这个商品太懒了，什么也没有留下',24,'https://ts1.cn.mm.bing.net/th/id/R-C.9503fd6e14fd87916941132c3598c0e3?rik=NVdUEZ%2bUA4AnWA&riu=http%3a%2f%2fi2.chuimg.com%2fdacb1336875511e6a9a10242ac110002_605w_320h.jpg%3fimageView2%2f2%2fw%2f660%2finterlace%2f1%2fq%2f90&ehk=HvfDBDNCVai%2fGn3Q73c6Y3ygiuXNB4%2bEZ7JGV79Jjqk%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1'),(8,'原神',648,1,'这个商品太懒了，什么也没有留下',22,'https://upload-bbs.miyoushe.com/upload/2023/03/05/319827834/eaf25761b59ca029e5a611a7009d7477_7524406914512364816.jpg?x-oss-process=image/resize,s_600/quality,q_80/auto-orient,0/interlace,1/format,jpg');
/*!40000 ALTER TABLE `tbl_cake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL DEFAULT '123456',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_unique` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'zhangsan','123456'),(2,'lisi','123456'),(3,'wangwu','123456'),(4,'yuanshen','123456'),(5,'liwei','123456'),(6,'lihonglin','123456'),(7,'dixuejin','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cake_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-21  9:15:54
