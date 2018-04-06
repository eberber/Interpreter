//import java.util.ArrayList;
//
//class AST {// ASTs at most have 3 children (3 is used for if statements).
//	// For every other statement and expression, at least one child will be null.
//	Token token;AST left;AST middle;AST right;
//	AST(Token t, AST l, AST m, AST r){
//		this.token = t;
//		this.left = l;this.middle = m;this.right = r;
//		}
//	//Also define getter and setter methods
//	}
//class ParsingException extends Exception {} 
//// this is to generate Parsing errors
//ArrayList<Token> scanner() {
//	ArrayList<Token> list = new ArrayList<Token> ();
//	//This is where you define your scanner.   It is supposed to return a list. 
//			return list;
//}
//ArrayList<Token> tokenList = new ArrayList<Token> (); 
////list of tokens accessible to all parsing methods
//int index = 0; // index in the list of tokens
//Token nextToken(){ // returns the token in the current index
//	return tokenList.get(index);
//}
//void consumeToken(){ // shifts the token forward
//	index++;
//}
//void printAST(AST tree){ //print the resulting AST to output file 
//
//}AST parseStatement() throws ParsingException {AST tree = parseBaseStatement();
//while (nextToken().value == ";")
//{Token t = nextToken();consumeToken();tree = new AST (t, tree, parseBaseStatement(), null);}
//return tree;}
//AST parseBaseStatement() throws  ParsingException {AST tree;
//if (nextToken().type == "Identifier") {tree = parseAssignment();
//}
//else if (nextToken().value == "if") {
//	tree = parseIfStatement();
//}
//else if (nextToken().value == "while"){
//	tree = parseWhileStatement();
//}
//else if (nextToken().value == "skip") {
//	tree = parseSkip();}
//else {throw new ParsingException();
//}
//return tree;
//}
//AST parseAssignment() throws ParsingException {
//	/* check whether nextToken is an identifier 
//	 *   if so, make a AST tree1 with token consisting of the identifier, 
//	 *    and null children.     
//	 *     next check if the next token is assignment operator.
//	 *     if so make a token t2.    
//	 *     next return a tree with t2 in the root, left child to be tree1,and right child to be the result of parsing expression.
//	 *     otherwise generate parsing error   
//	 *     otherwise generate parsing Error 
//	 *     */
//}
//AST parseIfStatement() throws ParsingException {
//	//	 check whether nextToken is "if".   
//	// * if so, make a token t1 for if statement, and consume the token.      
//	// * next parse boolean expression and assign it to AST tree1.
//	// * check if the next token is "then".
//	// * if so, consume the token, parse the statement and assign the result to tree2.
//	// * check if the next token is "else".
//	// * if so, consume the token, parse the statement and assign the result to tree3.
//	// * check if next token is "endif".
//	// * if so, consume the token, and return a tree with
//	// * token t1 at the root,tree1 as the left child
//	// * tree2 as the middle child
//	// * tree3 as the right child
//	// * otherwise generate parsing error
//	// * otherwise generate parsing error
//	// * otherwise generate parsing error
//	// * otherwise generate parsing error
//}AST parseWhileStatement() throws ParsingException {
//	//	 check whether next token is "while"   
//	// * if so, make a token t1 for while loops, and consume the token.
//	//		next parse the boolean expression and assign it to AST tree1.
//	// if the next token is "do"if so, consume the token, parse the statement and assign the result to tree2.
//	// check whether the next token is "endwhile"
//	// if so, consume the token, and return a tree with
//	// token t1 at the	 roottree1 as the left childtree2 as the middle child
//	// null as the right child
//	// otherwise generate parsing error
//	// otherwise generate parsing error  
//	// otherwise generate parsing error
//}
//AST parseSkip() throws ParsingException {
//	//	 check whether the next token is "skip"   
//	// * if so, make a token t1 containing "skip" and its type (KEYWORD).
//	// * return a tree with
//	// * token t1 as its root
//	// * null for left, middle and right children 
//	//	otherwise generate parsing error
//}
//// similarly define a method to parse each of the following:
//// * expressions
//// * numexpressions
//// * numterms
//// * numfactors
//// * numpieces
//// * numelements
//// * numbersidentifiersboolexpressionsbooltermsboolfactorsboolpiecesboolelementsbooleans
//
//
//
//
//
//
