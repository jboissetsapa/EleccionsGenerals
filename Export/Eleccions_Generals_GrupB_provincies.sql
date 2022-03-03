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
-- Table structure for table `provincies`
--

DROP TABLE IF EXISTS `provincies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincies` (
  `provincia_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `comunitat_aut_id` tinyint(3) unsigned NOT NULL,
  `nom` varchar(45) DEFAULT NULL,
  `codi_ine` char(2) NOT NULL,
  `num_escons` tinyint(3) unsigned DEFAULT NULL COMMENT 'Numero d''escons que li pertoquen a aquella provincia',
  PRIMARY KEY (`provincia_id`),
  UNIQUE KEY `uk_provincies_codi_ine` (`codi_ine`),
  KEY `idx_fk_provincies_comunitats_autonomes` (`comunitat_aut_id`),
  CONSTRAINT `fk_provincies_comunitats_autonomes` FOREIGN KEY (`comunitat_aut_id`) REFERENCES `comunitats_autonomes` (`comunitat_aut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincies`
--

LOCK TABLES `provincies` WRITE;
/*!40000 ALTER TABLE `provincies` DISABLE KEYS */;
INSERT INTO `provincies` VALUES (1,13,'Pontevedra','36',7),(2,10,'Castellón / Castelló','12',5),(3,17,'Almería','4',6),(4,18,'Santa Cruz de Tenerife','38',7),(5,12,'Cáceres','10',4),(6,18,'Las Palmas','35',8),(7,14,'Lleida','25',4),(8,17,'Cádiz','11',9),(9,1,'Guadalajara','19',3),(10,15,'Salamanca','37',4),(11,19,'Gipuzkoa','20',6),(12,17,'Huelva','21',5),(13,10,'Alicante / Alacant','3',12),(14,17,'Jaén','23',5),(15,3,'Huesca','22',3),(16,15,'Zamora','49',3),(17,19,'Araba / Álava','1',4),(18,4,'Ceuta','51',1),(19,17,'Granada','18',7),(20,6,'Illes Balears','7',8),(21,15,'Valladolid','47',5),(22,12,'Badajoz','6',6),(23,9,'Navarra','31',5),(24,16,'Murcia','30',10),(25,15,'Soria','42',2),(26,3,'Teruel','44',3),(27,2,'La Rioja','26',4),(28,17,'Sevilla','41',12),(29,1,'Cuenca','16',3),(30,14,'Barcelona','8',32),(31,13,'A Coruña','15',8),(32,7,'Madrid','28',37),(33,17,'Córdoba','14',6),(34,3,'Zaragoza','50',7),(35,17,'Málaga','29',11),(36,1,'Ciudad Real','13',5),(37,15,'Ávila','5',3),(38,13,'Ourense','32',4),(39,5,'Melilla','52',1),(40,11,'Cantabria','39',5),(41,13,'Lugo','27',4),(42,8,'Asturias','33',7),(43,15,'Segovia','40',3),(44,10,'Valencia / València','46',15),(45,15,'Burgos','9',4),(46,19,'Bizkaia','48',8),(47,1,'Albacete','2',4),(48,1,'Toledo','45',6),(49,15,'León','24',4),(50,15,'Palencia','34',3),(51,14,'Girona','17',6),(52,14,'Tarragona','43',6);
/*!40000 ALTER TABLE `provincies` ENABLE KEYS */;
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
