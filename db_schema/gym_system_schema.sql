-- MySQL Script generated by MySQL Workbench
-- Mon 06 Nov 2017 12:57:40 PM EST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema GymSystem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GymSystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GymSystem` DEFAULT CHARACTER SET utf8 ;
USE `GymSystem` ;

-- -----------------------------------------------------
-- Table `GymSystem`.`user_level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`user_level` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`user_level` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `description_UNIQUE` (`description` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`user` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `user_level_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `user_level_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_user_user_level1_idx` (`user_level_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_user_user_level1`
    FOREIGN KEY (`user_level_id`)
    REFERENCES `GymSystem`.`user_level` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`health_insurance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`health_insurance` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`health_insurance` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `description_UNIQUE` (`name` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`state` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`state` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(255) NOT NULL,
  `state_abbrev` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `state_UNIQUE` (`state` ASC),
  UNIQUE INDEX `state_abbrev_UNIQUE` (`state_abbrev` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`address` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`address` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `zipcode` VARCHAR(12) NOT NULL,
  `state_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `state_id`),
  UNIQUE INDEX `idtable1_UNIQUE` (`id` ASC),
  INDEX `fk_address_state1_idx` (`state_id` ASC),
  CONSTRAINT `fk_address_state1`
    FOREIGN KEY (`state_id`)
    REFERENCES `GymSystem`.`state` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`personal_information`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`personal_information` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`personal_information` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `health_insurance_id` INT UNSIGNED NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `health_insurance_id`, `address_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_personal_information_health_insurance1_idx` (`health_insurance_id` ASC),
  INDEX `fk_personal_information_address1_idx` (`address_id` ASC),
  CONSTRAINT `fk_personal_information_health_insurance1`
    FOREIGN KEY (`health_insurance_id`)
    REFERENCES `GymSystem`.`health_insurance` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_personal_information_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `GymSystem`.`address` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`manager`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`manager` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`manager` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `personal_information_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `personal_information_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_manager_user1_idx` (`user_id` ASC),
  INDEX `fk_manager_personal_information1_idx` (`personal_information_id` ASC),
  CONSTRAINT `fk_manager_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `GymSystem`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_manager_personal_information1`
    FOREIGN KEY (`personal_information_id`)
    REFERENCES `GymSystem`.`personal_information` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`trainer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`trainer` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`trainer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `personal_information_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `personal_information_id`, `user_id`),
  INDEX `fk_trainer_personal_information1_idx` (`personal_information_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_trainer_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_trainer_personal_information1`
    FOREIGN KEY (`personal_information_id`)
    REFERENCES `GymSystem`.`personal_information` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_trainer_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `GymSystem`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`membership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`membership` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`membership` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idmembership_UNIQUE` (`id` ASC),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`customer` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `membership_id` INT UNSIGNED NOT NULL,
  `personal_information_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `membership_id`, `personal_information_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_customer_membership1_idx` (`membership_id` ASC),
  CONSTRAINT `fk_customer_personal_information1`
    FOREIGN KEY (`personal_information_id`)
    REFERENCES `GymSystem`.`personal_information` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_membership1`
    FOREIGN KEY (`membership_id`)
    REFERENCES `GymSystem`.`membership` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`qualification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`qualification` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`qualification` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`machine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`machine` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`machine` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `picture_location` VARCHAR(255) NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`exercise_duration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`exercise_duration` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`exercise_duration` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `hours` INT UNSIGNED NOT NULL,
  `minutes` INT UNSIGNED NOT NULL,
  `seconds` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`exercise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`exercise` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`exercise` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `machine_id` INT UNSIGNED NULL,
  `duration_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_exercise_machine1_idx` (`machine_id` ASC),
  INDEX `fk_exercise_duration1_idx` (`duration_id` ASC),
  UNIQUE INDEX `duration_id_UNIQUE` (`duration_id` ASC),
  CONSTRAINT `fk_exercise_machine1`
    FOREIGN KEY (`machine_id`)
    REFERENCES `GymSystem`.`machine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exercise_duration1`
    FOREIGN KEY (`duration_id`)
    REFERENCES `GymSystem`.`exercise_duration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`exercise_set`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`exercise_set` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`exercise_set` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `repetitions` INT UNSIGNED NOT NULL,
  `exercise_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_exercise_set_exercise1_idx` (`exercise_id` ASC),
  CONSTRAINT `fk_exercise_set_exercise1`
    FOREIGN KEY (`exercise_id`)
    REFERENCES `GymSystem`.`exercise` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`workout_routine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`workout_routine` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`workout_routine` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`workout_routine_exercise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`workout_routine_exercise` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`workout_routine_exercise` (
  `workout_routine_id` INT UNSIGNED NOT NULL,
  `exercise_id` INT UNSIGNED NOT NULL,
  `exercise_order` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`workout_routine_id`, `exercise_id`),
  INDEX `fk_workout_routine_has_exercise_exercise1_idx` (`exercise_id` ASC),
  INDEX `fk_workout_routine_has_exercise_workout_routine1_idx` (`workout_routine_id` ASC),
  CONSTRAINT `fk_workout_routine_has_exercise_workout_routine1`
    FOREIGN KEY (`workout_routine_id`)
    REFERENCES `GymSystem`.`workout_routine` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_workout_routine_has_exercise_exercise1`
    FOREIGN KEY (`exercise_id`)
    REFERENCES `GymSystem`.`exercise` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`customer_workout_routine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`customer_workout_routine` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`customer_workout_routine` (
  `customer_membership_id` INT UNSIGNED NOT NULL,
  `workout_routine_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`customer_membership_id`, `workout_routine_id`),
  INDEX `fk_customer_has_workout_routine_workout_routine1_idx` (`workout_routine_id` ASC),
  INDEX `fk_customer_has_workout_routine_customer1_idx` (`customer_membership_id` ASC),
  CONSTRAINT `fk_customer_has_workout_routine_customer1`
    FOREIGN KEY (`customer_membership_id`)
    REFERENCES `GymSystem`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_has_workout_routine_workout_routine1`
    FOREIGN KEY (`workout_routine_id`)
    REFERENCES `GymSystem`.`workout_routine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`work_hours`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`work_hours` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`work_hours` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date_time` DATETIME(5) NOT NULL,
  `end_date_time` DATETIME(5) NOT NULL,
  `trainer_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_work_hours_trainer1_idx` (`trainer_id` ASC),
  CONSTRAINT `fk_work_hours_trainer1`
    FOREIGN KEY (`trainer_id`)
    REFERENCES `GymSystem`.`trainer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GymSystem`.`trainer_qualification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GymSystem`.`trainer_qualification` ;

CREATE TABLE IF NOT EXISTS `GymSystem`.`trainer_qualification` (
  `trainer_id` INT UNSIGNED NOT NULL,
  `qualification_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`trainer_id`, `qualification_id`),
  INDEX `fk_trainer_has_qualification_qualification1_idx` (`qualification_id` ASC),
  CONSTRAINT `fk_trainer_has_qualification_trainer1`
    FOREIGN KEY (`trainer_id`)
    REFERENCES `GymSystem`.`trainer` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trainer_has_qualification_qualification1`
    FOREIGN KEY (`qualification_id`)
    REFERENCES `GymSystem`.`qualification` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `GymSystem`.`user_level`
-- -----------------------------------------------------
START TRANSACTION;
USE `GymSystem`;
INSERT INTO `GymSystem`.`user_level` (`id`, `description`) VALUES (DEFAULT, 'ADMIN');
INSERT INTO `GymSystem`.`user_level` (`id`, `description`) VALUES (DEFAULT, 'MANAGER');
INSERT INTO `GymSystem`.`user_level` (`id`, `description`) VALUES (DEFAULT, 'TRAINER');
INSERT INTO `GymSystem`.`user_level` (`id`, `description`) VALUES (DEFAULT, 'CUSTOMER');

COMMIT;


-- -----------------------------------------------------
-- Data for table `GymSystem`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `GymSystem`;
INSERT INTO `GymSystem`.`user` (`id`, `username`, `password`, `user_level_id`) VALUES (1, 'admin', 'password', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `GymSystem`.`health_insurance`
-- -----------------------------------------------------
START TRANSACTION;
USE `GymSystem`;
INSERT INTO `GymSystem`.`health_insurance` (`id`, `name`) VALUES (DEFAULT, 'Cigna');
INSERT INTO `GymSystem`.`health_insurance` (`id`, `name`) VALUES (DEFAULT, 'Blue Cross Blue Shield');

COMMIT;


-- -----------------------------------------------------
-- Data for table `GymSystem`.`state`
-- -----------------------------------------------------
START TRANSACTION;
USE `GymSystem`;
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (1, 'Alabama', 'AL');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (2, 'Alaska', 'AK');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (3, 'Arizona', 'AZ');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (5, 'California', 'CA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (6, 'Colorado', 'CO');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (7, 'Connecticut', 'CT');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (8, 'Delaware', 'DE');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (9, 'Florida', 'FL');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (10, 'Georgia', 'GA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (11, 'Hawaii', 'HI');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (12, 'Idaho', 'ID');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (13, 'Illinois', 'IL');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (14, 'Indiana', 'IN');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (15, 'Iowa', 'IA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (16, 'Kansas', 'KS');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (17, 'Kentucky', 'KY');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (18, 'Louisiana', 'LA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (19, 'Maine', 'ME');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (20, 'Maryland', 'MD');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (21, 'Massachusetts', 'MA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (22, 'Michigan', 'MI');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (23, 'Minnesota', 'MN');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (24, 'Mississippi', 'MS');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (25, 'Missouri', 'MO');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (26, 'Montana', 'MT');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (27, 'Nebraska', 'NE');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (28, 'Nevada', 'NV');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (29, 'New Hampshire', 'NH');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (30, 'New Jersey', 'NJ');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (31, 'New Mexico', 'NM');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (32, 'New York', 'NY');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (33, 'North Carolina', 'NC');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (34, 'North Dakota', 'ND');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (35, 'Ohio', 'OH');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (36, 'Oklahoma', 'OK');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (37, 'Oregon', 'OR');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (38, 'Pennsylvania', 'PA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (39, 'Rhode Island', 'RI');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (40, 'South Carolina', 'SC');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (41, 'South Dakota', 'SD');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (42, 'Tennessee', 'TN');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (43, 'Texas', 'TX');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (44, 'Utah', 'UT');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (45, 'Vermont', 'VT');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (46, 'Virginia', 'VA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (47, 'Washington', 'WA');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (48, 'West Virginia', 'WV');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (49, 'Wisconsin', 'WI');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (50, 'Wyoming', 'WY');
INSERT INTO `GymSystem`.`state` (`id`, `state`, `state_abbrev`) VALUES (4, 'Arkansas', 'AR');

COMMIT;


-- -----------------------------------------------------
-- Data for table `GymSystem`.`membership`
-- -----------------------------------------------------
START TRANSACTION;
USE `GymSystem`;
INSERT INTO `GymSystem`.`membership` (`id`, `type`) VALUES (DEFAULT, 'INACTIVE');
INSERT INTO `GymSystem`.`membership` (`id`, `type`) VALUES (DEFAULT, 'ACTIVE');

COMMIT;

