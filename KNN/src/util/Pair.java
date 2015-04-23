package util;

import java.io.Serializable;

public class Pair<K, V> implements Serializable{

	private static final long serialVersionUID = 10L;
	private K element0;
    private V element1;

    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }
    
    public void setElement0(K element0){
    	this.element0 = element0;
    }

    public void setElement1(V element1){
    	this.element1 = element1;
    }
    
    public boolean equals( Pair<K,V> pair ){
    	return element0.equals(pair.getElement0()) && element1.equals(pair.getElement1());
    }
    
    public String toString(){
    	return "{"+this.element0+", "+this.element1+"}";
    }
}
