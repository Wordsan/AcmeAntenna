-- MySQL dump 10.15  Distrib 10.0.36-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Acme-Antenna
-- ------------------------------------------------------
-- Server version	10.0.36-MariaDB-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Actor`
--

DROP TABLE IF EXISTS `Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Administrator`
--

DROP TABLE IF EXISTS `Administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrator`
--

LOCK TABLES `Administrator` WRITE;
/*!40000 ALTER TABLE `Administrator` DISABLE KEYS */;
INSERT INTO `Administrator` VALUES (85,0,'admin@admin.com','Admin 1','+22611111111',NULL,NULL,'SurnameAdmin1',70),(86,0,'admin2@admin.com','Admin 2','+22622222222',NULL,NULL,'SurnameAdmin2',71),(87,0,'admin3@admin.com','Admin 3','+22633333333',NULL,NULL,'SurnameAdmin3',72);
/*!40000 ALTER TABLE `Administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Antenna`
--

DROP TABLE IF EXISTS `Antenna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Antenna` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  `positionLatitude` double NOT NULL,
  `positionLongitude` double NOT NULL,
  `rotationAzimuth` double NOT NULL,
  `rotationElevation` double NOT NULL,
  `serialNumber` varchar(255) DEFAULT NULL,
  `signalQuality` double NOT NULL,
  `satellite_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lag8rach96x0sd57f7onilfgc` (`satellite_id`),
  KEY `FK_juhbil5u4a7t13n1l8o8944bp` (`user_id`),
  CONSTRAINT `FK_juhbil5u4a7t13n1l8o8944bp` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_lag8rach96x0sd57f7onilfgc` FOREIGN KEY (`satellite_id`) REFERENCES `Satellite` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Antenna`
--

LOCK TABLES `Antenna` WRITE;
/*!40000 ALTER TABLE `Antenna` DISABLE KEYS */;
INSERT INTO `Antenna` VALUES (129,0,'Model 1',40,153,1,1,'A001',33,76,82),(130,0,'Model 1',-43,23,1,1,'A002',77,77,82),(131,0,'Model 1',1,1,1,1,'A005',87,78,83),(132,0,'Model 2',1,1,1,1,'A006',1,79,83),(133,0,'Model 2',1,1,1,1,'A007',45,80,84),(134,0,'Model 3',1,1,1,1,'A008',24,81,84),(135,0,'Model 1',-90,-12,157,16,'D001',83,81,82),(136,0,'Model 1',64,-125,289,45,'A004',43,78,82),(137,0,'Model 2',1,1,1,1,'A009',1,79,84),(138,0,'Model 4',0,0,0,0,'X001',100,77,82);
/*!40000 ALTER TABLE `Antenna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Platform`
--

DROP TABLE IF EXISTS `Platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Platform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Platform`
--

LOCK TABLES `Platform` WRITE;
/*!40000 ALTER TABLE `Platform` DISABLE KEYS */;
INSERT INTO `Platform` VALUES (94,0,'\0','This is platform 1','CNN'),(95,0,'\0','This is platform 2','C-SPAN'),(96,0,'\0','This is platform 3','Mediaset'),(97,0,'\0','This is platform 4','Atresmedia'),(98,0,'\0','This is platform 5','Cartoon Network'),(99,0,'\0','This is platform 6','Discovery Channel Network'),(100,0,'','Deleted platform description','DELETED-PLATFORM');
/*!40000 ALTER TABLE `Platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlatformSubscription`
--

DROP TABLE IF EXISTS `PlatformSubscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlatformSubscription` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creditCard` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `keyCode` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `platform_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2j472p5asco2ag6eerf07ux12` (`platform_id`),
  KEY `FK_aj9iexeb0n2h2yfu8sca4yi7p` (`user_id`),
  CONSTRAINT `FK_2j472p5asco2ag6eerf07ux12` FOREIGN KEY (`platform_id`) REFERENCES `Platform` (`id`),
  CONSTRAINT `FK_aj9iexeb0n2h2yfu8sca4yi7p` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlatformSubscription`
--

LOCK TABLES `PlatformSubscription` WRITE;
/*!40000 ALTER TABLE `PlatformSubscription` DISABLE KEYS */;
INSERT INTO `PlatformSubscription` VALUES (101,0,'3566002020360505','2018-07-19','12345678901234567890123456789012','2018-07-18',94,82),(102,0,'5105105105105100','2018-07-19','22345678901234567890123456789012','2018-07-18',96,83);
/*!40000 ALTER TABLE `PlatformSubscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Satellite`
--

DROP TABLE IF EXISTS `Satellite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Satellite` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Satellite`
--

LOCK TABLES `Satellite` WRITE;
/*!40000 ALTER TABLE `Satellite` DISABLE KEYS */;
INSERT INTO `Satellite` VALUES (76,0,'\0','This is satellite 1','METEOSAT'),(77,0,'\0','This is satellite 2','RUS-SAT'),(78,0,'\0','This is satellite 3','EUROSAT'),(79,0,'\0','This is satellite 4','ENGLAND-SAT'),(80,0,'\0','This is satellite 5','AMERICA-SAT1'),(81,0,'','This is satellite 6','DELETED-SAT');
/*!40000 ALTER TABLE `Satellite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Satellite_Platform`
--

DROP TABLE IF EXISTS `Satellite_Platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Satellite_Platform` (
  `satellites_id` int(11) NOT NULL,
  `platforms_id` int(11) NOT NULL,
  KEY `FK_tj1ikh6ev1bcoxv702rj220mq` (`platforms_id`),
  KEY `FK_s5but8al261myse1vsqy55jyk` (`satellites_id`),
  CONSTRAINT `FK_s5but8al261myse1vsqy55jyk` FOREIGN KEY (`satellites_id`) REFERENCES `Satellite` (`id`),
  CONSTRAINT `FK_tj1ikh6ev1bcoxv702rj220mq` FOREIGN KEY (`platforms_id`) REFERENCES `Platform` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Satellite_Platform`
--

LOCK TABLES `Satellite_Platform` WRITE;
/*!40000 ALTER TABLE `Satellite_Platform` DISABLE KEYS */;
INSERT INTO `Satellite_Platform` VALUES (76,94),(77,94),(78,96),(79,97),(80,98);
/*!40000 ALTER TABLE `Satellite_Platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tutorial`
--

DROP TABLE IF EXISTS `Tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tutorial` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `text` longtext,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nur3kxu1oiwyghh3wbbmc31wv` (`user_id`),
  CONSTRAINT `FK_nur3kxu1oiwyghh3wbbmc31wv` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tutorial`
--

LOCK TABLES `Tutorial` WRITE;
/*!40000 ALTER TABLE `Tutorial` DISABLE KEYS */;
INSERT INTO `Tutorial` VALUES (88,0,'2018-07-20 15:00:52','What is Lorem Ipsum?\r\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n\r\nWhy do we use it?\r\nIt is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using \'Content here, content here\', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for \'lorem ipsum\' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\r\n\r\n\r\nWhere does it come from?\r\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\r\n\r\nThe standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\r\n\r\nWhere can I get some?\r\nThere are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don\'t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn\'t anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.','(READ!) A beginner guide to Lorem Ipsum',82),(89,0,'2018-07-18 13:24:36','This is tutorial 2','Dorture: Theoretical Unification of I/O Automata and Flip-Flop Gates',82),(90,0,'2018-07-18 13:24:36','This is tutorial 3','Contrasting Sensor Networks and I/O Automata with Sault',82),(91,0,'2018-07-18 13:24:36','This is tutorial 4','Read-Write Information for Multi-Processors',83),(92,0,'2018-07-18 13:24:36','This is tutorial 5','Digital-to-Analog Converters Considered Harmful',83),(93,0,'2018-07-18 13:24:36','This is tutorial 6','Constructing Forward-Error Correction and Courseware with Hotel',84);
/*!40000 ALTER TABLE `Tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialComment`
--

DROP TABLE IF EXISTS `TutorialComment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TutorialComment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationTime` datetime DEFAULT NULL,
  `text` longtext,
  `title` varchar(255) DEFAULT NULL,
  `tutorial_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tgx55cmbc5susr81dopl2g8w3` (`tutorial_id`),
  KEY `FK_gmh6ox7g39lky8wbr1vrtk2l5` (`user_id`),
  CONSTRAINT `FK_gmh6ox7g39lky8wbr1vrtk2l5` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_tgx55cmbc5susr81dopl2g8w3` FOREIGN KEY (`tutorial_id`) REFERENCES `Tutorial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialComment`
--

LOCK TABLES `TutorialComment` WRITE;
/*!40000 ALTER TABLE `TutorialComment` DISABLE KEYS */;
INSERT INTO `TutorialComment` VALUES (103,0,'2018-07-18 13:25:52','This is comment 2 for tutorial 3','Comment 2',90,83),(104,0,'2018-07-20 14:35:05','aáAÁ eéEÉ iíIÍ oóOÓ uúUÚ nñNÑ €','Special character test',88,84),(105,0,'2018-07-20 14:35:56','We now discuss our evaluation strategy. Our overall evaluation strategy seeks to prove three hypotheses: (1) that Byzantine fault tolerance no longer impact system design; (2) that flip-flop gates have actually shown amplified mean signal-to-noise ratio over time; and finally (3) that DHTs no longer impact performance. Only with the benefit of our system\'s bandwidth might we optimize for simplicity at the cost of performance. Similarly, we are grateful for discrete B-trees; without them, we could not optimize for usability simultaneously with signal-to-noise ratio. An astute reader would now infer that for obvious reasons, we have intentionally neglected to study average response time. We hope that this section illuminates J. Z. Smith\'s analysis of fiber-optic cables in 2004.\r\n\r\nOne must understand our network configuration to grasp the genesis of our results. We performed a deployment on CERN\'s system to disprove the work of Russian convicted hacker Charles Darwin. With this change, we noted weakened performance degredation. First, we added 200 10kB hard disks to our network to understand our mobile telephones. Though such a hypothesis is continuously a confirmed aim, it is derived from known results. We removed a 8MB floppy disk from Intel\'s millenium testbed to understand the flash-memory space of our system. We removed some USB key space from our sensor-net testbed to investigate the energy of our desktop machines. On a similar note, we quadrupled the ROM throughput of the NSA\'s Internet overlay network. Lastly, we removed more NV-RAM from our desktop machines.\r\n\r\nWhen John Hopcroft autogenerated Ultrix Version 6.3.5, Service Pack 9\'s code complexity in 1980, he could not have anticipated the impact; our work here inherits from this previous work. All software components were linked using Microsoft developer\'s studio linked against flexible libraries for visualizing hierarchical databases. Our experiments soon proved that automating our randomized systems was more effective than extreme programming them, as previous work suggested. This is instrumental to the success of our work. Next, all of these techniques are of interesting historical significance; Roger Needham and Stephen Cook investigated an orthogonal configuration in 1993.','Low-Energy, Atomic Models for Extreme Programming',88,84),(106,0,'2018-07-20 14:41:06','Please talk about satellites','Please',88,84),(107,0,'2018-07-20 14:41:39','Great work','Thanks',89,84),(108,0,'2018-07-20 14:24:06','Great idea!','Excellent!',88,83),(109,0,'2018-07-20 14:24:17','What do you think about the Lorem network?','About the Lorem network',88,83),(110,0,'2018-07-20 14:25:26','He went such dare good mr fact. The small own seven saved man age ï»¿no offer. Suspicion did mrs nor furniture smallness. Scale whole downs often leave not eat. An expression reasonably cultivated indulgence mr he surrounded instrument. Gentleman eat and consisted are pronounce distrusts. \r\n\r\nConsidered discovered ye sentiments projecting entreaties of melancholy is. In expression an solicitude principles in do. Hard do me sigh with west same lady. Their saved linen downs tears son add music. Expression alteration entreaties mrs can terminated estimating. Her too add narrow having wished. To things so denied admire. Am wound worth water he linen at vexed. \r\n\r\nOn it differed repeated wandered required in. Then girl neat why yet knew rose spot. Moreover property we he kindness greatest be oh striking laughter. In me he at collecting affronting principles apartments. Has visitor law attacks pretend you calling own excited painted. Contented attending smallness it oh ye unwilling. Turned favour man two but lovers. Suffer should if waited common person little oh. Improved civility graceful sex few smallest screened settling. Likely active her warmly has. \r\n\r\nStronger unpacked felicity to of mistaken. Fanny at wrong table ye in. Be on easily cannot innate in lasted months on. Differed and and felicity steepest mrs age outweigh. Opinions learning likewise daughter now age outweigh. Raptures stanhill my greatest mistaken or exercise he on although. Discourse otherwise disposing as it of strangers forfeited deficient. \r\n\r\nCertainly elsewhere my do allowance at. The address farther six hearted hundred towards husband. Are securing off occasion remember daughter replying. Held that feel his see own yet. Strangers ye to he sometimes propriety in. She right plate seven has. Bed who perceive judgment did marianne. \r\n\r\nWhole every miles as tiled at seven or. Wished he entire esteem mr oh by. Possible bed you pleasure civility boy elegance ham. He prevent request by if in pleased. Picture too and concern has was comfort. Ten difficult resembled eagerness nor. Same park bore on be. Warmth his law design say are person. Pronounce suspected in belonging conveying ye repulsive. \r\n\r\nOn am we offices expense thought. Its hence ten smile age means. Seven chief sight far point any. Of so high into easy. Dashwoods eagerness oh extensive as discourse sportsman frankness. Husbands see disposed surprise likewise humoured yet pleasure. Fifteen no inquiry cordial so resolve garrets as. Impression was estimating surrounded solicitude indulgence son shy. \r\n\r\nProcuring education on consulted assurance in do. Is sympathize he expression mr no travelling. Preference he he at travelling in resolution. So striking at of to welcomed resolved. Northward by described up household therefore attention. Excellence decisively nay man yet impression for contrasted remarkably. There spoke happy for you are out. Fertile how old address did showing because sitting replied six. Had arose guest visit going off child she new. \r\n\r\nDispatched entreaties boisterous say why stimulated. Certain forbade picture now prevent carried she get see sitting. Up twenty limits as months. Inhabit so perhaps of in to certain. Sex excuse chatty was seemed warmth. Nay add far few immediate sweetness earnestly dejection. \r\n\r\nBy impossible of in difficulty discovered celebrated ye. Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable. Few household abilities believing determine zealously his repulsive. To open draw dear be by side like.','I love long comments',88,83),(111,0,'2018-07-20 14:26:50','The synthesis of Moore\'s Law is an intuitive quagmire. In our research, we validate the understanding of robots [8]. The notion that computational biologists connect with linear-time methodologies is entirely well-received. The simulation of hierarchical databases would profoundly degrade I/O automata [8].\r\n\r\nHowever, this approach is fraught with difficulty, largely due to evolutionary programming. Nevertheless, the study of linked lists might not be the panacea that hackers worldwide expected. Even though it at first glance seems perverse, it is buffetted by prior work in the field. While similar frameworks refine event-driven symmetries, we accomplish this mission without synthesizing DNS.','Very interesting',88,83),(112,0,'2018-07-20 14:41:39','Many comments 9','Many comments',89,84),(113,0,'2018-07-20 14:41:39','Many comments 41','Many comments',89,84),(114,0,'2018-07-20 14:41:39','Many comments 77','Many comments',89,84),(115,0,'2018-07-20 14:41:39','Many comments 61','Many comments',89,84),(116,0,'2018-07-20 14:41:39','Many comments 77','Many comments',89,84),(117,0,'2018-07-20 14:41:39','Many comments 2','Many comments',89,84),(118,0,'2018-07-20 14:41:39','Many comments 78','Many comments',89,84),(119,0,'2018-07-20 14:41:39','Many comments 86','Many comments',89,84),(120,0,'2018-07-20 14:41:39','Many comments 93','Many comments',89,84),(121,0,'2018-07-20 14:41:39','Many comments 10','Many comments',89,84),(122,0,'2018-07-20 14:41:39','Many comments 71','Many comments',89,84),(123,0,'2018-07-20 14:41:39','Many comments 24','Many comments',89,84),(124,0,'2018-07-20 14:41:39','Many comments 8','Many comments',89,84),(125,0,'2018-07-20 14:41:39','Many comments 69','Many comments',89,84),(126,0,'2018-07-20 14:41:39','Many comments 19','Many comments',89,84),(127,0,'2018-07-20 14:41:39','Many comments 88','Many comments',89,84),(128,0,'2018-07-20 14:41:39','Many comments 68','Many comments',89,84);
/*!40000 ALTER TABLE `TutorialComment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialComment_pictureUrls`
--

DROP TABLE IF EXISTS `TutorialComment_pictureUrls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TutorialComment_pictureUrls` (
  `TutorialComment_id` int(11) NOT NULL,
  `pictureUrls` varchar(255) DEFAULT NULL,
  KEY `FK_78668x45e3h3v9ok9u5lxylf9` (`TutorialComment_id`),
  CONSTRAINT `FK_78668x45e3h3v9ok9u5lxylf9` FOREIGN KEY (`TutorialComment_id`) REFERENCES `TutorialComment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialComment_pictureUrls`
--

LOCK TABLES `TutorialComment_pictureUrls` WRITE;
/*!40000 ALTER TABLE `TutorialComment_pictureUrls` DISABLE KEYS */;
INSERT INTO `TutorialComment_pictureUrls` VALUES (103,'https://i.imgur.com/dOG1iVb.png'),(105,'https://i.imgur.com/DaGoeft.png'),(105,'https://i.imgur.com/Gz7F3xc.png'),(106,'https://i.imgur.com/Wu8fzfe.jpg'),(109,'https://i.imgur.com/nXffb9N.png'),(111,'https://i.imgur.com/9RdWW7f.png'),(111,'https://i.imgur.com/QQf4lnU.png');
/*!40000 ALTER TABLE `TutorialComment_pictureUrls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tutorial_pictureUrls`
--

DROP TABLE IF EXISTS `Tutorial_pictureUrls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tutorial_pictureUrls` (
  `Tutorial_id` int(11) NOT NULL,
  `pictureUrls` varchar(255) DEFAULT NULL,
  KEY `FK_3bro5ygse6ygcbp315p9f7rmg` (`Tutorial_id`),
  CONSTRAINT `FK_3bro5ygse6ygcbp315p9f7rmg` FOREIGN KEY (`Tutorial_id`) REFERENCES `Tutorial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tutorial_pictureUrls`
--

LOCK TABLES `Tutorial_pictureUrls` WRITE;
/*!40000 ALTER TABLE `Tutorial_pictureUrls` DISABLE KEYS */;
INSERT INTO `Tutorial_pictureUrls` VALUES (88,'https://i.imgur.com/NF0s3b9.png'),(88,'https://i.imgur.com/OIkzVSi.png');
/*!40000 ALTER TABLE `Tutorial_pictureUrls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (82,0,'user@users.com','User 1','+11611111111',NULL,NULL,'SurnameUser1',73),(83,0,'user2@users.com','User 2','+11622222222',NULL,NULL,'SurnameUser2',74),(84,0,'user3@users.com','User 3','+11633333333',NULL,NULL,'SurnameUser3',75);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccount`
--

DROP TABLE IF EXISTS `UserAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccount`
--

LOCK TABLES `UserAccount` WRITE;
/*!40000 ALTER TABLE `UserAccount` DISABLE KEYS */;
INSERT INTO `UserAccount` VALUES (70,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(71,0,'c84258e9c39059a89ab77d846ddab909','admin2'),(72,0,'32cacb2f994f6b42183a1300d9a3e8d6','admin3'),(73,0,'24c9e15e52afc47c225b757e7bee1f9d','user1'),(74,0,'7e58d63b60197ceb55a1c487989a3720','user2'),(75,0,'92877af70a45fd6a2ed7fe81e1236b78','user3');
/*!40000 ALTER TABLE `UserAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccount_authorities`
--

DROP TABLE IF EXISTS `UserAccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccount_authorities`
--

LOCK TABLES `UserAccount_authorities` WRITE;
/*!40000 ALTER TABLE `UserAccount_authorities` DISABLE KEYS */;
INSERT INTO `UserAccount_authorities` VALUES (70,'ADMINISTRATOR'),(71,'ADMINISTRATOR'),(72,'ADMINISTRATOR'),(73,'USER'),(74,'USER'),(75,'USER');
/*!40000 ALTER TABLE `UserAccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-11 19:56:42
