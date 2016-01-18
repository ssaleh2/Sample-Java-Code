//********************************************************************
//  UnorderedLinkedList.java
//
//  Represents a linked implementation of an unordered list.
//********************************************************************

import java.util.*;

public class UnorderedLinkedList<T> extends LinkedListText<T>
{
   
   /******************************************************************
     Creates an empty list.
   ******************************************************************/
   public UnorderedLinkedList()
   {
		super();
   }
   
	/* adds element to the front of the list. */
	public void addToFront(T element) {
		LinearNode<T> node = new LinearNode<T>(element);
		if(isEmpty()){
			head = tail = node;
			count = 1;
		}
		else{
			node.setNext(head);
			head = node;
			count++;
		}
	}
	
	/* adds element to the rear of the list. */
	public void addToRear(T element) {
		LinearNode<T> node = new LinearNode<T>(element);
		if(isEmpty()){
			head = tail = node;
			count = 1;
		}
		else{
			tail.setNext(node);
			tail = node;
			count++;
		}
	}
	
	/* adds element after targetElement.Throws ElementNotFoundException if targetElement is not found.	*/
	public void addAfter(T element, T targetElement) {
		 LinearNode<T> current = head;
		 LinearNode<T> node = new LinearNode<T>(element);
		 LinearNode<T> previous = null;
		 boolean found = false;
		 while (current != null && !found){
		 	 if (targetElement.equals(current.getElement())) found = true;
		 	 else
		 	 {
		 	 	 previous = current;
		 	 	 current = current.getNext();
		 	 }
	         }     
	      if (!found)
	         throw new ElementNotFoundException ("List");
	      else{
	      	  if(current.equals(head)){
	      	      node.setNext(head.getNext());
	      	      head.setNext(node);
	      	  }
	    	  else if(current.equals(tail)) addToRear(element);
	    	  else{
	    		  previous = current;
	    		  current = current.getNext();
	    		  previous.setNext(node);
	    		  node.setNext(current);
	    	  }
	      }
     	count++;
	}
	
	/* returns a reference to the targetElement or null if the targetElement is not in the list.*/
	public T find(T targetElement) {
	      boolean found = false;	
	      LinearNode<T> previous = null;
	      LinearNode<T> current = head;
	    
	      while (current != null && !found){
	    	  //if the two elements equal to each other based on the object's compareTo
	          if (targetElement.equals (current.getElement()))
	             found = true;
	          else
	          {
	             previous = current;
	             current = current.getNext();
	          }
	      }
	    return current.getElement();
	}
	
	/* Makes the list empty. */
	public void makeEmpty() {
		tail = head = tail.getNext();
		count = 0;
	}
	
	/* Returns an array of the list elements. If there are n elements stored in the list, 
	   the length of the returned array should be n. */
	public Object[] toArray() {
		Object[] array = new Object[count];
		LinearNode<T> current = head;
		for(int x = 0; x<count; x++){
			if(x==0){
				array[0] = head.getElement();
				current = current.getNext();
			}
			else{
				array[x] = current.getElement();
				current = current.getNext();
			}
		}
		return array;
	}
	
   public static void main( String [ ] args ) {
        UnorderedLinkedList<Name> L = new UnorderedLinkedList<Name>( );

        System.out.println("add Carver and Nordstrom and Setia.");
        Name richard = new Name("Richard","Carver");
        Name dave = new Name("David","Nordstrom");
        Name sanjeev = new Name("Sanjeev","Setia");
        L.addToFront(richard);
        L.addToRear(dave);
        L.addAfter(sanjeev,richard); // add sanjeev after richard
        System.out.println("Display List using iterator");
        for (Iterator it = L.iterator(); it.hasNext(); )
           System.out.println("   "+it.next()); 
        Object elements[] = L.toArray();
        System.out.println("Display List as array");        
        for (int i=0; i<elements.length; i++)
           System.out.println("   "+elements[i]);   
        System.out.println("Is list empty?");         	 
        System.out.println("   is empty: " + L.isEmpty());
        System.out.println("Remove Carver and Nordstrom.");
        L.remove(richard);
        L.remove(dave);
        System.out.println("Is list empty?");          
        System.out.println("   is empty: " + L.isEmpty());   
        System.out.println("Display List");
        for (Iterator it = L.iterator(); it.hasNext(); )
           System.out.println("   "+it.next());   
        System.out.println("find Setia");
        Name justLast = new Name("","Setia");	// last name only 
        Name firstAndLast = L.find(justLast);
        System.out.println("Display Setia");
        System.out.println("   "+firstAndLast);			// first and last name
        L.makeEmpty();
        System.out.println("Is list empty?");         
        System.out.println("   is empty: " + L.isEmpty());   
        System.out.println("add Carver"); 
        L.addToFront(richard);
        System.out.println("Display List");
        for (Iterator it = L.iterator(); it.hasNext(); )
           System.out.println("   "+it.next());        
        
   }	
}

// class used by main() for tests
class Name {
	private String first;
	private String last;
 	public Name(String first, String last) {
		this.first = first; this.last = last;
	}
	public String getFirst() {return first;}
	public String getLast() {return last;}
	public String toString() {
		return (first + " " + last);
	}
	public boolean equals(Object rhs) {
	// returns true if last names are equal (ignores first name)
		if ( this == rhs ) 
			return true;
		if ( !(rhs instanceof Name) ) 
			return false;
		Name m = (Name) rhs;
		return last.equals(m.getLast());
	}	  
}
