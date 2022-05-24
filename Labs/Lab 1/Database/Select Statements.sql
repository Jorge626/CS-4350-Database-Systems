-- Number 1
SELECT T.TeacherName
FROM Teacher T INNER JOIN LocationNTime L
ON T.Quarter = 'Winter2011'
AND L.RoomN = 34

-- Number 2
SELECT C.CourseN, C.CourseName, T.TeacherName
FROM Course C, Teacher T, LocationNTime L
WHERE T.Quarter = L.Quarter
AND C.CourseN = L.CourseN
AND L.DayTime LIKE 'M%PM'

-- Number 3
SELECT T.TeacherName
FROM Teacher T INNER JOIN LocationNTime L
ON T.CourseN = L.CourseN 
WHERE L.RoomN = 723

-- Number 4
SELECT T.CourseN, T.Quarter, L.RoomN, L.DayTime
FROM Teacher T INNER JOIN LocationNTime L
ON T.CourseN = L.CourseN
WHERE T.Quarter = 'Spring 2005'
AND T.TeacherName = 'Karen Reed'

-- Number 5
SELECT T.CourseN, T.TeacherName
FROM Teacher T INNER JOIN Student S
ON (S.studentName = 'Ron Smith'
OR S.studentName = 'David Weidman')
AND T.CourseN = S.CourseN

-- Number 6
SELECT C.CourseN, T.Quarter
FROM Course C LEFT JOIN Teacher T
ON T.CourseN = C.CourseN  LEFT JOIN LocationNTime L
ON L.CourseN = T.CourseN
WHERE T.TeacherName = 'Karen Reed'
OR L.RoomN = 713

-- Number 7
SELECT T.TeacherName
FROM Teacher T, LocationNTime L, Course C
GROUP BY TeacherName
HAVING COUNT(C.CourseName) > 2

-- Number 8
SELECT DISTINCT TeacherName
FROM Teacher T INNER JOIN Course C
ON T.CourseN = C.CourseN
GROUP BY TeacherName
HAVING COUNT(T.CourseN) >= 2

-- Number 9
SELECT C.CourseN, C.CourseName, L.Quarter
FROM Course C, LocationNTime L
WHERE (L.DayTime LIKE '_______PM'
OR L.DayTime LIKE '_______AM')
AND C.CourseN = L.CourseN

-- Number 10
SELECT C.CourseN, C.CourseName
FROM Course C
WHERE C.Nunit > 4

-- Number 11
SELECT C.CourseN, S.studentName
FROM Course C, Student S
GROUP BY C.CourseN, S.studentName
HAVING COUNT(C.CourseN) >= 2

-- Number 12
SELECT C.*, T.*
FROM Course C left join Teacher T ON C.CourseN = T.CourseN
ORDER BY C.CourseN ASC

SELECT C.*, T.*
FROM Course C left join Teacher T ON C.CourseN = T.CourseN
ORDER BY C.CourseName DESC

--Number 13
SELECT CourseN, Quarter
FROM Teacher T
GROUP BY CourseN, Quarter
HAVING COUNT(*) > 1
ORDER BY CourseN DESC