package com.snowcattle.game.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author
 * @version
 */
public final class ArgumentList
{
   /**
    * default constructor
    */
   public ArgumentList()
   {
   }

   /**
    * Get a named argument value.
    *
    * @param  strArgName         Argument name.
    * @return                    The argument value.
    *
    */
   public String get(String strArgName)
   {
      return get(strArgName, String.class);
   } // get


   /**
    * Get a specific named argument value
    *
    * @param  strArgName          Argument name.
    * @param  nValueIndex         Index of the value to retrieve (0 base)
    *
    * @return                    The argument value.
    */
   @SuppressWarnings("unchecked")
   public <T> T get(String strArgName, Class<T> type)
   {
      Object oValue = m_hashValues.get(strArgName);
      if (oValue == null)
      {
    	  return null;
      }
      return (T)oValue;
   } // get


   /**
    * Add a named argument to the argument list
    *
    * @param   strArgName          Argument name.
    * @param   strArgValue         Argument value.
    * @return  the argument index
    */
   public ArgumentList set(String strArgName, Object  objArgValue)
   {
      // Is there already an arg of this name in the list?
      Object oValue = m_hashValues.get(strArgName);
      m_hashValues.put(strArgName, objArgValue);
      if (oValue == null)
      {
         // Create a new named argument
         m_arrOrderedNames.add(strArgName);
      }
      return this;
   } // add


   /**
    * Replace any number of values with a single value
    *
    * @param   strArgName          Argument name to replace.
    * @param   strArgValue         Argument value.
    */
   public void replace(String strArgName, Object strArgValue)
   {
      remove(strArgName);
      set(strArgName, strArgValue);
   }

   /**
    * Remove an argument and all its values from an ArgumentList.
    * @param   strArgName          Argument name to remove.
    */
   public void remove(String strArgName)
   {
      m_hashValues.remove(strArgName);
      m_arrOrderedNames.remove(strArgName);
   }

   /**
    * Get the ordered list of argument names.
    *
    * @return                  the ordered list of argument names
    */
   public Iterator<String> nameIterator()
   {
      return m_arrOrderedNames.iterator();
   } // nameIterator


   /**
    * Is the Argument List empty.
    *
    * @return     true => There are no arguments defined in the dictionary.
    */
   public boolean isEmpty()
   {
      return m_arrOrderedNames.isEmpty();
   } // isEmpty


  /**
   * get string representation
   *
   * @return String containing representation
   */
   public String toString()
   {
      StringBuffer strBuf = new StringBuffer(256);

      strBuf.append('{');

      boolean bFirst = true;
      Iterator<String> iterName = nameIterator();
      while ( iterName.hasNext() )
      {
         String strName = iterName.next();
         Object objValue = get(strName,Object.class);

         if ( bFirst == false )
         {
            strBuf.append(',');
         }
         strBuf.append(strName);
         strBuf.append('=');
         strBuf.append(objValue.toString());
         bFirst = false;
      }

      strBuf.append('}');

      return strBuf.toString();
   }

   // Private data
   private HashMap<String,Object> m_hashValues = new HashMap<String, Object>(5,1.0f);
   private ArrayList<String> m_arrOrderedNames = new ArrayList<String>(5);


} // end ArgumentList