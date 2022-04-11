package com.dego.util.lock;

public interface Task<E> {
    E run() throws InterruptedException;
}
