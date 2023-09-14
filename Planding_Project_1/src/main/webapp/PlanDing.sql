
-- 1. 프로젝트 후기 테이블
DROP TABLE IF EXISTS project.project_review_tbl;

-- 2. 프로젝트 리워드 테이블
DROP TABLE IF EXISTS project.project_reward_tbl;

-- 3. 후원 테이블
DROP TABLE IF EXISTS project.donation_tbl;

-- 4. 관리자 수익 테이블
DROP TABLE IF EXISTS project.admin_income_tbl;

-- 5. 리워드 테이블
DROP TABLE IF EXISTS project.reward_tbl;

-- 6. 북마크 테이블
DROP TABLE IF EXISTS project.bookmark_tbl;

-- 7. 공지사항 테이블
DROP TABLE IF EXISTS project.notice_tbl;

-- 8. 문의사항 테이블
DROP TABLE IF EXISTS project.qna_tbl;

-- 9. 프로젝트 플래너 테이블
DROP TABLE IF EXISTS project.project_planner_tbl;

-- 10. 주소 테이블
DROP TABLE IF EXISTS project.address_tbl;

-- 11. 회원 테이블
DROP TABLE IF EXISTS project.member_tbl;

-- 12. 프로젝트 테이블
DROP TABLE IF EXISTS project.project_tbl;

-- 테이블 순서대로 삭제한 후, 다시 생성합니다.

-- CREATE TABLE 문들을 여기에 추가합니다.

USE `project` ;
SHOW TABLES;




SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE SCHEMA IF NOT EXISTS `project` DEFAULT CHARACTER SET utf8 ;
USE `project` ;


CREATE TABLE IF NOT EXISTS `project`.`project_tbl` (
  `project_id` INT NOT NULL AUTO_INCREMENT COMMENT '프로젝트 ID',
  `kind` VARCHAR(10) NOT NULL COMMENT 'Donate or Funding',
  `title` NVARCHAR(50) NOT NULL COMMENT '프로젝트 제목',
  `summary` NVARCHAR(100) NOT NULL COMMENT '요약글',
  `thumbnail` VARCHAR(60) NOT NULL,
  `content` NVARCHAR(500) NOT NULL COMMENT '내용',
  `image` VARCHAR(1024) NOT NULL COMMENT '프로젝트 이미지',
  `startdate` DATETIME NOT NULL COMMENT '시작일',
  `enddate` DATETIME NOT NULL COMMENT '종료일',
  `goal_amount` INT NOT NULL COMMENT '목표 모금액',
  `curr_amount` INT NOT NULL COMMENT '현재 모금액',
  `status` VARCHAR(30) NOT NULL COMMENT '상태',
  `likes` INT NOT NULL COMMENT '관심 수',
  `regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`project_id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `project`.`member_tbl` (
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `password` VARCHAR(256) NOT NULL COMMENT '비밀번호',
  `name` NVARCHAR(20) NOT NULL COMMENT '이름',
  `email` VARCHAR(45) NOT NULL COMMENT '이메일',
  `phone` VARCHAR(11) NOT NULL,
  `account` INT NOT NULL COMMENT '가상계좌 (계좌잔액)',
  `admin_status` VARCHAR(1) NOT NULL COMMENT '관리자 여부',
  `joindate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
  `delete_status` VARCHAR(1) NULL DEFAULT 'N' COMMENT '탈퇴회원 여부',
  `deletedate` TIMESTAMP NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `project`.`reward_tbl` (
  `reward_id` INT NOT NULL AUTO_INCREMENT COMMENT '리워드 ID',
  `r_name` NVARCHAR(30) NOT NULL COMMENT '리워드 이름',
  `r_content` NVARCHAR(100) NOT NULL COMMENT '리워드 설명',
  `r_price` INT NOT NULL COMMENT '리워드 금액',
  PRIMARY KEY (`reward_id`))
ENGINE = InnoDB;

INSERT INTO project.reward_tbl (r_name, r_content, r_price)
VALUES ('donate', 'donate', 1000);


CREATE TABLE IF NOT EXISTS `project`.`donation_tbl` (
  `donation_id` INT NOT NULL AUTO_INCREMENT COMMENT '후원기록 ID',
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
  `r_price` INT NOT NULL,
  `add_donation` INT NULL COMMENT '추가 후원금액',
  `address_id` VARCHAR(100) NULL,
  `donatedate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '후원일자',
  PRIMARY KEY (`donation_id`),
  INDEX `fk_donation_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_donation_tbl_reward_tbl1_idx` (`reward_id` ASC) VISIBLE,
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



CREATE TABLE IF NOT EXISTS `project`.`admin_income_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `fee_income` INT NOT NULL COMMENT '수수료 수익',
  `incomedate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수익일자',
  INDEX `fk_admin_income_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_income_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `project`.`project_reward_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
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



CREATE TABLE IF NOT EXISTS `project`.`notice_tbl` (
  `notice_id` INT NOT NULL AUTO_INCREMENT COMMENT '공지사항ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '작성자 ID',
  `n_title` NVARCHAR(30) NOT NULL COMMENT '공지사항 제목',
  `n_content` NVARCHAR(500) NOT NULL COMMENT '공지사항 내용',
  `n_image` VARCHAR(100) NULL COMMENT '공지사항 이미지',
  `importance` VARCHAR(1) NULL COMMENT '중요글 여부 YN',
  `viewcount` INT NOT NULL COMMENT '조회수',
  `writetime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성시간',
  PRIMARY KEY (`notice_id`),
  INDEX `fk_notice_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_notice_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `project`.`qna_tbl` (
  `qna_id` INT NOT NULL AUTO_INCREMENT COMMENT '문의사항 ID',
  `q_writer` VARCHAR(20) NOT NULL COMMENT '작성자 ID',
  `q_title` VARCHAR(256) NOT NULL COMMENT '질문 제목',
  `q_content` NVARCHAR(500) NOT NULL COMMENT '질문 내용',
  `q_image` VARCHAR(100) NULL COMMENT '문의사항 이미지',
  `q_private` VARCHAR(1) NOT NULL COMMENT '비밀글 여부 YN',
  `q_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '질문시간',
  `a_writer` VARCHAR(20) NULL,
  `a_content` NVARCHAR(300) NULL DEFAULT 'unanswered' COMMENT '답변 내용',
  `a_time` TIMESTAMP NULL COMMENT '답변 시간',
  PRIMARY KEY (`qna_id`),
  INDEX `fk_qna_tbl_member_tbl1_idx` (`q_writer` ASC) VISIBLE,
  INDEX `fk_qna_tbl_member_tbl2_idx` (`a_writer` ASC) VISIBLE,
  CONSTRAINT `fk_qna_tbl_member_tbl1`
    FOREIGN KEY (`q_writer`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qna_tbl_member_tbl2`
    FOREIGN KEY (`a_writer`)
    REFERENCES `project`.`member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


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



CREATE TABLE IF NOT EXISTS `project`.`bookmark_tbl` (
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원ID',
  `project_id` INT NOT NULL COMMENT '관심프로젝트 ID',
  `likedate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '관심글 등록일자',
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `project`.`project_review_tbl` (
  `review_id` INT NOT NULL AUTO_INCREMENT COMMENT '프로젝트후기ID',
  `project_id` INT NOT NULL COMMENT '프로젝트ID',
  `content` NVARCHAR(500) NOT NULL COMMENT '후기내용',
  `image` VARCHAR(310) NULL COMMENT '이미지',
  INDEX `fk_project_review_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  PRIMARY KEY (`review_id`),
  CONSTRAINT `fk_project_review_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project`.`project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `project`.`address_tbl` (
  `address_id` VARCHAR(100) NOT NULL,
  `member_id` VARCHAR(20) NOT NULL,
  `receiver_name` NVARCHAR(20) NOT NULL,
  `receiver_phone` VARCHAR(11) NOT NULL,
  `postcode` INT NOT NULL,
  `address1` NVARCHAR(60) NOT NULL,
  `address2` NVARCHAR(60) NOT NULL,
  `basic_status` VARCHAR(1) NOT NULL COMMENT '기본주소 여부 YN',
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
