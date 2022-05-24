create table Course
(
	CourseN int PRIMARY KEY IDENTITY (1,1),
	CourseName varchar(50),
	Nunit int
)

create table Teacher
(
	CourseN int,
	Quarter varchar(15) PRIMARY KEY,
	TeacherName varchar(50),
)


create table LocationNTime
(
	CourseN int,
	Quarter varchar(15),
	DayTime varchar(50) PRIMARY KEY,
	RoomN int,
)

create table Student
(
	studentName varchar(50) PRIMARY KEY,
	CourseN int,
	Quarter varchar(15),
)