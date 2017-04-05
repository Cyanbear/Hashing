package hash_tables;

import java.util.ArrayList;

/**
 * @author H. James de St. Germain, April 2007
 *         # Adapted by Erin Parker to accept generic items for keys and values
 *
 *         Represents a hash table of key-value pairs.
 *         Linear probing is used to handle conflicts.
 * 
 * 
 */
public class Hash_Table_Linear_Probing<KeyType, ValueType> implements Hash_Map<KeyType, ValueType>
{

	protected ArrayList<Pair<KeyType, ValueType>>	table;			/** stores the objects and their key values */
	protected int									capacity;		/** how many objects can be stored in the table */
	protected int									num_of_entries; /** the current number of entries */
	protected boolean								resizeable;     /** whether the table can be resized or not */
	
	// A list of statistics (self-explanatory)
	protected int									hitCount;       
	protected int									insertionCount;
	protected int									collisionCount;
	protected int									findCount;
	protected long									functionTime;
	protected long									insertionTime;
	protected long									findTime;

	/**
	 * Hash Table Constructor
	 * @param initial_capacity - try to make this equal to twice the expected number of values
	 */
	public Hash_Table_Linear_Probing( int initial_capacity )
	{
		this.capacity = Primes.next_prime(initial_capacity);
		init_table();
		this.num_of_entries = 0;
		this.resizeable = true;
	}

	/**
	 * @return the next index in a probe
	 */
	protected int nextProbeIndex(int index, int count)
	{
		return (index + count) % capacity;
	}
	
	/**
	 * Continuously probes the table until it finds a valid spot, then returns the index.
	 * Returns -1 if no index was found.
	 * 
	 * @param index    - index to start at
	 * @param key      - key to check with
	 * @param isInsert - whether this is an insert or find probe
	 * 
	 * @return index of a valid spot
	 */
	private int probe(int index, KeyType key, boolean isInsert)
	{				
		int localCollisionCount = 1;
		while (true)
		{
			hitCount++;
			collisionCount++;
			
			int probedIndex = nextProbeIndex(index, localCollisionCount++);
			Pair<KeyType, ValueType> pair = table.get(probedIndex);
			
			if (isInsert)
			{
				if (pair == null) 			  return probedIndex;
			} else
			{
				if (pair == null) 			  return -1;
				else if(pair.key.equals(key)) return probedIndex;
			}
			
			if (probedIndex == index) 		  return -1;
		}
	}
	
	/**
	 * Puts the given "value" into the hash table under the given "key".
	 * On a duplicate entry, replace the old data with the new "value".
	 * 
	 * For Probing Tables: This method will double* the size of the table if the number
	 *                     of elements is > 1/2 the capacity
	 * For Chaining Tables: double* the size of the table if the average number of collisions
	 *                      is greater than 5.
	 *           *double --> double then choose next greatest prime 
	 * 
	 */
	public void insert( KeyType key, ValueType value )
	{		
		if (num_of_entries == capacity) return;
		
		double startTime = System.nanoTime(); // Used for timing
		
		// Check for resize
		if (num_of_entries > capacity / 2.0 && resizeable)
			resize(capacity * 2);
		
		// Insert key/value
		hitCount++;
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - startTime);
			
		Pair<KeyType, ValueType> pair = table.get(index);
		if (pair != null) 
		{	
			if (pair.key.equals(key)) 
			{
				collisionCount++;
				table.get(index).value = value;
			}
			else // Collision found
			{
				int probeIndex = probe(index, key, true);
				Pair<KeyType, ValueType> probedPair = table.get(probeIndex);
				
				if (probedPair != null) probedPair.value = value;
				else
				{
					num_of_entries++;
					table.set(probeIndex, new Pair<KeyType, ValueType>(key, value));
				}
			}
		} else 
		{
			num_of_entries++;
			table.set(index, new Pair<KeyType, ValueType>(key, value));
		}
		
		insertionCount++;
		insertionTime += (System.nanoTime() - startTime);
	}

	/**
	 * Search for an item in the hash table, using the given "key".
	 * Return the item if it exists in the hash table.
	 * Otherwise, returns null.
	 * 
	 */
	public ValueType find( KeyType key )
	{
		double startTime = System.nanoTime(); // Used for timing
		
		int index = key.hashCode() % capacity;
		functionTime += (System.nanoTime() - startTime);
		
		// Index the table
		hitCount++;
		Pair<KeyType, ValueType> pair = table.get(index);
		ValueType returnValue = null;

		if (pair != null) 
		{
			if (pair.key.equals(key)) returnValue = pair.value;
			else // Collision found 
			{
				int probedIndex = probe(index, key, false);
				
				if (probedIndex != -1) returnValue = table.get(probedIndex).value;
			}
		}
		
		findCount++;
		findTime += (System.nanoTime() - startTime);
		
		return returnValue;
	}

	/**
	 * Remove all items from the hash table (and resets stats).
	 */
	public void clear()
	{
		init_table();
		reset_stats();
		num_of_entries = 0;
	}

	/**
	 * Returns the capacity of the hash table.
	 */
	public int capacity() { return capacity; }

	/**
	 * Returns the number of entries in the hash table (i.e., the number of stored key-value pairs).
	 */
	public int size() { return num_of_entries; }

	/**
	 * Prints certain stats formatted for a spreadsheet.
	 */
	public void print_stats_spreadhseet()
	{
		Long   avgInsertionTime = (insertionCount == 0) ? 0 : insertionTime / insertionCount;
		Long   avgFindTime 	    = (findCount == 0) ? 0 : findTime / findCount;
		
		System.out.printf("%10d\t%10d\t%10d\n", capacity, avgInsertionTime, avgFindTime);
	}

	/**
	 * 
	 */
	public ArrayList<Double> print_stats()
	{
		System.out.println(this);
		
		ArrayList<Double> stats = new ArrayList<>();
		
		stats.add(hitCount / (double)(insertionCount + findCount));
		stats.add((double) num_of_entries);
		stats.add((double) capacity);
		
		return stats;
	}

	/**
	 * Fill in calculations to show some of the stats about the hash table
	 */
	public String toString()
	{
		String result = new String();
		
		// Calculate stats
		Long   avgHashTime 	    = (findCount == 0 && insertionCount == 0) ? 0 : functionTime / (findCount + insertionCount);
		Long   avgInsertionTime = (insertionCount == 0) ? 0 : insertionTime / insertionCount;
		Long   avgFindTime 	    = (findCount == 0) ? 0 : findTime / findCount;
		Double avgCollisions    = Math.round(100.0 * collisionCount / (insertionCount + findCount)) / 100.0;
		Double percentFilled    = Math.round(10000.0 * num_of_entries / capacity) / 100.0;

		
		result = "------------ Hash Table Info ------------\n"
					+ "  Average Collisions: "  	   + avgCollisions								 + "\n"
					+ "  Average Hash Function Time: " + avgHashTime   								 + "\n"
					+ "  Average Insertion Time: " 	   + avgInsertionTime				             + "\n"
					+ "  Average Find Time: "          + avgFindTime								 + "\n"
					+ "  Number of Elements: " 		   + num_of_entries							 	 + "\n"
					+ "  Capacity of Table: "          + capacity	 	                             + "\n"
					+ "  Percent filled: " 			   + percentFilled + "%"     					 + "\n"
					+ "-----------------------------------------\n";

		return result;

	}

	/**
	 * Resets the hash table stats.
	 *
	 */
	public void reset_stats()
	{
		hitCount       = 0;
		insertionCount = 0;
		collisionCount = 0;
		findCount 	   = 0;
		functionTime   = 0;
		insertionTime  = 0;
		findTime 	   = 0;
	}

	/**
	 * Clear the hash table array and initializes all of the entries in the table to null.
	 */
	protected void init_table()
	{
		table = new ArrayList<Pair<KeyType, ValueType>>(capacity);
				
		for (int index = 0; index < capacity; index++)
			table.add(null);
	}

	/**
	 * Sets whether the Hash_Map can be resized or not.
	 * 
	 * @param status - value to use
	 */
	public void set_resize_allowable( boolean status ) { this.resizeable = status; }
	
	/**
	 * Expand the hash table to the new size, IF the new_size is GREATER than the current size
	 * (if not, doesn't do anything)
	 * 
	 * NOTE: The new hash table should have buckets equal in number the next prime number
	 * greater than or equal to the given "new_size". All the data in the original hash
	 * table must be maintained in the recreated hash table.
	 * 
	 * Note: make sure if you change the size, you rebuild your statistics...
	 */
	public void resize( int new_size )
	{
		if (new_size > capacity)
		{
			// Build a new table
			new_size = Primes.next_prime(new_size);
			ArrayList<Pair<KeyType, ValueType>> newTable = new ArrayList<>(new_size);
			ArrayList<Pair<KeyType, ValueType>> oldTable = table;
						
			for (int index = 0; index < new_size; index++)
				newTable.add(null);

			capacity = new_size;
			
			this.table = newTable;
			
			// Move the previous items to new one
			reset_stats();
			
			for (Pair<KeyType, ValueType> pair : oldTable)
				if (pair != null) insert(pair.key, pair.value);
		}
	}
	
	public static void main(String[] args)
	{
		Hash_Map<String, String> table = new Hash_Table_Linear_Probing<>(100);
		
		for (int i = 0; i < 1000; i++)
			table.insert("" + (char)(i % 10 + 30) + (char)(i % 20 + 30), "x");
		
		for (int i = 0; i < 1000; i++)
			table.find("" + (char)(i % 10 + 30) + (char)(i % 20 + 30));
				
		System.out.println(table);
	}
}