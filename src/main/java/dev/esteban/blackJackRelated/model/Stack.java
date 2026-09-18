package dev.esteban.blackJackRelated.model;

public class Stack<T>
{
    private T[] stack;
    private int top_i;

    public Stack(int size)
    {
        stack = (T[]) new Object[size];
        top_i = -1;
    }

    public void push(T item)
    {
        if(top_i == stack.length - 1)
        {
            return;
        }

        top_i ++;
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
}