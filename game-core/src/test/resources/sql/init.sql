CREATE SCHEMA IF NOT EXISTS `db_0`;
CREATE SCHEMA IF NOT EXISTS `db_1`;
CREATE SCHEMA IF NOT EXISTS `db_2`;

DROP TABLE IF EXISTS `db_0`.`t_order` ;
DROP TABLE IF EXISTS `db_1`.`t_order`;
DROP TABLE IF EXISTS `db_2`.`t_order`;

DROP TABLE IF EXISTS `db_0`.`t_order_0`;
DROP TABLE IF EXISTS `db_1`.`t_order_0`;
DROP TABLE IF EXISTS `db_2`.`t_order_0`;

DROP TABLE IF EXISTS `db_0`.`t_order_1`;
DROP TABLE IF EXISTS `db_1`.`t_order_1`;
DROP TABLE IF EXISTS `db_2`.`t_order_1`;

CREATE TABLE IF NOT EXISTS `db_0`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_0`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_0`.`t_order` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `db_1`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_1`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_1`.`t_order` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `db_2`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_2`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));
CREATE TABLE IF NOT EXISTS `db_2`.`t_order` (`id` BIGINT NOT NULL, `user_id` INT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`));

