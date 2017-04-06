Author Information:
	Assignment 10 - Hashing
	Name: NAME
	Date: April 7th, 2017
	Partner: NAME

Project Summary:
	This project is essentially an experiment with the use of hash tables. This includes their
	implementations, functionality, and applications. One application for hashing is with passwords:
	passwords could be looked up in a hash table, while not making them completely vulnerable to attacks.
	Still, brute-forcing certain hashes is possible (MD5), so it is very important to choose a good hash
	method. We also implemented different kind of hash tables: linear/quadratic probing and chaining. The
	probing style of table just looks for an empty spot further down the array if a collision occurs, while
	the chaining version chains elements together when a collision occurs. A timing class is used to show
	how the tables function at different capacities.
	
Notes to the TA:
	* When reading files it is important to note the distinction between the UNIX file system and the Windows one.
	* To use the bad hash method in My_String, simply change the static field useBadHashMethod to true.
	  The good method is used otherwise.
	* Insertion and Find time includes the time taken to compute the hash.

Pledge:
	      I pledge that the work done here was my own and that I have learned how to write
	      this program (such that I could throw it out and restart and finish it in a timely
	      manner).  I am not turning in any work that I cannot understand, describe, or
	      recreate.  Any sources (e.g., web sites) other than the lecture that I used to
	      help write the code are cited in my work.  When working with a partner, I have
	      contributed an equal share and understand all the submitted work.  Further, I have
	      helped write all the code assigned as pair-programming and reviewed all code that
	      was written separately.
	                      NAME
	                      
Design Decisions:
	* Hash_Chaining uses a LinkedList, though it isn't as fast it was easier than just adding our own Node code.
	* Hash_Chaining extends Linear_Probing so it can inherit a lot of the fields/statistic stuff.
	* Preventing resizing for the Hash_Chaining implementation doesn't make much sense because it can store
	  elements beyond what its capacity is through the use of chaining.
	  
Problems Encountered:
	* Problem: print_stats was returning the wrong data
	  Solution: keeping track of a hitCount in the Hash_Table classes
	* Problem: Quadratic_Probing was throwing an IndexOutOfBoundsException
	  Solution: the probing method was returning a negative integer due to overflow; return the absolute value
	* Problem: Crack.java wasn't finding any passwords/hashes
	  Solution: use an MD5 hash method instead of hashCode()
	  