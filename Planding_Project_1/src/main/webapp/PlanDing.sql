-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `project` DEFAULT CHARACTER SET utf8 ;
USE `project` ;

-- -----------------------------------------------------
-- Table `project`.`project_tbl` 프로젝트 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`project_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `kind` VARCHAR(10) NOT NULL COMMENT 'Donate or Funding',
  `title` NVARCHAR(50) NOT NULL COMMENT '프로젝트 제목',
  `summary` NVARCHAR(100) NOT NULL COMMENT '요약글',
  `content` NVARCHAR(500) NOT NULL COMMENT '내용',
  `thumbnail` VARCHAR(60) NOT NULL,
  `startdate` DATE NOT NULL COMMENT '시작일',
  `enddate` DATE NOT NULL COMMENT '종료일',
  `goal_amount` INT NOT NULL COMMENT '목표 모금액',
  `curr_amount` INT NOT NULL COMMENT '현재 모금액',
  `category` VARCHAR(30) NOT NULL COMMENT '카테고리 - 환경/동물/사람',
  `status` VARCHAR(30) NOT NULL COMMENT '상태',
  `likes` INT NOT NULL COMMENT '관심 수',
  PRIMARY KEY (`project_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`member_tbl` 회원 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`member_tbl` (
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `password` VARCHAR(256) NOT NULL COMMENT '비밀번호',
  `name` NVARCHAR(20) NOT NULL COMMENT '이름',
  `email` VARCHAR(45) NOT NULL COMMENT '이메일',
  `joindate` TIMESTAMP NOT NULL DEFAULT now() COMMENT '가입일',
  `account` INT NOT NULL COMMENT '가상계좌 (계좌잔액)',
  `isAdmin` TINYINT NOT NULL COMMENT '관리자 여부',
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`reward_tbl` 리워드 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`reward_tbl` (
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
  `name` NVARCHAR(30) NOT NULL COMMENT '리워드 이름',
  `desc` NVARCHAR(100) NOT NULL COMMENT '리워드 설명',
  `price` INT NOT NULL COMMENT '리워드 금액',
  PRIMARY KEY (`reward_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`donation_tbl` 후원기록 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`donation_tbl` (
  `donate_id` INT NOT NULL COMMENT '후원기록 ID',
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
  `add_donation` INT NULL COMMENT '추가 후원금액',
  `donatedate` TIMESTAMP NOT NULL DEFAULT now() COMMENT '후원일자',
  `comment` NVARCHAR(100) NULL COMMENT '응원글',
  PRIMARY KEY (`donate_id`),
  INDEX `fk_donation_tbl_project_tbl_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_donation_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_donation_tbl_reward_tbl1_idx` (`reward_id` ASC) VISIBLE,
  CONSTRAINT `fk_donation_tbl_project_tbl`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_donation_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_donation_tbl_reward_tbl1`
    FOREIGN KEY (`reward_id`)
    REFERENCES `project`.`reward_tbl` (`reward_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`former_member_tbl` 탈퇴회원 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`former_member_tbl` (
  `member_id` VARCHAR(20) NOT NULL COMMENT '탈퇴한 회원 ID',
  `email` VARCHAR(45) NOT NULL COMMENT '이메일',
  `joindate` DATE NOT NULL COMMENT '가입일',
  `withdrawdate` TIMESTAMP NOT NULL DEFAULT now() COMMENT '탈퇴일',
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`admin_income_tbl` 관리자 수익 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`admin_income_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `fee_income` INT NOT NULL COMMENT '수수료 수익',
  `incomedate` INT NOT NULL COMMENT '수익일자',
  INDEX `fk_admin_income_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_income_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`project_reward_tbl` 프로젝트-리워드 매핑 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`project_reward_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
  `donation` INT NOT NULL,
  PRIMARY KEY (`project_id`, `reward_id`),
  INDEX `fk_project_reward_tbl_reward_tbl1_idx` (`reward_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_reward_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_reward_tbl_reward_tbl1`
    FOREIGN KEY (`reward_id`)
    REFERENCES `project`.`reward_tbl` (`reward_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`notice_tbl` 공지사항 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`notice_tbl` (
  `notice_id` INT NOT NULL,
  `member_id` VARCHAR(20) NOT NULL,
  `title` NVARCHAR(30) NOT NULL,
  `content` NVARCHAR(500) NOT NULL,
  `image` VARCHAR(60) NULL,
  `importance` TINYINT NULL,
  `viewcount` INT NOT NULL,
  `writetime` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`notice_id`),
  INDEX `fk_notice_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_notice_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`qna_tbl` 문의사항 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`qna_tbl` (
  `qna_id` INT NOT NULL COMMENT '문의사항 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '작성자 ID',
  `q_title` NVARCHAR(30) NOT NULL COMMENT '질문 제목',
  `q_content` VARCHAR(400) NOT NULL COMMENT '질문 내용',
  `q_time` TIMESTAMP NOT NULL DEFAULT now() COMMENT '질문시간',
  `a_title` NVARCHAR(30) NOT NULL COMMENT '답변 제목',
  `a_content` NVARCHAR(300) NOT NULL COMMENT '답변 내용',
  `a_time` TIMESTAMP NOT NULL DEFAULT now() COMMENT '답변 시간',
  PRIMARY KEY (`qna_id`),
  INDEX `fk_qna_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_qna_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`image_tbl` 이미지 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`image_tbl` (
  `image_id` INT NOT NULL COMMENT '이미지 ID',
  `filename` VARCHAR(60) NOT NULL COMMENT '파일명',
  `filepath` VARCHAR(100) NOT NULL COMMENT '파일경로',
  PRIMARY KEY (`image_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`project_image_tbl` 프로젝트-이미지 매핑 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`project_image_tbl` (
  `project_id` INT NOT NULL,
  `image_id` INT NOT NULL,
  INDEX `fk_project_image_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_project_image_tbl_image_tbl1_idx` (`image_id` ASC) VISIBLE,
  PRIMARY KEY (`project_id`, `image_id`),
  CONSTRAINT `fk_project_image_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_image_tbl_image_tbl1`
    FOREIGN KEY (`image_id`)
    REFERENCES `project`.`image_tbl` (`image_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`qna_image_tbl` 문의사항-이미지 매핑 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`qna_image_tbl` (
  `qna_id` INT NOT NULL,
  `image_id` INT NOT NULL,
  PRIMARY KEY (`qna_id`, `image_id`),
  INDEX `fk_qna_image_tbl_image_tbl1_idx` (`image_id` ASC) VISIBLE,
  CONSTRAINT `fk_qna_image_tbl_qna_tbl1`
    FOREIGN KEY (`qna_id`)
    REFERENCES `project`.`qna_tbl` (`qna_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qna_image_tbl_image_tbl1`
    FOREIGN KEY (`image_id`)
    REFERENCES `project`.`image_tbl` (`image_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`project_planner_tbl` 프로젝트 기획자 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`project_planner_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '기획자 ID',
  `planner_name` NVARCHAR(20) NOT NULL COMMENT '기획자 이름 (개인 또는 기업, 단체)',
  `introduce` NVARCHAR(100) NOT NULL COMMENT '기획자 간단 소개글',
  `bank` NVARCHAR(20) NOT NULL COMMENT '입금계좌 은행',
  `account` VARCHAR(45) NOT NULL COMMENT '입금계좌 계좌번호',
  PRIMARY KEY (`project_id`, `member_id`),
  INDEX `fk_project_planner_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_planner_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_planner_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`bookmark_tbl` 관심프로젝트 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`bookmark_tbl` (
  `member_id` VARCHAR(20) NOT NULL,
  `project_id` INT NOT NULL,
  `likedate` TIMESTAMP NOT NULL DEFAULT now(),
  INDEX `fk_bookmark_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_bookmark_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_bookmark_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bookmark_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`project_review_tbl` 프로젝트 펀딩후기 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`project_review_tbl` (
  `review_id` INT NOT NULL,
  `project_id` INT NOT NULL,
  `content` NVARCHAR(500) NOT NULL,
  `image1` VARCHAR(60) NULL,
  `image2` VARCHAR(60) NULL,
  `image3` VARCHAR(60) NULL,
  INDEX `fk_project_review_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  PRIMARY KEY (`review_id`),
  CONSTRAINT `fk_project_review_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`address_tbl` 주소 테이블
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`address_tbl` (
  `address_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `receiver_name` NVARCHAR(20) NOT NULL,
  `postcode` INT NOT NULL,
  `address1` NVARCHAR(60) NOT NULL,
  `address2` NVARCHAR(60) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`address_id`),
  INDEX `fk_address_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  UNIQUE INDEX `member_id_UNIQUE` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
