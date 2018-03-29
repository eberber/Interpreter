import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;
import java.util.*;
public class Token {

	private ArrayList<Token> list; //stores tokens and their values

	
	Token (String value, String token){
		String v = value; //takes value of token
		String t = token; //takes token type
		
	}
	
	static String[] split(String str1) { //cuts white spaces
		//everywhite space is a new element
		String []str2= null;
		str2 = new String[200];
		Scanner scan = new Scanner(str1).useDelimiter(" "); 
				// Printing the delimiter used
				System.out.println("The delimiter use is "+scan.delimiter());
				// Printing the tokenized Strings
				int i=0;
				while(scan.hasNext()){
					System.out.println(scan.next());
					str2[i] = scan.next();
					System.out.println("String 2: "+ str2[i]);

					i++;
				}
		scan.close();
		return str2;//pass to sepSpaces
	}
	
	public void sepSpaces(String[] str1) { //
		
		for(int i=0;i <str1.length; i++) { //for each element of the array determine what its token is
			tokenizeSepSpace(str1[i]);
		}
	}
	
	
	public void tokenizeSepSpace(String string) { //determines what the array elements token is and value to add to Token list
		// TODO Auto-generated method stub
		Token t =null;
		for(int i=0; i < string.length(); i++) {
			String s = string.substring(0, i);
			if(isIdentify(s)) { 
				t= new Token(s, "Identify");
			}
			else if(isNumber(s)) {
				t= new Token(s, "Number");
			}
			else if(isKeyword(s)) {
				t= new Token(s, "Keyword");
			}
			else if(isPunctuation(s)) {
				t= new Token(s, "Punctuation");
			}
			else {
				if(t!= null) {
					list.add(t);
					tokenizeSepSpace(string.substring(i, string.length()-1));
					break;
				}
				if(t==null) {
					System.out.println("Error");
				}
			}
			
		}
	}

	private boolean isPunctuation(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isKeyword(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isNumber(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isIdentify(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Enter string: ");
		 BufferedReader reader = 
                 new BufferedReader(new InputStreamReader(System.in));
      
      // Reading data using readLine
      String tokenizeMe = reader.readLine();

      // Printing the read line
      System.out.println(tokenizeMe);  
      split(tokenizeMe);
      
	}

}
