//: generics/Holder.java
public class Holder<T> {
    private T value;

    public Holder() {

    }

    public Holder(T val) {
        value = val;
    }

    public void set(T val) {
        value = val;
    }

    public T get() {
        return value;
    }

    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    public static  void main(String[] args) {

    }

}