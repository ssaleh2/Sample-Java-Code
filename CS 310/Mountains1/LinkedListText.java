//********************************************************************
//  LinkedListText.java       Authors: Lewis/Chase
//
//  Represents a linked implementation of a list.
//********************************************************************

import java.util.*;

public class LinkedListText<T> implements ListADT<T>, Iterable<T>
{
   protected int count;
   protected LinearNode<T> head, tail;
   
   /******************************************************************
     Creates an empty list.
   ******************************************************************/
   public LinkedListText()
   {
      count = 0;
      head = tail = null;
   }
   
   /******************************************************************
     Removes the first element in this list and returns a reference
     to it.  Throws an EmptyListException if the list is empty.
   ******************************************************************/
   public T removeFirst() throws EmptyCollectionException
   {
      if (isEmpty())
         throw new EmptyCollectionException ("List");
      
      LinearNode<T> result = head; 
      head = head.getNext();
      if (head == null)
         tail = null;
      count--;
      
      return result.getElement();
   }
   
   /******************************************************************
     Removes the last element in this list and returns a reference
     to it.  Throws an EmptyListException if the list is empty.
   ******************************************************************/
   public T removeLast() throws EmptyCollectionException
   {
      if (isEmpty())
         throw new EmptyCollectionException ("List");

      LinearNode<T> previous = null;
      LinearNode<T> current = head;

      while (current.getNext() != null)
      {
         previous = current; 
         current = current.getNext();
      }
      
      LinearNode<T> result = tail; 
      tail = previous;
      if (tail == null)
         head = null;
      else
         tail.setNext(null);
      count--;
      
      return result.getElement();
   }
   
   /******************************************************************
     Removes the first instance of the specified element from this
     list and returns a reference to it.  Throws an EmptyListException 
     if the list is empty.  Throws a NoSuchElementException if the 
     specified element is not found in the list.
   ******************************************************************/
   public T remove (T targetElement) throws EmptyCollectionException, 
         ElementNotFoundException 
   {
      if (isEmpty())
         throw new EmptyCollectionException ("List");
      
      boolean found = false;
      LinearNode<T> previous = null;
      LinearNode<T> current = head;
      
      while (current != null && !found)
         if (targetElement.equals (current.getElement()))
            found = true;
         else
         {
            previous = current;
            current = current.getNext();
         }
            
      if (!found)
         throw new ElementNotFoundException ("List");
      
      if (size() == 1)
         head = tail = null;
      else if (current.equals (head))
         head = current.getNext();
      else if (current.equals (tail))
      {
         tail = previous;
         tail.setNext(null);
      }
      else
         previous.setNext(current.getNext());
      
      count--;
      
      return current.getElement();
   }
   
   /******************************************************************
     Returns true if the specified element is found in this list and 
     false otherwise.  Throws an EmptyListException if the specified
     element is not found in the list.                                     
   ******************************************************************/
   public boolean contains (T targetElement) throws 
         EmptyCollectionException 
   {
      if (isEmpty())
         throw new EmptyCollectionException ("List");

      boolean found = false;
      Object result;

      LinearNode<T> current = head;

      while (current != null && !found) 
         if (targetElement.equals (current.getElement()))
            found = true;
         else
            current = current.getNext();
      
      return found;
   }
   
   /******************************************************************
     Returns true if this list is empty and false otherwise.
   ******************************************************************/
   public boolean isEmpty()
   {
      return (count == 0);
   }

   /******************************************************************
     Returns the number of elements in this list.
   ******************************************************************/
   public int size()
   {
      return count;
   }

   /******************************************************************
     Returns a string representation of this list.
   ******************************************************************/
   public String toString()
   {
      LinearNode<T> current = head;
      String result = "";
      while (current != null)
      {
         result = result + (current.getElement()).toString() + "\n";
         current = current.getNext();
      }

      return result;
   }

   /******************************************************************
     Returns an iterator for the elements currently in this list. 
   ******************************************************************/
   public Iterator<T> iterator()
   {
      return new LinkedIterator<T>(head, count);
   }

   /******************************************************************
     Returns the first element in this list. 
   ******************************************************************/
   public T first()
   {
      return head.getElement();
   }

   /******************************************************************
     Returns the last element in this list. 
   ******************************************************************/
   public T last()
   {
      return tail.getElement();
   }
}


