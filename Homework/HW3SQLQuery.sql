/* # 1 */
SELECT e.name 
FROM Employee e INNER JOIN Membership m
ON m.ID = e.ID INNER JOIN Committee c
ON c.title = m.title 
WHERE m.task = 'member' 
GROUP BY e.name 
HAVING count(*) > 2;

/* # 2 */
SELECT e.name 
FROM Employee e INNER JOIN Membership m
ON m.ID = e.ID INNER JOIN Committee c 
ON c.title = m.title 
GROUP BY e.name 
HAVING count(*) = (SELECT count(*) FROM Committee c);

/* # 3 */
SELECT e.ID, e.name, count(*) "Number of Commitee" 
FROM Employee e INNER JOIN Membership m
ON m.ID = e.ID INNER JOIN Committee c
ON c.title = m.title 
GROUP BY e.ID, e.name;

/* # 4  */
SELECT e.name 
FROM Employee e
WHERE e.ID IN (SELECT m.ID 
FROM Membership m INNER JOIN Committee c
ON c.title = m.title 
WHERE location = 'H345');

/* # 5  */
SELECT name 
FROM Employee 
WHERE phoneN IS NULL;

/* # 6  */
SELECT e.name, e.ID 
FROM Employee 
WHERE e.ID NOT IN (SELECT m.ID 
FROM Membership m INNER JOIN Committee c
ON m.title = c.title);

/* # 7  */
SELECT c.title
FROM Employee e, Committe c, Membership m
WHERE (e.name = 'Sandy Liu' OR e.name = 'Barry Smith') 
AND e.ID = m.ID 
AND m.title = c.title;

/* # 8  */
SELECT e.name , e.position
FROM Employee e
WHERE e.age >= ALL ( SELECT e.age FROM Employee e);

/* # 9  */
SELECT a.title, b.title 
FROM Commitee c
JOIN Membership m ON c.title = m.title
WHERE c.meetingDate != m.meetingDate;

/* # 10  */
SELECT name, position 
FROM Employee
WHERE phoneN != "NULL";