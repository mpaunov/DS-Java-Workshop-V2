import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SmartArray<E> implements Iterable<E> {
    private static final Integer INITIAL_CAPACITY = 8;
    private E[] elements;
    private int nextFreeIndex;
    private int iterableIndex;

    @SuppressWarnings("unchecked")
    public SmartArray() {
        this.elements = (E[]) new Object[INITIAL_CAPACITY];
        this.nextFreeIndex = 0;
        this.iterableIndex = 0;
    }

    public void add(E element) {
        assertGrowIsNeeded();
        this.elements[nextFreeIndex] = element;
        this.nextFreeIndex += 1;
    }

    @SuppressWarnings("unchecked")
    private E[] grow() {
        Object[] newElements = new Object[this.elements.length * 2];
        System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);
        return (E[]) newElements;
    }

    public E get(int index) {
        validateIndex(index);
        return this.elements[index];
    }

    public E remove(int index) {
        validateIndex(index);
        E removedElement = this.elements[index];

        if (this.nextFreeIndex - 1 - index >= 0)
            System.arraycopy(this.elements, index + 1, this.elements, index, this.nextFreeIndex - 1 - index);
        this.elements[this.nextFreeIndex - 1] = null;
        this.nextFreeIndex--;

        if (this.nextFreeIndex <= this.elements.length / 4) {
            this.elements = shrink();
        }

        return removedElement;
    }

    @SuppressWarnings("unchecked")
    private E[] shrink() {
        if (this.elements.length / 2 >= INITIAL_CAPACITY) {
            Object[] newElements = new Object[this.elements.length / 2];
            System.arraycopy(this.elements, 0, newElements, 0, this.nextFreeIndex);
            return (E[]) newElements;
        }
        return this.elements;
    }

    public boolean contains(E element) {
        boolean isFound = false;
        while (this.iterator().hasNext()) {
            if (this.iterator().next() == element) {
                isFound = true;
                break;
            }
        }
        this.ZeroIterableIndex();
        return isFound;
    }

    public void add(int firstIndex, E element) {
        validateIndex(firstIndex);
        assertGrowIsNeeded();

        if (this.nextFreeIndex - firstIndex >= 0)
            System.arraycopy(this.elements, firstIndex, this.elements, firstIndex + 1, this.nextFreeIndex - firstIndex);
        this.elements[firstIndex] = element;
        this.nextFreeIndex += 1;
    }

    public void forEach(Consumer<? super E> consumer) {
        while (this.iterator().hasNext()) {
            consumer.accept(this.iterator().next());
        }
        this.ZeroIterableIndex();
    }

    public void printAll() {
        while (this.iterator().hasNext()) {
            System.out.println(this.iterator().next());
        }
        this.ZeroIterableIndex();
    }

    public void filter(Predicate<E> predicate) {
        for (int i = 0; i < this.nextFreeIndex; i++) {
            if (!predicate.test(this.elements[i])) {
                this.remove(i);
                i--;
            }
        }
        this.ZeroIterableIndex();
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<E> comparator) {
        E[] tempArr = (E[]) new Object[this.nextFreeIndex];
        System.arraycopy(this.elements, 0, tempArr, 0, tempArr.length);
        tempArr = (E[]) Arrays.stream(tempArr).sorted(comparator).toArray(Object[]::new);
        System.arraycopy(tempArr, 0, this.elements, 0, tempArr.length);
    }

    public void swap(int fIndex, int sIndex) {
        validateIndex(fIndex);
        validateIndex(sIndex);
        E fElement = this.elements[fIndex];
        E sElement = this.elements[sIndex];
        this.elements[fIndex] = sElement;
        this.elements[sIndex] = fElement;
    }

    public int length() {
        return this.nextFreeIndex - 1;
    }

    private void validateIndex(int index) {
        if (index >= this.nextFreeIndex || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d",
                            index, this.nextFreeIndex));
        }
    }

    private void assertGrowIsNeeded() {
        if (this.nextFreeIndex >= this.elements.length) {
            this.elements = grow();
        }
    }

    private void ZeroIterableIndex() {
        this.iterableIndex = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterableIndex <= length();
            }

            @Override
            public E next() {
                return elements[iterableIndex++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        while (this.iterator().hasNext()) {
            result.append(this.iterator().next()).append("\s");
        }
        this.ZeroIterableIndex();
        return result.toString().trim();
    }
}