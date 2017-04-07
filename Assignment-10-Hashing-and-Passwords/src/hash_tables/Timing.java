package hash_tables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Basic class to test our implementations of Hash_Map.
 * 
 * @author Jaden Simon and Yingqi Song
 */

public class Timing 
{
	/**
	 * Reads the Names file into the Hash_Map.
	 * 
	 * @param hashTable - table to read into
	 * @param maxNames  - max names to read
	 * 
	 * @return the key/value pairs used
	 */
	public static ArrayList<Pair<My_String, Integer>> readNamesFile(Hash_Map<My_String, Integer> hashTable, int maxNames)
	{
    	try
    	{
    		BufferedReader reader = new BufferedReader(new FileReader("Resources/names"));
    		 ArrayList<Pair<My_String, Integer>> pairs = new ArrayList<>();
    		
    		reader.readLine(); // Junk line
    		
    		while(reader.ready() && hashTable.size() < maxNames)
    		{
    			String line = reader.readLine();
    			String[] parts = line.split(" ");
    			My_String name = new My_String(parts[0] + " " + parts[1]);
    			Integer age = Integer.parseInt(parts[parts.length - 1]);
    			
    			hashTable.insert(name, age);
    			pairs.add(new Pair<My_String, Integer>(name, age));
    		}
    		    		
    		reader.close();
    		
    		return pairs;
    	} catch(IOException error)
    	{
    		error.printStackTrace();
    	}
    	
    	return null;
	}
	
	/**
	 * Runs a test using the Names file on the given Hash_Map for the given capacities.
	 * 
	 * @param testTable       - table to test
	 * @param startCap        - start capacity
	 * @param endCap          - end capacity
	 * @param stepSize        - capacity step size
	 * @param spreadSheetMode - enables/disables simple data printing
	 * @param resizeable      - enables/disables resizeable tables
	 */
	public static void runTest(String tableName, int startCap, int endCap, int stepSize, 
								boolean spreadSheetMode, boolean resizeable)
	{	
		if (spreadSheetMode)
			System.out.println("Capacity\tInsertion Time\tFind Time\tHash Time");
		
		for (int cap = startCap; cap <= endCap; cap += stepSize)
		{
			Hash_Map<My_String, Integer> testTable = null;
			
			// Initialize the table based on the parameter type.
			switch(tableName)
			{
			case "Linear" 	 : testTable = new Hash_Table_Linear_Probing<My_String, Integer>(cap);
							   break;
			case "Quadratic" : testTable = new Hash_Table_Quadratic_Probing<My_String, Integer>(cap);
							   break;
			case "Chaining"  : testTable = new Hash_Table_Hash_Chaining<My_String, Integer>(cap);
							   break;
			default          : System.out.println("ERROR!");
							   System.exit(1);
			}
			
			testTable.set_resize_allowable(resizeable);
			
			// Begin testing
			 ArrayList<Pair<My_String, Integer>> pairs = readNamesFile(testTable, 1000);

			// Find all the keys in the table
			for (Pair<My_String, Integer> pair : pairs)
				testTable.find(pair.key);
			
			if (spreadSheetMode)
				testTable.print_stats_spreadhseet();
			else 
			{
				System.out.println("Testing capacity " + cap + "\n");
				testTable.print_stats();
			}
		}	
	}
	
	public static void main(String[] args)
	{
		boolean SPREAD_SHEET_MODE  = true;   // Outputs only the necessary data
		My_String.useBadHashMethod = false;   // Change this to use a bad Hash method
			
		// No resize
		
		System.out.println("RUNNING TESTS WITH NO RESIZING\n");
		
		System.out.println("RUNNING LINEAR TEST");
		runTest("Linear", 1000, 5000, 200, SPREAD_SHEET_MODE, false);
		
		System.out.println("RUNNING QUADRATIC TEST");
		runTest("Quadratic", 1000, 5000, 200, SPREAD_SHEET_MODE, false);
		
		System.out.println("RUNNING HASH CHAIN TEST");
		runTest("Chaining", 1000, 5000, 200, SPREAD_SHEET_MODE, false);
		
		// Resizing
		
		System.out.println("RUNNING TESTS WITH RESIZING\n");
		
		System.out.println("RUNNING LINEAR TEST");
		runTest("Linear", 1000, 5000, 200, SPREAD_SHEET_MODE, true);
		
		System.out.println("RUNNING QUADRATIC TEST");
		runTest("Quadratic", 1000, 5000, 200, SPREAD_SHEET_MODE, true);
		
		System.out.println("RUNNING HASH CHAIN TEST");
		runTest("Chaining", 1000, 5000, 200, SPREAD_SHEET_MODE, true);
		
		// Just hash chain testing
		
		System.out.println("RUNNING TESTS WITH RESIZING AND CAPACITY < ELEMENTS (HASH_CHAINING ONLY!)\n");
		
		System.out.println("RUNNING HASH CHAIN TEST");
		runTest("Chaining", 100, 1000, 10, SPREAD_SHEET_MODE, true);
		
		// Analysis test
		
		System.out.println("TESTING EACH IMPLEMENTATION AT 79 CAPACITY WITH NO RESIZE");
		System.out.println("RESULTS IN THE ORDER LINEAR -> QUADRATIC -> CHAINING\n");		
		
		Hash_Map<My_String, Integer> testTable = new Hash_Table_Linear_Probing<>(79);
		testTable.set_resize_allowable(false);
		readNamesFile(testTable, 79);
		testTable.print_stats();
		
		testTable = new Hash_Table_Quadratic_Probing<>(79);
		testTable.set_resize_allowable(false);
		readNamesFile(testTable, 79);
		testTable.print_stats();
		
		testTable = new Hash_Table_Hash_Chaining<>(79);
		testTable.set_resize_allowable(false);
		readNamesFile(testTable, 79);
		testTable.print_stats();
	}
}
