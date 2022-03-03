-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 192.168.56.102    Database: Eleccions_Generals_GrupB
-- ------------------------------------------------------
-- Server version	8.0.17-8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comunitats_autonomes`
--

DROP TABLE IF EXISTS `comunitats_autonomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunitats_autonomes` (
  `comunitat_aut_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `codi_ine` char(2) NOT NULL,
  PRIMARY KEY (`comunitat_aut_id`),
  UNIQUE KEY `uk_com_aut_codi_ine` (`codi_ine`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunitats_autonomes`
--

LOCK TABLES `comunitats_autonomes` WRITE;
/*!40000 ALTER TABLE `comunitats_autonomes` DISABLE KEYS */;
INSERT INTO `comunitats_autonomes` VALUES (1,'Castilla - La Mancha','7'),(2,'La Rioja','16'),(3,'Aragón','2'),(4,'Ciudad de Ceuta','18'),(5,'Ciudad de Melilla','19'),(6,'Illes Balears','4'),(7,'Comunidad de Madrid','12'),(8,'Principado de Asturias','3'),(9,'Comunidad Foral de Navarr','13'),(10,'Comunitat Valenciana','17'),(11,'Cantabria','6'),(12,'Extremadura','10'),(13,'Galicia','11'),(14,'Cataluña','9'),(15,'Castilla y León','8'),(16,'Región de Murcia','15'),(17,'Andalucía','1'),(18,'Canarias','5'),(19,'País Vasco','14');
/*!40000 ALTER TABLE `comunitats_autonomes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-03 12:41:55
