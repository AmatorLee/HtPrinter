package com.amator.htprinter.module;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by AmatorLee on 2018/4/8.
 */
@Entity
public class PrinterBean {

    @Id(assignable = true)
    public long id;

    public String name;

    public double price;

    public int sum;

    public int saleCount;

}
