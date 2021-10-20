package ru.larina;

import java.util.List;

public class PartBuffer {
    private int position;
    public List<Part> parts;

    public PartBuffer(List<Part> parts) {
        this.parts = parts;
    }

    public Part next() {
        position++;
        return parts.get(position - 1);
    }

    public void back() {
        position--;
    }

}
