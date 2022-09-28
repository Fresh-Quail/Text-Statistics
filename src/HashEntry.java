public class HashEntry {

    //Stores the entry's key
    private String key;
    //Stores the entry's value
    private int value;

    /**
     * Assigns the key and value of the object
     */
    public HashEntry(String key, int value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
