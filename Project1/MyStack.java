import java.util.ArrayList;

public class MyStack<AnyType> {
    public MyStack(){
        stack = new ArrayList<>();
    }
    public void push(AnyType x) {
        stack.add(x);
    }
    public AnyType peek(){
        return stack.get(stack.size() - 1);
    }
    public AnyType pop(){
        return stack.remove(stack.size() - 1);
    }
    public boolean empty(){
        if (stack.size() == 0) return true;
        return false;
    }

    ArrayList<AnyType> stack;
}
class TestBalancingSymbol {
    public static boolean isValid(String s){
        MyStack<Character> test = new MyStack<Character>();
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == '[' || s.charAt(i) == '(' || s.charAt(i) == '{') {
                test.push(s.charAt(i));
            } else if (s.charAt(i) == ']' && !test.empty() && test.peek() == '[') {
                test.pop();
            } else if (s.charAt(i) == ')' && !test.empty() && test.peek() == '(') {
                test.pop();
            } else if (s.charAt(i) == '}' && !test.empty() && test.peek() == '{') {
                test.pop();
            } else {
                return false;
            }
        }
        return test.empty();
    }
    public static void main( String [ ] args ){
        String[] test = {"()", "()[]{}", "(]", "([)]", "{[]}", "(("};
        for(int i = 0; i < test.length; i++){
            System.out.println(isValid(test[i]));
        }
    }
}