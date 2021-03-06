/**
 * 
 */
package hash_tables;

/**
 * @author H. James de St. Germain
 * @date   Spring 2007
 * 
 * a dummy wrapper for a string to allow us experiment with hash functions
 */
public class My_String implements Comparable<My_String> 
{
  public String value;
  public int preComputedHash = -1;                /** precomputing hashes is way faster! */
  public static boolean useBadHashMethod = false; /** forces all My_String objects to use the bad hash */
  
  /**
   * @param name
   */
  public My_String(String name)
  {
    this.value = name;
    this.preComputedHash = hashCode();
  }

  /**
   * Bad hash method.
   * 
   * @return hash of the String
   */
  public int naiveHashCode()
  {  
	  return (int)value.charAt(0);
  }
  
  /**
   * This our own the hash code method.
   * 
   * @return hash of the String
   */
  public int hashCode()
  {
	  if (preComputedHash != -1)
		  return preComputedHash;
		  
	  if (useBadHashMethod)
		  return naiveHashCode();
	  
	  double total = 0;
	  
	  for (int index = 0; index < value.length(); index++)
			  total += (int)this.value.charAt(index) * Math.pow(27, index);	 

      return (int) (total % Integer.MAX_VALUE);
  }
 
  /**
   * string value equality
   */
  @Override
  public boolean equals( Object other )
  {
    if ( other instanceof My_String)
      {
        return this.value.equals(((My_String)other).value);
      }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(My_String o)
  {
    return this.value.compareTo(o.value);
  }
  
  public String toString()
  {
    return this.value;
  }
  
}
