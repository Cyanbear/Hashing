package hash_tables;

/**
 * @author germain
 *
 * some helper methods dealing with prime numbers.  These methods can be "brute force"
 */
public class Primes
{
	

	/**
	 * @param value
	 *            - to check if prime
	 * @return - return true if value is prime
	 */
	public static boolean is_prime( int value )
	{
		if (value < 2) 
			return false;
		if (value % 2 == 0) 
			return (value == 2);
		
		for (int factor = 3; factor*factor <= value ; factor += 2)
			if (value % factor == 0)
				return false;
		
		return true;
	}
	

	/**
	 * next_prime
	 * 
	 * Note: static function
	 * 
	 * @param value
	 *            - the starting point to search for a prime
	 * @return - the value if prime, otherwise the next prime after value
	 */
	public static int next_prime( int value )
	{
		if (is_prime(value))
			return value;
		else
			return next_prime(value + 1);
	}


}
