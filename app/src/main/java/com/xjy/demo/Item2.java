package com.xjy.demo;

import com.xjy.jymultipleadapter.ItemType;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 15:43
 * FIXME
 */
public class Item2 implements ItemType {



    @Override
    public boolean canSwipe() {
        return false;
    }

    @Override
    public boolean canMove() {
        return false;
    }
}