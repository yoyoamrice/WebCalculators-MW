package edu.kirkwood.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a mathematical set. Contains a name and an array list of elements.
 * Contains methods for creating and modifying sets, as well as executing operations
 * between sets.
 */
public class Set {
    private String name;
    private ArrayList<String> elements;

    /**
     * Default constructor. Used only for testing purposes.
     */
    public Set() {
        this.name = "A";
        this.elements = new ArrayList<>();
    }

    /**
     * Parameterized constructor. Initializes a set with the name and elements provided.
     * @param name Name to refer to the set
     * @param elements ArrayList of Strings that represents the distinct elements of the set
     */
    public Set(String name, ArrayList<String> elements) {
        setName(name);
        setElements(elements);
    }

    /**
     * Returns the name of a set
     * @return name The name of the set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a set. Throws an exception for an empty name.
     * @param name Name of the set
     */
    public void setName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    /**
     * Returns the list of elements in the set.
     * @return ArrayList containing the elements of the set
     */
    public ArrayList<String> getElements() {
        return elements;
    }

    /**
     * Sets the elements for a set.
     * @param elements ArrayList of elements for the set
     */
    public void setElements(ArrayList<String> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Null elements list");
        }
        this.elements = elements;
    }

    /**
     * Adds an element to the list of elements of a set.
     * @param element String representing an element of the set
     */
    public void addElement(String element) {
        this.elements.add(element);
    }

    /**
     * Removes an element from a set.
     * @param element String representing the element to be removed from the set
     */
    public void removeElement(String element) {
        this.elements.remove(element);
    }

    /**
     * Provides set "Union" operation between the current set and a separate set. Union operation
     * combines the element of both sets with no duplicates.
     * @param other Separate set to act in union operation
     * @return New set representing the "Union" of the 2 sets
     */
    public Set union(Set other) {
        ArrayList<String> newElements = new ArrayList<>(this.elements);
        Set result = new Set("Result Set", newElements);
        for (String element : other.elements) {
            if (!newElements.contains(element)) {
                result.addElement(element);
            }
        }
        return result.sort();
    }

    /**
     * Provides set "Intersection" operation between the current set and a separate set. Intersection operation
     * retains only the elements that are contained in both original sets.
     * @param other Separate set to act in the intersection operation
     * @return New set representing the "Intersection" of the 2 sets.
     */
    public Set intersection(Set other) {
        ArrayList<String> newElements = new ArrayList<>();
        for (String element : this.elements) {
            if (other.elements.contains(element)) {
                newElements.add(element);
            }
        }
        Set result = new Set("Result Set", newElements);
        return result.sort();
    }

    /**
     * Provides set "Difference" operation between the current set and a separate set. Difference operation
     * begins by retaining only the elements from the first set, and then removes any elements contained
     * in the second set.
     * @param other Separate set to act in the difference operation
     * @return New set representing the "Difference" of the 2 sets
     */
    public Set difference(Set other) {
        ArrayList<String> newElements = new ArrayList<>(this.elements);
        Set result = new Set("Result Set", newElements);
        for (String element : other.elements) {
            result.removeElement(element);
        }

        return result.sort();
    }

    /**
     * Sorts the arraylist of elements for a set
     * @return Sorted arraylist of elements
     */
    public Set sort() {
        Collections.sort(this.elements);
        return this;
    }

    /**
     * Override of the toString method. Provides set in "Set Name: [1, 2, 3]" format.
     * @return String containing the name and elements of a set
     */
    @Override
    public String toString() {
        if (this.elements.isEmpty()) {
            return this.name + ": Empty Set";
        }
        return this.name + ": " + elements.toString();
    }

}
