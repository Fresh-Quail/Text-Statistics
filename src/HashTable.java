public class HashTable {

    /** Stores values and represents a hash table */
    private HashEntry[] hashTable;

    /** Stores the number of elements in the table */
    private int numItems = 0;

    /**
     * Initializes a HashTable with a default size
     */
    public HashTable()
    {
        hashTable = new HashEntry[1024];
    }

    /**
     * Initializes a HashTable with a given size
     */
    public HashTable(int size)
    {
        hashTable = new HashEntry[size];
    }

    /**
     *Stores the given key-value pair in the hash table
     */
    public void put(String key, int value)
    {
        //Rehashes the table if the load factor becomes too large
        if(((double)numItems/hashTable.length) >= 0.5)
        {
            //Represents the rehashed table
            HashEntry[] table = new HashEntry[hashTable.length * 2];

            //Rehashes each hashEntry into a new table
            for (HashEntry hashEntry : hashTable)
            {
                if (hashEntry != null)
                {
                    int j = 0;
                    //Finds the first open position for the hashEntry in the new table with linear probing
                    while (table[(Math.abs(hashEntry.getKey().hashCode()) + j) % table.length] != null)
                        j++;
                    table[(Math.abs(hashEntry.getKey().hashCode()) + j) % table.length] = new HashEntry(hashEntry.getKey(), hashEntry.getValue());
                }
            }
            hashTable = table;
        }
        //Represents if the entry already exists in the table
        boolean existence = false;

        int i = 0;
        //Finds the first open hashed position in the hashTable, checking if the entry already exists along the way
        while (hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length] != null && !existence)
        {
            if (hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length].getKey().equals(key))
                existence = true;
            else
                i++;
        }

        //Simply inputs the new item in the hashTable if it doesn't already exist
        if(!existence)
        {
            hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length] = new HashEntry(key, value);
            numItems++;
        }
    }

    /**
     *Stores the given key-value pair in the hash table, using the given hashCode
     */
    public void put(String key, int value , int hashCode)
    {
        //Rehashes the table if the load factor becomes too large
        if(((double)numItems/hashTable.length) >= 0.5)
        {
            //Represents the rehashed table
            HashEntry[] table = new HashEntry[hashTable.length * 2];

            //Rehashes each hashEntry into a new table
            for (HashEntry hashEntry : hashTable)
            {
                if (hashEntry != null)
                {
                    int j = 0;
                    //Finds the first open position for the hashEntry in the new table with linear probing
                    while (table[(Math.abs(hashCode) + j) % table.length] != null)
                        j++;
                    table[(Math.abs(hashCode) + j) % table.length] = new HashEntry(hashEntry.getKey(), hashEntry.getValue());
                }
            }
            hashTable = table;
        }
        //Represents if the entry already exists in the table
        boolean existence = false;

        int i = 0;
        //Finds the first open hashed position in the hashTable, checking if the entry already exists along the way
        while (hashTable[(Math.abs(hashCode) + i) % hashTable.length] != null && !existence)
        {
            if (hashTable[(Math.abs(hashCode) + i) % hashTable.length].getKey().equals(key))
                existence = true;
            else
                i++;
        }

        //Simply inputs the new item in the hashTable if it doesn't already exist
        if(!existence)
        {
            hashTable[(Math.abs(hashCode) + i) % hashTable.length] = new HashEntry(key, value);
            numItems++;
        }
    }

    /**
     * Update value associated with given key in the hash table
     */
    public void update(String key, int value)
    {
        int i = 0;
        //Finds the first open position in the hashTable while within the arrays bound
        //Or as long as the entry does not exist
        while(i <= numItems && hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length] != null && !hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length].getKey().equals(key))
            i++;

        //Hashes the entry into the table if it does not exist
        if(i > numItems || hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length] == null)
            put(key, value);
        //Otherwise, updates the value
        else
            hashTable[(Math.abs(key.hashCode()) + i) % hashTable.length].setValue(value);
    }

    /**
     * Returns the value associated with the given key if it exists. Otherwise, returns -1
     */
    public int get(String key)
    {
        int j = 0;
        //Uses a linear probe to search for the given key in the table
        while(j <= numItems && hashTable[(Math.abs(key.hashCode()) + j) % hashTable.length] != null)
        {
            if(hashTable[(Math.abs(key.hashCode()) + j) % hashTable.length].getKey().equals(key))
                return hashTable[(Math.abs(key.hashCode()) + j) % hashTable.length].getValue();
            j++;
        }
        //If the key does not exist in the table
        return -1;
    }

    /**
     * Returns the value associated with the given key using the given hashCode if it exists. Otherwise, returns -1
     */
    public int get(String key, int hashCode)
    {
        int j = 0;
        //Uses a linear probe to search for the given key in the table
        while(j <= numItems && hashTable[(Math.abs(hashCode) + j) % hashTable.length] != null)
        {
            if(hashTable[(Math.abs(hashCode) + j) % hashTable.length].getKey().equals(key))
                return hashTable[(Math.abs(hashCode) + j) % hashTable.length].getValue();
            j++;
        }
        //If the key does not exist in the table
        return -1;
    }
}
