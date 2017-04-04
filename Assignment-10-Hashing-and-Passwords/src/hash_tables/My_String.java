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
  
  /**
   * @param name
   */
  public My_String(String name)
  {
    this.value = name;
  }

  /**
   * This our own the hash code of the my_string .
   * 
   * s.charAt(0) * 31n-1 + s.charAt(1) * 31n-2 + ... + s.charAt(n-1)
   * @return total number of the function 
   */
  public int hashCode()
  {
	  int total = 0;
	  
	  for (int index = 0; index < value.length(); index++)
			  total+= (int)this.value.charAt(index) * Math.pow(51, index);	 
	  //System.out.println(total);
      return total;
  }
 

// @Override
//  public int hashCode()
//  {
//      return this.value.hashCode();
//  }
//  
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
