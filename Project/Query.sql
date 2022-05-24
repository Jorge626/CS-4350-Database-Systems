SELECT * FROM actualtripstopinfo;
SELECT * FROM bus;
SELECT * FROM driver;
SELECT * FROM stop;
SELECT * FROM trip;
SELECT * FROM tripoffering;
SELECT * FROM tripstopinfo;

SELECT tof.ScheduledStartTime, tof.ScheduledArrivalTime, tof.Drivername, tof.Busid
FROM tripoffering tof, trip t
WHERE tof.TripNumber = t.TripNumber
AND tof.Date = "5/10/2021"
AND t.StartLocationName = "Stop A"
AND t.DestinationName = "CPP Front";

SELECT t.TripNumber, t.StopNumber, t.SequenceNumber, t.DrivingTime
FROM tripstopinfo t
WHERE t.TripNumber = 1;

SELECT t.Date, t.ScheduledStartTime, t.ScheduledArrivalTime
FROM tripoffering t
WHERE DATEDIFF(t.Date, '2021-05-14');

SELECT t.Date, t.ScheduledStartTime, t.ScheduledArrivalTime
FROM tripoffering t
WHERE  YEARWEEK(t.date, 1) = YEARWEEK('', 1)
AND t.DriverName = 'Bill';

SELECT t.Date, t.ScheduledStartTime, t.ScheduledArrivalTime
FROM tripoffering t
WHERE t.date 
BETWEEN date('2021-05-14') AND DATE_ADD('2021-05-14', INTERVAL 7 DAY)
AND t.DriverName = 'Bill'