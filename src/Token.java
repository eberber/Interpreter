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
		System.out.println("string length: " + str1.length);
		for(int i=0;i <str1.length; i++){ //for each element of the array determine what its token is
			if(str1[i] != null) {
				System.out.println("Passing to token string: " + str1[i]);
				//tokenizeSepSpace(str1[i]);
			}
			else{ 
				 throw new IllegalArgumentException("The argument cannot be null in sepSpaces");
			}
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
				System.out.println("Match at identify");
				t= new Token(s, "Identify");
			}
			else if(isNumber(s)) {
				System.out.println("Match at Number");
				t= new Token(s, "Number");
			}
			else if(isKeyword(s)) {
				System.out.println("Match at keyword");
				t= new Token(s, "Keyword");
			}
			else if(isBoolean(s)) {
				System.out.println("Match at bool");
				t= new Token(s, "Bool");
			}
			else if(isPunctuation(s)) {
				System.out.println("Match at Punc");
				t= new Token(s, "Punctuation");
			}
			
				if(t!= null) {
					System.out.println("Made an addition to list");
					list.add(t);
					tokenizeSepSpace(string.substring(i, string.length()-1));
					break;
				}
				else if(t==null) {
					 throw new IllegalArgumentException("The argument cannot be null");
				}
			

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
		//for(int i =0; i< list.size(); i++) {
		//System.out.println("List value: " + Arrays.toString(list.toArray()));
		System.out.println("List token: " + list.get(1).getToken());
		//}
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



/*         START OF PARSER                    */

/*
class AST {// ASTs at most have 3 children (3 is used for if statements).
	// For every other statement and expression, at least one child will be null.
	Token token;AST left;AST middle;AST right;
	AST(Token t, AST l, AST m, AST r){
		this.token = t;
		this.left = l;this.middle = m;this.right = r;
	}
 Also define getter and setter methods}
class ParsingException extends Exception { } 
// this is to generate Parsing errors
ArrayList<Token> scanner() {
	ArrayList<Token> list = new ArrayList<Token> ();
	 This is where you define your scanner.   It is supposed to return a list. 
	return list;
}
ArrayList<Token> tokenList = new ArrayList<Token> (); 
//list of tokens accessible to all parsing methods
int index = 0; // index in the list of tokens
Token nextToken(){ // returns the token in the current index
	return tokenList.get(index);
}
void consumeToken(){ // shifts the token forward
	index++;
}
void printAST(AST tree){ print the resulting AST to output file 

}AST parseStatement() throws ParsingException {AST tree = parseBaseStatement();
while (nextToken().value == ";")
{Token t = nextToken();consumeToken();tree = new AST (t, tree, parseBaseStatement(), null);}
return tree;}
AST parseBaseStatement() throws  ParsingException {AST tree;
if (nextToken().type == "Identifier") {tree = parseAssignment();
}
else if (nextToken().value == "if") {
	tree = parseIfStatement();
}
else if (nextToken().value == "while"){
	tree = parseWhileStatement();
}
else if (nextToken().value == "skip") {
	tree = parseSkip();}
else {throw new ParsingException();
}
return tree;
}
AST parseAssignment() throws ParsingException {
	 check whether nextToken is an identifier 
	 *   if so, make a AST tree1 with token consisting of the identifier, 
	 *    and null children.     
	 *     next check if the next token is assignment operator.
	 *     if so make a token t2.    
	 *     next return a tree with t2 in the root, left child to be tree1,and right child to be the result of parsing expression.
	 *     otherwise generate parsing error   
	 *     otherwise generate parsing Error 
}
AST parseIfStatement() throws ParsingException {
	 check whether nextToken is "if".   
	 * if so, make a token t1 for if statement, and consume the token.      
	 * next parse boolean expression and assign it to AST tree1.
	 * check if the next token is "then".
	 * if so, consume the token, parse the statement and assign the result to tree2.
	 * check if the next token is "else".
	 * if so, consume the token, parse the statement and assign the result to tree3.
	 * check if next token is "endif".
	 * if so, consume the token, and return a tree with
	 * token t1 at the root,tree1 as the left child
	 * tree2 as the middle child
	 * tree3 as the right child
	 * otherwise generate parsing error
	 * otherwise generate parsing error
	 * otherwise generate parsing error
	 * otherwise generate parsing error
}AST parseWhileStatement() throws ParsingException {
	 check whether next token is "while"   
	 * if so, make a token t1 for while loops, and consume the token.
		next parse the boolean expression and assign it to AST tree1.
 if the next token is "do"if so, consume the token, parse the statement and assign the result to tree2.
 check whether the next token is "endwhile"
 if so, consume the token, and return a tree with
 token t1 at the	 roottree1 as the left childtree2 as the middle child
 null as the right child
 otherwise generate parsing error
 otherwise generate parsing error  
 otherwise generate parsing error
}
AST parseSkip() throws ParsingException {
	 check whether the next token is "skip"   
	 * if so, make a token t1 containing "skip" and its type (KEYWORD).
	 * return a tree with
	 * token t1 as its root
	 * null for left, middle and right children 
	otherwise generate parsing error
}
 similarly define a method to parse each of the following:
 * expressions
 * numexpressions
 * numterms
 * numfactors
 * numpieces
 * numelements
 * numbersidentifiersboolexpressionsbooltermsboolfactorsboolpiecesboolelementsbooleans






*/