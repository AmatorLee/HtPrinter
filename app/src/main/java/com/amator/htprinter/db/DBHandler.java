package com.amator.htprinter.db;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public interface DBHandler<T> {


    public void insertAll(List<T> tList);

    public void intsert(T t);

    public void updateAll(List<T> tList);

    public List<T> queryAll();

    public void deleteAll();

}
