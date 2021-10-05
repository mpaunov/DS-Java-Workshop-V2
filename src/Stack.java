import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Stack {
    private Node top;
    private int size;

    private static class Node {
        private int element;
        private Node prev;

        private Node(int element) {
            this.element = element;
        }
    }

    public Stack() {
    }

    public void push(int element) {
        Node newNode = new Node(element);
        if (this.top != null) {
            newNode.prev = this.top;
        }
        this.top = newNode;
        this.size++;
    }

    public int peek() {
        ensureNotEmpty();
        return this.top.element;
    }

    public int pop() {
        ensureNotEmpty();

        int popped = this.top.element;

        this.top = this.top.prev;
        this.size--;

        return popped;
    }

    private void ensureNotEmpty() {
        if (this.top == null) {
            throw new NoSuchElementException("Empty Stack");
        }
    }


    public int size() {
        return this.size;
    }

    public void forEach(Consumer<Integer> consumer) {
        Node current = this.top;

        while (current != null) {
            consumer.accept(current.element);
            current = current.prev;
        }
    }
}
