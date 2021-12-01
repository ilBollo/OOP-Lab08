package it.unibo.oop.lab.mvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 
 *
 */
public final class SimpleController implements Controller {
    private final List<String> stringStory = new LinkedList<>();
    private String nextString;

    @Override
    public void setNextString(final String nextString) {
        this.nextString = Objects.requireNonNull(nextString, "This method not accept null");
    }

    @Override
    public String getNextStringToPrint() {
        return this.nextString;
    }
    @Override
    public List<String> getHistory() {
        return stringStory;
    }

    @Override
    public void printCurrentString() {
        if (this.nextString == null) { 
            throw new IllegalStateException("There is no string set");
        }
        stringStory.add(this.nextString);
        System.out.println(this.nextString);
    }
}

