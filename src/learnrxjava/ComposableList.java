/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learnrxjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * 
 */
public class ComposableList<T> extends ArrayList<T> {

    public static <T> ComposableList<T> of(T... args) {
        ComposableList<T> results = new ComposableList<T>();
        for (T value : args) {
            results.add(value);
        }
        return results;
    }

    public static class JSON extends HashMap<String, Object> {
    };

    public static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        this.
    }

    /*
     Traversing a List
    
    
     */
    public void exercise1() {
        ComposableList<String> names = ComposableList.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        for (String name : names) {
            System.out.println(name);
        }
    }

    /*
     Traversing a ComposableList
    
     Unlike Lists and other collections that implement the Iterable 
     interface, you can _not_ use Java’s for each loop to traverse a ComposableList. 
     Instead ComposableList provides a forEach method to which you pass a lambda. The 
     ComposableList invokes your lambda once for every item in the stream, and passes 
     the item to the lambda each time.

     Note that the code is very similar, and we get the same result whether we 
     are using the Java for each syntax or the forEach method on the ComposableList.
    
     Note: This exercise is already finished, so just move on.
     */
    public void exercise2() {
        ComposableList<String> names = ComposableList.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        names.forEach(name -> {
            System.out.println(name);
        });
    }

    /*
     Projecting Lists

     Applying a function to a value and creating a new value is called a projection. To project one List into another, we apply a projection function to each item in the List and collect the results in a new List.

     exercise 3: Project a list  of videos into a list of {id,title} JSON objects using for each

     For each video, add a projected {id, title} json to the videoAndTitlePairs List.
     */
    class Bookmark {
        public int id;
        public int offset;
        public Bookmark(int id, int offset) {
            this.id = id;
            this.offset = offset;
        }
    }

    class InterestingMoment {
        String type;
        int time;
        public InterestingMoment(String type, int time) {
            this.type = type;
            this.time = time;
        }
    }

    class Video {

        public int id;
        public String title;
        public double rating;
        private ComposableList<Bookmark> bookmarks;
        private ComposableList<BoxArt> boxarts;
        private ComposableList<InterestingMoment> interestingMoments;

        public Video(int id, String title, double rating) {
            this.id = id;
            this.title = title;
            this.rating = rating;
        }

        public Video(int id, String title, double rating, ComposableList<Bookmark> bookmarks, ComposableList<BoxArt> boxarts) {
            this(id, title, rating);
            this.bookmarks = bookmarks;
            this.boxarts = boxarts;
        }

        public Video(int id, String title, double rating, ComposableList<Bookmark> bookmarks, ComposableList<BoxArt> boxarts, ComposableList<InterestingMoment> interestingMoments) {
            this(id, title, rating);
            this.bookmarks = bookmarks;
            this.boxarts = boxarts;
            this.interestingMoments = interestingMoments;
        }
    }

    public ComposableList<JSON> exercise3() {
        ComposableList<Video> newReleases = ComposableList.of(
                new Video(70111470, "Die Hard", 4.0),
                new Video(654356453, "Bad Boys", 5.0),
                new Video(65432445, "The Chamber", 4.0),
                new Video(675465, "Fracture", 5.0));

        ComposableList<JSON> videoAndTitlePairs = new ComposableList<JSON>();

        // ------------ INSERT CODE HERE! -----------------------------------
        // Use Java's forEach function to accumulate {id, title} JSON from each 
        // video. Put the results into the videoAndTitlePairs list using the 
        // List's add method. 
        // Example: videoAndTitlePairs.add(json("id", 5, "name", "Die Hard"));
        // ------------ INSERT CODE HERE! -----------------------------------
        // **************ANSWER START***************//
        // for(Video video: newReleases) {
        //    videoAndTitlePairs.add(json("id", video.id, "title", video.title));
        // }
        // **************ANSWER END***************//
        return videoAndTitlePairs;
    }

    /*
     All list projections share two operations in common:

     1. Traverse the source list
     2. Add each item's projected value to a new list

     Why not abstract away how these operations are carried out?

     exercise 4: Implement map() for List

     If the List interface had a map() method, it would make projections easier. 
     The map method accepts the projection function to be applied to each item 
     in the source List, and returns a List of the projected results.
    
     Ideally we'd be able to call ComposableList.of(1,2,3).map(x -> x + 1) to create
     a new List with a value of ComposableList.of(2,3,4). Unfortunately List doesn't
     have a map method and there's no way for us to add one. However we _can_ 
     define a static method map that accepts a list, a projection function, and 
     returns the translated list.
    
     map(ComposableList.of(1,2,3), x -> x + 1) is equivalent to ComposableList.of(2,3,4)
     */
    public <R> ComposableList<R> map(Function<T, R> projectionFunction) {
        ComposableList<R> results = new ComposableList<R>();
        for (T itemInList : this) {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the projectionFunction to each item in the list and add
            // each result to the results list.
            // Note that you can apply a projectionFunction to a value like this:
            // projectionFunction.apply(5)
            // ------------ INSERT CODE HERE! ----------------------------

            // **************ANSWER START***************//
            // results.add(projectionFunction.apply(itemInList));
            // **************ANSWER END***************//
        }
        return results;
    }
    /*
     exercise 5: Use map() to project a ComposableList of videos into a stream of {id,title} JSON

     The good news is that unlike List, ComposableLists has a map method. 
     ComposableList.of(1,2,3).map(x -> x + 1) is equivalent to writing ComposableList.of(2,3,4)
     Let's repeat exercise 3 and collect {id, title} pairs for each video in newReleases, but this time we'll use the map method on ComposableLists instead of a for each loop on Lists
     */

    public ComposableList<JSON> exercise5() {
        ComposableList<Video> newReleases = ComposableList.of(
                new Video(70111470, "Die Hard", 4.0),
                new Video(654356453, "Bad Boys", 5.0),
                new Video(65432445, "The Chamber", 4.0),
                new Video(675465, "Fracture", 5.0));

    // ------------ INSERT CODE HERE! -----------------------------------
        // Use the map function to accumulate {id, title} json from each video.
        // Uncomment the code below and finish the expression
        // return videoAndTitlePairs.map(video -> 
         // **************ANSWER START***************//
        // return newReleases.map(video -> json("id", video.id, "title", video.title));
        // **************ANSWER END***************//
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Notice that map allows us to specify what projection we want to apply to a list, but hides how the operation is carried out.

     Filtering Lists

     Like projection, filtering a list is also a very common operation. To filter a list we apply a test to each item in the list and collect the items that pass into a new list.

     exercise 6: Use forEach() to collect only those videos with a rating of 5.0

     Use forEach() to loop through the videos in the newReleases list and, if a video has a rating of 5.0, add it to the videos list.
     */
    public ComposableList<Video> exercise6() {
        ComposableList<Video> newReleases = ComposableList.of(
                new Video(
                        70111470,
                        "Die Hard",
                        4.0
                ),
                new Video(
                        654356453,
                        "Bad Boys",
                        5.0
                ),
                new Video(
                        65432445,
                        "The Chamber",
                        4.0
                ),
                new Video(
                        675465,
                        "Fracture",
                        5.0
                ));

        ComposableList<Video> highRatedVideos = new ComposableList<Video>();

        // ------------ INSERT CODE HERE! -----------------------------------
        // Use forEach function to accumulate every video with a rating of 5.0
        // ------------ INSERT CODE HERE! -----------------------------------
        // return highRatedVideos;
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    /*
     Notice that like map() every filter() operation shares some operations in common:

     1. Traverse the list
     2. Add objects that pass the test to a new list

     Why not create a helper function for this common pattern?

     exercise 7: Implement filter()

     If the List interface had a filter() method, it would make projections easier. 
     The filter method accepts a predicate test function to be applied to each 
     item in the source List. The filter method then returns a new List of all
     of the items for which the predicate function returned true.
    
     Ideally we'd be able to call ComposableList.of(1,2,3).filter(x -> x > 1) to 
     create a new List with a value of ComposableList.of(2,3). Unfortunately List 
     doesn't have a map method and there's no way for us to add one. However we 
     _can_ define a static method filter that accepts a list, a predicate 
     function and returns the filtered list.
    
     filter(ComposableList.of(1,2,3), x -> x > 1) is equivalent to ComposableList.of(2,3)
     */

    public ComposableList<T> filter(Predicate<T> predicateFunction) {
        ComposableList<T> results = new ComposableList<T>();
        for (T itemInList : this) {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the predicateFunction to each item in the list. If the
            // result is true, add the result to the results list.
            // Note: you can apply the predicateFunction to a value like this:
            // predicateFunction.test(5)
            // ------------ INSERT CODE HERE! ----------------------------

            // **************ANSWER START***************//
            // if (predicateFunction.test(itemInList)) {
            //    results.add(itemInList);
            // }
            // **************ANSWER END***************//
        }

        // return results;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Like map(), filter() let's us express what data we want without requiring us to specify how we want to collect the data.

     Query Data by Chaining Method Calls

     exercise 8: Chain filter and map to collect the ids of videos that have a rating of 5.0
     */
    public ComposableList<Integer> exercise8() {
        ComposableList<Video> newReleases
            = ComposableList.of(
                    new Video(
                            70111470,
                            "Die Hard",
                            4.0
                    ),
                    new Video(
                            654356453,
                            "Bad Boys",
                            5.0
                    ),
                    new Video(
                            65432445,
                            "The Chamber",
                            4.0
                    ),
                    new Video(
                            675465,
                            "Fracture",
                            5.0
                    ));

    // ------------ INSERT CODE HERE! -----------------------------------
        // Chain the filter and map functions to select the id of all videos
        // with a rating of 5.0.
    //return newReleases // Complete this expression
        // ------------ INSERT CODE HERE! -----------------------------------
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    /*
     Chaining together map() and filter() gives us a lot of expressive power. These high level functions let us express what data we want, but leave the underlying libraries a great deal of flexibility in terms of how our queries are executed.

     Querying Trees

     Sometimes, in addition to flat lists, we need to query trees. Trees pose a challenge because we need to flatten them in order to apply filter() and map() operations on them. In this section we'll define a concatMap() function that we can use alongside or instead of map() to query trees.

     exercise 9: Flatten the movieLists list into a list of video ids

     Let's start by using two nested forEach loops collect the id of every video in the two-dimensional movieLists list.
     */

    class MovieList {

        public String name;
        public ComposableList<Video> videos;

        public MovieList(String name, ComposableList<Video> videos) {
            this.name = name;
            this.videos = videos;
        }
    }

    public ComposableList<Integer> exercise9() {
        ComposableList<MovieList> movieLists = ComposableList.of(
            new MovieList(
                    "New Releases",
                    ComposableList.of(
                            new Video(70111470, "Die Hard", 4.0),
                            new Video(654356453, "Bad Boys", 5.0))),
            new MovieList(
                    "Dramas",
                    ComposableList.of(
                            new Video(65432445, "The Chamber", 4.0),
                            new Video(675465, "Fracture", 5.0))));

        ComposableList<Integer> allVideoIdsInMovieLists = new ComposableList<Integer>();

        // ------------   INSERT CODE HERE!  -----------------------------------
        // Use two nested forEach loops to flatten the movieLists into a list of
        // video ids.
        // ------------   INSERT CODE HERE!  -----------------------------------
        //return allVideoIdsInMovieLists;
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    /*
     Flattening trees with nested forEach expressions is easy because we can explicitly add items to the list. Unfortunately it's exactly this type of low-level operation that we've been trying to abstract away with functions like map() and filter(). Can we define a function that's abstract enough to express our intent to flatten a tree, without specifying too much information about how to carry out the operation?

     exercise 10: Implement concatMap()

     Flattening trees is very common. Ideally we'd be able to use a helper function to do the heavy lifting for us. Let's implement the concatMap() method for List. Like map(), the concatMap() function applies a projection function to each item in a list. However the projection function passed to concatMap converts each value into a List of values, creating a tree. Before returning the tree, the concatMap method flattens it. Here's an example of concatMap in action. 

     ComposableList.of(
     ComposableList.of( 0,   1,   2),
     ComposableList.of(10,  11,  12),
     ComposableList.of(20,  21,  22))
     .concatMap(x -> x * 10)
    
     is equivalent to

     ComposableList.of(0, 10, 20, 100, 110, 120, 200, 210, 220)

     Unfortunately List doesn't have a concatMap method and there's no way for us to add one. However we _can_ define a static method concatMap. The concatMap method accepts a ComposableList<T> , a function that accepts a single value and returns a collection (Function<T, ComposableList<R>> ), and returns a flat list of the results (ComposableList<R> ).
     */

    public static <T, R> ComposableList<R> concatMap(ComposableList<T> that, Function<T, List<R>> projectionFunctionThatReturnsList) {
        ComposableList<R> results = new ComposableList<R>();
        for (T itemInList : that) {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the projection function to each item in the list.
            // This will create _another_ list. Then loop through each
            // inner list and add each item to the flat results list.
            // Note that you can apply a projectionFunction to a value like this:
            // projectionFunctionThatReturnsList.apply(5)
            // ------------ INSERT CODE HERE! ----------------------------

            // **************ANSWER START***************//
            // for(R itemInInnerList: projectionFunctionThatReturnsList.apply(itemInList)) {
            //     results.add(itemInInnerList);
            // }
            // **************ANSWER END***************//
        }
        return results;
    }
    /*

     As you may have already guessed, the ComposableList class has the concatMap method. The concatMap method may seem pretty abstract at first, and it may not be immediately obvious how it can be used to transform data in a tree. In the next exercise we will combine this function with the map function to query a tree.

     exercise 11: Use map() and concatMap() to project and flatten the movieLists into a stream of video ids

     Hint: nest a map() call within a concatMap().
     */

    public ComposableList<Integer> exercise11() {
        ComposableList<MovieList> movieLists = ComposableList.of(
                new MovieList(
                        "New Releases", // name
                        ComposableList.of( // videos
                                new Video(70111470, "Die Hard", 4.0),
                                new Video(654356453, "Bad Boys", 5.0))),
                new MovieList(
                        "Dramas",
                        ComposableList.of(
                                new Video(65432445, "The Chamber", 4.0),
                                new Video(675465, "Fracture", 5.0))));

        // ------------   INSERT CODE HERE!  -----------------------------------
        // Use map and concatAll to flatten the movieLists in a list of video ids.
        // return movieLists // finish expression
        // ------------   INSERT CODE HERE!  -----------------------------------
        // **************ANSWER START***************//
        // return movieLists.
        //     concatMap(movieList -> 
        //         movieList.videos.map(video -> video.id));
        // **************ANSWER END***************//
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    /*
     Wow! Great work. Mastering the combination of map() and concatMap() is key to effective functional programming. You're half way there! Let's try a more complicated example...

     exercise 12: Retrieve id, title, and a 150x200 box art url for every video

     You've managed to flatten a tree that's two levels deep, let's try for three! Let's say that instead of a single boxart url on each video, we had a collection of boxart objects, each with a different size and url. Create a query that selects {id, title, boxart} json for every video in the movieLists. This time though, the boxart property in the result will be the url of the boxart object with dimensions of 150x200px. Let's see if you can solve this problem using _only_ map(), concatMap(), and filter().
     */

    class BoxArt {

        public int width;
        public int height;
        public String url;

        public BoxArt(int width, int height, String url) {
            this.width = width;
            this.height = height;
            this.url = url;
        }
    }

    public ComposableList<JSON> exercise12() {
        ComposableList<MovieList> movieLists = ComposableList.of(
                new MovieList(
                        "Instant Queue",
                        ComposableList.of(
                                new Video(
                                        70111470,
                                        "Die Hard",
                                        5.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg")
                                        )),
                                new Video(
                                        654356453,
                                        "Bad Boys",
                                        4.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg")
                                        ))
                        )
                ),
                new MovieList(
                        "New Releases",
                        ComposableList.of(
                                new Video(
                                        65432445,
                                        "The Chamber",
                                        4.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg")
                                        )),
                                new Video(
                                        675465,
                                        "Fracture",
                                        5.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                                        ))
                        )
                )
        );

        // Use one or more map, concatAll, and filter calls to create an ComposableList with the following items
        // {
        //   {"id": 675465,"title": "Fracture","boxart":"http://cdn-0.nflximg.com/images/2891/Fracture150.jpg" },
        //   {"id": 65432445,"title": "The Chamber","boxart":"http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg" },
        //   {"id": 654356453,"title": "Bad Boys","boxart":"http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg" },
        //   {"id": 70111470,"title": "Die Hard","boxart":"http://cdn-0.nflximg.com/images/2891/DieHard150.jpg" }
        // };
        // return movieLists // Complete this expression!
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     It's a very common pattern to see several nested concatMap operations, with the last operation being a map. You can think of this pattern as the functional version of a nested forEach.

     Reducing Lists

     Sometimes we need to perform an operation on more than one item in the list at the same time. For example, let's say we need to find the largest integer in a list. We can't use a filter() operation, because it only examines one item at a time. To find the largest integer we need to the compare items in the list to each other.

     One approach could be to select an item in the list as the assumed largest number (perhaps the first item), and then compare that value to every other item in the list. Each time we come across a number that was larger than our assumed largest number, we'd replace it with the larger value, and continue the process until the entire list was traversed.

     If we replaced the specific size comparison with a lambda, we could write a function this handled the list traversal process for us. At each step our function would apply the lambda to the last value and the current value and use the result as the last value the next time. Finally we'd be left with only one value. This process is known as reducing because we reduce many values to a single value.

     exercise 13: Use forEach to find the largest box art

     In this example we use forEach to find the largest box art. Each time we examine a new boxart we update a variable with the currently known maximumSize. If the boxart is smaller than the maximum size, we discard it. If it's larger, we keep track of it. Finally we're left with a single boxart which must necessarily be the largest.
     */
    public BoxArt exercise13() {
        ComposableList<BoxArt> boxarts = ComposableList.of(
                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg"),
                new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg")
        );
        int currentSize;
        int maxSize = -1;
        BoxArt largestBoxart = null;

        for (BoxArt boxart : boxarts) {
            currentSize = boxart.width * boxart.height;
            if (currentSize > maxSize) {
                largestBoxart = boxart;
                maxSize = currentSize;
            }
        };

        return largestBoxart;
    }
    /*
     This process is a reduction because we're using the information we derived from the last computation to calculate the current value. However in the example above, we still have to specify the method of traversal. Wouldn't it be nice if we could just specify what operation we wanted to perform on the last and current value? Let's create a helper function to perform reductions on lists.

     exercise 14: Implement reduce()

     Let's add a reduce() function to the List type. Like map

     */
    // [1,2,3].reduce(function(accumulatedValue, currentValue) { return accumulatedValue + currentValue; }); == [6];
    // [1,2,3].reduce(function(accumulatedValue, currentValue) { return accumulatedValue + currentValue; }, 10); == [16];

    public ComposableList<T> reduce(BiFunction<T, T, T> combiner) {
        int counter = 1;
        T accumulatedValue = null;

        // If the list is empty, do nothing
        if (this.size() == 0) {
            return this;
        } else {
            accumulatedValue = this.get(0);

            // Loop through the list, feeding the current value and the result of
            // the previous computation back into the combiner function until
            // we've exhausted the entire list and are left with only one function.
            while (counter < this.size()) {
                accumulatedValue = combiner.apply(accumulatedValue, this.get(1));
                counter++;
            }

            return ComposableList.of(accumulatedValue);
        }
    }

    ;

    public <R> ComposableList<R> reduce(R initialValue, BiFunction<R, T, R> combiner) {
        int counter;
        R accumulatedValue;

        // If the list is empty, do nothing
        if (this.size() == 0) {
            return new ComposableList<R>();
        } else {
            counter = 0;
            accumulatedValue = initialValue;

            // Loop through the list, feeding the current value and the result of
            // the previous computation back into the combiner function until
            // we've exhausted the entire list and are left with only one function.
            while (counter < this.size()) {
                accumulatedValue = combiner.apply(accumulatedValue, this.get(0));
                counter++;
            }

            return ComposableList.of(accumulatedValue);
        }
    }

    ;
    /*        
     exercise 16: Retrieve the largest rating.

    Let's use our new reduce function to isolate the largest value in a list of ratings.
    */
    public ComposableList<Integer> exercise16() {
        ComposableList<Integer> ratings = ComposableList.of(2, 3, 1, 4, 5);
        // You should return a list containing only the largest rating. Remember that reduce always
        // returns a list with one item.

        // complete the expression below
        //return ratings.reduce
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Nice work. Now let's try combining reduce() with our other functions to build more complex queries.

     exercise 17: Retrieve url of the largest boxart

     Let's try combining reduce() with map() to reduce multiple boxart objects to a single value: the url of the largest box art.
     */
    public ComposableList<String> exercise17() {
        ComposableList<BoxArt> boxarts = ComposableList.of(
                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg"),
                new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg")
        );

        // You should return a list containing only the largest box art. Remember that reduce always
        // returns a list with one item.
        // return boxarts.reduce  
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     exercise 18: Reducing with an initial value

     Sometimes when we reduce a list, we want the reduced value to be a different type than the items stored in the list. Let's say we have a list of videos and we want to reduce them to a single map where the key is the video id and the value is the video's title.
     */
    public ComposableList<Map<Integer, String>> exercise18() {
        ComposableList<Video> videos = ComposableList.of(
                new Video(
                        65432445,
                        "The Chamber",
                        5.0
                ),
                new Video(
                        675465,
                        "Fracture",
                        4.0
                ),
                new Video(
                        70111470,
                        "Die Hard",
                        5.0
                ),
                new Video(
                        654356453,
                        "Bad Boys",
                        3.0
                )
        );

        // Expecting this output...
        // [
        //   {
        //       "65432445": "The Chamber",
        //       "675465": "Fracture",
        //       "70111470": "Die Hard",
        //       "654356453": "Bad Boys"
        //   }
        // ]
        /*
         return videos.
         reduce(
         // Use an empty map as the initial value instead of the first item in
         // the list.
         new HashMap<Integer, String> (),
         (accumulatedMap, video) -> {

                     

         return copyOfAccumulatedMap;
         });
         */
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Nice work. Now let's try combining reduce() with our other functions to build more complex queries.

     exercise 19: Retrieve the id, title, and smallest box art url for every video.

     This is a variation of the problem we solved earlier, where we retrieved the url of the boxart with a width of 150px. This time we'll use reduce() instead of filter() to retrieve the smallest box art in the boxarts list.
     */
    public JSON exercise19() {
        ComposableList<MovieList> movieLists = ComposableList.of(
                new MovieList(
                        "New Releases",
                        ComposableList.of(
                                new Video(
                                        70111470,
                                        "Die Hard",
                                        4.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                                        )),
                                new Video(
                                        654356453,
                                        "Bad Boys",
                                        5.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                                new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                                        ))
                        )
                ),
                new MovieList(
                        "Thrillers",
                        ComposableList.of(
                                new Video(
                                        65432445,
                                        "The Chamber",
                                        3.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                                        )),
                                new Video(
                                        675465,
                                        "Fracture",
                                        4.0,
                                        null,
                                        ComposableList.of(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                                new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                                        ))
                        )
                )
        );

        // Use one or more concatMap, map, and reduce calls to create a list with the following items (order doesn't matter)
        // [
        //   {"id": 675465,"title": "Fracture","boxart":"http://cdn-0.nflximg.com/images/2891/Fracture120.jpg" },
        //   {"id": 65432445,"title": "The Chamber","boxart":"http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg" },
        //   {"id": 654356453,"title": "Bad Boys","boxart":"http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg" },
        //   {"id": 70111470,"title": "Die Hard","boxart":"http://cdn-0.nflximg.com/images/2891/DieHard150.jpg" }
        // ];

        /*
         return movieLists.
         concatMap(function(movieList) {

         })
         */
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Zipping Lists

     Sometimes we need to combine two lists by progressively taking an item from each and combining the pair. If you visualize a zipper, where each side is a list, and each tooth is an item, you'll have a good idea of how the zip operation works.

     exercise 20: Combine videos and bookmarks by index

     Use a for loop to traverse the videos and bookmarks list at the same time. For each video and bookmark pair, create a {videoId, bookmarkId} pair and add it to the videoIdAndBookmarkIdPairs list.
     */

    public ComposableList<JSON> exercise20() {
        ComposableList<Video> videos = ComposableList.of(
                new Video(
                        70111470,
                        "Die Hard",
                        4.0
                ),
                new Video(
                        654356453,
                        "Bad Boys",
                        5.0
                ),
                new Video(
                        65432445,
                        "The Chamber",
                        4.0
                ),
                new Video(
                        675465,
                        "Fracture",
                        5.0
                )
        );
        
        ComposableList<Bookmark> bookmarks = ComposableList.of(
                new Bookmark(470, 23432),
                new Bookmark(453, 234324),
                new Bookmark(445, 987834)
        );

        ComposableList<JSON> videoIdAndBookmarkIdPairs = new ComposableList<JSON>();

        for (int counter = 0; counter < Math.min(videos.size(), bookmarks.size()); counter++) {
            // Insert code here to create a {videoId, bookmarkId} pair and add it to the videoIdAndBookmarkIdPairs list.
        }

        // return videoIdAndBookmarkIdPairs;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     exercise 21: Implement zip

     Let's add a static zip() function to the List type. The zip function accepts a combiner function, traverses each list at the same time, and calls the combiner function on the current item on the left-hand-side and right-hand-side. The zip function requires an item from each list in order to call the combiner function, therefore the list returned by zip will only be as large as the smallest input list.
     */
    // JSON.stringify(List.zip([1,2,3],[4,5,6], function(left, right) { return left + right })) == '[5,7,9]' accumulatedValue + currentValue; }); == [6];
    public static <T0,T1,R> ComposableList<R> zip(ComposableList<T0> left, ComposableList<T1> right, BiFunction<T0,T1, R> combinerFunction) {
        ComposableList<R> results = new ComposableList<R>();

        for (int counter = 0; counter < Math.min(left.size(), right.size()); counter++) {
            // Add code here to apply the combinerFunction to the left and right-hand items in the respective lists
        }

        // return results;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    ;
            
    /*
    exercise 22: Combine videos and bookmarks by index

    Let's repeat exercise 20, but this time let's use your new zip() function. For each video and bookmark pair, create a {videoId, bookmarkId} pair.
    */

    public ComposableList<JSON> exercise22() {
        ComposableList<Video> videos = ComposableList.of(
                new Video(
                70111470,
                "Die Hard",
                4.0
            ),
            new Video(
                654356453,
                "Bad Boys",
                5.0
            ),
            new Video(
                65432445,
                "The Chamber",
                4.0
            ),
            new Video(
                675465,
                "Fracture",
                5.0
            )
        );
        
        ComposableList<Bookmark> bookmarks = ComposableList.of(
            new Bookmark(470, 23432),
            new Bookmark(453, 234324),
            new Bookmark(445, 987834)
        );

        // return ComposableList.zip( //... finish this expression
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     exercise 23: Retrieve each video's id, title, middle interesting moment time, and smallest box art url.

     This is a variation of the problem we solved earlier. This time each video has an interesting moments collection, each representing a time during which a screenshot is interesting or representative of the title as a whole. Notice that both the boxarts and interestingMoments lists are located at the same depth in the tree. Retrieve the time of the middle interesting moment and the smallest box art url simultaneously with zip(). Return an {id, title, time, url} object for each video.
     */
    ComposableList<JSON> exercise23() {
        ComposableList<MovieList> movieLists = ComposableList.of(
                new MovieList(
                    "New Releases",
                    ComposableList.of(
                        new Video(
                                70111470,
                                "Die Hard",
                                4.0,
                                null,
                                ComposableList.of(
                                        new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                        new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                                ),
                                ComposableList.of(
                                        new InterestingMoment("End", 213432),
                                        new InterestingMoment("Start", 64534),
                                        new InterestingMoment("Middle", 323133)
                                )
                        ),
                        new Video(
                                654356453,
                                "Bad Boys",
                                5.0,
                                null,
                                ComposableList.of(
                                        new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                        new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                                ),
                                ComposableList.of(
                                        new InterestingMoment("End", 54654754),
                                        new InterestingMoment("Middle", 6575665)
                                )
                        )
                    )
            ),
            new MovieList(
                "Instant Queue",
                ComposableList.of(
                    new Video(
                            65432445,
                            "The Chamber",
                            4.0,
                            null,
                            ComposableList.of(
                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                            ),
                            ComposableList.of(
                                new InterestingMoment("End", 132423),
                                new InterestingMoment("Start", 54637425),
                                new InterestingMoment("Middle", 3452343)
                            )
                    ),
                    new Video(
                            675465,
                            "Fracture",
                            5.0,
                            null,
                            ComposableList.of(
                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                            ),
                            ComposableList.of(
                                new InterestingMoment("End", 45632456),
                                new InterestingMoment("Start", 234534),
                                new InterestingMoment("Middle", 3453434)
                            )
                    )
                )
            )
        );

        //------------ COMPLETE THIS EXPRESSION --------------
    /*
        return movieLists.
            concatMap(function(movieList) {

            });
    */
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Powerful Queries

     Now that we've learned the five operators let's flex our muscles and write some powerful queries.

     exercise 24: Converting from Lists to Trees

     When information is organized in a tree like a JSON expression, relationships point from parent to child. In relational systems like databases, relationships point from children to their parents. Both ways of organizing information are equivalent, and depending on the circumstances, we might get data organized in one way or another. It may surprise you to learn that you can use the 5 query functions you already know to easily convert between these representations. In other words, not only can you query lists from trees, you can query trees from lists.

     We have 2 lists each containing lists, and videos respectively. Each video has a listId field indicating its parent list. We want to build a list of list objects, each with a name and a videos list. The videos list will contain the video's id and title. In other words we want to build the following structure:

     ComposableList.of(
        new MovieList(
            "New Releases",
            ComposableList.of(
                new Video(
                    65432445,
                    "The Chamber"
                ),
                new Video(
                    675465,
                    "Fracture"
                )
            )
        ),
        new MovieList(
            "Thrillers",
            ComposableList.of(
                new Video(
                    70111470,
                    "Die Hard"
                ),
                new Video(
                    654356453,
                    "Bad Boys"
                )
            )
        )
     )

     Note: please make sure when creating objects (both lists and videos) that you add properties in the same order as above. This doesn't impact the correctness of your code, but the verifier expects properties to be created in this order.
     */
    class MovieListRow {

        private int id;
        private String name;

        public MovieListRow(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    class VideoRow {

        private int listId;
        private int videoId;
        private String name;

        public VideoRow(int listId, int videoId, String name) {
            this.listId = listId;
            this.videoId = videoId;
            this.name = name;
        }
    }

    public ComposableList<List<MovieList>> exercise24() {
        ComposableList<MovieListRow> lists = ComposableList.of(
                new MovieListRow(
                        5434364,
                        "New Releases"
                ),
                new MovieListRow(
                        65456475,
                        "Thrillers"
                )
        );
        ComposableList<VideoRow> videos = ComposableList.of(
                        new VideoRow(
                                5434364,
                                65432445,
                                "The Chamber"
                        ),
                        new VideoRow(
                                5434364,
                                675465,
                                "Fracture"
                        ),
                        new VideoRow(
                                65456475,
                                70111470,
                                "Die Hard"
                        ),
                        new VideoRow(
                                65456475,
                                654356453,
                                "Bad Boys"
                        )
                );

        // return lists. // complete this expression
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     Looks like you figured out that you can use map and filter to join two different lists by a key. Now let's try a more complex example...

     exercise 25: Converting from Lists to Deeper Trees

     Let's try creating a deeper tree structure. This time we have 4 seperate lists each containing lists, videos, boxarts, and bookmarks respectively. Each object has a parent id, indicating its parent. We want to build a list of list objects, each with a name and a videos list. The videos list will contain the video's id, title, bookmark time, and smallest boxart url. In other words we want to build the following structure:

    ComposableList.of(
        new MovieList(
            "New Releases",
                ComposableList.of(
                    new Video(
                        "The Chamber",
                        32432,
                        5.0
                    ),
                    new Video(
                        675465,
                        "Fracture",
                        3.0
                    )
                )
            ),
        new MovieList(
            "Thrillers",
            ComposableList.of(
                new Video(
                    70111470,
                    "Die Hard",
                    5.0
                ),
                new Video(
                    654356453,
                    "Bad Boys",
                    4.0
                )
            )
        )
    )
     
     */
    class BoxArtRow {

        public int videoId;
        public int width;
        public int height;
        public String url;

        public BoxArtRow(int videoId, int width, int height, String url) {
            this.videoId = videoId;
            this.width = width;
            this.height = height;
            this.url = url;
        }
    }

    class BookmarkRow {

        public int videoId;
        public int bookmarkId;

        public BookmarkRow(int videoId, int bookmarkId) {
            this.videoId = videoId;
            this.bookmarkId = bookmarkId;
        }
    }

    public ComposableList<List<MovieList>> exercise25() {
        ComposableList<MovieListRow> lists = ComposableList.of(
            new MovieListRow(
                    5434364,
                    "New Releases"
            ),
            new MovieListRow(
                    65456475,
                    "Thrillers"
            )
        );
        ComposableList<VideoRow>  videos = ComposableList.of(
            new VideoRow(
                    5434364,
                    65432445,
                    "The Chamber"
            ),
            new VideoRow(
                    5434364,
                    675465,
                    "Fracture"
            ),
            new VideoRow(
                    65456475,
                    70111470,
                    "Die Hard"
            ),
            new VideoRow(
                    65456475,
                    654356453,
                    "Bad Boys"
            )
        );
        ComposableList<BoxArtRow> boxarts = ComposableList.of(
                new BoxArtRow(65432445, 130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                new BoxArtRow(65432445, 200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg"),
                new BoxArtRow(675465, 200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                new BoxArtRow(675465, 120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                new BoxArtRow(675465, 300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg"),
                new BoxArtRow(70111470, 150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                new BoxArtRow(70111470, 200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg"),
                new BoxArtRow(654356453, 200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                new BoxArtRow(654356453, 140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
        );
        ComposableList<BookmarkRow> bookmarks = ComposableList.of(
                new BookmarkRow(65432445, 32432),
                new BookmarkRow(675465, 3534543),
                new BookmarkRow(70111470, 645243),
                new BookmarkRow(654356453, 984934)
        );

        // return lists. // complete this expression
        
        throw new UnsupportedOperationException("Not implemented yet.");

    }
}
