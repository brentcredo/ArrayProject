package cmsc256;  // do not remove or comment out this statement

/**
 *  CMSC 256 Fall 2019
 *  Project 1
 *  Credo, Brent
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Project1 {
/*
 * The main method
 * Throws the exceptions
 * Displays the tallest height, the three data points for the row with the
 * lowest weight, and the average height of the entries in a given age
 * 
 */
    public static void main(String[] args) {
    	/*
    	 * creating the object proj that can be used to call methods
    	 * calling checkArgs method and storing it into input
    	 * creating allData array
    	 */
    	
    	Project1 proj = new Project1();
    	
    	String fileName = proj.checkArgs(args);
    	String[][] allData = null;
    	/*
    	 * receiving data from the file
    	 * throws FileNotFoundException or IOException if there is an error reading the file
    	 * 
    	 */
    	try {
    		File input = proj.getFile(fileName);
    		allData = proj.readFile(input, 500);
    		
    		int tallestHeight = proj.findTallest(allData);
    		System.out.println("The tallest height is " + tallestHeight);
    		
    		String[] mini = proj.findLightestRecord(allData);
    		System.out.print("The row with the lowest weight is ");
    		System.out.println(Arrays.deepToString(mini));

    	}
    	catch(FileNotFoundException ff) {
    		System.out.println("Error reading the file. Program terminated");
    	}
    	catch(IOException ex) {
    		System.out.println("Error reading the file");
    	}
    	
    	for(String[] str : allData)Arrays.toString(str);
    }

    /**
     *   Gets the file name from command line argument;
     *   If parameter is empty, call promptForFileName() method
     * @param argv  String array from command line argument
     * @return      the name of the data file
     */
    public String checkArgs(String[] argv) {
    	/*
    	 * calls promptForFileName method
    	 * reads the file name from the command line argument
    	 */
    	String fileName = promptForFileName();

    	if (argv.length > 0) {
			fileName = argv[0];
		}
    	/*
    	 * if there is nothing call promptForFileName
    	 */
    	else {
    		fileName = promptForFileName();
		}
		return fileName;
    }
    /**
     * Prompt user to enter a file name
     * @return user entered file name
     */
    public String promptForFileName() {
		
    	/*
    	 * takes in the words of the file name and sends to the checkArgs method
    	 */
    	
    	Scanner scnr = new Scanner (System.in);
		
		System.out.println("Please enter the file name.");
		String inputFileName = scnr.next();
		
    	return inputFileName;

    }

    /**
     * Retrieve file with the given file name.
     * Prompts user if file cannot be opened or does not exist.
     * @param fileName  The name of the data file
     * @return          File object
     * @throws java.io.FileNotFoundException
     */
    public File getFile(String fileName) throws FileNotFoundException {
		/*
		 * ensures that the file exists and returns the new file
		 */
    	File file = new File(fileName);
		while(!file.exists()) {
			String fname = promptForFileName();
			file = new File(fname);
		}
    	return file;
    }
    /**
     * Reads the comma delimited file to extract the number data elements
     * provided in the second argument.
     * @param file          The File object
     * @param numRecords    The number of values to read from the input file
     * @return              2D array of data from the File
     * @throws IOException if any lines are missing data
     */
    public String[][] readFile(File file, int numRecords) throws IOException {
		/*
		 * takes info and puts it into object inFile
		 * declaring 2d array named data 
		 */

    	Scanner inFile = new Scanner(file);
		String[][] data = new String [numRecords][3];
		/*
		 * takes first line and sets row to 0
		 */
		String line = inFile.nextLine();
		int row = 0;
		/*
		 * first line is skipped
		 * delimiter skips the ","
		 */
		while(inFile.hasNextLine() && row < numRecords) {
			line = inFile.nextLine();
			Scanner inLine = new Scanner(line);
			inLine.useDelimiter(",");
		/*
		 * goes through columns in list
		 * throws IOException if there is missing data
		 */
			for(int col = 0; col < 3; col++) {
				if (inLine.hasNext()) 
					data[row][col] = inLine.next().trim();
				else {
					inLine.close();
					
					throw new IOException("Error reading data file.");
				}
			}
			/*
			 * row is increased each time to go through array
			 * returns data once array is full
			 */
			row++;
			inLine.close();
		} 
    	inFile.close();
		return data;	
    }
    /**
     * Determines the tallest height in the data set
     * Height is the second field in each row
     * @param db        2D array of data containing [gender] [height] [weight]
     * @return          Maximum height value
     */
    public int findTallest(String[][] db) {
    	/*
    	 * finds tallest (the second column in the list
    	 * iterates through the array and records the max given all of the values in the
    	 * column
    	 * returns the max
    	 */
    	int max = Integer.parseInt(db[0][1]);
    	for(int i = 0; i < db.length; i++) {
    		max = Math.max(max, Integer.parseInt(db[i][1]));
    	}
    	return max;
    }
    /**
     * Returns the values in the record that have the lowest weight
     * @param db        2D array of data containing [gender] [height] [weight]
     * @return          Smallest weight value
     */
    public String[] findLightestRecord(String[][] db) {
    	/*
    	 * sets the lightest to the first row in the third column
    	 */
    	int minRow = 0;
    	int min = Integer.parseInt(db[0][2]);
    	/*
    	 * finds the mininum number of all the values in the third column(lightest) and updates the value
    	 */
    	
    	for(int i = 0; i < db.length; i++) {
    		min = Math.min(min, Integer.parseInt(db[i][2]));
    	}
    	/*
    	 * matches the smallest number in the third column with the 
    	 * location of it in the array
    	 * 
    	 * takes the row number from the location and sets it to the min row
    	 */
    	for (int j = 0; j < db.length; j++) {
    		if (Integer.parseInt(db[j][2]) == min) {
    			minRow = j;
    		}
    	}
    	/*
    	 * create a new array called array
    	 */
    	int[] array = new int[3];
    	/*
    	 * puts the row with the lightest record into an array
    	 */
    	for (int q = 0; q < array.length; q++) {
    		array[q] += Integer.parseInt(db[minRow][q]);
    	}
    	/*
    	 * sets array in a string array
    	 */
    	String[] smallWeight = new String [array.length];
    	/*
    	 * iterates through the new array with the lighest weight
    	 */
    	for (int q = 0; q < smallWeight.length; q++) {
    		smallWeight[q] = String.valueOf(array[q]);
    	}
    	return smallWeight;
    }
    /**
     * Calculates the average height for all records with the given age range.
     * @param db            2D array of data containing [age] [height] [weight]
     * @param lowerBound    youngest age to include in the average
     * @param upperBound    oldest age to include in the average
     * @return              The average height for the given range or 0 if no
     *                      records match the filter criteria
     */
    public double findAvgHeightByAgeRange(String[][] db, int lowerBound, int upperBound) {
    	/*
    	 * sets the parameters to int values
    	 * sets of the values to 0
    	 * 
    	 */
    	int lowerNumber = lowerBound;
    	int higherNumber = upperBound;
    	double sum = 0;
    	double counter = 0;
    	double total = 0;

    	/*
    	 * changes string values to int
    	 * finds all of the weight values in between the ages range and adds up the total
    	 * also keeps track of the amount of values used in the total
    	 */
    	for (int p = 0; p < db.length; p++) {
    		if (Integer.parseInt(db[p][0]) >= lowerNumber && 
    			Integer.parseInt(db[p][0]) <= higherNumber) {
    			sum += Integer.parseInt(db[p][1]);
    			counter += 1;
    		}
    	}
    	/*
    	 * if the total is zero, it returns the zero
    	 */
    	if(counter == 0) {
    		total = 0;
    	}
    	/*
    	 * determines the total(average) by dividing the sum by the counter
    	 */
    	else {
    		total = sum / counter;
    	}
    	return total;
    }
}
