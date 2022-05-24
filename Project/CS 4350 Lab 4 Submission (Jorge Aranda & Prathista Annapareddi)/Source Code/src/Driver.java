import java.sql.*;
import java.util.*;

public class Driver {
    public static void main(String[] args){
    	Scanner input = new Scanner(System.in);
    	String isDone = "";
    	int choice;
    	while (!isDone.equalsIgnoreCase("y")){
    		choice = menu();
    		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    		switch(choice){
    			case 1:
    				displaySchedule();
    				break;
				case 2:
					editSchedule();
					break;
				case 3:
					displayStops();
					break;
				case 4:
					displayDriverSchedule();
					break;
				case 5:
					addDriver();
					break;
				case 6:
					addBus();
					break;
				case 7:
					deleteBus();
					break;
				case 8:
					recordTripData();
					break;
				case 9:
					printTables();
					break;
				default:
					System.out.println("Error: Invalid input...");
					break;
				}
				System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nExit the program? (Y/N): ");
				isDone = input.nextLine();
				System.out.println();
			}

			System.out.println("Thank you for using our program!");
    }

    // Method for displaying menu
    public static int menu(){
		System.out.print("              Main Menu\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"1) Display Schedule\n" +
				"2) Edit Schedule\n" +
				"3) Display stops of a trip\n" +
				"4) Display weekly schedule of a driver\n" +
				"5) Add a driver\n" +
				"6) Add a bus\n" +
				"7) Delete a bus\n" +
				"8) Record data of a trip\n" +
				"9) Print tables\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"Please insert an option: ");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		return choice;
    }
    
    // Method for displaying edit menu
    public static int editMenu(){
		System.out.print("          Edit Schedule Menu\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"1) Delete a trip offering\n" +
				"2) Add a set of trip offerings\n" +
				"3) Change the driver for a trip\n" +
				"4) Change the bus for a trip\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"Please insert an option: ");
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		return choice;
    }

    /*	Method for displaying schedule (#1)
	Display the schedule of all trips for a given StartLocationName and Destination Name, and Date.
	In addition to these attributes, the schedule includes: Scheduled StartTime,  ScheduledArrivalTime , DriverID, and BusID.
    */
    public static void displaySchedule(){
		System.out.println("\nDisplay Schedule\n"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		String start, dest, date;
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter a Start Location: ");
		start = input.nextLine();
		System.out.print("Please enter a Destination: ");
		dest = input.nextLine();
		System.out.print("Please enter a Date in YYYY-MM-DD format: ");
		date = input.nextLine();

		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
			ResultSet myResult = myStatement.executeQuery(
					"SELECT tof.ScheduledStartTime, tof.ScheduledArrivalTime, " +
					"tof.Drivername, tof.Busid\n" +
					"FROM tripoffering tof, trip t\n" +
					"WHERE tof.TripNumber = t.TripNumber\n" +
					"AND tof.Date = \"" + date + "\"\n" +
					"AND t.StartLocationName = \"" + start + "\"\n" +
					"AND t.DestinationName = \"" + dest + "\";");

			while (myResult.next()) {
				System.out.printf("Schedule Start Time: %s\nSchedule Arrival Time: %s\nDriver Name: %s\nBusID: %s\n",
						myResult.getString("ScheduledStartTime"), myResult.getString("ScheduledArrivalTime"), myResult.getString("DriverName"),
						myResult.getString("BusID"));
			}
			myConn.close();
		}
		catch (Exception exc) {
			System.out.println("Error: Could not connect...");
			exc.printStackTrace();
		}
    }

    /*	Method for editing schedule (#2)
	Edit the schedule i.e., edit the table of Trip Offering as follows:
		-Delete a trip offering specified by Trip#, Date, and ScheduledStartTime;
		-Add a set of trip offerings assuming the values of all attributes are given (the software asks if you have more trips to enter);
		- Change the driver for a given Trip offering (i.e., given TripNumber, Date, ScheduledStartTime);
		- Change the bus for a given Trip offering.
    */
    public static void editSchedule(){
		System.out.println("\nEditing Schedule\n"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Scanner input = new Scanner(System.in);
		int choice;
		String isDone = "", isDone2 = "", date, start, arrival, driver, bus, trip;
		while (!isDone.equalsIgnoreCase("n")) {
			choice = editMenu();
			switch (choice) {
				case 1:
					System.out.print("Please enter Trip #: ");
					trip = input.nextLine();
					System.out.print("Please enter Date in YYYY-MM-DD format: ");
					date = input.nextLine();
					System.out.print("Please enter Scheduled Start Time: ");
					start = input.nextLine();

					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						PreparedStatement myStatement = myConn.prepareStatement(
								"DELETE FROM tripoffering " +
								"WHERE TripNumber = ? " +
								"AND Date = ? " +
								"AND ScheduledStartTime = ? ");
						myStatement.setString(1, trip);
						myStatement.setString(2, date);
						myStatement.setString(3, start);
						int pass = myStatement.executeUpdate();
						if (pass > 0)
							System.out.println("Trip successfully deleted");
						else
							System.out.println("Trip record does not exist");
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}

					break;
				case 2:
					while (!isDone2.equalsIgnoreCase("n")) {
						System.out.print("Please enter Trip #: ");
						trip = input.nextLine();
						System.out.print("Please enter Date in YYYY-MM-DD format: ");
						date = input.nextLine();
						System.out.print("Please enter Scheduled Start Time: ");
						start = input.nextLine();
						System.out.print("Please enter Scheduled Arrival Time: ");
						arrival = input.nextLine();
						System.out.print("Please enter Driver's Name: ");
						driver = input.nextLine();
						System.out.print("Please enter BusID: ");
						bus = input.nextLine();

						try {
							Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
							PreparedStatement myStatement = myConn.prepareStatement("INSERT INTO tripoffering VALUES (?, ?, ?,?, ?, ?)");
							int tripInt = Integer.parseInt(trip);
							myStatement.setInt(1, tripInt);
							myStatement.setString(2, date);
							myStatement.setString(3, start);
							myStatement.setString(4, arrival);
							myStatement.setString(5, driver);
							myStatement.setString(6, bus);
							myStatement.execute();
							System.out.println("Trip successfully added");
							myConn.close();
						}
						catch (Exception exc) {
							System.out.println("Error: Could not connect...");
							exc.printStackTrace();
						}

						System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nInsert more trips? (Y/N): ");
						isDone2 = input.nextLine();
						System.out.println();
					}
					break;
				case 3:
					System.out.print("Please enter Trip #: ");
					trip = input.nextLine();
					System.out.print("Please enter Date in YYYY-MM-DD format: ");
					date = input.nextLine();
					System.out.print("Please enter Scheduled Start Time: ");
					start = input.nextLine();
					System.out.print("Please enter the new driver's name for this trip: ");
					driver = input.nextLine();

					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						PreparedStatement myStatement = myConn.prepareStatement(
								"UPDATE tripoffering " +
										"SET DriverName = ? " +
										"WHERE TripNumber = ? " +
										"AND Date = ? " +
										"AND ScheduledStartTime = ?");
						myStatement.setString(1, driver);
						myStatement.setString(2, trip);
						myStatement.setString(3, date);
						myStatement.setString(4, start);
						int pass = myStatement.executeUpdate();
						if (pass > 0)
							System.out.println("Trip successfully updated");
						else
							System.out.println("Trip record does not exist");
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 4:
					System.out.print("Please enter Trip #: ");
					trip = input.nextLine();
					System.out.print("Please enter the new BusID for this trip: ");
					bus = input.nextLine();

					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						PreparedStatement myStatement = myConn.prepareStatement(
								"UPDATE tripoffering " +
										"SET BusID = ? " +
										"WHERE TripNumber = ?");
						myStatement.setString(1, bus);
						myStatement.setString(2, trip);
						int pass = myStatement.executeUpdate();
						if (pass > 0)
							System.out.println("Trip successfully updated");
						else
							System.out.println("Trip record does not exist");
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				default:
					break;
			}
			System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nContinue editing? (Y/N): ");
			isDone = input.nextLine();
			System.out.println();
		}
    }

    /*	Method for displaying stops (#3)
	Display the stops of a given trip (i.e., the attributes of the table TripStopInfo).
    */
    public static void displayStops(){
		System.out.print("1) Display all stops\n" +
				"2) Display stop of a given trip\n" +
				"3) Display the actual trip stop info\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"Please insert an option: ");
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		input.nextLine();
		switch (choice){
			case 1:
				try {
					Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
					Statement myStatement = myConn.createStatement();
					ResultSet myResult = myStatement.executeQuery("SELECT * FROM tripstopinfo");

					while (myResult.next()) {
						System.out.printf("--------------------------------------\nTrip Number: %s\nStop Number: %s\nSequence Number:  %s\nDriving Time: %s\n",
								myResult.getString("TripNumber"), myResult.getString("StopNumber"),
								myResult.getString("SequenceNumber"), myResult.getString("DrivingTime"));
					}
					myConn.close();
				}

				catch (Exception exc) {
					System.out.println("Error: Could not connect to database...");
					exc.printStackTrace();
				}
				break;
			case 2:
				System.out.print("Please enter trip #: ");
				String trip = input.nextLine();
				try {
					Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
					Statement myStatement = myConn.createStatement();
					ResultSet myResult = myStatement.executeQuery(
							"SELECT t.TripNumber, t.StopNumber, t" +
							".SequenceNumber, t.DrivingTime\n" +
							"FROM tripstopinfo t\n" +
							"WHERE t.TripNumber = " + trip + "");

					while (myResult.next()) {
						System.out.printf("Trip Number: %s\nStop Number: %s\nSequence Number:  %s\nDriving Time: %s\n",
								myResult.getString("TripNumber"), myResult.getString("StopNumber"),
								myResult.getString("SequenceNumber"), myResult.getString("DrivingTime"));
					}
					myConn.close();
				}

				catch (Exception exc) {
					System.out.println("Error: Could not connect to database...");
					exc.printStackTrace();
				}
				break;
			case 3:
				try {
					Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
					Statement myStatement = myConn.createStatement();
					ResultSet myResult = myStatement.executeQuery("SELECT * FROM ActualTripStopInfo");

					while (myResult.next()) {
						System.out.printf("--------------------------------------\nTrip Number: %s\nDate: %s\n" +
										"Start Time: %s\nStop Number: %s\nArrival Time: %s\nActual Start Time: %s\n" +
										"Actual Arrival Time: %s\nNumber of Passengers In: %s\n" +
										"Number of Passengers Out: %s\n",
								myResult.getString("TripNumber"), myResult.getString("date"),
								myResult.getString("ScheduledStartTime"), myResult.getString("StopNumber"),
								myResult.getString("ScheduledArrivalTime"),
								myResult.getString("ActualStartTime"),
								myResult.getString("ActualArrivalTime"),
								myResult.getString("NumberOfPassengerIn"),
								myResult.getString("NumberOfPassengerOut"));
					}
					myConn.close();
				}

				catch (Exception exc) {
					System.out.println("Error: Could not connect to database...");
					exc.printStackTrace();
				}
				break;
			default:
				System.out.println("Invalid option...");
				break;
		}
    }

    /*	Method for displaying driver schedule (#4)
	Display the weekly schedule of a given driver and date.
    */
    public static void displayDriverSchedule(){
		System.out.println("\nDisplaying Driver's Schedule\n"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Scanner input = new Scanner(System.in);
		String driver, date;
		System.out.print("Please enter the driver's name: ");
		driver = input.nextLine();
		System.out.print("Please enter Date in YYYY-MM-DD format: ");
		date = input.nextLine();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
			ResultSet myResult = myStatement.executeQuery(
					"SELECT t.Date, t.ScheduledStartTime, t.ScheduledArrivalTime " +
							"FROM tripoffering t " +
							"WHERE '" + date + "'" +
							"BETWEEN date('" + date + "') AND DATE_ADD('" + date + "', INTERVAL 7 DAY) " +
							"AND t.DriverName = '" + driver + "'");

			while (myResult.next()) {
				System.out.printf("--------------------------------------\nDate: %s\nScheduled Start Time: %s\nScheduledArrivalTime: %s\n",
						myResult.getString("Date"), myResult.getString("ScheduledStartTime"), myResult.getString("ScheduledArrivalTime"));
			}
			myConn.close();
		}

		catch (Exception exc) {
			System.out.println("Error: Could not connect to database...");
			exc.printStackTrace();
		}
    }

    /* 	Method for adding a driver (#5)
	Add a drive.
	Driver(DriverName, DriverTelephoneNumber)
	Get input for new Driver's name and number
    */
    public static void addDriver(){
        Scanner input = new Scanner(System.in);
        boolean check = false;
        String newDriverName = "", newDriverNumber = "";
        while(check == false) {
            System.out.println("\nAdding a New Driver\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Enter the new driver's name: ");
            newDriverName = input.nextLine();
            System.out.println("Enter the new driver's telephone number: ");
            newDriverNumber = input.nextLine();

            //Verify if inputs are correct:
            System.out.printf("\nNew Driver Details: \nDriver Name: %s\nDriver's Telephone Number: %s\n",
                    newDriverName, newDriverNumber);
            check = verifyData();
        }

        //ADD SQL HERE
        try{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
            //add bus using input
            myStatement.executeUpdate("INSERT INTO Driver VALUES ('" +
                    newDriverName + "', '" + newDriverNumber + "')");
            System.out.println("\nBus successfully added\n");
            myConn.close();
        }catch (Exception exc) {
            System.out.println("Error: Could not connect to database...");
            exc.printStackTrace();
        }
    }

    /*	Method for adding a driver (#6)
        Add a bus.
        Bus (BusID, Model, Year)
        Get input for new Bus's ID, Model and Year
    */
    public static void addBus(){
        Scanner input = new Scanner(System.in);
        boolean check = false;
        String newBusID = "", newBusModel = "", newBusYear = "";
        while(check == false) {
            System.out.println("\nAdding a New Bus\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Enter the new bus ID: ");
            newBusID = input.nextLine();
            System.out.println("Enter the new bus Model: ");
            newBusModel = input.nextLine();
            System.out.println("Enter the new bus Year: ");
            newBusYear = input.nextLine();

            //Verify if inputs are correct:
            System.out.printf("\nNew Bus Details: \nBus ID: %s\nBus Model: %s\nBus Year: %s\n",
                    newBusID, newBusModel, newBusYear);
            check = verifyData();
        }

        //ADD SQL HERE
        try{
            //add bus using input
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
            myStatement.executeUpdate("INSERT INTO Bus VALUES ('" + newBusID +
                    "', '" + newBusModel + "', '" + newBusYear + "')");
            System.out.println("\nBus successfully added\n");
            myConn.close();
        }catch (Exception exc) {
            System.out.println("Error: Could not connect to database...");
            exc.printStackTrace();
        }
    }


    /*	Method for deleting a bus (#7)
        Delete a bus.
        Since BusID is the primary key, use that to identify which bus to delete.
     */
    public static void deleteBus(){
        System.out.println("\nDeleting Bus\n"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        boolean check = false;
        String BusID = "";
        while(check == false) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Bus ID of bus you want to delete: ");
            BusID = input.nextLine();

            System.out.printf("\nThe Bus ID of the Bus that will be deleted: %s\n", BusID);
            check = verifyData();
        }

        //ADD SQL HERE
        try{
            //delete bus using Bus ID
            //if DELETE returns 0 --> no rows found matching that data so return an error
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
            int myResult = myStatement.executeUpdate("DELETE Bus FROM bus " +
                    "WHERE BusID = '" + BusID + "' ");
            if(myResult == 0){
                System.out.printf("The Bus with ID: %s does not exist. ", BusID);
            }else{
                System.out.println("\nBus successfully deleted\n");
            }
            myConn.close();
        }catch (Exception exc) {
            System.out.println("Error: Could not connect to database...");
            exc.printStackTrace();
        }
    }

    /*	Method for recording trip data (#8)
        Record (insert) the actual data of a given trip offering specified by its key.
        The actual data include the attributes of the table ActualTripStopInfo.
        ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber,
                            SecheduledArrivalTime, ActualStartTime, ActualArrivalTime,
                            NumberOfPassengerIn, NumberOf PassengerOut)
     */
    public static void recordTripData(){
        boolean check = false;
        String tripNumber = "", date= "", startTime= "", stopNumber= "", arrivalTime= "",
                actualStart= "", actualArrival= "", passengersIn= "", passengersOut= "";
        while(check == false) {
            System.out.println("\nRecording Trip Data\n"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Scanner input = new Scanner(System.in);
            System.out.print("Trip Number: ");
            tripNumber = input.nextLine();
            System.out.print("Date (Please use YYYY-MM-DD format): ");
            date = input.nextLine();
            System.out.print("Scheduled Start Time: ");
            startTime = input.nextLine();
            System.out.print("Stop Number: ");
            stopNumber = input.nextLine();
            System.out.print("Scheduled Arrival Time: ");
            arrivalTime = input.nextLine();
            System.out.print("Actual Start Time: ");
            actualStart = input.nextLine();
            System.out.print("Actual Arrival Time: ");
            actualArrival = input.nextLine();
            System.out.print("Number of Passengers in: ");
            passengersIn = input.nextLine();
            System.out.print("Number of Passengers out: ");
            passengersOut = input.nextLine();

            //Verify if inputs are correct:
            System.out.printf("\nTrip Data: \nTrip Number: %s\nDate: %s\nScheduled Start Time: %s\n" +
                    "Stop Number: %s\nScheduled Arrival Time: %s\nActual Start Time: %s\n" +
                    "Actual Arrival Time: %s\nPassengers in: %s\nPassengers out: %s\n",
                    tripNumber, date, startTime, stopNumber, arrivalTime, actualStart,
                    actualArrival, passengersIn, passengersOut);
            check = verifyData();
        }

        try{

            //add data into "ActualTripStopInfo"
			Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
			Statement myStatement = myConn.createStatement();
            myStatement.executeUpdate("INSERT INTO ActualTripStopInfo VALUES ('" +
                    tripNumber + "', '" + date + "', '" + startTime + "', '" + stopNumber + "', '" +
                    arrivalTime + "', '" + actualStart + "', '" + actualArrival + "', '" + passengersIn +
                    "', '" + passengersOut + "')");
            System.out.println("\nTrip data successfully recorded\n");
            myConn.close();
        }catch (Exception exc) {
            System.out.println("Error: Could not connect to database...");
            exc.printStackTrace();
        }
    }

    public static boolean verifyData(){
        Scanner input = new Scanner(System.in);
        int verify = 0;
        while (verify !=1 || verify != 2) {
            System.out.println("\nIs the data printed above correct? (Please enter number)\n1. Yes\n2. No");
            verify = input.nextInt();
            if (verify == 1) {
                return true;
            } else if (verify == 2) {
                System.out.println("Let's try that again!");
                return false;
            } else{
                System.out.println("Invalid input!");
            }
        }
        return false;
    }

    // Menu for displaying tables
	public static int tablesMenu(){
		System.out.print("            Print Tables\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"1) ActualTripStopInfo\n" +
				"2) Bus\n" +
				"3) Driver\n" +
				"4) Stop\n" +
				"5) Trip\n" +
				"6) TripOffering\n" +
				"7) TripStopInfo\n" +
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
				"Please select which table to print: ");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		return choice;
	}

	// Method for printing out tables of database
	public static void printTables(){
		Scanner input = new Scanner(System.in);
		int choice;
		String isDone = "";
		while (!isDone.equalsIgnoreCase("n")) {
			choice = tablesMenu();
			int i = 0;
			switch (choice) {
				case 1:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM actualtripstopinfo");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Trip Number: %s\nDate: %s\nScheduled Start Time: %s\nStop Number: %s\nScheduled Arrival Time: %s\nActual Start Time: %s\n"
											+ "Actual Arrival Time: %s\nNumber of Passengers in: %s\nNumber of Passengers out: %s\n",
									myResult.getString("TripNumber"), myResult.getString("Date"), myResult.getString("ScheduledStartTime"), myResult.getString("StopNumber"),
									myResult.getString("ScheduledArrivalTime"), myResult.getString("ActualStartTime"), myResult.getString("ActualArrivalTime"),
									myResult.getString("NumberOfPassengerIn"), myResult.getString("NumberOfPassengerOut"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 2:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM bus");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("BusID: %s\nModel: %s\nYear: %s\n",
									myResult.getString("BusID"), myResult.getString("Model"), myResult.getString("Year"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 3:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM driver");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Driver Name: %s\nDriver Telephone Number: %s\n",
									myResult.getString("DriverName"), myResult.getString("DriverTelephoneNumber"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 4:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM stop");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Stop Number: %s\nStop Address: %s\n",
									myResult.getString("StopNumber"), myResult.getString("StopAddress"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 5:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM trip");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Trip Number: %s\nStart Location Name: %s\nDestination Name: %s\n",
									myResult.getString("TripNumber"), myResult.getString("StartLocationName"), myResult.getString("DestinationName"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 6:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM tripoffering");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Trip Number: %s\nDate: %s\nScheduled Start Time: %s\nScheduled Arrival Time: %s\n"
											+ "Driver Name: %s\nBusID: %s\n",
									myResult.getString("TripNumber"), myResult.getString("Date"), myResult.getString("ScheduledStartTime"),
									myResult.getString("ScheduledArrivalTime"), myResult.getString("DriverName"), myResult.getString("BusID"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				case 7:
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_5c232c80cdde118", "b8e764a5ced118", "837b8d43");
						Statement myStatement = myConn.createStatement();
						ResultSet myResult = myStatement.executeQuery("SELECT * FROM tripstopinfo");
						while (myResult.next()) {
							i++;
							System.out.println("Row " + i);
							System.out.printf("Trip Number: %s\nStop Number: %s\nSequence Number: %s\nDriving Time: %s\n",
									myResult.getString("TripNumber"), myResult.getString("StopNumber"),
									myResult.getString("SequenceNumber"), myResult.getString("DrivingTime"));
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						myConn.close();
					}
					catch (Exception exc) {
						System.out.println("Error: Could not connect...");
						exc.printStackTrace();
					}
					break;
				default:
					System.out.println("Error: Invalid Option");
					break;
			}
			System.out.print("Print another table? (Y/N): ");
			isDone = input.nextLine();
		}
	}
}
