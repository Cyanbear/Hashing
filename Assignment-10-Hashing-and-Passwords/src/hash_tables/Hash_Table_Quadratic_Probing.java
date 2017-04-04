package hash_tables;

public class Hash_Table_Quadratic_Probing<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType>
{
	public Hash_Table_Quadratic_Probing(int initial_capacity) 
	{
		super(initial_capacity);
	}
	
	protected int nextProbeIndex(int index, int count)
	{
		return (index + (count * count)) % capacity;
	}
}
