insert into Course (CourseName, Nunit) 
values('Systems Programming', 3)
insert into Course (CourseName, Nunit) 
values('Computer Organization and Assembly Programming', 3)
insert into Course (CourseName, Nunit) 
values('Formal Languages and Automata', 3)
insert into Course (CourseName, Nunit) 
values('Database Systems', 3)
insert into Course (CourseName, Nunit) 
values('Software Engineering', 5)

insert into Teacher (CourseN, Quarter, TeacherName)
values(1, 'Winter2011', 'Jacob Smith')
insert into LocationNTime (CourseN, Quarter, DayTime, RoomN)
values(1, 'Winter2011', 'M2:00PM', 34)
insert into Teacher (CourseN, Quarter, TeacherName)
values(2, 'Summer 2011', 'Jacob Smith')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(2, 'Summer 2011', 'M4:00PM', 12)
insert into Teacher (CourseN, Quarter, TeacherName)
values(3, 'Spring2005', 'Jacob Smith')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(3, 'Spring2005', 'Th4:00PM', 723)
insert into Teacher (CourseN, Quarter, TeacherName)
values(7, 'Fall 2016', 'Jacob Smith')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(7, 'Fall 2016', 'F5:00PM', 723)

insert into Teacher (CourseN, Quarter, TeacherName)
values(4, 'Spring 2005', 'Karen Reed')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(4, 'Spring 2005', 'W4:00PM', 723)
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(5, 'Spring 2005', 'T4:00PM', 723)
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(6, 'Spring 2005', 'F4:00PM', 713)
insert into Teacher (CourseN, Quarter, TeacherName)
values(8, 'Fall 2012', 'Karen Reed')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(8, 'Fall 2012', 'MWF1:00PM', 723)
insert into Teacher (CourseN, Quarter, TeacherName)
values(9, 'Fall 2005', 'Karen Reed')
insert into LocationNTime(CourseN, Quarter, DayTime, RoomN)
values(9, 'Fall 2005', 'M1:00PM', 723)


insert into Student (studentName, CourseN, Quarter)
values('Ron Smith', 1, 'Winter2011')
insert into Student (studentName, CourseN, Quarter)
values('David Weidman', 1, 'Winter2011')


SELECT *
FROM Course

SELECT *
FROM Teacher

SELECT *
FROM LocationNTime

SELECT *
FROM Student