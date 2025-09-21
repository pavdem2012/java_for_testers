package collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CollectionTests {
    @Test
    void arrayTests() {
        //var array = new String[]{"a", "b", "c"};
        var array = new String[3];
        Assertions.assertEquals(3, array.length);
        array[0] = "a";
        Assertions.assertEquals("a", array[0]);

        array[0] = "d";
        Assertions.assertEquals("d", array[0]);
    }

    @Test
    void listTests() {
        //var list = List.of("a", "b", "c"); //немодифицированный
        //var list  = new ArrayList<String>();
        var list = new ArrayList<>(List.of("a", "b", "c", "a"));
        Assertions.assertEquals(4, list.size());
        list.add("a");
        list.add("b");
        list.add("c");
        Assertions.assertEquals(7, list.size());
        Assertions.assertEquals("a", list.get(0));
        list.set(0, "d");
        Assertions.assertEquals("d", list.get(0));
    }

    @Test
    void setTests() {
        var set = new HashSet<>(List.of("a", "b", "c", "a"));
        Assertions.assertEquals(3, set.size());
        var element = set.stream().findAny().get();
        set.add("a");
        Assertions.assertEquals(3, set.size());
        set.add("T");
        Assertions.assertEquals(4, set.size());
    }

    @Test
    void testMap() {
        var digits = new HashMap<Character, String>();
        digits.put('0', "zero");
        digits.put('1', "one");
        digits.put('2', "two");
        digits.put('3', "three");
        digits.put('4', "four");
        digits.put('5', "five");
        digits.put('6', "six");
        digits.put('7', "seven");
        digits.put('8', "eight");
        digits.put('9', "nine");
        Assertions.assertEquals("one", digits.get('1'));
        digits.put('1', "один");
        Assertions.assertNotEquals("one", digits.get('1'));
        Assertions.assertEquals("один", digits.get('1'));


    }

}
