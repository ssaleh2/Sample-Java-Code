import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Application {
	private static boolean quit = false;
	private static String choice = "";
	private static String record_string = "";
	
	//instantiate three data structures used for the country map, mountain map, and altitude tree set
	private static MyMap<String, UnorderedLinkedList<MountainRecord>> countryMap = new MyMap<String, UnorderedLinkedList<MountainRecord>>();
	private static MyMap<String, MountainRecord> mountainMap = new MyMap<String,MountainRecord>();
	private static TreeSet<MountainRecord> altitudes = new TreeSet<MountainRecord>();
	
	public static void loadFile() throws FileNotFoundException{
		   //scan file
		   Scanner scan_file = new Scanner(new File("mountains.txt"));	
		   String input ="";
		   //check if file has another line of text
		   while(scan_file.hasNextLine()){
			   input = scan_file.nextLine();
			   MountainRecord current_record = new MountainRecord();
			   //create mountain record for each line of input
			   createRecord(input, current_record);
			   
			   //add record to mountain map and altitude tree set
			   UnorderedLinkedList<MountainRecord> records = new UnorderedLinkedList<MountainRecord>();
			   mountainMap.put(current_record.name, current_record);
			   altitudes.add(current_record);
			   
			   //if the current record has a country that is not in the country map, create a new key with
			   //that country and a new value linked list and include the first mountain record for that country
			   if(countryMap.containsKey(current_record.country)==false){
				   records = new UnorderedLinkedList<MountainRecord>();
				   records.addToRear(current_record);
				   countryMap.put(current_record.country, records);
			   }
			   
			   //if the current record has a country that is already in the country map, get the linked list value 
			   //of that country key, add the record to the value list and re-add the entry with the new value list
			   //thereby replacing the old value list
			   else if(countryMap.containsKey(current_record.country)==true){
				  records = countryMap.get(current_record.country);
				  records.addToRear(current_record);
				  countryMap.put(current_record.country, records);
			   }
		   }
		   
	   }
	 
	 
	 
	private static void createRecord(String input, MountainRecord record) {
		String name = "";
		String country = "";
		Integer altitude = 0;
		String alt_string ="";
		int check_line=0;
		
		//before first # is the mountain
		while(input.charAt(check_line)!='#'){
			name+=input.charAt(check_line);
			check_line++;
		}
		check_line++;
		//between the first and second # is the name of the country
		while(input.charAt(check_line)!='#'){
		   country+=input.charAt(check_line);
		   check_line++;
		}
		check_line++;
		//between the second # and the end of the line is the altitude
		while(check_line!=(input.length())){
		   alt_string +=input.charAt(check_line);
		   check_line++;
		} 
		//get Integer value for altitude
		altitude = new Integer(alt_string);	
		
		//set mountain name, country, and altitude for the record
		record.name = name;
		record.altitude = altitude;
		record.country = country;
	}



	public static void main(String[] args) throws FileNotFoundException {
		loadFile();
		
		//scan input
		Scanner scan_input = new Scanner(System.in);
		
		//while user does not choose option "q"
		while(!quit){
			System.out.print("\nPlease choose from the following options. \n\n\t* Insert a Mountain Record - i\n\t* Display a Mountain Record - m \n\t* Display the n Highest Mountains - n \n\t* Display the Mountains in a Particular Country - c \n\t* Display a Mountain with a Given (or close to a given) Altitude - a \n\t* Quit - q\n");
			choice = scan_input.next();
			scan_input.nextLine();
			
			//insert record and add to three data structures as above
			if (choice.equals("i")){
				System.out.print("Please insert a record in the format inside the parentheses \n" +
				"\t(Name, Mount#Location#AltitudeInFeet): \n\n");
				 record_string = scan_input.nextLine();
				 MountainRecord new_record = new MountainRecord();
				 createRecord(record_string, new_record);
				 mountainMap.put(new_record.name, new_record);
			     altitudes.add(new_record);
		         if(countryMap.containsKey(new_record.country)==false){
		        	   UnorderedLinkedList<MountainRecord> records = new UnorderedLinkedList<MountainRecord>();
					   records.addToRear(new_record);
					   countryMap.put(new_record.country, records);
		         }
				 else if(countryMap.containsKey(new_record.country)==true){
					  UnorderedLinkedList<MountainRecord> records = countryMap.get(new_record.country);
					  records.addToRear(new_record);
					  countryMap.put(new_record.country, records);
				 }
			}
			
			//display record based on the mountain map
			else if(choice.equals("m")){
				System.out.print("Please enter the key (Name of Mountain): \n\n");
				String key_string = scan_input.nextLine();
				//if mountain record found
				if(mountainMap.containsKey(key_string)){
					System.out.println(mountainMap.get(key_string));
				}
				//if mountain record not found
				else{
					System.out.println("There is no mountain named " + key_string);
				}
			}
			
			//displays mountain records with the n highest altitudes using the altitude tree set
			else if(choice.equals("n")){
				System.out.print("Please enter the number of mountains to display in descending order: \n\n");
				int number = scan_input.nextInt();
				Iterator<MountainRecord> iterator = altitudes.descendingIterator();
				int counter = 0;
				//iterate in descending order for n times
				while (iterator.hasNext()&&counter<number){
					  System.out.print(iterator.next() + "\n");
					  counter++;
				  }
			}
			
			//display records in a certain country using the country map
			else if(choice.equals("c")){
				System.out.print("Please enter the desired country: \n\n");
				String country_input = scan_input.nextLine();
				//if country found
				if(countryMap.containsKey(country_input)){
					System.out.println(countryMap.get(country_input));
				}
				//if country not found
				else{
					System.out.println("There are no mountains in country " + country_input + "\n");
				}
				
			}
			
			//displays a record that has an altitude strictly less than provided altitude (or strictly greater if less is not available)
			else if(choice.equals("a")){
				System.out.print("Please enter an altitude to display a mountain with the greatest altitude strictly less than the given altitude: \n\n");
				int altitude_input = scan_input.nextInt();
				MountainRecord temp_record = new MountainRecord("","",altitude_input); 
				if(altitudes.lower(temp_record)!=null){
				System.out.println("A mountain with an altitude close to "
				+ altitude_input + " is: \n\t" + altitudes.lower(temp_record));
				}
				else{
					System.out.println("A mountain with an altitude close to "
					+ altitude_input + " is: \n\t" + altitudes.higher(temp_record));
							
				}
			}
			
			//quit
			else if(choice.equals("q")){
				quit = true;
				System.out.println("Exiting...");
			}
			
			//invalid option
			else{
				System.out.println("\nPlease enter a valid option.\n ");
			}
			
		}
		}	
	}
