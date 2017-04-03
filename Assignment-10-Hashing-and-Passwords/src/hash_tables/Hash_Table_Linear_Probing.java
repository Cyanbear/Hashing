package hash_tables;

import java.util.ArrayList;

import static hash_tables.Primes.next_prime;

/**
 * @author H. James de St. Germain, April 2007
 *         # Adapted by Erin Parker to accept generic items for keys and values
 *
 *         Represents a hash table of key-value pairs.
 *         Linear probing is used to handle conflicts.
 * 
 * 
 *         FIXME: You are to comment all functions and variables
 */
public class Hash_Table_Linear_Probing<KeyType, ValueType> implements Hash_Map<KeyType, ValueType>
{

	private ArrayList<Pair<KeyType, ValueType>>	table;			/** stores the objects and their key values */
	protected int								capacity;		/** how many objects can be stored in the table */
	protected int								num_of_entries; /** the current number of entries */
	protected boolean							resizeable;     /** whether the table can be resized or not */
	
	// A list of statistics
	// TODO: add statistics

	/**
	 * Hash Table Constructor
	 * @param initial_capacity - try to make this equal to twice the expected number of values
	 */
	public Hash_Table_Linear_Probing( int initial_capacity )
	{
		this.capacity = next_prime(initial_capacity);
		init_table();
		this.num_of_entries = 0;
		this.resizeable = true;
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
	 * FIXME: Make sure you collect statistics in this method. See print_stats().
	 */
	public void insert( KeyType key, ValueType value )
	{
		//FIXME
	}
	
	/**
	 * if doubling is off, then do not change table size in insert method
	 * 
	 * @param on - turns doubling on (the default value for a hash table should be on)
	 */
	public void doubling_behavior(boolean on)
	{
		// FIXME: 
	}

	/**
	 * Search for an item in the hash table, using the given "key".
	 * Return the item if it exists in the hash table.
	 * Otherwise, returns null.
	 * 
	 * FIXME: Make sure you collect statistics in this method. See print_stats().
	 */
	public ValueType find( KeyType key )
	{
		// FIXME
		return null;
	}

	/**
	 * Remove all items from the hash table (and resets stats).
	 */
	public void clear()
	{
		init_table();
		this.num_of_entries = 0;
		reset_stats();
	}

	/**
	 * Returns the capacity of the hash table.
	 */
	public int capacity()
	{
		// FIXME: return the number of total buckets
		return 0;
	}

	/**
	 * Returns the number of entries in the hash table (i.e., the number of stored key-value pairs).
	 */
	public int size()
	{
		// FIXME: return the number of filled buckets
		return 0;
	}


	/**
	 * 
	 */
	public ArrayList<Double> print_stats()
	{
		// FIXME: insert code
		return null;
	}

	/**
	 * Fill in calculations to show some of the stats about the hash table
	 */
	public String toString()
	{
		String result = new String();
		result = "------------ Hash Table Info ------------\n"
					+ "  Average collisions: " + "  Average Hash Function Time: " + "  Average Insertion Time: "
					+ "  Average Find Time: " + "  Percent filled : " + "  Size of Table  : " + "  Elements       : "
					+ "-----------------------------------------\n";

		return result;

	}

	/**
	 * Resets the hash table stats.
	 *
	 */
	public void reset_stats()
	{
		// FIXME: insert code
	}

	/**
	 * Clear the hash table array and initializes all of the entries in the table to null.
	 * 
	 * 
	 */
	private void init_table()
	{
		table = new ArrayList<Pair<KeyType, ValueType>>();
		
		for (int index = 0; index < capacity; index++)
			table.set(index, null);
		
		num_of_entries = 0;
	}

	/**
	 * Sets whether the Hash_Map can be resized or not.
	 * 
	 * @param status - value to use
	 */
	public void set_resize_allowable( boolean status )
	{
		this.resizeable = true;
	}
	
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
			
		}
	}
	
	


}