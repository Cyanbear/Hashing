package hash_tables;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Hash_MapTest {
	private Hash_Map<String, Integer> testTable = new Hash_Table_Linear_Probing<>(100);
	private Hash_Map<Integer, Integer> testTable1 = new Hash_Table_Linear_Probing<>(100);


	@Before
	public void before() {
		testTable.clear();
	}

	@Test
	public void insert_test()

	{
		testTable.insert("a", 1);
		testTable.insert("b", 2);
		testTable.insert("c", 3);
		testTable.insert("d", 4);
		assertEquals((Integer) 1, testTable.find("a"));

	}

	@Test
	public void find_test()

	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 3);
		testTable.insert("d", 4);
		assertEquals((Integer) 3, testTable.find("a"));

	}
	
	@Test
	public void find_test1()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 3);
		testTable.insert("d", 4);
		assertEquals(null, testTable.find("c"));

	}
	@Test
	public void find_test2()

	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		assertEquals((Integer) 2, testTable.find("a"));

	}
	@Test
	public void remove_test()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 3);
		testTable.insert("d", 4);
		testTable.clear();
		assertEquals(null, testTable.find("a"));

	}
	@Test
	public void capacity_test()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 3);
		testTable.insert("d", 4);
		testTable.clear();
		assertEquals(101, testTable.capacity());

	}
	@Test
	public void size_test()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 3);
		testTable.insert("d", 4);
		testTable.clear();
		assertEquals(0, testTable.size());

	}
	@Test
	public void size_test1()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		assertEquals(2, testTable.size());

	}
	@Test
	public void size_test2()
	
	{
		for(int i = 0 ;i<100;i++){
			testTable1.insert(100-i, i);
		}
		
		assertEquals(100, testTable1.size());

	}
	@Test
	public void size_test3()
	
	{
		for(int i = 0 ;i<102;i++){
			testTable1.insert(100-i, i);
		}
		
		assertEquals(101, testTable1.capacity());

	}
	@Test
	public void size_test4()
	
	{
		for(int i = 0 ;i<200;i++){
			testTable1.insert(200-i, i);
		}
		
		assertEquals(100, testTable1.size());

	}
	@Test
	public void resize_test()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		testTable.resize(5);

		assertEquals(101, testTable.capacity());

	}
	@Test
	public void resize_test1()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		testTable.resize(200);

		assertEquals(201, testTable.capacity());

	}

}
