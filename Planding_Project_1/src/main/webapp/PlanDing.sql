show tables;
show events;
select now();


/* 테이블 일괄삭제 (순서대로)
drop table former_member_tbl;
drop table project_review_tbl;
drop table bookmark_tbl;

drop table project_reward_tbl;
drop table donation_tbl;
drop table reward_tbl;

drop table project_planner_tbl;
drop table admin_income_tbl;
drop table project_tbl;

drop table address_tbl;
drop table qna_tbl;
drop table notice_tbl;
drop table member_tbl;
*/

/*
 2023.9.17
 모든 테이블 삭제 후
 프로젝트 등록 테스트를 위해
 member_tbl, project_tbl, reward_tbl, project_planner_tbl, project_reward_tbl 테이블 생성
 
 프로젝트 등록관련 초기화
 drop table project_reward_tbl;
 drop table project_planner_tbl;
 drop table reward_tbl;
 drop table project_tbl;
   
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
drop table project_tbl;
CREATE TABLE `project_tbl` (
  `project_id` INT NOT NULL AUTO_INCREMENT COMMENT '프로젝트 ID',
  `kind` VARCHAR(10) NOT NULL COMMENT 'Donate or Funding',
  `title` NVARCHAR(50) NOT NULL COMMENT '프로젝트 제목',
  `summary` NVARCHAR(1000) NOT NULL COMMENT '요약글',
  `thumbnail` VARCHAR(300) NOT NULL,
  `content` NVARCHAR(10000) NOT NULL COMMENT '내용',
  `image` VARCHAR(3000) NOT NULL COMMENT '프로젝트 이미지',
  `startdate` DATETIME NOT NULL COMMENT '시작일',
  `enddate` DATETIME NOT NULL COMMENT '종료일',
  `goal_amount` INT NOT NULL COMMENT '목표 모금액',
  `curr_amount` INT NOT NULL COMMENT '현재 모금액',
  `p_status` VARCHAR(30) NOT NULL COMMENT '상태',
  `regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`project_id`))
ENGINE = InnoDB;

/*
alter table project_tbl
modify content nvarchar(10000);

alter table project_tbl
modify image varchar(3000);

alter table project_tbl
modify thumbnail varchar(300);

*/
show columns from project_tbl;
show columns from member_tbl;
show columns from address_tbl;
show columns from project_planner_tbl;
show columns from reward_tbl;
show columns from project_reward_tbl;
show columns from donation_tbl;
show columns from admin_income_tbl;
show columns from notice_tbl;
show columns from qna_tbl;



/* 테스트를 위해 데이터 1개 등록 */
insert into project_tbl(kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, p_status, likes, regdate)
values('donate','기부제목','기부요약','thumbnail.jpg','프로젝트 내용','content_image.jpg',
		'2023-09-11', '2023-09-15', 5000, 0, 'ongoing', 0, '2023-09-11');
insert into project_tbl(kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, p_status, likes)
values('donate','기부제목2','기부요약2','thumbnail2.jpg','프로젝트 내용2','content_image2.jpg',
		'2023-09-11', '2023-09-15', 1000000, 200000, 'unauthorized', 0);

update project_tbl
set regdate='2023-09-17', startdate='2023-09-18', enddate='2023-09-23', p_status = 'ongoing'
where project_id = 9;

update project_tbl
set title = '플라스틱 프리: 제로웨이스트 물병 혁명'
where project_id = 9;
		
select * from project_tbl;



select count(*) from project_tbl where kind='donate' and p_status='unauthorized';
select count(*) from project_tbl where kind='donate' and p_status='unauthorized';

select project_id, kind, title, summary
thumbnail, content, image,
DATE_FORMAT(startdate,'%Y.%m.%d %H:%i:%S') as startdate,
DATE_FORMAT(enddate,'%Y.%m.%d %H:%i:%S') as enddate,
goal_amount, curr_amount,
p_status, likes,
DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl where kind = ?"
				  + " order by regdate desc

-- -----------------------------------------------------
-- Table `project`.`member_tbl`
-- -----------------------------------------------------
CREATE TABLE `member_tbl` (
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `password` VARCHAR(256) NOT NULL COMMENT '비밀번호',
  `name` NVARCHAR(20) NOT NULL COMMENT '이름',
  `email` VARCHAR(45) NOT NULL COMMENT '이메일',
  `phone` VARCHAR(11) NOT NULL,
  `money` INT NOT NULL COMMENT '가상계좌 (계좌잔액)',
  `admin_status` VARCHAR(1) NOT NULL COMMENT '관리자 여부',
  `joindate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
  `delete_status` VARCHAR(1) NULL DEFAULT 'N' COMMENT '탈퇴회원 여부',
  `deletedate` TIMESTAMP NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;

select * from member_tbl;

delete from member_tbl where member_id = 'testAdmin11';

update member_tbl
set password='delete', name='delete',
email='delete', phone='delete',
delete_status='Y',
deletedate=current_timestamp
where member_id='testuser0009';



-- -----------------------------------------------------
-- Table `project`.`reward_tbl`
-- -----------------------------------------------------
CREATE TABLE `reward_tbl` (
  `reward_id` VARCHAR(100) NOT NULL COMMENT '리워드 ID',
  `r_name` NVARCHAR(30) NOT NULL COMMENT '리워드 이름',
  `r_content` NVARCHAR(100) NOT NULL COMMENT '리워드 설명',
  `r_price` INT NOT NULL COMMENT '리워드 금액',
  PRIMARY KEY (`reward_id`))
ENGINE = InnoDB;

insert into reward_tbl values('default','donate','최소 후원금액',1000);

select * from reward_tbl;
delete from reward_tbl where reward_id = 'pj9rwd3';

-- -----------------------------------------------------
-- Table `project`.`donation_tbl`
-- -----------------------------------------------------
CREATE TABLE `donation_tbl` (
  `donation_id` INT NOT NULL AUTO_INCREMENT COMMENT '후원기록 ID',
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '회원 ID',
  `reward_id` VARCHAR(100) NOT NULL COMMENT '리워드 ID',
  `r_price` INT NOT NULL,
  `add_donation` INT NULL COMMENT '추가 후원금액',
  `address_id` VARCHAR(100) NULL,
  `donatedate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '후원일자',
  PRIMARY KEY (`donation_id`),
  INDEX `fk_donation_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_donation_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from donation_tbl;

-- -----------------------------------------------------
-- Table `project`.`admin_income_tbl`
-- -----------------------------------------------------
CREATE TABLE `admin_income_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `fee_income` INT NOT NULL COMMENT '수수료 수익',
  `incomedate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수익일자',
  INDEX `fk_admin_income_tbl_project_tbl1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_income_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

insert into admin_income_tbl(project_id, fee_income) values(1, 50000);
insert into admin_income_tbl(project_id, fee_income) values(2, 30000);

select * from admin_income_tbl;


delete from admin_income_tbl where project_id = 2;

select project_id, fee_income,
DATE_FORMAT(incomedate,'%Y.%m.%d') as incomedate
from admin_income_tbl
where incomedate between '2023-9-1' and '2023-9-30';

-- -----------------------------------------------------
-- Table `project`.`project_reward_tbl`
-- -----------------------------------------------------
CREATE TABLE `project_reward_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `reward_id` VARCHAR(100) NOT NULL COMMENT '리워드 ID',
  PRIMARY KEY (`project_id`, `reward_id`),
  INDEX `fk_project_reward_tbl_reward_tbl1_idx` (`reward_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_reward_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project_tbl` (`project_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_reward_tbl_reward_tbl1`
    FOREIGN KEY (`reward_id`)
    REFERENCES `reward_tbl` (`reward_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from reward_tbl;
select * from project_reward_tbl;
insert into project_reward_tbl values(1, 'default');


insert into project_reward_tbl values(2, 'default');

delete from project_reward_tbl;


-- -----------------------------------------------------
-- Table `project`.`notice_tbl`
-- -----------------------------------------------------
CREATE TABLE `notice_tbl` (
  `notice_id` INT NOT NULL AUTO_INCREMENT COMMENT '공지사항ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '작성자 ID',
  `n_title` NVARCHAR(30) NOT NULL COMMENT '공지사항 제목',
  `n_content` NVARCHAR(5000) NOT NULL COMMENT '공지사항 내용',
  `n_image` VARCHAR(300) NULL COMMENT '공지사항 이미지',
  `importance` VARCHAR(1) NULL COMMENT '중요글 여부 YN',
  `viewcount` INT NOT NULL COMMENT '조회수',
  `writetime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성시간',
  PRIMARY KEY (`notice_id`),
  INDEX `fk_notice_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_notice_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

alter table notice_tbl
modify n_content nvarchar(5000);
alter table notice_tbl
modify n_image varchar(300);

drop table qna_tbl;
-- -----------------------------------------------------
-- Table `project`.`qna_tbl`
-- -----------------------------------------------------
CREATE TABLE `qna_tbl` (
  `qna_id` INT NOT NULL AUTO_INCREMENT COMMENT '문의사항 ID',
  `q_writer` VARCHAR(20) NOT NULL COMMENT '작성자 ID',
  `q_title` VARCHAR(256) NOT NULL COMMENT '질문 제목',
  `q_content` NVARCHAR(5000) NOT NULL COMMENT '질문 내용',
  `q_image` VARCHAR(300) NULL COMMENT '문의사항 이미지',
  `q_private` VARCHAR(1) NOT NULL COMMENT '비밀글 여부 YN',
  `q_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '질문시간',
  `a_writer` VARCHAR(20) NULL,
  `a_content` NVARCHAR(5000) NULL DEFAULT 'unanswered' COMMENT '답변 내용',
  `a_time` TIMESTAMP NULL COMMENT '답변 시간',
  PRIMARY KEY (`qna_id`),
  INDEX `fk_qna_tbl_member_tbl1_idx` (`q_writer` ASC) VISIBLE,
  INDEX `fk_qna_tbl_member_tbl2_idx` (`a_writer` ASC) VISIBLE,
  CONSTRAINT `fk_qna_tbl_member_tbl1`
    FOREIGN KEY (`q_writer`)
    REFERENCES `member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qna_tbl_member_tbl2`
    FOREIGN KEY (`a_writer`)
    REFERENCES `member_tbl` (`member_id`)
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

update qna_tbl
set q_private='N'
where qna_id = 2;

/*
alter table qna_tbl
modify q_content nvarchar(5000);
alter table qna_tbl
modify a_content nvarchar(5000);
alter table qna_tbl
modify q_image varchar(300);
*/


-- -----------------------------------------------------
-- Table `project`.`project_planner_tbl`
-- -----------------------------------------------------
CREATE TABLE `project_planner_tbl` (
  `project_id` INT NOT NULL COMMENT '프로젝트 ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '기획자 ID',
  `planner_name` NVARCHAR(20) NOT NULL COMMENT '기획자 이름 (개인 또는 기업, 단체)',
  `introduce` NVARCHAR(1000) NOT NULL COMMENT '기획자 간단 소개글',
  `bank` NVARCHAR(20) NOT NULL COMMENT '입금계좌 은행',
  `account_num` VARCHAR(45) NOT NULL COMMENT '입금계좌 계좌번호',
  PRIMARY KEY (`project_id`, `member_id`),
  INDEX `fk_project_planner_tbl_member_tbl1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_planner_tbl_project_tbl1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project_tbl` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_planner_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

/*
alter table project_planner_tbl
modify introduce nvarchar(1000);
*/
delete from project_planner_tbl;

select * from project_planner_tbl;

insert into project_planner_tbl values(2, 'testuser0002','기획자2','후원테스트용기획자','woori','20230920');




-- -----------------------------------------------------
-- Table `project`.`address_tbl`
-- -----------------------------------------------------
CREATE TABLE `address_tbl` (
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
  CONSTRAINT `fk_address_tbl_member_tbl1`
    FOREIGN KEY (`member_id`)
    REFERENCES `member_tbl` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from address_tbl;
delete from address_tbl where member_id = 'testAdmin11';


drop view project_planner_view;
-- -----------------------------------------------------
-- View `project`.`project_planner_view` 프로젝트-플래너 조인한 뷰 생성
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_planner_view`;
CREATE OR REPLACE VIEW `project_planner_view` AS
select project_id, kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, p_status, regdate, member_id, planner_name, introduce, bank, account_num
from project_tbl join project_planner_tbl
using(project_id);

select startdate from project_planner_view;



drop view project_donation_reward_view;
-- -----------------------------------------------------
-- View `project`.`project_donation_reward_view`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_donation_reward_view`;
CREATE  OR REPLACE VIEW `project_donation_reward_view` AS
select project_id, kind, title, summary, thumbnail, content, image, startdate, enddate, goal_amount, curr_amount, p_status, regdate,
donation_id, member_id, reward_id, r_price, add_donation, address_id, donatedate,
r_name, r_content
from project_tbl join (select donation_id, project_id, member_id, reward_id, r_price, add_donation, address_id, donatedate, r_name, r_content
						from donation_tbl natural join reward_tbl) donate_reward
using(project_id);

select * from project_donation_reward_view;


drop view project_adminIncome_view;
-- -----------------------------------------------------
-- View `project`.`project_adminIncome_view`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_adminIncome_view`;
CREATE  OR REPLACE VIEW `project_adminIncome_view` AS
select project_id, kind, title, summary, thumbnail, content, image, startdate
, enddate, goal_amount, curr_amount, p_status, regdate
, fee_income, incomedate
from project_tbl left outer join admin_income_tbl
using(project_id);


show events;
drop event updateStatusOngoing;
drop event updateStatusDone;

/* 시작일이 오늘이랑 같고, 상태가 ready(공개예정)인 프로젝트 */
create EVENT updateStatusOngoing
on schedule every 1 day
comment '상태를 진행중으로 업데이트'

DO

update project_tbl
set p_status = 'ongoing'
where startdate = CURDATE() and p_status='ready';



/* 종료일이 오늘보다 과거이며, 상태가 ongoing(진행중)이고, 현재모금액<목표모금액인 펀딩 프로젝트 */
create EVENT updateStatusDone
on schedule every 1 day
comment '상태를 종료로 업데이트'

DO

update project_tbl
set p_status = 'done'
where enddate < CURDATE() and p_status='ongoing' and curr_amount < goal_amount and kind='fund';

/* 종료일이 오늘보다 과거이며, 상태가 ongoing(진행중)인 기부 프로젝트 */
/* 종료일이 오늘보다 과거이며, 상태가 ongoing(진행중)이고, 현재모금액>=목표모금액인 펀딩 프로젝트 */
create EVENT updateStatusSuccess
on schedule every 1 day
comment '상태를 성공으로 업데이트'

DO

update project_tbl
set p_status = 'success'
where (enddate < CURDATE() and p_status='ongoing' and curr_amount >= goal_amount and kind='fund') or (enddate<CURDATE() and p_status='ongoing' and kind='donate');


/* 주문한지 6개월이 지난 주소(not 기본주소)를 삭제 -> 이벤트 스케줄러 생성 문제로 6개월 후 자동삭제 X*/
CREATE EVENT DeleteExpiredAddresses
ON SCHEDULE EVERY 1 DAY
DO
BEGIN
  DECLARE cutoff_date DATE;
  
  -- 오늘로부터 6개월 전의 날짜 계산
  SET cutoff_date = DATE_SUB(NOW(), INTERVAL 6 MONTH);

  -- address_tbl에서 오늘로부터 6개월 이상 지난 주문을 참조하고, basic_status가 'N'인 주소 삭제
  DELETE FROM address_tbl
  WHERE address_id IN (
					    SELECT DISTINCT a.address_id
					    FROM address_tbl a INNER JOIN donation_tbl d
					    ON a.address_id = d.address_id
					    WHERE d.donatedate <= cutoff_date
					    AND a.basic_status = 'N'
					  );
END;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



/* [토스페이먼츠 사용 시] 결제 정보 테이블 (결제승인 + 결제취소) (플랜딩 포인트 충전내역) */
create table pay_tbl(
orderId varchar(64) primary key, /*1. 결제번호(주문 구분. 6~64자 사이 문자열)*/


mId nvarchar(14) not null, /* 2. 상점 아이디, 토스페이먼츠에서 발급 (최대길이 14)*/
paymentKey varchar(200) not null, /* 3. 결제의 키값(결제취소 시 필수값) : 결제 식별하는 역할. 중복되지 않는 고유한 값 (최대길이 200)*/


order_num int references order_table,/* 4. 주문번호 */

member_id varchar(45) not null,/* 5. 사용자 Id */ 
email varchar(45) not null, /* 6. 사용자 이메일 */

orderName varchar(100), /* 7. 주문명 (최대 100) (예: 생수 외 1건) (결제취소 시)*/

pay_method varchar(100) not null, /* 8. [결제수단] 카드(토스로 실행위해 이것을 선택)(파라미터값은 method이름으로 넘어옴), 가상계좌, 간편결제, 은행 등 */
easyPay varchar(100) not null, /* 9. 토스간편결제 이용 시, 결제 후 [결제수단] easyPay : ("provider": "토스페이","amount":100, "discount":500)*/
pay_bank varchar(10), /*10. 결제은행 */

pay_status varchar(45) not null, /*11. 결제상황("DONE"성공,"CANCELED"실패) */
pay_date DATETIME not null, /*12. 결제일 (결제 승인한 날짜시간) */
totalAmount int not null, /* 13. 총 결제금액 */

cancelReason nvarchar(100), /*14. [결제취소] 시 취소사유(필수) */
cancel_date DATETIME, /*15. 결제 취소일 (취소된 날짜시간) */
cancelAmount INT /* 16. 총 취소 금액 */
);

show events;