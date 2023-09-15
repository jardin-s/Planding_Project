/* 테이블 일괄삭제 (순서대로)
drop table former_member_tbl;

drop table project_reward_tbl;
drop table donation_tbl;
drop table reward_tbl;

drop table project_review_tbl;
drop table bookmark_tbl;
drop table project_planner_tbl;
drop table admin_income_tbl;
drop table project_tbl;

drop table address_tbl;
drop table qna_tbl;
drop table notice_tbl;
drop table member_tbl;
*/

/*
 2023.9.13
 member_tbl, qna_tbl 기존생성
 매출관리 테스트를 위해 project_tbl, admin_income_tbl 생성
   
 */

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
-- Table `project`.`project_tbl`
-- -----------------------------------------------------
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

/* 테스트를 위해 데이터 1개 등록 */
insert into project_tbl(kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, status, likes)
values('donate','기부제목','기부요약','thumbnail.jpg','프로젝트 내용','content_image.jpg',
		'2023-09-11', '2023-09-15', 1000000, 100000, 'unauthorized', 0);
insert into project_tbl(kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, status, likes)
values('donate','기부제목2','기부요약2','thumbnail2.jpg','프로젝트 내용2','content_image2.jpg',
		'2023-09-11', '2023-09-15', 1000000, 200000, 'unauthorized', 0);
		
select * from project_tbl;

-- -----------------------------------------------------
-- Table `project`.`member_tbl`
-- -----------------------------------------------------
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

select * from member_tbl;

update member_tbl
set password='delete', name='delete',
email='delete', phone='delete',
delete_status='Y',
deletedate=current_timestamp
where member_id='testuser0009';



-- -----------------------------------------------------
-- Table `project`.`reward_tbl`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`reward_tbl` (
  `reward_id` INT NOT NULL AUTO_INCREMENT COMMENT '리워드 ID',
  `r_name` NVARCHAR(30) NOT NULL COMMENT '리워드 이름',
  `r_content` NVARCHAR(100) NOT NULL COMMENT '리워드 설명',
  `r_price` INT NOT NULL COMMENT '리워드 금액',
  PRIMARY KEY (`reward_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`donation_tbl`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`donation_tbl` (
  `donate_id` INT NOT NULL AUTO_INCREMENT COMMENT '후원기록 ID',
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `reward_id` INT NOT NULL COMMENT '리워드 ID',
  `r_price` INT NOT NULL,
  `add_donation` INT NULL COMMENT '추가 후원금액',
  `address_id` VARCHAR(100) NULL,
  `donatedate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '후원일자',
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
-- Table `project`.`admin_income_tbl`
-- -----------------------------------------------------
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

insert into admin_income_tbl(project_id, fee_income) values(1, 50000);
insert into admin_income_tbl(project_id, fee_income) values(2, 30000);

select * from admin_income_tbl;

select project_id, fee_income,
DATE_FORMAT(incomedate,'%Y.%m.%d') as incomedate
from admin_income_tbl
where incomedate between '2023-9-1' and '2023-9-30';

-- -----------------------------------------------------
-- Table `project`.`project_reward_tbl`
-- -----------------------------------------------------
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


-- -----------------------------------------------------
-- Table `project`.`notice_tbl`
-- -----------------------------------------------------
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


drop table qna_tbl;
-- -----------------------------------------------------
-- Table `project`.`qna_tbl`
-- -----------------------------------------------------
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

select * from qna_tbl;

select count(*) from qna_tbl;

delete from qna_tbl;

select qna_id, member_id, q_title, q_content, q_image, isPrivate
from qna_tbl;

select qna_id, member_id, q_title, q_content, q_image, isPrivate,
DATE_FORMAT(q_time,'%Y.%m.%d %H:%i') as q_time,
a_content,
DATE_FORMAT(a_time,'%Y.%m.%d %H:%i') as a_time
from qna_tbl;

select count(*) from qna_tbl where q_title regexp '제목';

-- -----------------------------------------------------
-- Table `project`.`project_planner_tbl`
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
-- Table `project`.`bookmark_tbl`
-- -----------------------------------------------------
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`project_review_tbl`
-- -----------------------------------------------------
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


-- -----------------------------------------------------
-- Table `project`.`address_tbl`
-- -----------------------------------------------------
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

select * from address_tbl;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;