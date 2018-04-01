import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;
import java.util.*;
public class Token {

	String value;
	String token;
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	private static ArrayList<Token> list; //stores tokens and their values
	public ArrayList<Token> getList() {
		return list;
	}

	Token (String v, String t){
		 value = v; //takes value of token
		 token = t; //takes token type
	}
	
	static String[] split(String str1) { //cuts white spaces
		//everywhite space is a new element
		String []str2= null;
		str2 = new String[200];
		Scanner scanner = new Scanner(str1).useDelimiter("\\s"); 
		// Printing the delimiter used
		System.out.println("The delimiter use is "+scanner.delimiter());
		// Printing the tokenized Strings
		int i=0;
		while (scanner.hasNext()) {
			str2[i] = scanner.next();
			System.out.println("Element: " + i + " Content: " + str2[i] + "\n");
			i++;
		}
		scanner.close();
		return str2;//pass to sepSpaces
	}

	public static void sepSpaces(String[] str1) { //
		for(int i=0;i <str1.length; i++) { //for each element of the array determine what its token is
			if(str1[i] != null) {
				System.out.println("Passing to token string: " + str1[i]);
				tokenizeSepSpace(str1[i]);
			}
			else System.out.println("Reached null");

		}


	}


	public static void tokenizeSepSpace(String string) { //determines what the array elements token is and value to add to Token list
		// TODO Auto-generated method stub
		Token t =null;
		String s;
		for(int i=0; i < string.length(); i++) {
			System.out.println("string passed in:" + string);
			s = string.substring(0, i+1);
			System.out.println("substring:" + string.substring(0, i));
			System.out.println("string s:" + s);

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
				System.out.println("Match at Punc");
				t= new Token(s, "Punctuation");
			}
			else {
				if(t!= null) {
					list.add(t);
					tokenizeSepSpace(string.substring(i, string.length()-1));
					break;
				}
				if(t==null) {
					System.out.println("Error 1");
				}
			}

		}
	}

	private static boolean isPunctuation(String s) {
		// TODO Auto-generated method stub
		switch (s){
		case "+":  return true;
		case "-": return true;
		case "*": return true;
		case "/": return true;
		case "&": return true;
		case "|": return true;
		case "!": return true;
		case "(": return true;
		case ")": return true;
		case "=": return true;
		case ":": return true;
		case ";": return true;
		case "\n":return true;
		default: return false;
		}
	}

	private static boolean isKeyword(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isNumber(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isIdentify(String s) {
		// TODO Auto-generated method stub
		return false;
	}
	private static void printArraylist(){
		for(int i =0; i< list.size(); i++) {
			System.out.println("List value: " + list.get(i).getValue());
			System.out.println("List token: " + list.get(i).getToken());
		}
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

		sepSpaces(split(tokenizeMe));
		printArraylist();
	}
}
