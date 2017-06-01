CREATE SCHEMA IF NOT EXISTS `db_0`;
CREATE SCHEMA IF NOT EXISTS `db_1`;
CREATE SCHEMA IF NOT EXISTS `db_2`;

DROP TABLE IF EXISTS `db_0`.`t_order` ;
DROP TABLE IF EXISTS `db_1`.`t_order`;
DROP TABLE IF EXISTS `db_2`.`t_order`;
DROP TABLE IF EXISTS `db_0`.`t_more_order`;
DROP TABLE IF EXISTS `db_1`.`t_more_order`;
DROP TABLE IF EXISTS `db_2`.`t_more_order`;
DROP TABLE IF EXISTS `db_0`.`t_tocken` ;
DROP TABLE IF EXISTS `db_1`.`t_tocken`;
DROP TABLE IF EXISTS `db_2`.`t_tocken`;

DROP TABLE IF EXISTS `db_0`.`t_order_0`;
DROP TABLE IF EXISTS `db_1`.`t_order_0`;
DROP TABLE IF EXISTS `db_2`.`t_order_0`;
DROP TABLE IF EXISTS `db_0`.`t_more_order_0`;
DROP TABLE IF EXISTS `db_1`.`t_more_order_0`;
DROP TABLE IF EXISTS `db_2`.`t_more_order_0`;
DROP TABLE IF EXISTS `db_0`.`t_tocken_0`;
DROP TABLE IF EXISTS `db_1`.`t_tocken_0`;
DROP TABLE IF EXISTS `db_2`.`t_tocken_0`;

DROP TABLE IF EXISTS `db_0`.`t_order_1`;
DROP TABLE IF EXISTS `db_1`.`t_order_1`;
DROP TABLE IF EXISTS `db_2`.`t_order_1`;
DROP TABLE IF EXISTS `db_0`.`t_more_order_1`;
DROP TABLE IF EXISTS `db_1`.`t_more_order_1`;
DROP TABLE IF EXISTS `db_2`.`t_more_order_1`;
DROP TABLE IF EXISTS `db_0`.`t_tocken_1`;
DROP TABLE IF EXISTS `db_1`.`t_tocken_1`;
DROP TABLE IF EXISTS `db_2`.`t_tocken_1`;

CREATE TABLE IF NOT EXISTS `db_0`.`t_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_more_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_more_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_more_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_tocken` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_tocken_0` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_0`.`t_tocken_1` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `db_1`.`t_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_more_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_more_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_more_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_tocken` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_tocken_0` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_1`.`t_tocken_1` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `db_2`.`t_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_more_order` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_more_order_0` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_more_order_1` (`id` BIGINT NOT NULL, `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_tocken` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_tocken_0` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `db_2`.`t_tocken_1` (`id` VARCHAR(50), `user_id` BIGINT NOT NULL, `status` VARCHAR(50), PRIMARY KEY (`id`)) COLLATE utf8_general_ci;