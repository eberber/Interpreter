import java.util.ArrayList;
import java.lang.Object;
public class Token {

	private ArrayList<Token> list; //stores tokens and their values

	
	Token (String value, String token){
		String v = value; //takes value of token
		String t = token; //takes token type
		
	}
	
	String[] split(String str1) { //cuts white spaces
		//everywhite space is a new element
		String []str2 = null;
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
			if(isIdentify()) { 
				t= new Token(s, "Identify");
			}
			else if(isNumber()) {
				t= new Token(s, "Number");
			}
			else if(isKeyword()) {
				t= new Token(s, "Keyword");
			}
			else if(isPunctuation()) {
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
