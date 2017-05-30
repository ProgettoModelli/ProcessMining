package com.rtt.cnmining;

import java.util.ArrayList;

public class Dizionario<T, V> {

    public ArrayList<T> keys;
    public ArrayList<V> values;

    public Dizionario(){
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public void add(T key, V value){
        this.keys.add(key);
        this.values.add(value);
    }

    public T getKey(int index){
        if( index < this.keys.size())
            return this.keys.get(index);
        return null;
    }

    public T getKeyByValue(V value){
        int index = this.values.indexOf(value);
        return this.keys.get(index);
    }

    public V getValueByKey(T key){
        int index = this.keys.indexOf(key);
        return this.values.get(index);
    }

    public V getValue(int index){
        if( index < this.values.size())
            return this.values.get(index);
        return null;
    }

    public int size(){
        return this.keys.size();
    }

}
