package originalgremlin;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.*;

public class TrieTest {
    Trie trie;

    @BeforeMethod
    public void setUp() throws Exception {
        trie = new Trie();
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetSize() throws Exception {
        assertEquals(trie.getSize(), 0);
        trie.insert("apple");
        assertEquals(trie.getSize(), 1);
        trie.delete("apple");
        assertEquals(trie.getSize(), 0);

        trie.insert("banana");
        trie.insert("band");
        trie.insert("bean");
        assertEquals(trie.getSize(), 3);
    }

    @Test
    public void testGetSizeWithPrefix() throws Exception {
        assertEquals(trie.getSize(), 0);
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("band");
        trie.insert("bean");
        assertEquals(trie.getSize(""), 4);
        assertEquals(trie.getSize("a"), 1);
        assertEquals(trie.getSize("b"), 3);
        assertEquals(trie.getSize("ba"), 2);
        assertEquals(trie.getSize("be"), 1);
        assertEquals(trie.getSize("not found"), 0);

        trie.delete("band");
        assertEquals(trie.getSize(""), 3);
        assertEquals(trie.getSize("a"), 1);
        assertEquals(trie.getSize("b"), 2);
        assertEquals(trie.getSize("ba"), 1);
        assertEquals(trie.getSize("be"), 1);
        assertEquals(trie.getSize("band"), 0);
        assertEquals(trie.getSize("not found"), 0);
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(trie.isEmpty());
        trie.insert("apple");
        assertFalse(trie.isEmpty());
        trie.delete("apple");
        assertTrue(trie.isEmpty());
    }

    @Test
    public void testInsert() throws Exception {
        assertFalse(trie.contains("apple"));
        assertFalse(trie.contains("banana"));
        assertFalse(trie.contains("band"));
        assertFalse(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("band");
        trie.insert("bean");
        assertTrue(trie.contains("apple"));
        assertTrue(trie.contains("banana"));
        assertTrue(trie.contains("band"));
        assertTrue(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
    }

    @Test
    public void testInsertMultiple() throws Exception {
        assertFalse(trie.contains("apple"));
        assertFalse(trie.contains("banana"));
        assertFalse(trie.contains("band"));
        assertFalse(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));

        ArrayList<String> keys = new ArrayList<>();
        keys.add("apple");
        keys.add("banana");
        keys.add("band");
        keys.add("bean");
        trie.insert(keys);

        assertTrue(trie.contains("apple"));
        assertTrue(trie.contains("banana"));
        assertTrue(trie.contains("band"));
        assertTrue(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
    }

    @Test
    public void testDelete() throws Exception {
        assertFalse(trie.contains("apple"));
        assertFalse(trie.contains("banana"));
        assertFalse(trie.contains("band"));
        assertFalse(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("band");
        trie.insert("bean");
        assertTrue(trie.contains("apple"));
        assertTrue(trie.contains("banana"));
        assertTrue(trie.contains("band"));
        assertTrue(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
        trie.delete("apple");
        trie.delete("banana");
        trie.delete("not found");
        trie.delete("something else");
        assertFalse(trie.contains("apple"));
        assertFalse(trie.contains("banana"));
        assertTrue(trie.contains("band"));
        assertTrue(trie.contains("bean"));
        assertFalse(trie.contains("app"));
        assertFalse(trie.contains("ban"));
        assertFalse(trie.contains("not found"));
        assertFalse(trie.contains("something else"));
    }

    @Test
    public void testClear() throws Exception {
        assertEquals(trie.getSize(), 0);
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("band");
        trie.insert("bean");
        assertEquals(trie.getSize(), 4);
        assertTrue(trie.contains("apple"));
        assertTrue(trie.contains("banana"));
        assertTrue(trie.contains("band"));
        assertTrue(trie.contains("bean"));
        trie.clear();
        assertEquals(trie.getSize(), 0);
        assertFalse(trie.contains("apple"));
        assertFalse(trie.contains("banana"));
        assertFalse(trie.contains("band"));
        assertFalse(trie.contains("bean"));
    }

    @Test
    public void testContains() throws Exception {
        // adequately tested by insert and delete
    }

    @Test
    public void testGetAll() throws Exception {
        trie.insert("banana");
        trie.insert("band");
        trie.insert("mango");
        trie.insert("apple");
        trie.insert("mandolin");
        trie.insert("grape");
        trie.insert("bean");
        trie.insert("fruit");
        // check that everything was returned
        Collection<String> keys = trie.getAll();
        assertEquals(keys.size(), 8);
        // check for proper sorting
        String[] array = keys.toArray(new String[0]);
        assertEquals(array[0], "apple");
        assertEquals(array[1], "banana");
        assertEquals(array[2], "band");
        assertEquals(array[3], "bean");
        assertEquals(array[4], "fruit");
        assertEquals(array[5], "grape");
        assertEquals(array[6], "mandolin");
        assertEquals(array[7], "mango");
    }

    @Test
    public void testGetAllWithPrefix() throws Exception {
        trie.insert("banana");
        trie.insert("band");
        trie.insert("mango");
        trie.insert("apple");
        trie.insert("mandolin");
        trie.insert("grape");
        trie.insert("bean");
        trie.insert("fruit");
        // check that everything was returned
        Collection<String> bKeys = trie.getAll("b");
        Collection<String> mKeys = trie.getAll("m");
        Collection<String> zKeys = trie.getAll("z");
        assertEquals(bKeys.size(), 3);
        assertEquals(mKeys.size(), 2);
        assertEquals(zKeys.size(), 0);
        // check for proper sorting
        String[] bArray = bKeys.toArray(new String[0]);
        String[] mArray = mKeys.toArray(new String[0]);
        String[] zArray = zKeys.toArray(new String[0]);
        assertEquals(bArray[0], "banana");
        assertEquals(bArray[1], "band");
        assertEquals(bArray[2], "bean");
        assertEquals(mArray[0], "mandolin");
        assertEquals(mArray[1], "mango");
    }
}