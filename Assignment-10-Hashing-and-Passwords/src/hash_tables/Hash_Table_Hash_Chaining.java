package hash_tables;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An extension of Hash_Table_Linear_Probing that uses Chaining for collisions.
 * Unlike probing, a chaining hash table expands based on the average chain length.
 * For this implementation, 5 is the maximum average chain length.
 * 
 * @author Jaden Simon and Yingqi Song
 */

public class Hash_Table_Hash_Chaining<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType> 
{
	protected ArrayList<LinkedList<Pair<KeyType, ValueType>>>		table;		/** uses a LinkedList of Pairs */
	protected int													chainCount; /** keeps track of how many chains there are */
	
	public Hash_Table_Hash_Chaining(int initial_capacity) 
	{
		super(initial_capacity);
		this.chainCount = 0;
	}

	@Override
	protected void init_table()
	{
		table = new ArrayList<LinkedList<Pair<KeyType, ValueType>>>(capacity);
				
		for (int index = 0; index < capacity; index++)
			table.add(null);
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
			if (pair.key.equals(key)) return pair;
			
			collisionCount++;
		}
		
		return null;
	}
	
	@Override
	public ValueType find( KeyType key )
	{
		double startTime = System.nanoTime(); // Used for timing
		
		long hashTime = System.nanoTime();
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - hashTime);
		
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
	
	@Override
	public void insert( KeyType key, ValueType value )
	{		
		long startTime = System.nanoTime(); // Used for timing
		
		// Check for resize
		if (num_of_entries > 5.0 * chainCount && resizeable)
			resize(capacity * 2);
		
		long hashTime = System.nanoTime();
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - hashTime);
			
		// Insert key/value
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
	
	@Override
	public void resize( int new_size )
	{
		if (new_size > capacity)
		{
			// Build a new table
			new_size = Primes.next_prime(new_size);
			ArrayList<LinkedList<Pair<KeyType, ValueType>>> newTable = new ArrayList<>(new_size);
			ArrayList<LinkedList<Pair<KeyType, ValueType>>> oldTable = table;
						
			for (int index = 0; index < new_size; index++)
				newTable.add(null);

			this.capacity = new_size;
			this.table = newTable;
			
			// Move the previous items to new one
			num_of_entries = 0;
			chainCount = 0;
			reset_stats();
			
			for (LinkedList<Pair<KeyType, ValueType>> chain : oldTable)
				if (chain != null)
					for (Pair<KeyType, ValueType> pair : chain)
						if (pair != null) 
							insert(pair.key, pair.value);			
		}
	}
	
	@Override
	public void clear()
	{
		super.clear();
		chainCount = 0;
	}
	
	/**
	 * Fill in calculations to show some of the stats about the hash table
	 * The chaining version includes average chain length, and considers percent filled
	 * to be the ratio of chainCount and capacity.
	 */
	@Override
	public String toString()
	{
		String result = new String();
		
		// Calculate stats
		Long   avgHashTime 	    = (findCount == 0 && insertionCount == 0) ? 0 : functionTime / (findCount + insertionCount);
		Long   avgInsertionTime = (insertionCount == 0) ? 0 : insertionTime / insertionCount;
		Long   avgFindTime 	    = (findCount == 0) ? 0 : findTime / findCount;
		Double percentFilled    = Math.round(10000.0 * chainCount / capacity) / 100.0;
		Double avgChainLength   = Math.round(100.0 * num_of_entries / chainCount) / 100.0;
		Double avgCollisions    = Math.round(100.0 * collisionCount / (insertionCount + findCount)) / 100.0;

		
		result = "------------ Hash Table Info ------------\n"
					+ "  Average Collisions: "  	   + avgCollisions								 + "\n"
					+ "  Average Hash Function Time: " + avgHashTime   								 + "\n"
					+ "  Average Insertion Time: " 	   + avgInsertionTime				             + "\n"
					+ "  Average Find Time: "          + avgFindTime								 + "\n"
					+ "  Average Chain Length: "       + avgChainLength  							 + "\n"
					+ "  Number of Elements: " 		   + num_of_entries							 	 + "\n"
					+ "  Capacity of Table: "          + capacity		                             + "\n"
					+ "  Percent filled: " 			   + percentFilled + "%"     					 + "\n"
					+ "-----------------------------------------\n";

		return result;
	}
}
