package dev.esteban.blackJackRelated.model;

public class Stack<T>
{
    private T[] stack;
    private int top_i;
    private int size;

    public Stack(int capacity)
    {
        stack = (T[]) new Object[capacity];
        top_i = -1;
        size = 0;
    }

    public void push(T item)
    {
        if(top_i == stack.length - 1)
        {
            return;
        }

        top_i ++;
        size ++;
        stack[top_i] = item;
    }

    public T pop()
    {

        if(isEmpty())
        {
            return null;
        }

        T temp = stack[top_i];
        stack[top_i] = null;
        top_i --;
        size --;
        return temp;
    }

    public T peek()
    {
        if(isEmpty())
        {
            return null;
        }
        else
        {
            return stack[top_i];
        }
    }

    public boolean isEmpty()
    {
        if(top_i == -1)
        {
            return true;
        }
        return false;
    }

    public int getSize()
    {
        return size;
    }
}