SELECT * FROM course;
SELECT * FROM enrollment;
SELECT * FROM section;
SELECT * FROM student;

# 1
SELECT s.Name, s.SSN, COUNT(*) NumberOfCourses
FROM Student s, Enrollment e
WHERE s.SSN = e.SSN
GROUP BY s.Name, s.SSN;

#2
SELECT s.Name, s.SSN, COUNT(DISTINCT e.CourseNumber) NumberOfCourses
FROM Student s LEFT OUTER JOIN Enrollment e
ON s.SSN = e.SSN
GROUP BY s.Name, s.SSN;

#3
SELECT s.Name, s.SSN, COUNT(*) CompletedCourses
FROM student s, enrollment e
WHERE s.SSN = e.SSN 
AND e.GRADE is NOT NULL
GROUP BY s.Name, s.SSN;

#4
SELECT s.Name, s.SSN, COUNT(*) NumberOfCourses
FROM student s, enrollment e
WHERE s.SSN = e.SSN 
AND e.Grade IN (SELECT Grade
FROM Enrollment
WHERE Grade = 'A' OR Grade = 'B' OR Grade = 'C')
GROUP BY S.Name, S.SSN;

#5
SELECT CourseTitle, CourseNumber
FROM Course
WHERE PrerequisiteCourseNumber is NULL;

#6
SELECT s.Name, S.SSN
FROM Student s, Enrollment e
WHERE s.SSN = e.SSN AND
s.Name NOT IN (SELECT s1.Name FROM Enrollment e1, Student s1
WHERE e1.SSN = s1.SSN 
AND e1.Grade NOT LIKE 'A');

#7
SELECT s.Name, E.SSN, E.CourseNumber
FROM Student s, Enrollment e
WHERE s.SSN = e.SSN
GROUP BY s.Name, E.SSN, e.CourseNumber HAVING COUNT(*) > 2;

#8
SELECT s.Name, S.SSN, e.Quarter, COUNT(*)
FROM Student S, Enrollment e
WHERE s.SSN = e.SSN
GROUP BY s.Name, s.SSN, e.Quarter;

#9
SELECT s.Name, s.SSN
FROM Student s, Enrollment e
WHERE s.SSN = e.SSN
AND e.CourseNumber HAVING COUNT(*) > 1;

#10
SELECT s.Name, s.SSN, COUNT(*) NumberOfCourses, COUNT(IF(Grade IS NOT NULL, 1, 0)) NumberOfCoursesCompleted
FROM Student s, Enrollment e
WHERE s.SSN = e.SSN
GROUP BY s.Name, s.SSN;

#11
SELECT PrerequisiteCourseNumber, COUNT(*) SamePrerequisite
FROM Course
GROUP BY PrerequisiteCourseNumber HAVING SamePrerequisite > 1;

#12
SELECT s.SSN, S.Name, E.CourseNumber
FROM Student s INNER JOIN Enrollment e 
ON s.SSN = e.SSN
WHERE Grade = 'A' AND Grade IS NOT NULL;

#13?
SELECT s.SSN, S.Name, e.CourseNumber
FROM Student s INNER JOIN Enrollment e
ON s.SSN = e.SSN 
WHERE Grade != 'A';

#14?
SELECT s.Name, s.Major, sec.DayTime
FROM Student s INNER JOIN Section sec
WHERE sec.DayTime > 'MW12:00PM';

#15?
SELECT s.Name, s.Major, sec.DayTime
FROM Student s INNER JOIN Section sec
WHERE sec.DayTime >= 'MW12:00PM';

#16?
SELECT s.Name, s.Major, sec.DayTime
FROM Student s INNER JOIN Section sec
WHERE DayTime < 'MW12:00PM';

#17?
SELECT CourseNumber, max(Quarter) Highest
FROM Enrollment;

#18?
SELECT e.CourseNumber, e.Quarter, c.CourseTitle
FROM Enrollment e INNER JOIN Course c
ON e.CourseNumber = c.CourseNumber
WHERE e.Quarter = (SELECT MAX(NumberUnits) FROM Course);

#19?
SELECT s.Name, s.Major
FROM Student s INNER JOIN Course c
WHERE c.NumberUnits = (SELECT MAX(NumberUnits) FROM Course);

#20?
SELECT CourseNumber, PrerequisiteCourseNumber, MAX(NumberUnits)
FROM Course;