package ru.larina;


public class Part {
   public Type type;
   public String value;

    public Part(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Part(Type type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

}
