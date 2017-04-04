package cracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Crack 
{
	/**
	 * @param string - string to use
	 * @return MD5 hash of the string
	 */
	static public String stringToMD5Hash(String string)
	{
		MessageDigest hash_generator;
		try {
			hash_generator = java.security.MessageDigest.getInstance("MD5");

			hash_generator.update(string.getBytes());
			byte[] digest = hash_generator.digest();

			StringBuffer hashword_hex_code = new StringBuffer();
			for (byte b : digest)
			{
		 	   hashword_hex_code.append(String.format("%02x", b & 0xff));
			}
			
			return hashword_hex_code.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Reads a file into a Collection of Strings.
	 * 
	 * @param file_name  - file to read
	 * @param collection - collection to insert into
	 */
	static public void read_file_into_collection(String file_name, Collection<String> collection)
	{
    	try
    	{
    		BufferedReader reader = new BufferedReader(new FileReader(file_name));
    		
    		while(reader.ready())
    			collection.add(reader.readLine());
    		    		
    		reader.close();
    	} catch(IOException error)
    	{
    		error.printStackTrace();
    	}
	}
	
	/**
	 * @param file_name - file to read
	 * @return an array containing each string
	 */
    static public ArrayList<String> read_file_into_array( String file_name )
    {
    	ArrayList<String> words = new ArrayList<>();
    	
    	read_file_into_collection(file_name, words);
    	
    	return words;
    }
    
    /**
     * @param file_name - file to read
     * @return a HashSet containing each string
     */
    static public HashSet<String> read_file_into_hash_set( String file_name )
    {
    	HashSet<String> words = new HashSet<>();
    	
    	read_file_into_collection(file_name, words);
    	
    	return words;
    }

    /**
     * 
     * This method compares (hashes of) all permutations of up to "Max_Length" characters vs
     * the given list of hashes (the password file)
     * 
     * @param hashes
     *            - hashes that you are seeing if you can find matches to
     * @param max_length
     *            - how many characters the passwords can be (in length)
     * @return the list of found passwords and their corresponding md5 hashes (e.g., [ "cat :
     *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342" ])
     */
    static public ArrayList<String> brute_force_attack( Collection<String> hashes, int max_length )
    {
    	ArrayList<String> successes = new ArrayList<>();
    	
    	brute_force_attack(hashes, successes, new StringBuilder(), 0, max_length);
    	
    	return successes;
    }

    // recommended but not required recursive helper
    static public void brute_force_attack(
                            Collection<String> hashes, ArrayList<String> successes, StringBuilder so_far,
                            int depth, int max_length )
    {
	
    	if (hashes.contains(stringToMD5Hash(so_far.toString())))
			successes.add(so_far.toString() + " : " + stringToMD5Hash(so_far.toString()));
    	
    	if (depth < max_length)
	    	for (int value = (int) 'a'; value <= (int) 'z'; value++)
	    		brute_force_attack(hashes, successes, so_far.append((char) value), depth + 1, max_length);
    	    	
    	if (depth != 0)
    		so_far.deleteCharAt(depth - 1);    	    	
    }

    /**
     * compare all words in the given list (dictionary) to the password collection we wish to crack
     *
     * @param dictionary
     *            - The list of likely passwords
     * @param hashes
     *            - Collection of the hashwords we are trying to break
     * @return the list of found passwords and their corresponding md5 hashes (e.g., "cat :
     *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342")
     */
    static public ArrayList<String> dictionary_attack( ArrayList<String> dictionary, Collection<String> hashes )
    {
    	ArrayList<String> successes = new ArrayList<>();
    	
    	for (String word : dictionary)
    		if (hashes.contains(word.hashCode()))
    			successes.add(word + " : " + word.hashCode());
    	
    	return successes;
    }

    /**
     * Begin a brute for attack on the password hashfile
     * 
     * Use up to 8 threads
     * 
     * compute all permutations of strings and compare them to a list of already hashed passwords
     * to see if there is a match
     * 
     * @param max_permutation_length
     *            - how long of passwords to attempt (suggest 6 or less)
     * @return a list of successfully cracked passwords
     */
    public static ArrayList<ArrayList<String>> multi_thread_brute_force_attack( int max_permutation_length, Collection<String> hashes )
    {
            long start_time = System.nanoTime();
            System.out.println("starting computation");

            ArrayList<Thread> threads = new ArrayList<>();

            int count = 0;
            int AVAILABLE_THREADS = 8;
            
                            
            ExecutorService thread_pool = Executors.newFixedThreadPool( AVAILABLE_THREADS );
            ArrayList<ArrayList<String>> successes = new ArrayList<ArrayList<String>>();
            
            for (int i=0; i<26; i++)
            {
                    successes.add( new ArrayList<>() );
            }
            
            for (int i=0; i<26; i++)
            {
                    int temp = i; 

                    thread_pool.execute(
                                    new Runnable()
                                    {
                                            
                                            @Override
                                            public void run()
                                            {
                                                    System.out.println("working on permutation " + temp);
                                                    brute_force_attack(hashes, successes.get(temp), new StringBuilder("" + (char) ('a' + temp + 1)),
                                                                    1, max_permutation_length);
                                            }
                                    });;
            }
                            
            try
            {
                    thread_pool.shutdown();
                    thread_pool.awaitTermination(1, TimeUnit.DAYS);
            }
            catch (Exception e)
            {
                    e.printStackTrace();
            }

            long total_time = System.nanoTime() - start_time;
            System.out.println("done: ( " + (total_time / 1000000000.0) + " seconds )");

            return successes;

    }
    
    public static void main(String[] args)
    {
    	HashSet<String> hashes = read_file_into_hash_set("Resources\\a_few_hashes");
    	
    	ArrayList<ArrayList<String>> pass = multi_thread_brute_force_attack(5, hashes);

    	System.out.println(pass);
    }
}
