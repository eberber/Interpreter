import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;
import java.text.NumberFormat;
import java.text.ParsePosition;
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

	private static ArrayList<Token> list = new ArrayList<Token>(); //stores tokens and their values
	public ArrayList<Token> getList() {
		if(list ==null) {
			return new ArrayList<Token>();
		}
		else 
			return list;
	}

	Token (String v, String t){
		this.value = v; //takes value of token
		this.token = t; //takes token type
	}

	static String[] split(String str1) { //cuts white spaces
		//everywhite space is a new element
		String []str2;
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
		System.out.println("string length: " + str1.length);
		for(int i=0;i <str1.length; i++){ //for each element of the array determine what its token is
			if(str1[i] != null) {
				System.out.println("Passing to token string: " + str1[i]);
				tokenizeSepSpace(str1[i]);
			}
			else{ 
				return;
				//throw new IllegalArgumentException("The argument cannot be null in sepSpaces");
			}
		}

	}

	public static void tokenizeSepSpace(String string) { //determines what the array elements token is and value to add to Token list
		// TODO Auto-generated method stub
		Token t1 =null;
		Token t2 =null;
		String s;
		for(int i=0; i < string.length(); i++) {
			t1 = null;
			System.out.println("string passed in:" + string);
			s = string.substring(0, i+1);
			System.out.println("substring:" + string.substring(0,i+1));
			System.out.println("string s:" + s);

			 if(isKeyword(s)) {
				System.out.println("Match at keyword");
				t1= new Token(s, "Keyword");
			}
			else if(isIdentify(s)) { 
				System.out.println("Match at identify");
				t1= new Token(s, "Identify");
			}
			else if(isNumber(s)) {
				System.out.println("Match at Number");
				t1= new Token(s, "Number");
			}
			else if(isBoolean(s)) {
				System.out.println("Match at bool");
				t1= new Token(s, "Bool");
			}
			else if(isPunctuation(s)) {
				System.out.println("Match at Punc");
				t1= new Token(s, "Punctuation");
			}
				if(t1!= null) {
					t2=new Token(t1.getValue(),t1.getToken());
				}
				else if(t1==null) {
					if(t2==null) {
						return;
					}
					else {
						System.out.println("Made an addition to list " + t2.value );
						list.add(t2);
						tokenizeSepSpace(string.substring(i, string.length()-1));
						break;
					}
				}
		}
		if(t1!= null) {
			list.add(t1);
		}
	}

	private static boolean isNumber(String s) {
		// TODO Auto-generated method stub
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(s, pos);
		return s.length() == pos.getIndex();
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
		switch (s){
		case "if":  return true;
		case "then": return true;
		case "else": return true;
		case "endif": return true;
		case "while": return true;
		case "do": return true;
		case "endwhile": return true;
		case "skip": return true;
		default: return false;
		}
	}

	private static boolean isBoolean(String s) {
		// TODO Auto-generated method stub
		switch (s){
		case "true":  return true;
		case "false": return true;
		default: return false;
		}
	}

	private static boolean isIdentify(String s) {
		// TODO Auto-generated method stub
		if(Character.isLetter(s.charAt(0))) { //is first char a letter
			for(int i=0; i < s.length(); i++) {
				if(Character.isLetter(s.charAt(i))  || Character.isDigit(s.charAt(i))) {
					continue;//must be letter or number
				}
				else {
					return false;
				}
			}
			return true; //if we run through loop and meets cond, return true
		}

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


