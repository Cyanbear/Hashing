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
	 * @return the keys used
	 */
	private static ArrayList<My_String> readNamesFile(Hash_Map<My_String, Integer> hashTable, int maxNames)
	{
    	try
    	{
    		BufferedReader reader = new BufferedReader(new FileReader("Resources/names"));
    		ArrayList<My_String> keys = new ArrayList<>();
    		
    		reader.readLine(); // Junk line
    		
    		while(reader.ready() && hashTable.size() < maxNames)
    		{
    			String line = reader.readLine();
    			String[] parts = line.split(" ");
    			My_String name = new My_String(parts[0] + " " + parts[1]);
    			Integer age = Integer.parseInt(parts[parts.length - 1]);
    			
    			hashTable.insert(name, age);
    			keys.add(name);
    		}
    		    		
    		reader.close();
    		
    		return keys;
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
	 */
	private static void runTest(Hash_Map<My_String, Integer> testTable, int startCap, 
								int endCap, int stepSize, boolean spreadSheetMode)
	{	
		if (spreadSheetMode)
			System.out.println("Capacity\tInsertion Time\tFind Time");
		
		for (int cap = startCap; cap <= endCap; cap += stepSize)
		{
			// Initialize the table based on the parameter type.
			if (testTable instanceof Hash_Table_Linear_Probing)
			{
				testTable = new Hash_Table_Linear_Probing<My_String, Integer>(cap);
				testTable.set_resize_allowable(false);
			}
			else if (testTable instanceof Hash_Table_Quadratic_Probing)
			{
				testTable = new Hash_Table_Linear_Probing<My_String, Integer>(cap);
				testTable.set_resize_allowable(false);
			}
			else if (testTable instanceof Hash_Table_Hash_Chaining)
			{
				testTable = new Hash_Table_Linear_Probing<My_String, Integer>(cap);
			}
			
			// Begin testing
			ArrayList<My_String> keys = readNamesFile(testTable, 1000);

			if (spreadSheetMode)
			{
				for (My_String key : keys)
					testTable.find(key);
				
				testTable.print_stats_spreadhseet();
			} else 
			{
				System.out.println("Testing capacity " + cap + "\n");
				
				testTable.print_stats();
				testTable.reset_stats();
	
				for (My_String key : keys)
					testTable.find(key);
	
				testTable.print_stats();
			}
		}	
	}
	
	public static void main(String[] args)
	{
		boolean SPREAD_SHEET_MODE = true; // Outputs only the necessary data
		
		// Probing tests
	
		System.out.println("RUNNING LINEAR TEST");
		runTest(new Hash_Table_Linear_Probing<My_String, Integer>(0), 1000, 5000, 200, SPREAD_SHEET_MODE);
		
		System.out.println("RUNNING QUADRATIC TEST");
		runTest(new Hash_Table_Quadratic_Probing<My_String, Integer>(0), 1000, 5000, 200, SPREAD_SHEET_MODE);
		
		// Hash_Chaining test
		
		System.out.println("RUNNING HASH CHAIN TEST");
		runTest(new Hash_Table_Hash_Chaining<My_String, Integer>(0), 100, 1000, 10, SPREAD_SHEET_MODE);
		
		Hash_Table_Quadratic_Probing<My_String, Integer> testTable = new Hash_Table_Quadratic_Probing<>(79);
		testTable.set_resize_allowable(false);
		readNamesFile(testTable, 79);
		testTable.print_stats();
	}
}
