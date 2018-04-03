
public class AST {


	// ASTs at most have 3 children (3 is used for if statements).
	// For every other statement and expression, at least one child will be null.
	Token token;
	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public AST getLeft() {
		return left;
	}

	public void setLeft(AST left) {
		this.left = left;
	}

	public AST getMiddle() {
		return middle;
	}

	public void setMiddle(AST middle) {
		this.middle = middle;
	}

	public AST getRight() {
		return right;
	}

	public void setRight(AST right) {
		this.right = right;
	}

	AST left;
	AST middle;
	AST right;
	AST(Token t, AST l, AST m, AST r){
		this.token = t;
		this.left = l;
		this.middle = m;
		this.right = r;
	}
	/* Also define getter and setter methods*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

} class ParsingException extends Exception { 
	
} // this is to generate Parsing errors
