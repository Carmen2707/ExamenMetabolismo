package com.example.examenmetabolismo;

import java.util.ArrayList;

public interface DAO<T> {
    public ArrayList<T> getAll();

    public T get(Long id);

    public T save(T data);
}
