import org.junit.*;
import java.lang.reflect.Field;

import static org.junit.Assert.*;


public class HashTableTests
{
    @Test
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        HashTable table = new HashTable();
        Field arrayField = table.getClass().getDeclaredField("hashTable");
        arrayField.setAccessible(true);

        assertEquals(((HashEntry[]) arrayField.get(table)).length, 1024);

        table = new HashTable(1000);
        assertEquals(((HashEntry[]) arrayField.get(table)).length, 1000);
    }

    @Test
    public void testPut_Get() throws NoSuchFieldException, IllegalAccessException {
        HashTable table = new HashTable(30);
        Field arrayField = table.getClass().getDeclaredField("hashTable");
        Field numItems = table.getClass().getDeclaredField("numItems");
        arrayField.setAccessible(true);
        numItems.setAccessible(true);

        //Tests zero----------------------------------------------------------------------------------------------------
        assertEquals(-1, table.get(""));
        assertEquals(-1, table.get("One"));

        //Tests one entry-----------------------------------------------------------------------------------------------
        table.put("One", 1);
        assertEquals(1, table.get("One"));

        //Tests multiple entries----------------------------------------------------------------------------------------
        table.put("Two", 2);
        table.put("Three", 3);
        table.put("Four", 4);
        table.put("Five", 5);
        assertEquals(1, table.get("One"));
        assertEquals(2, table.get("Two"));
        assertEquals(3, table.get("Three"));
        assertEquals(4, table.get("Four"));
        assertEquals(5, table.get("Five"));

        assertEquals(5, numItems.get(table));

        //Tests duplicate entries---------------------------------------------------------------------------------------
        table.put("One", 0);
        table.put("Two", 0);
        table.put("Three", 0);

        table.put("Four", 4);
        table.put("Four", 4);
        table.put("Five", 5);
        table.put("Five", 5);
        assertEquals(1, table.get("One"));
        assertEquals(2, table.get("Two"));
        assertEquals(3, table.get("Three"));
        assertEquals(4, table.get("Four"));
        assertEquals(5, table.get("Five"));

        assertEquals(5, numItems.get(table));

        //Tests collisions----------------------------------------------------------------------------------------------
        HashEntry[] hashes = new HashEntry[]{new HashEntry("Zero", 0), new HashEntry("One", 1), new HashEntry("Two", 2), new HashEntry("Three", 3), new HashEntry("Four", 4), new HashEntry("Five", 5),
                new HashEntry("Six", 6), new HashEntry("Seven", 7), new HashEntry("Eight", 8), new HashEntry("Nine", 9), new HashEntry("Ten", 10), new HashEntry("Eleven", 11)};
        table = new HashTable(30);
        table.put("Zero", 0, 0);
        table.put("One", 1, 1);
        table.put("Two", 2, 2);
        table.put("Three", 3, 3);
        table.put("Four", 4, 4);
        table.put("Five", 5, 5);
        for (int i = 0; i < 6; i++)
        {
            assertEquals(hashes[i].getKey(), ((HashEntry[])arrayField.get(table))[i].getKey());
        }

        //Tests collisions at the beginning of the hash table-----------------------------------------------------------
        table.put("Six", 6, 0);
        assertEquals("Six", ((HashEntry[])arrayField.get(table))[6].getKey());
        assertEquals(6, table.get("Six", 0));

        table.put("Seven", -1, 0);
        assertEquals("Seven", ((HashEntry[])arrayField.get(table))[7].getKey());
        assertEquals(-1, table.get("Seven", 0));

        //Tests collisions in the middle of the hash table--------------------------------------------------------------
        table.put("Eight", 8, 4);
        assertEquals("Eight", ((HashEntry[])arrayField.get(table))[8].getKey());
        assertEquals(8, table.get("Eight", 4));

        table.put("Nine", -1, 4);
        assertEquals("Nine", ((HashEntry[])arrayField.get(table))[9].getKey());
        assertEquals(-1, table.get("Nine", 4));

        //Tests collisions at the end of the hash table-----------------------------------------------------------------
        table.put("Ten", 10, 10);
        assertEquals("Ten", ((HashEntry[])arrayField.get(table))[10].getKey());
        assertEquals(10, table.get("Ten", 10));

        table.put("Eleven", -1, 11);
        assertEquals("Eleven", ((HashEntry[])arrayField.get(table))[11].getKey());
        assertEquals(-1, table.get("Eleven", 11));

        for (int i = 0; i < 6; i++)
        {
            assertEquals(hashes[i].getKey(), ((HashEntry[])arrayField.get(table))[i].getKey());
        }

        assertEquals(12, numItems.get(table));
    }

    @Test
    public void testUpdate() throws NoSuchFieldException, IllegalAccessException {
        HashTable table = new HashTable(15);
        Field arrayField = table.getClass().getDeclaredField("hashTable");
        Field numItems = table.getClass().getDeclaredField("numItems");
        arrayField.setAccessible(true);
        numItems.setAccessible(true);
        //Tests empty - when there no items in the table
        assertEquals(-1, table.get("One"));
        table.update("One", 1);
        assertEquals(1, table.get("One"));

        //Tests changing the time when there is one item in the table
        table.update("One", 0);
        assertEquals(0, table.get("One"));
        table.update("One", 3);
        assertEquals(3, table.get("One"));

        //Test inserting multiple items and changing them
        table.update("Two", 2);
        table.update("Three", 3);
        table.update("Four", 4);
        assertEquals(2, table.get("Two"));
        assertEquals(3, table.get("Three"));
        assertEquals(4, table.get("Four"));
        table.update("Four", -1);
        table.update("Two", 0);
        assertEquals(0, table.get("Two"));
        assertEquals(-1, table.get("Four"));

        //Asserts that no duplicates are put into the table
        assertEquals(4, numItems.get(table));
    }

    @Test
    public void testRefactor() throws NoSuchFieldException, IllegalAccessException {
        HashTable table = new HashTable(10);
        Field arrayField = table.getClass().getDeclaredField("hashTable");
        Field numItems = table.getClass().getDeclaredField("numItems");
        arrayField.setAccessible(true);
        numItems.setAccessible(true);

        assertEquals(10, ((HashEntry[])arrayField.get(table)).length);

        table.put("Zero", 0);
        table.put("One", 1);
        table.put("Two", 2);
        table.put("Three", 3);
        assertEquals(10, ((HashEntry[])arrayField.get(table)).length);
        table.put("Four", 4);
        assertEquals(10, ((HashEntry[])arrayField.get(table)).length);
        table.put("Five", 5);
        assertEquals(6, numItems.get(table));

        assertEquals(20, ((HashEntry[])arrayField.get(table)).length);
        assertEquals(0, table.get("Zero"));
        assertEquals(1, table.get("One"));
        assertEquals(2, table.get("Two"));
        assertEquals(3, table.get("Three"));
        assertEquals(4, table.get("Four"));
        assertEquals(5, table.get("Five"));
    }
}
