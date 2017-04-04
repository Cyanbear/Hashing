package hash_tables;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class Hash_MapTest {
	private Hash_Map<String, Integer> testTable = new Hash_Table_Linear_Probing<>(100);
	private Hash_Map<Integer, Integer> testTable1 = new Hash_Table_Linear_Probing<>(100);


	@Before
	public void before() {
		testTable = new Hash_Table_Linear_Probing<>(100);
		testTable1 = new Hash_Table_Linear_Probing<>(100);
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
			testTable1.insert(102 - i, i);
		}
		
		assertEquals(211, testTable1.capacity());

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
		
		for(int i = 0 ;i<103;i++){
			testTable1.insert(103-i, i);
		}		
		
		assertEquals(101, testTable1.size());
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

		assertEquals(211, testTable.capacity());

	}
	@Test
	public void test_collisions()
	{
		testTable.insert("a", 1);
		testTable.insert("a", 2);
		ArrayList<Double> stats = testTable.print_stats();

		assertTrue(0.5 == stats.get(0));
	}
	@Test
	public void test_collisions1()
	{
		for(int i = 201 ;i >= 0;i--)
			testTable1.insert(i, i);
			
		ArrayList<Double> stats = testTable1.print_stats();
		
		assertTrue(202.0 == stats.get(1) && 431.0 == stats.get(2));
	}
	@Test
	public void test_collisions2()
	{
		testTable1 = new Hash_Table_Linear_Probing<>(5);
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1);
		testTable1.insert(1, 1);
		testTable1.insert(2, 1);
		testTable1.insert(3, 1);
		testTable1.insert(5, 1);
		
		ArrayList<Double> stats = testTable1.print_stats();

		assertTrue(4.0 / 5.0 == stats.get(0));
	}
	@Test
	public void test_collisions3()
	{
		testTable1 = new Hash_Table_Linear_Probing<>(11);
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1);
		testTable1.insert(1, 1);
		testTable1.insert(2, 1);
		testTable1.insert(3, 1);
		testTable1.insert(4, 1);
		testTable1.insert(5, 1);
		testTable1.insert(13, 1);
		
		ArrayList<Double> stats = testTable1.print_stats();

		assertTrue(4.0 / 7.0 == stats.get(0));
	}
	@Test
	public void test_collisions4()
	{
		testTable1 = new Hash_Table_Linear_Probing<>(11);
		testTable1.set_resize_allowable(false);
		
		testTable1.insert(0, 1);
		testTable1.insert(1, 1);
		testTable1.insert(2, 1);
		testTable1.insert(3, 1);
		testTable1.insert(4, 1);
		testTable1.insert(5, 1);
		testTable1.insert(13, 1);
		
		ArrayList<Double> stats = testTable1.print_stats();

		assertTrue(4.0 / 7.0 == stats.get(0));
	}

}
