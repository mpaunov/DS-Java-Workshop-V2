package workshop;

import java.util.Iterator;

/**
 * Custom implementation of LIFO data structure
 */

public class Stack<T> implements Iterable<T> {

    private class StackIterator implements Iterator<T> {

        private Node<T> current = top;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T element = current.element;
            current = current.prev;
            return element;
        }
    }

    private static class Node<E> {
        private E element;
        private Node<E> prev;

        private Node(E element) {
            this.element = element;
        }
    }

    private Node<T> top;
    private int size;

    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.prev = top;
        top = newNode;
        size++;
    }

    public T pop() {
        ensureNotEmpty();

        T value = top.element;
        top = top.prev;
        size--;
        return value;
    }

    private void ensureNotEmpty() {
        if (top == null) {
            throw new IllegalStateException("workshop.Stack is empty!");
        }
    }

    public T peek() {
        ensureNotEmpty();
        return top.element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

}
