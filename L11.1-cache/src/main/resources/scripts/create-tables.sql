CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `age` INT(3) NOT NULL DEFAULT 0,
  `address_id` BIGINT(20) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `addresses` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `phones` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NULL,
  `number` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));