CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `age` INT(3) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));