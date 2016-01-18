import java.util.TreeSet;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;

public class MyMap<KeyType, ValueType> {
	int count = 0;
	public MyMap() {
		items = new UnorderedLinkedList<Entry<KeyType,ValueType>>(); 
	} 

	/* map key to value. Create a new (key,val) Entry and store it in the UnorderedLinkedList. */
	public void put( KeyType key, ValueType val ) { 
		//if key is not contained, add entry to map
		if(containsKey(key)== false){
			items.addToFront(new Entry<KeyType, ValueType>(key, val));
			count++;
		}
		//if key is contained, replace the value of the old key with the new value
		else if(containsKey(key) == true && count !=0){
			get(key);
			Entry<KeyType, ValueType> new_entry = new Entry<KeyType, ValueType>(key, val);
			LinearNode<Entry<KeyType, ValueType>> current = items.head;
			for(int x = 0; x < items.count; x++){
				if(((current.getElement()).equals(new_entry))){
					((Entry<KeyType, ValueType>)current.getElement()).val = val;
				}
				else
					current = current.getNext();
			}
		}
	}
	/* get the value that key is mapped to by retrieving the Entry from the UnorderedLinkedList. */
	public ValueType get( KeyType key ) {
		int counter = 0;
		LinearNode<Entry<KeyType, ValueType>> current = items.head;
		Entry<KeyType, ValueType> entry = new Entry<KeyType, ValueType>(key,null);
		while(counter < items.count){
			//search through all the entries of the map and return value if key found
			if((current.getElement()).equals(entry)){
				 return (ValueType) ((Entry) current.getElement()).getValue();
			}
			else{ 
				counter++;
				current = current.getNext();
			}
		}
		//if key is not found in map
		return null;
	} 

	/* return true if the map is empty; otherwise, return false. */
	public boolean isEmpty() { 
		return items.isEmpty();
	}

	/* make the map empty. */
	public void makeEmpty() { 
		count=0;
		items.makeEmpty();
	}

	/* return a list of Entries. (Note: Consider using method asList() in Java class Arrays.  */
	public List entryList() {
		return Arrays.asList(items.toArray());
	}
	
	/* return a list of the keys */	
	public List keyList() {
		KeyType[] key_array = (KeyType[]) new Object[count];
		int counter = 0;
		LinearNode<Entry<KeyType, ValueType>> current = items.head;
		while(counter < count){
			key_array[counter] = (KeyType) ((Entry) current.getElement()).getKey();
			current = current.getNext();
			counter++;
		}
		return Arrays.asList(key_array);
	}
	
	/* return true if the key is present; otherwise, return false */
	public boolean containsKey(KeyType key) { 
		int counter = 0;
		LinearNode<Entry<KeyType, ValueType>> current = items.head;
		Entry entry = new Entry(key,null);
		while(counter < items.count){
			if(entry.equals((current.getElement())))
				return true;
			else 
				counter++;
				current = current.getNext();
		}
		return false;
	}

	private UnorderedLinkedList<Entry<KeyType,ValueType>> items; 

	/* An Entry is a (key,value) pair */
	public static class Entry<KeyType, ValueType> {

		private KeyType key;
		private ValueType val;
		
		Entry( KeyType k, ValueType v ) {
			key=k;
			val=v;
		}

		public int hashCode() {
			return key.hashCode();
		}

		public boolean equals( Object rhs ) {
			// Two Entry objects are equal if they have the same key value
			return rhs instanceof Entry && 
			key.equals( ((Entry<KeyType, ValueType>)rhs).key ); 
		} 
		
		public KeyType getKey() {return key;}
		public ValueType getValue() {return val;}
		public String toString() {
			return (key.toString() + "," + val.toString());
		}
	}
	
   public static void main( String [ ] args ) {
        MyMap<String,String> M = new MyMap<String,String>( );

        System.out.println("Insert Carver mapped to 003 and Nordstrom.");
        M.put("Carver","CS310-003");
        M.put("Nordstrom","CS310-002");
        System.out.println("Insert Carver mapped to 001."); // overwrite 003
        M.put("Carver","CS310-001");
        System.out.println("Get Carver:");
        System.out.println("   " + M.get("Carver"));
        System.out.println("Display entries:");
        List entries = M.entryList();
        for (Iterator it = entries.iterator(); it.hasNext(); )
            System.out.println("   "+it.next());
        System.out.println("Display keys:");
        List keys = M.keyList();
        for (Iterator it = keys.iterator(); it.hasNext(); )
            System.out.println("   "+it.next());    
        System.out.println("Key Carver is present?");
        System.out.println("   "+M.containsKey("Carver"));          	 

        System.out.println("is empty: " + M.isEmpty()); 
        System.out.println("Make empty"); 
        M.makeEmpty();
        System.out.println("is empty: " + M.isEmpty()); 
        
        System.out.println("Insert Carver mapped to 002.");
        M.put("Carver","CS310-002");
        System.out.println("Get Carver:");
        System.out.println("   " + M.get("Carver"));
        System.out.println("Display entries:");
        entries = M.entryList();
        for (Iterator it = entries.iterator(); it.hasNext(); )
           System.out.println("   "+it.next()); 
   }
}
