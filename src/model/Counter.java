package model;

public class Counter implements AutoCloseable{
    private int count = 0;

    public void addCount() {
        count++;
        System.out.printf("Animals count: %d\n", count);
    }

    @Override
    public void close() {
        System.out.println("Counter was closed");
    }
}