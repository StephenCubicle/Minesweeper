public class GenericArrayStack<E> implements Stack<E> {
   
   // ADD YOUR INSTANCE VARIABLES HERE
    private E[] elems;
    private int top;

   // Constructor
    public GenericArrayStack( int capacity ) {
        
    // ADD YOU CODE HERE
        elems = (E[]) new Object[capacity];
        top = 0;

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        
    // ADD YOU CODE HERE
        return (top == 0);
    }

    public void push( E elem ) {
        
    // ADD YOU CODE HERE
        if (top == elems.length){
            E[] newElems;
            newElems = (E[]) new Object[elems.length + 1];
            for (int i = 0; i < elems.length; i++){
                newElems[i] = elems[i];
            }
            elems = newElems;
        }
        elems[top] = elem;
        top ++;

    }
    public E pop() {
        
    // ADD YOU CODE HERE

        top = top - 1;
        E saved = elems[top];
        elems[top] = null;

        return saved;
    }

    public E peek() {
        
    // ADD YOU CODE HERE
        return elems[top - 1];
    }
}
