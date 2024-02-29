-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema membership
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema membership
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `membership` DEFAULT CHARACTER SET utf8 ;
USE `membership` ;

-- -----------------------------------------------------
-- Table `membership`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `membership`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `membership`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `membership`.`roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `permissions` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `membership`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `membership`.`user_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_roles_users_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_roles_roles1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_roles_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `membership`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_roles_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `membership`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
