package hash_tables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Timing 
{
	private static ArrayList<My_String> readNamesFile(Hash_Map<My_String, Integer> hashTable)
	{
    	try
    	{
    		BufferedReader reader = new BufferedReader(new FileReader("Resources/names"));
    		ArrayList<My_String> keys = new ArrayList<>();
    		
    		reader.readLine(); // Junk line
    		
    		while(reader.ready())
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
	
	public static void main(String[] args)
	{
		Hash_Table_Linear_Probing<My_String, Integer> tableLinear;
		Hash_Table_Quadratic_Probing<My_String, Integer> tableQuadratic;
		Hash_Table_Hash_Chaining<My_String, Integer> tableChain;
		
		for (int cap = 1000; cap <= 5000; cap += 200)
		{
			System.out.println("\nTESTING WITH CAPACITY " + cap);
			
			tableLinear = new Hash_Table_Linear_Probing<>(cap);
			tableQuadratic = new Hash_Table_Quadratic_Probing<>(cap);
			tableLinear.set_resize_allowable(false);
			tableQuadratic.set_resize_allowable(false);

			ArrayList<My_String> keys = readNamesFile(tableLinear);
			readNamesFile(tableQuadratic);

			tableLinear.print_stats();
			tableQuadratic.print_stats();
		
			tableLinear.reset_stats();
			tableQuadratic.reset_stats();

			for (My_String key : keys)
			{
				tableLinear.find(key);
				tableQuadratic.find(key);
			}
			
			tableLinear.print_stats();
			tableQuadratic.print_stats();		
		}
		
		for (int cap = 10; cap <= 1000; cap += 10)
		{
			System.out.println("\nTESTING WITH CAPACITY " + cap);
			
			tableChain = new Hash_Table_Hash_Chaining<>(cap);

			ArrayList<My_String> keys = readNamesFile(tableChain);

			tableChain.print_stats();
			tableChain.reset_stats();

			for (My_String key : keys)
				tableChain.find(key);

			tableChain.print_stats();
		}
	}
}
