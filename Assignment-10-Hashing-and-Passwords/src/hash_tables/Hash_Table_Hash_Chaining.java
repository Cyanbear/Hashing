package hash_tables;

import java.util.ArrayList;
import java.util.LinkedList;

public class Hash_Table_Hash_Chaining<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType> 
{
	protected ArrayList<LinkedList<Pair<KeyType, ValueType>>>		table;		/** uses a chain of pairs */
	protected int													chainCount; /** keeps track of how many chains there are */
	
	public Hash_Table_Hash_Chaining(int initial_capacity) 
	{
		super(initial_capacity);
	}

	/**
	 * Finds a Pair with the specific key in a chain.
	 * 
	 * @param chain - chain to look in
	 * @param key   - key to look for
	 * @return a Pair if found, null otherwise
	 */
	private Pair<KeyType, ValueType> find(LinkedList<Pair<KeyType, ValueType>> chain, KeyType key)
	{
		for (Pair<KeyType, ValueType> pair : chain)
		{
			collisionCount++;
			
			if (pair.key.equals(key)) return pair;
		}
		
		return null;
	}
	
	public ValueType find( KeyType key )
	{
		double startTime = System.nanoTime(); // Used for timing
		
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - startTime);
		
		// Index the table
		LinkedList<Pair<KeyType, ValueType>> chain = table.get(index);
		ValueType returnValue = null;

		if (chain != null) 
		{
			Pair<KeyType, ValueType> pair = find(chain, key);
			
			if (pair != null) returnValue = pair.value;
		}
		
		findCount++;
		findTime += (System.nanoTime() - startTime);
		
		return returnValue;
	}
	
	public void insert( KeyType key, ValueType value )
	{
		double startTime = System.nanoTime(); // Used for timing
		
		// Check for resize (using # of chains!)
		if (chainCount > 0.5 * capacity && resizeable)
		{
			if (doubling) resize(capacity * 2);
			else 	      resize(capacity + 1);
		}
		
		// Insert key/value
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - startTime);
			
		LinkedList<Pair<KeyType, ValueType>> chain = table.get(index);
		if (chain != null) // Search the chain for duplicate
		{	
			Pair<KeyType, ValueType> pair = find(chain, key);
			
			if (pair != null) pair.value = value;
			else 
			{
				num_of_entries++;
				chain.add(new Pair<KeyType, ValueType>(key, value));
			}
		} else  // Add a new chain
		{
			chainCount++;
			num_of_entries++;
			LinkedList<Pair<KeyType, ValueType>> newChain = new LinkedList<>();
			newChain.add(new Pair<KeyType, ValueType>(key, value));
			table.set(index, newChain);
		}		
		
		insertionCount++;
		insertionTime += (System.nanoTime() - startTime);
	}
	
	public void clear()
	{
		super.clear();
		chainCount = 0;
	}
	
	/**
	 * Fill in calculations to show some of the stats about the hash table
	 * Extra info provided for this version.
	 */
	public String toString()
	{
		String result = new String();
		ArrayList<Double> stats = print_stats();
		
		// Calculate stats
		Long   avgHashTime 	    = (findCount == 0 && insertionCount == 0) ? 0 : functionTime / (findCount + insertionCount);
		Long   avgInsertionTime = (insertionCount == 0) ? 0 : insertionTime / insertionCount;
		Long   avgFindTime 	    = (findCount == 0) ? 0 : findTime / findCount;
		Double percentFilled    = Math.round(10000.0 * chainCount / capacity) / 100.0;
		Double avgChainLength   = Math.round(10000.0 * num_of_entries / chainCount) / 100.0;

		
		result = "------------ Hash Table Info ------------\n"
					+ "  Average collisions: "  	   + stats.get(0)								 + "\n"
					+ "  Average Hash Function Time: " + avgHashTime   								 + "\n"
					+ "  Average Insertion Time: " 	   + avgInsertionTime				             + "\n"
					+ "  Average Find Time: "          + avgFindTime								 + "\n"
					+ "  Average Chain Length: "       + avgChainLength  							 + "\n"
					+ "  Number of Elements: " 		   + stats.get(1)							 	 + "\n"
					+ "  Capacity of Table: "          + stats.get(2) 	                             + "\n"
					+ "  Percent filled: " 			   + percentFilled + "%"     					 + "\n"
					+ "-----------------------------------------\n";

		return result;
	}
}
