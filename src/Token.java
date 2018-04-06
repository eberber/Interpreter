import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
/*Scanner
 * Author: Edgar Berber
 * Programming Languages
 */
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
	int index = 0; // index in the list of tokens
	Token nextToken(){ // returns the token in the current index
		return list.get(index);
	}
	void consumeToken(){ // shifts the token forward
		index++;
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

	



	/* PARSER */
	class AST {// ASTs at most have 3 children (3 is used for if statements).
		// For every other statement and expression, at least one child will be null.
		Token token;AST left;AST middle;AST right;
		AST(Token t, AST l, AST m, AST r){
			this.token = t;
			this.left = l;
			this.middle = m;
			this.right = r;
		}
		//Also define getter and setter methods
	}
	class ParsingException extends Exception {} 

	AST parseStatement() throws ParsingException {
		AST tree = parseBaseStatement();
		while (nextToken().value == ";"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseBaseStatement(), null);}
		return tree;
	}
	AST parseBaseStatement() throws  ParsingException {
		AST tree;
		if (nextToken().token == "Identifier") {tree = parseAssignment();
		}
		else if (nextToken().value == "if") {
			tree = parseIfStatement();
		}
		else if (nextToken().value == "while"){
			tree = parseWhileStatement();
		}
		else if (nextToken().value == "skip") {
			tree = parseSkip();}
		else {
			throw new ParsingException();
		}
		return tree;
	}
	AST parseAssignment() throws ParsingException {
		/* check whether nextToken is an identifier 
		 *   if so, make a AST tree1 with token consisting of the identifier, 
		 *    and null children.     
		 *     next check if the next token is assignment operator.
		 *     if so make a token t2.    
		 *     next return a tree with t2 in the root, left child to be tree1,and right child to be the result of parsing expression.
		 *     otherwise generate parsing error   
		 *     otherwise generate parsing Error 
		 *     */
		AST tree;

		if(nextToken().token=="Identify") {
			Token hold =nextToken();
			tree = new AST(hold,null,null,null);
			if(nextToken().value==":=") {
				Token t2 = new Token(":=","Punctuation");
				AST tree2 = parseExpressions();
				AST tree3 = new AST(t2,tree,null, tree2);
				return tree3;
			}
			else{
				throw new ParsingException();
			} 
		}
		else{
			throw new ParsingException();
		}
	}
	private AST parseExpressions() {
		// TODO Auto-generated method stub
		return null;
	}
	AST parseIfStatement() throws ParsingException {
		//	 check whether nextToken is "if".   
		// * if so, make a token t1 for if statement, and consume the token.      
		// * next parse boolean expression and assign it to AST tree1.
		// * check if the next token is "then".
		// * if so, consume the token, parse the statement and assign the result to tree2.
		// * check if the next token is "else".
		// * if so, consume the token, parse the statement and assign the result to tree3.
		// * check if next token is "endif".
		// * if so, consume the token, and return a tree with
		// * token t1 at the root,tree1 as the left child
		// * tree2 as the middle child
		// * tree3 as the right child
		// * otherwise generate parsing error
		// * otherwise generate parsing error
		// * otherwise generate parsing error
		// * otherwise generate parsing error
		AST tree1;
		AST tree2;
		AST tree3;
		AST tree4;
		if(nextToken().value=="if") {
			Token t1 = new Token("if","Keyword"); 
			consumeToken();
			tree1 = parseBoolExpress();
			if(nextToken().value =="then") {
				consumeToken();
				tree2 = parseStatement();
				if(nextToken().value =="else"){
					consumeToken();
					tree3 = parseStatement();
					if(nextToken().value =="endif") {
						consumeToken();
						tree4 = new AST(t1, tree1,tree2,tree3);
						return tree4;
					}
					else {
						throw new ParsingException();
					}
				}
				else {
					throw new ParsingException();
				}
			}
			else {
				throw new ParsingException();
			}
		}
		else {
			throw new ParsingException();
		}
	}

	AST parseWhileStatement() throws ParsingException {

		//	 check whether next token is "while"   
		// * if so, make a token t1 for while loops, and consume the token.
		//		next parse the boolean expression and assign it to AST tree1.
		// if the next token is "do"if so, consume the token, parse the statement and assign the result to tree2.
		// check whether the next token is "endwhile"
		// if so, consume the token, and return a tree with
		// token t1 at the	 roottree1 as the left childtree2 as the middle child
		// null as the right child
		// otherwise generate parsing error
		// otherwise generate parsing error  
		// otherwise generate parsing error
		Token t1;
		AST tree1;
		AST tree2;
		AST tree3;
		if(nextToken().value=="while"){
			t1 = new Token("while","Keyword");
			consumeToken();
			tree1 = parseBoolExpress();
			if(nextToken().value=="do"){
				consumeToken();
				tree2 = parseStatement();
				if(nextToken().value=="endwhile"){
					consumeToken();
					tree3 = new AST(t1, tree1, tree2, null);
					return tree3;
				}
				else {
					throw new ParsingException();
				}
			}
			else {
				throw new ParsingException();
			}

		}
		else {
			throw new ParsingException();
		}
	}
	
	AST parseSkip() throws ParsingException {
		//	 check whether the next token is "skip"   
		// * if so, make a token t1 containing "skip" and its type (KEYWORD).
		// * return a tree with
		// * token t1 as its root
		// * null for left, middle and right children 
		//	otherwise generate parsing error
		AST tree;
		if(nextToken().getToken()=="skip") {
			Token t1 = new Token("skip", "Keyword");
			tree =new AST(t1,null,null,null);
			return tree;
		}
		else{
			throw new ParsingException();
		}

	}

	AST numElement() throws ParsingException {
		AST tree1;
		AST tree2;

		Token hold;
		if (nextToken().value == "(") {
			consumeToken();
			tree1 =parseNumExpr();
			if(nextToken().value==")"){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token =="Number"){
			hold = nextToken();
			tree2 = new AST(hold, null,null,null);
		}
		else if(nextToken().token =="Identify"){
			hold = nextToken();
			tree2 = new AST(hold, null,null,null);
		}
		else{
			throw new ParsingException();
		}
		return tree2;
	}

	private AST parseNumExpr() throws ParsingException {
		// TODO Auto-generated method stub
		AST tree = parseNumTerm();
		while (nextToken().value == "+"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseNumTerm(), null);}
		return tree;
	}

	private AST parseNumTerm() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseNumFactor();
		while (nextToken().value == "-"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseNumFactor(), null);}
		return tree;
	}

	private AST parseNumFactor() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseNumPiece();
		while (nextToken().value == "/"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseNumPiece(), null);}
		return tree;
	}

	private AST parseNumPiece() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseNumElement();
		while (nextToken().value == "*"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseNumElement(), null);}
		return tree;
	}

	private AST parseNumElement() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree1;
		Token hold;
		
		if (nextToken().value == "(") {
			consumeToken();
			tree1 =parseNumExpr();
			if(nextToken().value==")"){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token =="Number"){
			hold = nextToken();
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else if(nextToken().token =="Identify"){
			hold = nextToken();
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else{
			throw new ParsingException();
		}
		return tree1;
	}

	
	private AST parseBoolExpress() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseBoolTerm();
		if (nextToken().value == "="){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseBoolTerm(), null);
			}
		return tree;
	}

	private AST parseBoolTerm() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseBoolFactor();
		while (nextToken().value == "|"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseBoolFactor(), null);}
		return tree;
	}

	private AST parseBoolFactor() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree = parseBoolpiece();
		while (nextToken().value == "&"){
			Token t = nextToken();
			consumeToken();
			tree = new AST (t, tree, parseBoolpiece(), null);}
		return tree;
	}

	private AST parseBoolpiece() throws ParsingException{
		AST tree;
		Token temp;
		if(nextToken().value=="!"){
			
			temp = nextToken();
			consumeToken();
			tree = new AST(temp, parseBoolElem(), null, null);
		}
		else{
			tree = parseBoolElem();
		}
		
		
		return tree;
	}

	private AST parseBoolElem() throws ParsingException {
		// TODO Auto-generated method stub
		AST tree1;
		Token hold;
		
		if (nextToken().value == "(") {
			consumeToken();
			tree1 =parseBoolExpress();
			if(nextToken().value==")"){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token =="Bool"){
			hold = nextToken();
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else if(nextToken().token =="Identify"){
			hold = nextToken();
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else{
			tree1 = parseNumExpr();
			if(nextToken().value!="=="){
					throw new ParsingException();
			}
			else{
				hold = new Token(nextToken().value, nextToken().token);
				tree1 = new AST(hold, tree1, parseNumExpr(), null);
			}
			
		}
		
		return tree1;
	}

	 void printTree(AST tree){
		if(tree ==null){
			return;
		}
		//	System.out.println(tree.token);
			//printTree(tree.token, null);
			System.out.println(tree.left);
			printTree( tree.left);
			System.out.println(tree.middle);
			printTree( tree.middle);
			System.out.println(tree.right);
			printTree(tree.right);
	}
	
	public static void main(String[] args) throws IOException, ParsingException {
		// TODO Auto-generated method stub
		Token grab = null;
		System.out.println("Enter string: ");
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(System.in));

		// Reading data using readLine
		String tokenizeMe = reader.readLine();

		// Printing the read line
		System.out.println(tokenizeMe);  
		//scanner calls
		sepSpaces(split(tokenizeMe));
		printArraylist();
		//parser calls
		//grab.printTree(grab.parseStatement());
	}
	
}




