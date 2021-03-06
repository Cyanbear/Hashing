package hash_tables;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class Hash_MapTest {
	private Hash_Map<String, Integer> testTable;
	private Hash_Map<Integer, Integer> testTable1;
	private Hash_Map<My_String, Integer> testTable2;


	@Before
	public void before() {
		testTable = new Hash_Table_Linear_Probing<>(11);
		testTable1 = new Hash_Table_Linear_Probing<>(11);
		testTable2 = new Hash_Table_Linear_Probing<>(11);
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
	public void insert_test_almost_full()

	{
		for (int index = 0; index < 10; index++)
			testTable1.insert(index, index);
		
		testTable1.insert(12, 12);
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
		assertEquals(11, testTable.capacity());

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
			testTable1.insert(102 - i, i);
		}
				
		assertEquals(102, testTable1.size());

	}
	@Test
	public void size_test4()
	
	{
		for(int i = 0 ;i<200;i++){
			testTable1.insert(200-i, i);
		}
		
		assertEquals(200, testTable1.size());

	}
	@Test
	public void size_test5()
	
	{
		testTable1.set_resize_allowable(false);
		
		for(int i = 0 ;i<100;i++){
			testTable1.insert(100-i, i);
		}		
		
		if (testTable1 instanceof Hash_Table_Hash_Chaining)
			assertEquals(100, testTable1.size());
		else
			assertEquals(11, testTable1.size());
	}
	@Test
	public void resize_test()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		testTable.resize(5);

		assertEquals(11, testTable.capacity());

	}
	@Test
	public void resize_test1()
	
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		testTable.insert("a", 2);
		testTable.insert("d", 4);
		testTable.resize(200);

		assertEquals(211, testTable.capacity());

	}
	@Test
	public void test_collisions()
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		ArrayList<Double> stats = testTable.print_stats();

		assertTrue(0.0 == stats.get(0));
	}
	@Test
	public void test_collisions1()
	{
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1); 
		testTable1.insert(11, 1);
		testTable1.insert(22, 1);
		testTable1.insert(3, 1);  
		testTable1.insert(4, 1);  
		testTable1.insert(12, 1); 
		testTable1.insert(25, 1); 
			
		ArrayList<Double> stats = testTable1.print_stats();
		
		if (testTable1 instanceof Hash_Table_Hash_Chaining)
			assertTrue(4.0 / 7.0 == stats.get(0));
		else if (testTable instanceof Hash_Table_Quadratic_Probing)
			assertTrue(7.0 / 7.0 == stats.get(0));
		else if (testTable instanceof Hash_Table_Linear_Probing)
			assertTrue(10.0 / 7.0 == stats.get(0));
		
		assertTrue(stats.get(1) == 7 && stats.get(2) == 11);
	}

	@Test
	public void test_collisions2()
	{
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1);
		testTable1.insert(1, 1);
		testTable1.insert(2, 1);
		testTable1.insert(3, 1);
		testTable1.insert(4, 1);
		testTable1.insert(5, 1);
		testTable1.insert(11, 1);
		
		ArrayList<Double> stats = testTable1.print_stats();

		if (testTable1 instanceof Hash_Table_Hash_Chaining)
			assertTrue(1.0 / 7.0 == stats.get(0));
		else if (testTable instanceof Hash_Table_Quadratic_Probing)
			assertTrue(3.0 / 7.0 == stats.get(0));	
		else if (testTable instanceof Hash_Table_Linear_Probing)
			assertTrue(6.0 / 7.0 == stats.get(0));
		
		assertTrue(stats.get(1) == 7 && stats.get(2) == 11);
	}
	@Test
	public void test_collisions3()
	{
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1);
		testTable1.insert(1, 1);
		testTable1.insert(2, 1);
		testTable1.insert(3, 1);
		testTable1.insert(4, 1);
		testTable1.insert(5, 1);
		testTable1.insert(14, 1);
		
		ArrayList<Double> stats = testTable1.print_stats();

		if (testTable1 instanceof Hash_Table_Hash_Chaining)
			assertTrue(1.0 / 7.0 == stats.get(0));
		else if (testTable instanceof Hash_Table_Quadratic_Probing)
			assertTrue(2.0 / 7.0 == stats.get(0));
		else if (testTable instanceof Hash_Table_Linear_Probing)
			assertTrue(3.0 / 7.0 == stats.get(0));
		
		assertTrue(stats.get(1) == 7 && stats.get(2) == 11);
	}
	
	@Test
	public void test_consistency()
	{
		 ArrayList<Pair<My_String, Integer>> pairs = Timing.readNamesFile(testTable2, 1000);
		 
		 for (Pair<My_String, Integer> pair : pairs)
			 assertTrue("Conistency test failed!", (testTable2.find(pair.key) - pair.value) == 0);	 
		 
		 assertTrue(testTable2.size() == pairs.size());
	}

}
