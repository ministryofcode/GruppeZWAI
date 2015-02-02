CREATE TABLE M_USER (ID NUMERIC PRIMARY KEY, muname VARCHAR(255) UNIQUE, mpwd VARCHAR(255));
CREATE TABLE NEWSLETTER (id NUMERIC PRIMARY KEY, text VARCHAR(1000));
CREATE TABLE M_ADMIN (ID NUMERIC PRIMARY KEY, mpwd VARCHAR(255) UNIQUE);

insert into M_USER (ID,muname,mpwd) values (1,'alan','pwd1');
insert into M_USER (ID,muname,mpwd) values (2,'ada','pwd2');
insert into M_USER (ID,muname,mpwd) values (3,'bob','pwd3');
insert into M_USER (ID,muname,mpwd) values (4,'eve','pwd4');
insert into M_USER (ID,muname,mpwd) values (5,'alice','pwd5');
