create database Programs;

use Programs;

create Table IF NOT EXISTS Student (
regNumber INT PRIMARY KEY,
Name Varchar(100),
email Varchar(150),
phone varchar(20)
);

create Table IF NOT EXISTS Instructor (
EmpID INT PRIMARY KEY,
Name Varchar(100),
email Varchar(150),
Designation varchar(100),
phone Varchar(20)
);

create Table IF NOT EXISTS Course(
CouseID Int PRIMARY KEY,
Name Varchar (100),
contactHours int,
InstructorID int,
Foreign Key (InstructorID) references Instructor(EmpID) on delete CASCADE
);

alter table Course
RENAME column CouseId to CourseID;

Create table IF NOT EXISTS Take(
StudentID int,
CourseID int,
Grade Varchar(2),
Primary KEY (StudentID, CourseID),
Foreign KEY (StudentID) REFERENCES Student(RegNumber) on delete CASCADE,
Foreign KEY (CourseID) References course(CourseID) on delete CASCADE
);


Insert into Student (RegNumber, Name,email,phone) VALUES
(1,'Nidhi','nidhi@gmail.com','12345');
Insert into Student (RegNumber, Name,email,phone) VALUES
(2,'Gowthami','gowthami@gmail.com','43215678');
Insert into Student (RegNumber, Name,email,phone) VALUES
(3,'Imaad','imaad@gmail.com','432156788');
Insert into Student (RegNumber, Name,email,phone) VALUES
(4,'Nithin','nithin@gmail.com','6748393030');


Insert into Instructor (EmpID, Name,email,Designation,phone)VALUES
(2,'XYZ','xyz@gmail.com','Asst.Prof','1234567891');
Insert into Instructor (EmpID, Name,email,Designation,phone)VALUES
(1,'ABC','abc@gmail.com','Prof','987654321');
Insert into Instructor (EmpID, Name,email,Designation,phone)VALUES
(3,'LKJH','lkjh@gmail.com','Asst.Prof','3456789876');

Insert Into Course(CourseID,Name , ContactHours, InstructorID)VALUES
(3,'math',10,2);
Insert Into Course(CourseID,Name , ContactHours, InstructorID)VALUES
(4,'ADS',15,1);
Insert Into Course(CourseID,Name , ContactHours, InstructorID)VALUES
(2,'ABD',20,3);
Insert Into Course(CourseID,Name , ContactHours, InstructorID)VALUES
(1,'FML',20,2);

Insert into take (studentID, CourseID ,Grade)VALUES
(1,3,'D');
Insert into take (studentID, CourseID ,Grade)VALUES
(3,3,'B');
Insert into take (studentID, CourseID ,Grade)VALUES (2,2,'A');
Insert into take (studentID, CourseID ,Grade)VALUES (4,2,'A');

create Table NewCourse(
CourseID int PRIMARY KEY,
Name Varchar (100),
contactHours int,
InstructorID int,
Foreign Key (InstructorID) references Instructor(EmpID)
);

INSERT INTO NewCourse(CourseID,Name , ContactHours, InstructorID) SELECT * from course;