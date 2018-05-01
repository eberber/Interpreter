import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
/* Interpreter
 * Author: Edgar Berber
 * 
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
			//System.out.println("string s:" + s);

			if(isKeyword(s)) {
				System.out.println("Match at keyword");
				t1= new Token(s, "Keyword");
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
			else if(isIdentify(s)) { 
				System.out.println("Match at identify");
				t1= new Token(s, "Identify");
			}
			if(t1!= null) {
				t2=new Token(t1.getValue(),t1.getToken());
			}
			else { // t1 is null
				if(t2==null) { System.out.println("Helllo" + s);
				if (s.compareTo(":") != 0) return;
				}
				else {
					System.out.println("Made an addition to list " + t2.value );
					list.add(t2);
					tokenizeSepSpace(string.substring(i, string.length()));
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
		case "==": return true;
		case ":=": return true;
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


	/**************************** PARSER *********************************/
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
		System.out.println("token in parse statement " + nextToken().value);

		AST tree = parseBaseStatement();

		if(index < list.size()) {
			while (index < list.size() && nextToken().value.compareTo(";") == 0){ 
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseBaseStatement(), null);
			}
		}
		return tree;
	}
	AST parseBaseStatement() throws  ParsingException {
		AST tree;
		System.out.println("ParseBaseStatement " + nextToken().value);
		if (nextToken().token.compareTo("Identify")==0) {
			tree = parseAssignment();
		}
		else if (nextToken().value.compareTo("if") == 0) {
			tree = parseIfStatement();
		}
		else if (nextToken().value.compareTo("while") == 0){
			tree = parseWhileStatement();
		}
		else if (nextToken().value.compareTo("skip") ==  0) {
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
		System.out.println("Token in parseAssign " + nextToken().value);
		if(nextToken().token=="Identify") {
			Token hold =nextToken();
			tree = new AST(hold,null,null,null);
			consumeToken();
			System.out.println("Token in parseAssign after consume " + nextToken().value);
			if(nextToken().value.compareTo(":=") == 0) {
				Token t2 = new Token(":=","Punctuation");
				consumeToken();
				AST tree2 = parseExpressions();
				AST tree3 = new AST(t2,tree,tree2, null);
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
	AST parseExpressions() throws ParsingException {
		// TODO Auto-generated method stub
		AST tree;
		int temp = index;
		System.out.println("ParseExpressions " + nextToken().value);
		try {
			tree = parseNumExpr(); 
			System.out.println("TREE TOKEN VAL " + tree.token.value);
			if(index < list.size()) {
				if(nextToken().value.compareTo("&") ==0 || 
						nextToken().value.compareTo("|") ==0  
						|| nextToken().value.compareTo("=") ==0 || 
						nextToken().value.compareTo("==") ==0 )
				{
					throw new ParsingException();
				}
			}
		} catch (Exception e) {
			System.out.println("+++" + e.getStackTrace());
			index = temp; 
			tree = parseBoolExpress();
		}
		return tree;
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
		if(nextToken().value.compareTo("if")== 0 ) {
			Token t1 = new Token("if","Keyword"); 
			consumeToken();
			tree1 = parseBoolExpress();
			if(nextToken().value.compareTo("then") == 0) {
				consumeToken(); System.out.println("Then consumed: " + nextToken().value);
				tree2 = parseStatement();
				System.out.println("Value before else "+ nextToken().value);
				if(nextToken().value.compareTo("else") == 0){
					consumeToken();	
					tree3 = parseStatement();
					System.out.println("Value before endif "+ nextToken().value);
					if(nextToken().value.compareTo("endif") == 0) {
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
				System.out.println("******** " + nextToken().value);
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
		if(nextToken().value.compareTo("while") ==0){
			t1 = new Token("while","Keyword");
			consumeToken();
			tree1 = parseBoolExpress();
			if(nextToken().value.compareTo("do") ==0 ){
				consumeToken();
				tree2 = parseStatement();
				if(nextToken().value.compareTo("endwhile") ==0 ){
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
		System.out.println("ParseSkip token: "  + nextToken().getValue());
		if(nextToken().getValue().compareTo("skip")== 0) {
			Token t1 = new Token("skip", "Keyword");
			tree =new AST(t1,null,null,null);
			consumeToken();
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
		if (nextToken().value.compareTo("(") ==0) {
			consumeToken();
			tree1 =parseNumExpr();
			if(nextToken().value.compareTo(")") ==0){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token.compareTo("Number") ==0 ){
			hold = nextToken();
			tree2 = new AST(hold, null,null,null);
		}
		else if(nextToken().token.compareTo("Identify") ==0){
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
		System.out.println("Next token in parse num expr "+ nextToken().value);	
		AST tree = parseNumTerm();
		if(index < list.size()) {
			while (index < list.size() && nextToken().value.compareTo("+") == 0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseNumTerm(), null);
			}
		}
		return tree;
	}

	private AST parseNumTerm() throws ParsingException{
		// TODO Auto-generated method stub
		System.out.println("Next toke in parsenumterm " + nextToken().value);

		AST tree = parseNumFactor();
		if(index < list.size()) {
			while (index< list.size() && nextToken().value.compareTo("-") == 0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseNumFactor(), null);
			}
		}
		return tree;
	}

	private AST parseNumFactor() throws ParsingException{
		// TODO Auto-generated method stub
		System.out.println("next toke on num factr " + nextToken().value);

		AST tree = parseNumPiece();
		if(index < list.size()) {
			while (index < list.size() && nextToken().value.compareTo("/") == 0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseNumPiece(), null);
			}
		}
		return tree;
	}

	private AST parseNumPiece() throws ParsingException{
		// TODO Auto-generated method stub
		System.out.println("next token in parse num piece " + nextToken().value);

		AST tree = parseNumElement();

		if(index < list.size()) {
			while (index < list.size() && nextToken().value.compareTo("*") ==0 ){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseNumElement(), null);
			}
		}
		System.out.println("TREE TOKEN  " + tree.token.value);
		return tree;
	}

	private AST parseNumElement() throws ParsingException{
		// TODO Auto-generated method stub
		AST tree1;
		Token hold;
		System.out.println("Parse num element toke " + nextToken().value);
		if (nextToken().value.compareTo("(") == 0) {
			consumeToken();
			tree1 =parseNumExpr();

			if(nextToken().value.compareTo(")") ==0){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token.compareTo("Number") == 0){
			System.out.println("is this a num");
			hold = nextToken();
			System.out.println("HOLD : " +hold.value);
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else if(nextToken().token.compareTo("Identify") ==0){
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
		if(index < list.size()) {
			if (nextToken().value.compareTo("=") == 0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseBoolTerm(), null);
			}
		}
		return tree;	
	}

	private AST parseBoolTerm() throws ParsingException{
		// TODO Auto-generated method stub
		System.out.println("next toke in parse bool term " + nextToken().value);
		AST tree = parseBoolFactor();
		if(index < list.size()) {
			while (index < list.size() && nextToken().value.compareTo("|") ==0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseBoolFactor(), null);
			}
		}
		return tree;
	}

	private AST parseBoolFactor() throws ParsingException{
		// TODO Auto-generated method stub
		System.out.println("next toke in parse bool factor " + nextToken().value);
		AST tree = parseBoolpiece();
		//System.out.println("next toke in parse bool factor before loop " + nextToken().value);
		if(index < list.size()) {
			//System.out.println("next toke in parse bool factor " + nextToken().value);
			while (index < list.size() && nextToken().value.compareTo("&") == 0){
				Token t = nextToken();
				consumeToken();
				tree = new AST (t, tree, parseBoolpiece(), null);
			}
		}
		return tree;
	}

	private AST parseBoolpiece() throws ParsingException{
		AST tree;
		Token temp;
		System.out.println("Next toke in parseboolPiece " + nextToken().value);
		if(nextToken().value.compareTo("!") == 0){

			temp = nextToken();
			consumeToken();
			tree = new AST(temp, parseBoolElem(), null, null);
		}
		else{
			System.out.println("here in none-! boolpiece");
			tree = parseBoolElem();
		}


		return tree;
	}

	private AST parseBoolElem() throws ParsingException {
		// TODO Auto-generated method stub
		AST tree1 = null;
		Token hold;
		System.out.println("Parse bool elem toke " + nextToken().value);
		if (nextToken().value.compareTo("(") == 0) {
			consumeToken();
			tree1 =parseBoolExpress();
			if(nextToken().value.compareTo(")") == 0){
				consumeToken();
				return tree1;
			}
			else{
				throw new ParsingException();
			}
		}
		else if(nextToken().token.compareTo("Bool")  == 0){
			hold = nextToken();
			tree1 = new AST(hold, null,null,null);
			consumeToken();
		}
		else if(nextToken().token.compareTo("Identify") == 0){
			int temp = index;
			try {
				tree1 = parseNumExpr();
				if(nextToken().value.compareTo("==") != 0) {
					throw new Exception();
				}
				hold = new Token(nextToken().value, nextToken().token);
				consumeToken();
				tree1 = new AST(hold, tree1, parseNumExpr(), null);
			}
			catch(Exception e) { 
				index = temp;
				hold = nextToken();
				tree1 = new AST(hold, null,null,null);
				consumeToken();
			}
		}
		return tree1;
	}
	// ast that parser builds
	AST ast;
	void printTree(AST tree, int indent){
		if(tree ==null){
			return;
		}
		for (int i = 0; i < indent; i++) 
			System.out.print(" ");
		System.out.println(tree.token.getToken() + " " + tree.token.getValue());
		printTree( tree.left, indent+1);
		printTree( tree.middle, indent+1);
		printTree(tree.right, indent+1);
		ast = tree; //final tree stored globally	
	}


	/********************* EVALUATOR ****************************/


	// memory can be modeled as a map from identifiers to values (both represented as strings)
	Map<String, String> memory = new HashMap<String, String>();


	void printMemory() {
		System.out.println("Inside Memory Print");
		Iterator it = memory.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	// this method finds the middle sibling of a left subtree
	AST findMiddleSibling(AST ast, AST tree) {
		/*
				if ast is the same as tree then return null
				if ast.left is the same as tree then return ast.middle
				else call findMiddleSibling(ast.left, tree)
		 */
		if(ast == tree) {
			return null;
		}
		if(ast.left == tree) {
			return ast.middle;
		}
		else {
			return ast = findMiddleSibling(ast.left, tree);

		}

	}


	// this method is to find the subtree within ast that needs to be evaluated
	// this subtree must refer to a statement
	AST findStatementToBeEvaluated(AST tree) {
		/*
				if tree.token.value is ";" and tree.left is not null then 
					call findStatementToBeEvaluated(tree.left)
				else if tree.token.value is ";" and tree.left is null (which means middle and right are also null) then
					 nullify the subtree in ast that refers to tree and return null
				else if tree.token.value is not ";" (i.e., it is either ":=", "if", "skip", or "while") then 
					return tree.
		 */
		if(tree.token != null) {
			System.out.println("findStatementToBeEvaluated token: \n" + tree.token.value);
		}
		if(tree.left!= null) {
			System.out.println("findStatementToBeEvaluated tree left: \n" + tree.left.token.value);
		}
		if(tree.middle != null) {
			System.out.println("findStatementToBeEvaluated tree middle: \n" + tree.middle.token.value);
		}
		if(tree.right != null) {
			System.out.println("findStatementToBeEvaluated tree right: \n" + tree.right.token.value);
		}
		if(tree.token.value.compareTo(";") == 0 && tree.left != null) {
			findStatementToBeEvaluated(tree.left);
		}
		else if(tree.token.value.compareTo(";") == 0 && tree.left == null) {
			ast = null;
			return null;
		}
		else if (tree.token.value.compareTo(";") != 0) {
			return tree;
		}
		return null;
	}

	// this method is to evaluate expressions
	void evalExp(AST tree) {
		/*
				set tree1 to tree.
				if tree.token.type is "number" or "boolean" then return
				else if tree.token.type is "identifier" then 
					set tree to new AST(new Token(v,t), null, null, null) where
						v is the mapped value for tree.token.value in the memory and
						t is either "number" or boolean based on what v is

				else if tree.token.value is either "+", "-", "*", "/", or "==" then
					if tree.left.token.type is "number" and tree.middle.token.type is "number" then
						convert tree.left.token.value to an int and call it arg1
						convert tree.middle.token.value to an int and call it arg2
						apply tree.token.value to arg1 and arg2 and name it result
						set tree to new AST(new Token(result, "number"), null, null, null)
					else if tree.left.token.type is not "number" then
						call evalExp(tree.left)
					else call evalExp(tree.middle)

				else if tree.token.value is either "&", "|", or "=" then
					if tree.left.token.type is "boolean" and tree.middle.token.type is "boolean" then
						convert tree.left.token.value to a boolean and call it arg1
						convert tree.middle.token.value to a boolean and call it arg2
						apply tree.token.value to arg1 and arg2 and name it result
						set tree to new AST(new Token(result, "boolean"), null, null, null)
					else if tree.left.token.type is not "boolean" then
						call evalExp(tree.left)
					else call evalExp(tree.middle)

				else if tree.token.value is "!" then
					if tree.left.token.type is "boolean" then
						convert tree.left.token.value to a boolean and call it arg
						apply tree.token.value to arg and name it result
						set tree to new AST(new Token(result, "boolean"), null, null, null)
					else call evalExp(tree.left)

				set the subtree in ast that refers to tree1 to tree.
		 */



		/*	set tree1 to tree.
		if tree.token.type is "number" or "boolean" then return
				else if tree.token.type is "identifier" then 
				set tree to new AST(new Token(v,t), null, null, null) where
				v is the mapped value for tree.token.value in the memory and
				t is either "number" or boolean based on what v is
		 */

		/*AST tree1;
		tree1 = tree;
		if(tree.token.token.compareTo("Number") ==0 ||
				tree.token.token.compareTo("Bool") ==0 ) {
			return;
		}
		else if(tree.token.token.compareTo("Identify") ==0) {
			tree = new AST(new Token(v,t) , null,null,null);
		}


		 */


	}
	void evalAssignment(AST evaluateMe) {
		/*evalExp(statementToBeEvaluated.middle)
	if memory already has a map for key statementToBeEvaluated.left.token.value then 
		update the mapped value with statementToBeEvaluated.middle.token.value
	else define a new map in memory with key statementToBeEvaluated.left.token.value and 
		value statementToBeEvaluated.middle.token.value
	findMiddleSibling(ast, statementToBeEvaluated) and name it sibling
	set the subtree in ast that refers to statementToBeEvaluated to sibling
	set the subtree in ast that referes to sibling to null */
		evalExp(evaluateMe.middle);
		if(memory.containsKey(evaluateMe.left.token.value)) {
			memory.put(evaluateMe.left.token.value, evaluateMe.middle.token.value);
		}
		else {
			Map<String, String> temp = new HashMap<String, String>();
			temp.put(evaluateMe.left.token.value, evaluateMe.middle.token.value);			
		}
		AST sibling = findMiddleSibling(ast, evaluateMe);


	}
	void evalStatementToBeEvaluated(AST statementToBeEvaluated){
		/*
				if statementToBeEvaluated.token.value is ":=" then
					evalExp(statementToBeEvaluated.middle)
					if memory already has a map for key statementToBeEvaluated.left.token.value then 
						update the mapped value with statementToBeEvaluated.middle.token.value
					else define a new map in memory with key statementToBeEvaluated.left.token.value and 
						value statementToBeEvaluated.middle.token.value
					findMiddleSibling(ast, statementToBeEvaluated) and name it sibling
					set the subtree in ast that refers to statementToBeEvaluated to sibling
					set the subtree in ast that referes to sibling to null

				else if statementToBeEvaluated.token.value is "if" then	
					evalExp(statementToBeEvaluated.left)
					if statementToBeEvaluated.left.token.value is "true" then 
						set the subtree in ast that refers to statementToBeEvaluated to statementToBeEvaluated.middle
					else set the subtree in ast that refers to statementToBeEvaluated to statementToBeEvaluated.right

				else if statementToBeEvaluated.token.value is "while" then
					clone statementToBeEvaluated.left to a new tree, called whileConditionExpression
					evalExp(whileConditionExpression)
					if whileConditionExpression.token.value is "false" then
						findMiddleSibling(ast, statementToBeEvaluated) and name it sibling
						set the subtree in ast that refers to statementToBeEvaluated to sibling
						set the subtree in ast that referes to sibling to null
					else set the subtree in ast that refers to statementToBeEvaluated to 
						new AST (new Token(";","punctuation"), statementToBeEvaluated.middle,statementToBeEvaluated, null)
				else if statementToBeEvaluated.token.value is "skip" then
					findMiddleSibling(ast, statementToBeEvaluated) and name it sibling
					set the subtree in ast that refers to statementToBeEvaluated to sibling
					set the subtree in ast that referes to sibling to null
		 */
	}


	// this is the main evaluator method
	void evaluateAST() {
		//while(ast != null) {
		System.out.println("Inside evaluateAST");
		AST statementToBeEvaluated = findStatementToBeEvaluated(ast);
		if (statementToBeEvaluated != null){
			evalStatementToBeEvaluated(statementToBeEvaluated);
		}
		//}
	} 

	public static void main(String[] args) throws IOException, ParsingException {
		// TODO Auto-generated method stub
		Token grab = new Token("", "");
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
		System.out.println("AST: ");
		grab.printTree(grab.parseStatement() , 0);
		//evaluator
		grab.evaluateAST();	
		//grab.printMemory();
	}
}