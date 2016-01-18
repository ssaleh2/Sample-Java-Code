import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 */

/**
 * @author Sameh
 *
 */
final class Node<T>{
	private Node<T> next;
	private Node<T> previous;
	private T element;
	public Node(){
		next = null;
		previous = null;
		element = null;
	}
	public Node(T elem)
	{
		next = null;
		previous = null;
		element = elem;
	}
	
	public Node<T> getNext(){
		return next;
	}

	public void setNext(Node <T> node){
		next = node;
	}
	public Node<T> getPrevious(){
		return previous;
	}

	public void setPrevious(Node<T> node){
		previous = node;
	}
	
	public T getElement(){
		return element;
	}
	
	public void setElement(T elem){
		element = elem;
	}
}



public class Number {
	private Node<String> low = new Node<String>(null);
	private Node<String> high = new Node<String>(null);
	private int digitCount = 0;
	private int decimalPlaces = 0;
	private boolean negative = false;
	
	public Number() {
		this("0");
	}
	public Number (String num){
		accept(num);
	}
	
	public void accept(String num){
		validate(num);
		int error = 0;
		int start = 1;
		boolean onedecimal = true;
		//splits input into array to form linked list
		String[] placeValue = num.split("");
		if (this.digitCount==0){
			this.digitCount = num.length();
		}
		//removes negative if it is there
		if(placeValue.length>1 && placeValue[1].equals("-")){
			this.negative = true;
			this.digitCount--;
			start = 2;
		}
		//accounts for decimal places and creates linked list representaiton
		for(int x=start; x<num.length()+1; x++){
			Node<String> node = new Node<String>(placeValue[x]);
			if ((x==1 || x ==2 || x==3) && !(placeValue[x].equals(".")) && !(placeValue[x].equals("-")) && (high.getElement() == null)){
				this.high = node;
				this.low = node;
				node.setPrevious(null);
				node.setNext(null);
			}
			else if (placeValue[x].equals(".") && onedecimal == true){
				//Number of decimal places is the numbers of characters in the string minus the values in front
				//the decimal point minus the decimal point
				this.decimalPlaces = num.length()-(x);
				this.digitCount--;
				onedecimal = false;
			}
			else if (placeValue[x].matches("[0-9]")){
				this.low.setNext(node);
				node.setNext(null);
				node.setPrevious(this.low);
				this.low  = node;
			}
			else{
				//if invalid, number equals 0
				Node<String> zero = new Node<String>("0");
				this.high = zero;
				this.low = zero;
				zero.setPrevious(null);
				zero.setNext(null);
				this.digitCount = 1;
				this.decimalPlaces = 0;
				if (error == 0){
					System.out.println("Illegal input. Number defaulted to 0.");
					error++;
				}
				
			}
		}
	}
	
	public static void validate(String num){
		//catches if number is not valid
		if(num!=""){
			try{
				Double.parseDouble(num);
			}
			catch(NumberFormatException e){
				System.err.println(num + " Caught Number Format Exception: O" + e.getMessage());
			}
		}
	}
	
	public Number add(Number n){
		Number sum;
		//uses absolute add and subtract based on what is needed for the sum with signs of two values added 
		if ((this.negative == false) && (n.negative == false)){
			sum = this.addAbsolute(n);
			sum.negative = false;
		}
		else if ((this.negative == true) && (n.negative == true)){
			sum = this.addAbsolute(n);
			sum.negative = true;
		}
		else if ((this.negative == false) && (n.negative == true)){
			if ((this.compareToAbsolute(n) == 1) || (this.compareToAbsolute(n) == 0)){
				sum = this.subtractAbsolute(n);
				sum.negative = false;
			}
			else{
				sum = this.subtractAbsolute(n);
				sum.negative = true;
			}
		}

		else if((n.negative == false) && (this.negative == true)){
			if ((this.compareToAbsolute(n) == 1) || (this.compareToAbsolute(n) == 0)){
				sum = this.subtractAbsolute(n);
				if (sum.compareToAbsolute(new Number("0")) == 0){
					sum.negative = false;
				}
				else{
					sum.negative = true;
				}
			}
			else{
				sum = this.subtractAbsolute(n);
				sum.negative = false;
			}
		}
		
		else{
			sum = this.subtractAbsolute(n); 
			sum.negative = true;
		}
		return sum;
	}
	
	public Number subtract(Number n){
		Number difference;

		//uses absolute add and subtract based on what is needed for the difference with signs of two values added
		if ((this.negative == false) && (n.negative == false)){
			if ((this.compareToAbsolute(n) == 1) || (this.compareToAbsolute(n) == 0)){
				difference = this.subtractAbsolute(n);
				difference.negative = false;
			}
			else{
				difference = this.subtractAbsolute(n);
				difference.negative = true;
			}
		}
		
		else if ((this.negative == true) && (n.negative == true)){
			if ((this.compareToAbsolute(n) == 1) || (this.compareToAbsolute(n) == 0)){
				difference = this.subtractAbsolute(n);
				if (difference.compareToAbsolute(new Number("0")) == 0){
					difference.negative = false;
				}
				else{
					difference.negative = true;
				}
			}
			else{
				difference = this.subtractAbsolute(n);
				difference.negative = false;
			}
		}
		
		else if ((this.negative == false) && (n.negative == true)){
			difference = this.addAbsolute(n);
			difference.negative = false;
		}
		
		else if((n.negative == false) && (this.negative == true)){
			difference = this.addAbsolute(n);
			difference.negative = true;
		
		}
		
		else{
			difference = this.subtractAbsolute(n); 
			difference.negative = true;
		}
		return difference;
	}
	
	public Number multiply(Number n){
		Number product = new Number("");
		Node<String> nPtr = n.high;
		Node<String> thisPtr;
		while (nPtr != null){
			Number partProd = new Number("");
			int carry = 0;
			thisPtr = this.low;
			while (thisPtr != null){
				int newDigit = carry + Integer.parseInt(thisPtr.getElement())*Integer.parseInt(nPtr.getElement());
				carry = newDigit/10;
				newDigit = newDigit % 10;
				Node<String> newNode = new Node<String> ("" + newDigit);
				if (partProd.high.getElement() == null){
					partProd.high = newNode;
					partProd.low = newNode;
					partProd.high.setPrevious(null);
					partProd.low.setNext(null);
				}
				else{
					partProd.high.setPrevious(newNode);
					newNode.setPrevious(null);
					newNode.setNext(partProd.high);
					partProd.high = newNode;
				
				}
				partProd.digitCount++;
				thisPtr = thisPtr.getPrevious();
			}
			if (carry != 0){
				Node<String> newNode = new Node<String>("" + carry);
				partProd.high.setPrevious(newNode);
				newNode.setPrevious(null);
				newNode.setNext(partProd.high);
				partProd.high = newNode;
				partProd.digitCount++;
			}
			Node<String> zeroNode = new Node<String> ("0");
			if(product.low.getElement() == null){
				product.low = zeroNode;
				product.high = zeroNode;
				product.low.setNext(null);
				product.high.setPrevious(null);
			}
			else{
				product.low.setNext(zeroNode);
				zeroNode.setNext(null);
				zeroNode.setPrevious(product.low);
				product.low = zeroNode;
			}
			product.digitCount++;
			
		product = product.addAbsolute(partProd);
		nPtr = nPtr.getNext();
		}
		
		product.decimalPlaces = this.decimalPlaces + n.decimalPlaces;
		if(((this.negative == false) && (n.negative == false)) || ((this.negative == true) && (n.negative == true))){
			product.negative = false;
		}
		else{
			product.negative = true;
		}
		return product;
	}
	
	public void reverseSign(){
		if(this.negative == true){
			this.negative = false;
		}
		else{
			this.negative = true;
		}
	}
	
	public String toString(){
		Node<String> temp = high;
		String display="";
		if (this.negative == true){
			display = "-";
		}
		int counter = 0;
            while (temp != null) {
            	if (counter == (this.digitCount - this.decimalPlaces)){
            		display += ".";
            	}
            	counter++;
                display += "" + temp.getElement();
                temp = temp.getNext();
            }
            return display;
	}
	
	private Number addAbsolute(Number n){
		
		Number sum = new Number();
		sum.high.setElement(null);
		int carry = 0;
		int newDigit = 0;
		
		//adds extra zero nodes to either current or n node so that they can align for addition
		n = this.addZeros(n);
		Node<String> thisPtr= this.low;
		Node<String> nPtr = n.low;
		//add each node from original and new to return sum
		while (thisPtr != null){
			newDigit = carry + Integer.parseInt(thisPtr.getElement()) + Integer.parseInt(nPtr.getElement());
			Node<String> newNode = new Node<String>("" + newDigit%10);
			if(sum.high.getElement() == null){
				sum.high = newNode;
				sum.low = newNode;
				newNode.setPrevious(null);
				newNode.setNext(null);
			}
			else{
				sum.high.setPrevious(newNode);
				newNode.setPrevious(null);
				newNode.setNext(sum.high);
				sum.high = newNode;
				sum.digitCount++;
			}
			carry = newDigit/10;
			thisPtr = thisPtr.getPrevious();
			nPtr = nPtr.getPrevious();
		}
			if(carry !=0){
				Node<String> newNode = new Node<String>("" + carry);
				newNode.setPrevious(newNode);
				newNode.setPrevious(null);
				newNode.setNext(sum.high);
				sum.high = newNode;
				sum.digitCount++;
			}
			sum.decimalPlaces = this.decimalPlaces;
			thisPtr = sum.high;		
			int counter = 0;
			
			//remove extra zeros from front of value
			while(sum.digitCount>1 && (thisPtr.getElement().equals("0")) && (counter!= (sum.digitCount - sum.decimalPlaces))){
				counter ++;
				sum.digitCount--;
				if(sum.high.getNext() == sum.low){
					sum.high = sum.low;
					sum.high.setPrevious(null);
					sum.low.setNext(null);
				}
				else{
					sum.high = sum.high.getNext();
					sum.high.setPrevious(null);
				}
				thisPtr = thisPtr.getNext();
			}

			//remove extra zeros from decimal value
			counter = 0;
			thisPtr = sum.low;
			while(sum.digitCount>1 && thisPtr.getElement().equals("0") && counter!= sum.decimalPlaces){
				counter ++;
				sum.decimalPlaces--;
				sum.digitCount--;
				if(sum.low.getPrevious() == sum.high){
					sum.low = sum.high;
					sum.low.setNext(null);
					sum.high.setPrevious(null);
				}
				else{
					sum.low = sum.low.getPrevious();	
					sum.low.setNext(null);
				}
			}
				return sum;
	}
	
	private Number subtractAbsolute(Number n){
		Number difference = new Number();
		difference.high.setElement(null);
		int borrow = 0;
		int newDigit = 0;
		
		//compare which number, this or n, has the bigger magnitude to determine which one goes on top
		if(this.compareToAbsolute(n)==0 || this.compareToAbsolute(n)==1){
			
			//add zeros as necessary
			n = this.addZeros(n);
			Node<String> thisPtr= this.low;
			Node<String> nPtr = n.low;
			while (thisPtr !=null){
				newDigit =  Integer.parseInt(thisPtr.getElement()) - Integer.parseInt(nPtr.getElement()) - borrow;
				if(newDigit < 0){
					newDigit += 10;
					borrow = 1;
				}
				else{
					borrow = 0;
				}
				Node<String> newNode = new Node<String>("" + newDigit);
				if(difference.high.getElement() == null){
					difference.high = newNode;
					difference.low = newNode;
					newNode.setPrevious(null);
					newNode.setNext(null);
			
				}
				else{
					difference.high.setPrevious(newNode);
					newNode.setPrevious(null);
					newNode.setNext(difference.high);
					difference.high = newNode;
					difference.digitCount++;
				}
				
				thisPtr = thisPtr.getPrevious();
				nPtr = nPtr.getPrevious();
			
			}
			difference.decimalPlaces = this.decimalPlaces;
		}
		else{
			//add zeros as necessary
			n = this.addZeros(n);
			Node<String> thisPtr= this.low;
			Node<String> nPtr = n.low;
			while (thisPtr !=null){
				newDigit =  Integer.parseInt(nPtr.getElement()) - Integer.parseInt(thisPtr.getElement()) - borrow;
				if(newDigit < 0){
					newDigit += 10;
					borrow = 1;
				}
				else{
					borrow = 0;
				}
				
				Node<String> newNode = new Node<String>("" + newDigit);
				if(difference.high.getElement() == null){
					difference.high = newNode;
					difference.low = newNode;
					newNode.setPrevious(null);
					newNode.setNext(null);
			
				}
				else{
					difference.high.setPrevious(newNode);
					newNode.setPrevious(null);
					newNode.setNext(difference.high);
					difference.high = newNode;
					difference.digitCount++;
				}
				
				thisPtr = thisPtr.getPrevious();
				nPtr = nPtr.getPrevious();
			}
			difference.decimalPlaces = this.decimalPlaces;
		}
		Node<String> thisPtr = difference.high;		
		int counter = 0;
		while(difference.digitCount>1 && (thisPtr.getElement().equals("0")) && (counter!= (difference.digitCount - difference.decimalPlaces))){
			counter ++;
			difference.digitCount--;
			if(difference.high.getNext() == difference.low){
				difference.high = difference.low;
				difference.high.setPrevious(null);
				difference.low.setNext(null);
			}
			else{
				difference.high = difference.high.getNext();
				difference.high.setPrevious(null);
			}
			thisPtr = thisPtr.getNext();
		}
		
		counter = 0;
		thisPtr = difference.low;
		while(difference.digitCount>1 && thisPtr.getElement().equals("0") && counter!= difference.decimalPlaces){
			counter ++;
			difference.decimalPlaces--;
			difference.digitCount--;
			if(difference.low.getPrevious() == difference.high){
				difference.low = difference.high;
				difference.low.setNext(null);
				difference.high.setPrevious(null);
			}
			else{
				difference.low = difference.low.getPrevious();	
				difference.low.setNext(null);
			}
			thisPtr = thisPtr.getPrevious();
		}
		return difference;
	}
	
	private int compareToAbsolute(Number n){
		//if original digits (not inc decimal places) is more than new digits (not inc decimal places)
		if((this.digitCount - this.decimalPlaces)>(n.digitCount - n.decimalPlaces)){
			return 1;
		}
		//vice versa
		else if((n.digitCount - n.decimalPlaces)>(this.digitCount - this.decimalPlaces)){
			return -1;
		}
		//compare each node of both original and new and determine if one is greater than the other
		else{
			n = this.addZeros(n);	
			Node<String> thisPtr= this.high;
			Node<String> nPtr = n.high;
			if(Integer.parseInt(thisPtr.getElement()) > Integer.parseInt(nPtr.getElement())){
				return 1;
			}
			else if(Integer.parseInt(thisPtr.getElement()) < Integer.parseInt(nPtr.getElement())){
				return -1;
			}
			
			while (Integer.parseInt(thisPtr.getElement()) == Integer.parseInt(nPtr.getElement())){
				if(Integer.parseInt(thisPtr.getElement()) > Integer.parseInt(nPtr.getElement())){
					return 1;
				}
				else if(Integer.parseInt(thisPtr.getElement()) < Integer.parseInt(nPtr.getElement())){
					return -1;
				}
				else if((thisPtr == this.low) && (this.low.getElement().equals(n.low.getElement()))){					
					return 0;
				}
				thisPtr = thisPtr.getNext();
				nPtr = nPtr.getNext();
			}
		}
		//otherwise return 2 (should not happen)
		return 2;
	}
	
	private Number addZeros(Number n){
		//Original number has more place values than new number
		if((this.digitCount - this.decimalPlaces) > (n.digitCount - n.decimalPlaces)){
			for(int x = (n.digitCount - n.decimalPlaces); x<(this.digitCount - this.decimalPlaces); x++){
				Node<String> node = new Node<String>("0");
				n.high.setPrevious(node);
				node.setPrevious(null);
				node.setNext(n.high);
				n.high  = node;
				n.digitCount++;
			}
		}
		
		//New number has more place values than original number
		else if((n.digitCount - n.decimalPlaces) > (this.digitCount - this.decimalPlaces)){
			for(int x = (this.digitCount - this.decimalPlaces); x<(n.digitCount - n.decimalPlaces); x++){
				Node<String> node = new Node<String>("0");
				this.high.setPrevious(node);
				node.setPrevious(null);
				node.setNext(this.high);
				this.high  = node;
				this.digitCount++;
				}
				
		}
		//Original number has more decimal places than new number
		if(this.decimalPlaces>n.decimalPlaces){
			for(int x = n.decimalPlaces; x<this.decimalPlaces; x++){
				Node<String> node = new Node<String>("0");
				n.low.setNext(node);
				node.setNext(null);
				node.setPrevious(n.low);
				n.low  = node;
				n.digitCount++;
			}	
			n.decimalPlaces = this.decimalPlaces;
			
		}

		//New number has more decimal places than original number
		else if(n.decimalPlaces>this.decimalPlaces){
			for(int x = this.decimalPlaces; x<n.decimalPlaces; x++){
				Node<String> node = new Node<String>("0");
				this.low.setNext(node);
				node.setNext(null);
				node.setPrevious(this.low);
				this.low  = node;
				this.digitCount++;
			}
			this.decimalPlaces = n.decimalPlaces;
		}
		return n;

	}
	
	public static void main(String[] args) throws IOException {
		Number original = new Number("0");
		Number newNumber;
		String choice = "";
		//while choice does not equal q
		while(!choice.equals("q")){
			System.out.print("enter a value: e" + "\t add: a\nsubtract:s \t\t multiply: m \nreverse sign: r \t clear: c \nquit: q\n-> ");
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			choice = in.readLine().toLowerCase();
			//if user input equals e, get new value
			if (choice.equals("e")){
				 converter = new InputStreamReader(System.in);
				 in = new BufferedReader(converter);
				 original = new Number(in.readLine());
			}
			//if a add to original value
			else if(choice.equals("a")){
				converter = new InputStreamReader(System.in);
				 in = new BufferedReader(converter);
				 try{
					 newNumber = new Number(in.readLine());
					 original = original.add(newNumber);
					 System.out.println("Sum: " + original + "\n"); 
				 }
				 catch(NumberFormatException e){
					 System.err.println("Caught Number Format Exception: S " + e.getMessage());	
				 }
				}
			//if s subtract from original value
			else if(choice.equals("s")){
				converter = new InputStreamReader(System.in);
				 in = new BufferedReader(converter);
				 try{
					 newNumber = new Number(in.readLine());
					 original = original.subtract(newNumber);
					 System.out.println("Difference: " + original + "\n");
				 }
				 catch(NumberFormatException e){
					 System.err.println("Caught Number Format Exception: D " + e.getMessage());				
				 }
			}
			//if m multiply by previous value
			else if(choice.equals("m")){
				converter = new InputStreamReader(System.in);
				 in = new BufferedReader(converter);
				 
				 try{
					newNumber = new Number(in.readLine());
					original = original.multiply(newNumber);
					System.out.println("Multiply: " + original + "\n");
				 }
				 catch(NumberFormatException e){
					 System.err.println("Caught Number Format Exception: P " + e.getMessage());
				 }
			 }
			//reverse sign of original value
			else if(choice.equals("r")){
				original.reverseSign();
				System.out.println("Value: " + original + "\n");
			}
			//clear value to 0
			else if(choice.equals("c")){
				 original = new Number("0");
				 System.out.println("Value: " + original + "\n");
			}
			//invalid option
			else{
				if(!choice.equals("q")){
					System.out.println("\nPlease enter a valid option.\n ");
				}
			}
			
		}
		}

}
