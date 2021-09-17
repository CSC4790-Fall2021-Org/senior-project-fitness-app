import java.util.*;
import java.io.*;

public class FileIO {
	
	private File currentFile;
	private String fname;
	
	
	//By default use a file called workouts.txt
	public FileIO() {
		
        this.fname = "workouts.txt";
        init(fname);
		
	}
	
	//Let the user specify a file
	public FileIO(String theFile) {
		
		 this.fname = theFile + ".txt";
		 init(fname);

	}
	
	private void init(String fn) {
		try {
			currentFile = new File(fn);
        	currentFile.createNewFile();
		} catch (IOException err) {
			//Cannot make new file.
			System.out.println("Cannot make new file.");
		}
      
	}
	
	//Main Scanner for File. Each line is considered a new workout
	private int scanLines(String s) throws FileNotFoundException {
		
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		String temp;
		
		Scanner s1 = new Scanner(currentFile);
		s1.reset();
		s1.useDelimiter("\n");
		
		while(s1.hasNext()) {
			//Check to see if this line is relevant:
			temp = s1.next();
			if(temp.startsWith(s)){
				data.add(readLine(temp));
			}
			
		}
		
		//Done with scanner - close it.
		s1.close();
		
		//Print out workout data
		System.out.println("Here is the workout data:\n");
		for(String[] someData : data) {
			//ASSUMES 3 ELEMENTS IN EACH
			System.out.println("Workout: " + someData[0] + " | Reps: " + someData[1] + " | Weight: " + someData[2]);
		}
		
		return data.size();
	}
	
	//Read all data from the file. Prints data and returns number of entries.
	//Returns -1 if file cannot be accessed. 
	public int getAllData() {
		return getWorkout("");
	}
	
	//Reads data of 1 workout type. Assumes string has already been formatted.
	//Returns -1 if file cannot be accessed. 
	public int getWorkout(String workOut) {
		int numLines = -1;
		try {
			workOut = formatter(workOut);
			numLines = scanLines(workOut);
		} catch (FileNotFoundException e) {
			
			System.out.println("Couldn't read file.");
		}
		
		return numLines;
		
	}
	
	//Assumes it will always receive 3 elements
	private String[] readLine(String s) {
		//Make the array of elements to return: SHOULD BE 3
		String[] elements = new String[3];
		int place = 0;
		
		//Scan the given string for workoutType, reps, and weights
		Scanner s2 = new Scanner(s);
		//All elements should be separated by a |
		s2.useDelimiter(",,");
		while(s2.hasNext() && place < 3) {
			elements[place] = s2.next();
			place++;
		}
		
		s2.close();
		return elements;
		
	}
	
	///////////////////// Writing Data to File \\\\\\\\\\\\\\\\\\\\\\
	
	
	//Inputs data to file. Returns true if data inputed, false if not
	public boolean inputWorkout(String type, int r, int w) {
		String reps = Integer.toString(r);
		String weight = Integer.toString(w);
		
		try {
			FileWriter f = new FileWriter(fname, true);
			String finalStr = formatter(type) + ",," + formatter(reps) + ",," + formatter(weight) + "\n";
			f.write(finalStr);
			f.close();
		} catch (IOException e) {
			//Couldn't get to file.
			System.out.println("Cannot access file.");
			return false;
		}
		
		return true;
	}
	
	//Formats User Inputs
	private String formatter(String s) {
		//Removes all caps and leading/trailing whitespace from string
		s = s.trim();
		s = s.toLowerCase();
		return s;
	}
	
}
