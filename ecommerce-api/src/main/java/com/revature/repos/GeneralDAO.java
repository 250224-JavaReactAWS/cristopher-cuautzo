package com.revature.repos;

import java.util.List;

public interface GeneralDAO <T>{
    T create(T obj);
    T update(T obj);
    T getById(int id);
    List<T> getAll();
    boolean deleteById(int id);
}
