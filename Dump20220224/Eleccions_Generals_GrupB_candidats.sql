-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 192.168.56.101    Database: Eleccions_Generals_GrupB
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
-- Table structure for table `candidats`
--

DROP TABLE IF EXISTS `candidats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidats` (
  `candidat_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `candidatura_id` int(10) unsigned NOT NULL,
  `persona_id` int(10) unsigned NOT NULL,
  `provincia_id` tinyint(3) unsigned NOT NULL,
  `num_ordre` tinyint(4) DEFAULT NULL COMMENT 'Num ordre del candidatdins la llista del partit dins de la circumpscripció que es presenta.',
  `tipus` enum('T','S') DEFAULT NULL COMMENT 'T=Titular, S=Suplent',
  PRIMARY KEY (`candidat_id`),
  UNIQUE KEY `uk_candidats_persona_cand` (`candidatura_id`,`persona_id`),
  KEY `fk_candidats_provincies1_idx` (`provincia_id`),
  KEY `fk_candidats_persones1_idx` (`persona_id`),
  KEY `fk_candidats_candidatures1_idx` (`candidatura_id`),
  CONSTRAINT `fk_candidats_candidatures1` FOREIGN KEY (`candidatura_id`) REFERENCES `candidatures` (`candidatura_id`),
  CONSTRAINT `fk_candidats_persones1` FOREIGN KEY (`persona_id`) REFERENCES `persones` (`persona_id`),
  CONSTRAINT `fk_candidats_provincies1` FOREIGN KEY (`provincia_id`) REFERENCES `provincies` (`provincia_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidats`
--

LOCK TABLES `candidats` WRITE;
/*!40000 ALTER TABLE `candidats` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidats` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 13:08:34
