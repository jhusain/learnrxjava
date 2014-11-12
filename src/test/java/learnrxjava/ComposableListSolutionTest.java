package learnrxjava;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import learnrxjava.types.BoxArt;
import learnrxjava.types.ComposableList;
import learnrxjava.types.JSON;
import learnrxjava.types.MovieList;
import learnrxjava.types.Video;

import org.junit.Test;

public class ComposableListSolutionTest {

    @Test
    public void testExercise1() {
        System.out.println("----------- testExercise1 ----------------");
        ComposableListSolution.exercise1();
    }

    @Test
    public void testExercise2() {
        System.out.println("----------- testExercise2 ----------------");
        ComposableListSolution.exercise2();
    }

    @Test
    public void testExercise3() {
        System.out.println("----------- testExercise3 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise3();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise4map() {
        System.out.println("----------- testExercise4 map ----------------");
        ComposableList<Integer> slr = ComposableListSolution.of(1, 2, 3).map(i -> i + 1);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(2, 3, 4), slr);
    }

    @Test
    public void testExercise5() {
        System.out.println("----------- testExercise5 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise5();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise6() {
        System.out.println("----------- testExercise6 ----------------");
        ComposableList<Video> slr = ComposableListSolution.exercise6();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise7filter() {
        System.out.println("----------- testExercise7 filter ----------------");
        ComposableList<Integer> slr = ComposableListSolution.of(1, 2, 3).filter(i -> i <= 2);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(1, 2), slr);
    }

    @Test
    public void testExercise8() {
        System.out.println("----------- testExercise8 ----------------");
        ComposableList<Integer> slr = ComposableListSolution.exercise8();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise9() {
        System.out.println("----------- testExercise9 ----------------");
        ComposableList<Integer> slr = ComposableListSolution.exercise9();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise10() {
        System.out.println("----------- testExercise10 ----------------");
        ComposableList<Integer> slr = ComposableListSolution.of(1, 2, 3).concatMap((t) -> ComposableListSolution.of(t+1, t+2, t+3));
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(2, 3, 4, 3, 4, 5, 4, 5, 6), slr);
    }

    @Test
    public void testExercise11() {
        System.out.println("----------- testExercise11 ----------------");
        ComposableList<Integer> slr = ComposableListSolution.exercise11();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise12() {
        System.out.println("----------- testExercise12 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise12();
        slr.forEach(System.out::println);
        
        //   {"id": 675465,"title": "Fracture","boxart":"http://cdn-0.nflximg.com/images/2891/Fracture150.jpg" },
        //   {"id": 65432445,"title": "The Chamber","boxart":"http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg" },
        //   {"id": 654356453,"title": "Bad Boys","boxart":"http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg" },
        //   {"id": 70111470,"title": "Die Hard","boxart":"http://cdn-0.nflximg.com/images/2891/DieHard150.jpg" }
        
    }

    @Test
    public void testExercise13() {
        System.out.println("----------- testExercise13 ----------------");
        BoxArt slr = ComposableListSolution.exercise13();
        System.out.println(slr);
    }

    @Test
    public void testExercise14reduce() {
        System.out.println("----------- testExercise14 reduce ----------------");
        ComposableList<Integer> slr = ComposableListSolution.of(1, 2, 3).reduce((s, i) -> s + i);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(6), slr);
    }

    @Test
    public void testExercise15reduce() {
        System.out.println("----------- testExercise15 reduce ----------------");
        ComposableList<Integer> slr = ComposableListSolution.of(1, 2, 3).reduce(1, (s, i) -> s + i);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(7), slr);
    }

    @Test
    public void testExercise16() {
        System.out.println("----------- testExercise16 ----------------");
        ComposableList<Integer> slr = ComposableListSolution.exercise16();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise17() {
        System.out.println("----------- testExercise17 ----------------");
        ComposableList<String> slr = ComposableListSolution.exercise17();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise18() {
        System.out.println("----------- testExercise18 ----------------");
        ComposableList<Map<Integer, String>> slr = ComposableListSolution.exercise18();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise19() {
        System.out.println("----------- testExercise19 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise19();
        System.out.println(slr);
    }

    @Test
    public void testExercise20() {
        System.out.println("----------- testExercise20 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise20();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise21zip() {
        System.out.println("----------- testExercise21 zip ----------------");
        ComposableList<Integer> slr = ComposableListSolution.zip(ComposableListSolution.of(1, 2, 3), ComposableListSolution.of(4, 5, 6), (l, r) -> l + r);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(5, 7, 9), slr);
    }

    @Test
    public void testExercise22() {
        System.out.println("----------- testExercise22 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise22();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise23() {
        System.out.println("----------- testExercise23 ----------------");
        ComposableList<JSON> slr = ComposableListSolution.exercise23();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise24() {
        System.out.println("----------- testExercise24 ----------------");
        ComposableList<List<MovieList>> slr = ComposableListSolution.exercise24();
        slr.forEach(System.out::println);
    }

    @Test
    public void testExercise25() {
        System.out.println("----------- testExercise25 ----------------");
        ComposableList<List<MovieList>> slr = ComposableListSolution.exercise25();
        slr.forEach(System.out::println);
    }
}
