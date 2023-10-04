CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `library`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `public_day` date DEFAULT NULL,
  `pages_number` int DEFAULT NULL,
  `sold` int DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `description` varchar(2555) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (2,'To Kill a Mockingbird','Harper Lee','Horror','1960-07-11',281,0,'book-cover-To-Kill-a-Mockingbird-many-1961.webp','a poignant novel by Harper Lee, set in 1930s Alabama. Through the eyes of Scout Finch, we witness the unjust trial of a black man accused of rape, highlighting themes of racism and injustice. It is a compelling exploration of morality and the power of empathy in a divided society.'),(3,'Sapiens: A Brief History of Humankind','Yuval Noah Harari','Non-fiction','2014-02-10',443,15,'23692271.jpg','\"Sapiens: A Brief History of Humankind\" by Yuval Noah Harari provides a concise yet thought-provoking overview of human history, from our origins to modern times. It explores major milestones, societal shifts, and the interplay between biology, culture, and technology. The book challenges conventional narratives and invites readers to reflect on our collective journey as a species.'),(6,'The Great Gatsby','F. Scott Fitzgerald','Funny','2011-06-08',112,25,'CK-3.webp','a classic novel by F. Scott Fitzgerald, published in 1925. It explores the pursuit of wealth, love, and the American Dream in the 1920s. Through the story of Jay Gatsby and his infatuation with Daisy Buchanan, the novel examines themes of illusion, moral decay, and the complexities of human desires. Fitzgerald\'s vivid prose captures the glamour and disillusionment of the Jazz Age, offering a critical portrayal of the era and its hollowness.'),(7,'The Hobbit','J.R.R. Tolkien','Funny','2023-05-07',1120,0,'Image-via-Amazon-1.jpg','a fantasy novel by J.R.R. Tolkien that follows Bilbo Baggins, a hobbit, on a quest to reclaim a treasure from the dragon Smaug. It is filled with adventure, mythical creatures, and themes of heroism and personal growth.'),(9,'The Alchemist','Paulo Coelho','Honor','2023-05-11',123,0,'alchemist_12323.jpg','A novel written by Brazilian author Paulo Coelho. Published in 1988, it has since become one of the most widely translated and best-selling books in the world, captivating millions of readers with its timeless wisdom and enchanting storytelling.');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `book_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,1,'vietanh',1,5,'hay'),(2,7,'User4',1,2,'Tam on'),(3,4,'Minh',1,5,'Qua hay');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Viet Anh','havietanh2409@gmail.com','vietanh','123','user'),(2,'Owenz','alo123@gmail.com','owenz','123','user'),(3,'Admin','admin@gmail.com','admin','123','admin'),(4,'Minh','user@gmail.com','user','123','user'),(5,'Hieu','user2@gmail.com','user2','123','user'),(6,'Thanh','user3@gmail.com','user3','123','user'),(7,'Dat','user4@gmail.com','user4','123','user'),(9,'Long','user5@gmail.com','user5','123','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userorder`
--

DROP TABLE IF EXISTS `userorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userorder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `book_name` varchar(255) NOT NULL,
  `status` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userorder`
--

LOCK TABLES `userorder` WRITE;
/*!40000 ALTER TABLE `userorder` DISABLE KEYS */;
INSERT INTO `userorder` VALUES (1,7,1,'The Alchemist',0,12),(2,7,1,'The Alchemist',0,13),(3,7,1,'The Alchemist',0,12),(4,7,2,'To Kill a Mockingbird',0,1),(5,7,6,'The Great Gatsby',1,1),(6,7,3,'Sapiens: A Brief History of Humankind',1,1),(7,9,6,'The Great Gatsby',1,1),(8,7,7,'The Hobbit',1,1),(9,9,6,'The Great Gatsby',1,5),(10,7,1,'The Alchemist',0,2),(11,7,1,'The Alchemist',0,5),(12,7,1,'The Alchemist',0,66),(13,7,2,'To Kill a Mockingbird',1,12);
/*!40000 ALTER TABLE `userorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-10 22:35:30
