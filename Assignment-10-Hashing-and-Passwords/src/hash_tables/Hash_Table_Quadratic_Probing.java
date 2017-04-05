package hash_tables;

/**
 * Simple extension of Hash_Table_Linear_Probing. Only its probing index method is changed.
 * 
 * @author Jaden Simon and Yingqi Song
 */
public class Hash_Table_Quadratic_Probing<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType>
{
	public Hash_Table_Quadratic_Probing(int initial_capacity) 
	{
		super(initial_capacity);
	}
	
	protected int nextProbeIndex(int index, int count)
	{
		return Math.abs((index + (count * count))) % capacity;
	}
}
