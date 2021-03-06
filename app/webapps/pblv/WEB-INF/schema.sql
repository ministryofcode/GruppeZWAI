CREATE TABLE M_USER (ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, muname VARCHAR(255) UNIQUE, mpwd VARCHAR(255), picName VARCHAR(255), sessionID VARCHAR(255));
CREATE TABLE NEWSLETTER (id INTEGER PRIMARY KEY, text VARCHAR(1000));
CREATE TABLE M_ADMIN (ID INTEGER PRIMARY KEY, mpwd VARCHAR(255) UNIQUE);
CREATE TABLE M_POSTS (ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, U_ID INTEGER FOREIGN KEY REFERENCES M_USER(ID), message VARCHAR(255), private BOOLEAN);
CREATE TABLE M_FRIENDS(ID INTEGER FOREIGN KEY REFERENCES M_USER(ID), U_ID INTEGER FOREIGN KEY REFERENCES M_USER(ID));
CREATE TABLE M_LIKES(ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, U_ID INTEGER FOREIGN KEY REFERENCES M_USER(ID), P_ID INTEGER FOREIGN KEY REFERENCES M_POSTS(ID), likestatus BOOLEAN);
CREATE TABLE M_MESSAGES(ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, S_ID INTEGER, R_ID INTEGER, Message VARCHAR(255), Feedback BOOLEAN, READ BOOLEAN)



insert into M_USER (ID,muname,mpwd,picName) values (1,'alan','pwd1', 'pic1');
insert into M_USER (ID,muname,mpwd,picName) values (2,'ada','pwd2', 'pic2');
insert into M_USER (ID,muname,mpwd,picName) values (3,'bob','pwd3', 'pic3');
insert into M_USER (ID,muname,mpwd,picName) values (4,'eve','pwd4', 'pic4');
insert into M_USER (ID,muname,mpwd,picName) values (5,'alice','pwd5', 'pic1');

insert into M_Friends(ID, U_ID) values (1,2);
