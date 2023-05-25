package com.gameForum.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfo<T> {
    Integer pages;

    Integer total;

    Integer size;

    Integer current;

    List<T> records = new ArrayList<T>();

}
