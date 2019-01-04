public class StackDemo{
	public static void main(String[] args){
		GenArrayStack<Integer> stack = new GenArrayStack<>(Integer.class, 5);
        stack.push(5);
        stack.push(6);
        System.out.println(stack.peek());
        stack.pop();
        System.out.println(stack.peek());
        stack.pop();
        System.out.println(stack.peek());
	}
}