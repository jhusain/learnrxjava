package learnrxjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import learnrxjava.types.Bookmark;
import learnrxjava.types.BoxArt;
import learnrxjava.types.ComposableList;
import learnrxjava.types.InterestingMoment;
import learnrxjava.types.JSON;
import learnrxjava.types.MovieList;
import learnrxjava.types.Video;

/**
 * Mastering concurrency is challenging, But we can make it much easier by simply choosing the right 
 * abstraction to model an asynchronous operation, and then using simple composition operations to 
 * glue different instances of these abstractions together to build solutions to complex problems.
 * 
 * To learn how stream composition works, we will first learn how to use the composition methods 
 * (map, filter, flatMap, reduce, zip) to compose together a data structure with which most developers 
 * are already familiar: a list.
 */
public class ComposableListSolutions<T> extends ArrayList<T> implements ComposableList<T> {
    private static final long serialVersionUID = 1L;

    /*
    Exercise 1: Consuming the data in a list

    Most Java developers are accustomed to consuming the data in a list using the for each loop:
     */
    public static void exercise1() {
        ComposableListSolutions<String> names = ComposableListSolutions.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        for (String name : names) {
            System.out.println(name);
        }
    }

    /*
    Exercise 2: Consuming the data in a list using the new forEach() method
    
    Java 8 introduces the forEach method() to all collections as well as the new Stream
    type. The forEach method accepts a lambda, and invokes it once for each item in the
    collection.

    The forEach method is _more versatile_ than the Java for each loop syntax, 
    because it can execute synchronously over the data in a List, or asynchronously 
    as each value in a Reactive Stream arrives. In other words the forEach method 
    allows us to consume the data in all collections the same way regardless of 
    whether the collection is a List, Stream, or an Reactive Stream (aka Observable). 

    From now on we will _always_ use the forEach method instead of the Java for each loop 
    so that we can get comfortable with this new method. Note that the code is very 
    similar, and we get the same result whether we are using the Java for each syntax 
    or the forEach method.

    The code below performs the same operation as the previous exercise, but this time
    uses the forEach method instead of the Java forEach loop.
     */
    public static void exercise2() {
        ComposableListSolutions<String> names = ComposableListSolutions.of("Ben", "Jafar", "Matt", "Priya", "Brian");

        names.forEach(name -> {
            System.out.println(name);
        });
    }

    /*
    Exercise 3: Projecting a list

    Applying a function to a value and creating a new value is called a 
    projection. To project contents of one List into another, we apply 
    a projection function to each item in the List and collect the results in 
    a new List.

    Project a list of videos into a list of {id,title} JSON objects using forEach.

    For each video, add a projected {id, title} json to the videoAndTitlePairs 
    List. You can create JSON objects like this: 
    json("id", 23, "title", "Die Hard")
     */
    public static ComposableList<JSON> exercise3() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
                new Video(70111470, "Die Hard", 4.0),
                new Video(654356453, "Bad Boys", 5.0),
                new Video(65432445, "The Chamber", 4.0),
                new Video(675465, "Fracture", 5.0));

        ComposableListSolutions<JSON> videoAndTitlePairs = new ComposableListSolutions<JSON>();
        
        newReleases.forEach(video -> {
           videoAndTitlePairs.add(json("id", video.id, "title", video.title));
        });
        
        return videoAndTitlePairs;
    }

    /*
    Exercise 4: Implement map() to help with list projection

    All list projections share two operations in common:

    1. Traverse the source list
    2. Add each item's projected value to a new list

    Why not create a helper method for these common operations?

    If our ComposableList had a map() method, it would make projections easier. 
    The map method accepts the projection function to be applied to each item 
    in the source List, and returns a List of the projected results.

    ComposableList.of(1,2,3).map(x -> x + 1) is equivalent to ComposableList.of(2,3,4)

    Finish the implementation of ComposableList's map method below:
     */
    public <R> ComposableList<R> map(Function<T, R> projectionFunction) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();
        this.forEach(itemInList -> {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the projectionFunction to each item in the list and add
            // each result to the results list.
            // Note that you can apply a projectionFunction to a value like this:
            //  projectionFunction.apply(5)
            // ------------ INSERT CODE HERE! ----------------------------
            results.add(projectionFunction.apply(itemInList));
        });
        
        return results;
    }
    
    /*
    Exercise 5: Use map() to project a ComposableList of videos into a stream of {id,title} JSON

    Let's repeat exercise 3 and collect {id, title} JSON for each video in newReleases, but this time we'll 
    use ComposableList's map method instead of the forEach method.
    */
    public static ComposableList<JSON> exercise5() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
            new Video(70111470, "Die Hard", 4.0),
            new Video(654356453, "Bad Boys", 5.0),
            new Video(65432445, "The Chamber", 4.0),
            new Video(675465, "Fracture", 5.0));
         
        // complete this expression 
        return newReleases.map(video -> {
           return json("id", video.id, "title", video.title); 
        });
    }

    /*
    Exercise 6: Filtering Lists

    Like projection, filtering a list is also a very common operation. To filter a list we apply a test to each 
    item and collect the items that pass into a new list.

    Use forEach() to loop through the videos in the newReleases list and, if a video has a rating of 5.0, add it 
    to the videos list.
     */
    public static ComposableList<Video> exercise6() {
        ComposableListSolutions<Video> newReleases = ComposableListSolutions.of(
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

        ComposableListSolutions<Video> highRatedVideos = new ComposableListSolutions<Video>();

        newReleases.forEach(video -> {
            // Insert code here that adds a video to the highRatedVideos list
            // if it has a rating of 5.0.
            if(video.rating == 5) {
                highRatedVideos.add(video);
            }
        });
        
        return highRatedVideos;
    }
    
    /*
    Exercise 7: Implement filter()

    Notice that every filter operation shares some operations in common:

    1. Traverse the list
    2. Add objects that pass a test to a new list

    Why not create a helper function for this common pattern?

    The filter() method accepts a Predicate function, applies it to each item in the
    List, and returns a new List of all of the items for which passed the test.
    A Predicate is a test function that returns true or false (ex. x -> x > 1).

    ComposableList.of(1,2,3).filter(x -> x > 1) returns ComposableList.of(2,3)
     */
    public ComposableList<T> filter(Predicate<T> predicateFunction) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        this.forEach(itemInList -> {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the predicateFunction to each item in the list. If the
            // result is true, add the result to the results list.
            // Note: you can apply the predicateFunction to a value like this:
            // predicateFunction.test(5)
            
            if(predicateFunction.test(itemInList)) {
                results.add(itemInList);
            }
            
            // ------------ INSERT CODE HERE! ----------------------------
        });

        return results;
    }

    /*
    Exercise 8: Query Data by Chaining Method Calls

    The map() and filter() functions let us express what data we want without requiring us to specify how the 
    data is collected. They can also be combined to create more complex queries.
    In this Exercise, we will chain map and filter to collect the IDs of videos that have a rating of 5.0.

     */
    public static ComposableList<Integer> exercise8() {
        ComposableListSolutions<Video> newReleases
            = ComposableListSolutions.of(
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
        
        return newReleases.filter(v -> v.rating == 5).map(v -> v.id);

        // ------------ INSERT CODE HERE! -----------------------------------
    }

    /*
    Exercise 9: Querying Trees

    Chaining together map() and filter() gives us a lot of expressive power. These high level functions let us 
    express _what_ data we want, instead of specifying _how_ we want it retrieved. Queries written with map and 
    filter are general enough to be applied to any data source including Lists, Java 8 Streams, and Reactive Streams. 
    That means that our queries can easily be modified to run in parallel or asynchronously simply by switching 
    the underlying data source from a List to a Java 8 Parallel Stream or an Rx Observable respectively.

    In addition to flat lists, sometimes we need to query Trees. In the exercise below, we will use two nested 
    forEach() calls to retrieve the video IDs from each movieList and accumulate the results into a new List.
     */
    public static ComposableList<Integer> exercise9() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
            new MovieList(
                "New Releases",
                ComposableListSolutions.of(
                        new Video(70111470, "Die Hard", 4.0),
                        new Video(654356453, "Bad Boys", 5.0))),
            new MovieList(
                "Dramas",
                ComposableListSolutions.of(
                        new Video(65432445, "The Chamber", 4.0),
                        new Video(675465, "Fracture", 5.0))));

        ComposableListSolutions<Integer> allVideoIdsInMovieLists = new ComposableListSolutions<Integer>();

        // ------------   INSERT CODE HERE!  -----------------------------------
        // Use two nested forEach loops to flatten the movieLists into a list of
        // video ids.
        
        movieLists.forEach(ml -> {
            ml.videos.forEach(v -> {
                allVideoIdsInMovieLists.add(v.id);
            });
        });

        // ------------   INSERT CODE HERE!  -----------------------------------
        
        return allVideoIdsInMovieLists;
    }
    
    /*
    Exercise 10: Implement concatMap()

    To flatten trees with nested forEach expressions we accumlate the results in a new flat list. Querying a List 
    of data from a Tree is a very common operation. Ideally we'd be able to use a helper function to do the heavy 
    lifting for us. 

    Let's implement the concatMap() method for List. Like map(), the concatMap() function applies a projection 
    function to each item in a list. However the projection function passed to concatMap tranforms each individual 
    value into a _list of values_, creating a tree structure. Before returning the tree, the concatMap method 
    flattens the tree by concatenating each inner list together in order. Here's an example of concatMap in action:
    
    ComposableList
        .of(
            ComposableList.of( 0,   1,   2),
            ComposableList.of(10,  11,  12),
            ComposableList.of(20,  21,  22))
        .concatMap(x -> x * 10)

    is equivalent to

    ComposableList.of(0, 10, 20, 100, 110, 120, 200, 210, 220)
     */   
    public <R> ComposableList<R> concatMap(
        Function<T, ComposableList<R>> projectionFunctionThatReturnsList) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();
        for (T itemInList : this) {
            // ------------ INSERT CODE HERE! ----------------------------
            // Apply the projection function to each item in the list.
            // This will create _another_ list. Then loop through each
            // inner list and add each item to the results list.
            // Note that you can apply a projectionFunction to a value like this:
            // projectionFunctionThatReturnsList.apply(5)
            
            ComposableList<R> l = projectionFunctionThatReturnsList.apply(itemInList);
            l.forEach(r -> {
                results.add(r);
            });

            // ------------ INSERT CODE HERE! ----------------------------
        }
        
        return results;
    }
    
    /*
    As you may have already guessed, the ComposableList class has the concatMap method. The concatMap method 
    may seem pretty abstract at first, and it may not be immediately obvious how it can be used to transform 
    data in a tree. In the next exercise we will combine this function with the map function to query a tree.

    exercise 11: Use map() and concatMap() to project and flatten the movieLists into a stream of video ids

    Hint: nest a map() call within a concatMap().
     */
    public static ComposableList<Integer> exercise11() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
                new MovieList(
                        "New Releases", // name
                        ComposableListSolutions.of( // videos
                                new Video(70111470, "Die Hard", 4.0),
                                new Video(654356453, "Bad Boys", 5.0))),
                new MovieList(
                        "Dramas",
                        ComposableListSolutions.of(
                                new Video(65432445, "The Chamber", 4.0),
                                new Video(675465, "Fracture", 5.0))));

        // ------------   INSERT CODE HERE!  -----------------------------------
        // Use map and concatAll to flatten the movieLists in a list of video ids.
        // return movieLists // finish expression
        // ------------   INSERT CODE HERE!  -----------------------------------
        // **************ANSWER START***************//
         return movieLists.
             concatMap(movieList -> 
                 movieList.videos.map(video -> video.id));
        // **************ANSWER END***************//
    }
    
    /*
    exercise 12: Retrieve id, title, and a 150x200 box art url for every video

    Mastering the combination of map() and concatMap() is key to effective functional programming. You're half way 
    there! Let's try a more complicated example...

    You've managed to flatten a tree that's two levels deep, let's try for three! Let's say that instead of a 
    single boxart url on each video, we had a collection of boxart objects, each with a different size and url. 
    Create a query that selects {id, title, boxart} json for every video in the movieLists. This time though, 
    the boxart property in the result will be the url of the boxart object with dimensions of 150x200px. Let's 
    see if you can solve this problem using _only_ map(), concatMap(), and filter(). No other methods are allowed, 
    including the List::get method.
    */
    public static ComposableList<JSON> exercise12() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
            new MovieList(
                "Instant Queue",
                ComposableListSolutions.of(
                    new Video(
                        70111470,
                        "Die Hard",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg")
                        )),
                    new Video(
                        654356453,
                        "Bad Boys",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg")
                        ))
                )
            ),
            new MovieList(
                "New Releases",
                ComposableListSolutions.of(
                    new Video(
                        65432445,
                        "The Chamber",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg")
                        )),
                    new Video(
                        675465,
                        "Fracture",
                        5.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                        ))
                )
            )
        );

        // Use one or more map, concatAll, and filter calls to create an ComposableList with the following items
        // {
        //   {"id": 70111470,"title": "Die Hard","boxart":"http://cdn-0.nflximg.com/images/2891/DieHard150.jpg" }
        //   {"id": 654356453,"title": "Bad Boys","boxart":"http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg" },
        //   {"id": 65432445,"title": "The Chamber","boxart":"http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg" },
        //   {"id": 675465,"title": "Fracture","boxart":"http://cdn-0.nflximg.com/images/2891/Fracture150.jpg" },
        // };

        return movieLists.concatMap(ml -> {
           return ml.videos.concatMap(v -> {
               ComposableList<BoxArt> boxart = v.boxarts.filter(ba -> ba.width == 150 && ba.height == 200);
               return boxart.map(ba -> {
                   return json("id", v.id, "title", v.title, "boxart", ba.url);                   
               });
           });
        });
    }

    /*
    Exercise 13: Reducing Lists

    Sometimes we need to aggregate the values in a collection and produce a single value. For example let's say 
    we need to find the largest integer in a list. We can't use a filter() operation, because it only examines 
    one item at a time. To find the largest integer we need to compare at least two values at a time.

    One approach could be to select an item in the list as the assumed largest number (perhaps the first item), 
    and then compare that value to every other item in the list. Each time we come across a number that was 
    larger than our assumed largest number we'd replace the assumed largest value with the larger value and continue.

    In this example we use a loop to find the largest box art. Each time we examine a new boxart we update a 
    variable with the currently known maximumSize. If the boxart is smaller than the maximum size, we discard it.
    If it's larger, we keep track of it. Finally we're left with a single boxart which must necessarily be the largest.
    */
    public static BoxArt exercise13() {
        ComposableListSolutions<BoxArt> boxarts = ComposableListSolutions.of(
            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
            new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg"),
            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
        );

        int currentSize = 0;
        int maxSize = -1;
        BoxArt largestBoxart = null;

        for (BoxArt boxart: boxarts) {
            currentSize = boxart.width * boxart.height;
            if (currentSize > maxSize) {
                // ****** INSERT CODE HERE ********
                // Assign the largestBoxart to the current boxart, and assign the maxSize to the currentSize.
                largestBoxart = boxart;
                maxSize = currentSize;
                // ****** INSERT CODE HERE ********
            }
        }

        return largestBoxart;
    }
    /*
    Exercise 14: Implement reduce()

    Combining the value produced from the last computation with each value until a single value is produced 
    is a very common pattern. Many types of operations require us to process at least two items a time like 
    computing the minimum or the maximum value in a List. Let's create a helper function called reduce() to 
    aggregate a single value from a list of values.

    Reduce recursively combines the results of the last aggregation with the next item in the list until the 
    values in the list are exhausted, and then returns a list containing only the last aggregation value. 
    Reduce uses the first value in the list as the initial aggregation value. For example 
    ComposableList.of(1,2,3).reduce((acc,curr) -> acc + curr) is structurally equivalent to ComposableList.of(6).  
    In the previous example, theBinary function is invoked three times. 

    The first time the binary lambda function is invoked the accumulated value acc is 1 (the first value in the 
    list) and the current value curr is 2 (the second value in the list). The next time the binary lambda function 
    is invoked, the accumulated value is the result of the previous function invocation (3) and the current value 
    is the next value in the list (3).  
     */
    public ComposableList<T> reduce(BiFunction<T, T, T> combiner) {
        int counter = 1;
        T accumulatedValue = null;

        // If the list is empty, return this
        if (this.size() == 0) {
            // ************ INSERT CODE HERE **************
            //  if the list is empty, return this
            return this;
            // ********************************************
        } else {
            accumulatedValue = this.get(0);

            // Loop through the list, feeding the current value and the result of
            // the previous computation back into the combiner function until
            // we've exhausted the entire list and are left with only one function.
            while (counter < this.size()) {
                // ****** INSERT CODE HERE ********
                // Set accumulatedValue to the result of passing accumulatedValue and the list value at the 
                // counter index to the combiner function.
                
                accumulatedValue = combiner.apply(accumulatedValue, this.get(counter));
                
                // ****** INSERT CODE HERE ********

                counter++;
            }

            return ComposableListSolutions.of(accumulatedValue);
        }
    }

    /*
    Exercise 15: Implement a reduce overload that accepts an initial value

    Sometimes when we reduce a list, we want the final reduced value to be a different type than the items 
    stored in the list. For example, let's say we have a list of videos and we want to reduce them to a 
    single map where the key is the video id and the value is the video's title.

    This overload of reduce accepts a initial value, which is used as the accumulated value the first time 
    the binary function is run instead of the first item in the List. Just as in the previous implementation 
    of reduce, the results of the previous function invocation and the current item in the list are passed 
    to the binary function recursively until there are no more items in the list to reduce. Finally The 
    reduce method produces a list containing only a single reduced value. Note that binary function will 
    always return the same type as the initial accumulated value. 

    ComposableList
        .of(
            1,
            2,
            3)
        .reduce(10, (acc, curr) -> acc + curr) is structurally equivalent to ComposableList.of(16).
    */
    public <R> ComposableList<R> reduce(R initialValue, BiFunction<R, T, R> combiner) {
        int counter;
        R accumulatedValue;

        // If the list is empty, do nothing
        if (this.size() == 0) {
            return new ComposableListSolutions<R>();
        } else {
            counter = 0;
            accumulatedValue = initialValue;

            // Loop through the list, feeding the current value and the result of
            // the previous computation back into the combiner function until
            // we've exhausted the entire list and are left with only one function.
            while (counter < this.size()) {
                accumulatedValue = combiner.apply(accumulatedValue, this.get(counter));
                counter++;
            }

            return ComposableListSolutions.of(accumulatedValue);
        }
    }

    /*        
    Exercise 16: Retrieve the largest rating.

    Use the right reduce overload to select the maximum value in a list of ratings.
    */
    public static ComposableList<Integer> exercise16() {
        ComposableListSolutions<Integer> ratings = ComposableListSolutions.of(2, 3, 5, 1, 4);
        // You should return a list containing only the largest rating. Remember that reduce always
        // returns a list with one item.

        // complete the expression below
        return ratings.reduce((max, item) -> {
            if(item > max) {
                return item;
            } else {
                return max;
            }
        });
    }
    /*
    Exercise 17: Retrieve url of the largest boxart

    Now let's try combining reduce() with our other functions to build more complex queries. Let's try combining 
    reduce() with map() to reduce multiple boxart objects to a single value: the url of the largest box art.
     */
    public static ComposableList<String> exercise17() {
        ComposableListSolutions<BoxArt> boxarts = ComposableListSolutions.of(
                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/Fracture150.jpg"),
                new BoxArt(425, 150, "http://cdn-0.nflximg.com/images/2891/Fracture425.jpg"),
                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
        );

        // You should return a list containing only the largest box art. Remember that reduce always
        // returns a list with one item.
        return boxarts.reduce((max, box) -> {
            int maxSize = max.height * max.width;
            int boxSize = box.height * box.width;
            if(boxSize > maxSize) {
                return box;
            } else {
                return max;
            }
        }).map(boxart -> boxart.url);
    }

    /*
    Exercise 18: Reducing with an initial value

    Sometimes when we reduce a list, we want the reduced value to be a different type than the items stored 
    in the list. Let's say we have a list of videos and we want to reduce them to a single map where the key 
    is the video id and the value is the video's title.
     */
    public static ComposableList<Map<Integer, String>> exercise18() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
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

        // **************** Uncomment code and finish the expression below ******************
        return videos.
            reduce(
            // Use an empty map as the initial value instead of the first item in
            // the list.
            new HashMap<Integer, String> (),
            (accumulatedMap, video) -> {
                // ************ INSERT CODE HERE ************
                // Remember that you the functions passed to map, filter, concatMap, reduce, and zip can only 
                // change objects created inside the function. That means that you cannot simply add the video to 
                // the accumulatedMap. You must make a copy of the accumulated map, and add the video title to that.
                // Granted, this is very inefficient. However there are libraries that provide immutable maps to make 
                // copying maps very efficient, allowing this to be a viable approach. For the purpose of this 
                // exercise simply copy the accumulatedMap into a new map, add the video information to the copy, 
                // and return the copy.
                // ************ INSERT CODE HERE ************
                
                accumulatedMap.put(video.id, video.title);
                
                return accumulatedMap;
            });
    }

    /*
    Exercise 19: Retrieve the id, title, and smallest box art url for every video.

    Nice work. Now let's try combining reduce() with our other functions to build more complex queries.
    
    This is a variation of the problem we solved earlier, where we retrieved the url of the boxart with a 
    width of 150px. This time we'll use reduce() instead of filter() to retrieve the _smallest_ box art in 
    the boxarts list.
     */
    public static ComposableList<JSON> exercise19() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
                new MovieList(
                    "New Releases",
                    ComposableListSolutions.of(
                        new Video(
                            70111470,
                            "Die Hard",
                            4.0,
                            null,
                            ComposableListSolutions.of(
                                    new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                    new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                            )),
                        new Video(
                            654356453,
                            "Bad Boys",
                            5.0,
                            null,
                            ComposableListSolutions.of(
                                    new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                    new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                            ))
                    )
                ),
                new MovieList(
                    "Thrillers",
                    ComposableListSolutions.of(
                        new Video(
                            65432445,
                            "The Chamber",
                            3.0,
                            null,
                            ComposableListSolutions.of(
                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                            )),
                        new Video(
                            675465,
                            "Fracture",
                            4.0,
                            null,
                            ComposableListSolutions.of(
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

        // Uncomment the code below and finish the expression.
        return movieLists.
            concatMap(ml -> {
                return ml.videos.<JSON>concatMap(v -> {
                    return v.boxarts.reduce((max, box) -> {
                        int maxSize = max.height * max.width;
                        int boxSize = box.height * box.width;
                        if(boxSize < maxSize) {
                            return box;
                        } else {
                            return max;
                        }
                    }).map(maxBoxart -> {
                        return json("id", v.id, "title", v.title, "boxart", maxBoxart.url);
                    });
                });
            });
    }

    /*
    Exercise 20: Zipping Lists

    Sometimes we need to combine the items in two different list by index. If you visualize a zipper, where each 
    side is a list, and each tooth is an item, you'll have a good idea of how the zip operation works. Before we 
    learn about zip() though, we'll solve a similar problem using forEach.

    In this exercise we'll combine videos and bookmarks by index. Use the forEach method to traverse the videos 
    and bookmarks list at the same time. For each video and bookmark pair, create a {videoId, bookmarkId} JSON 
    and add it to the videoIdAndBookmarkIdPairs list.
    */
    public static ComposableList<JSON> exercise20() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
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
        
        ComposableListSolutions<Bookmark> bookmarks = ComposableListSolutions.of(
                new Bookmark(470, 23432),
                new Bookmark(453, 234324),
                new Bookmark(445, 987834)
        );

        ComposableListSolutions<JSON> videoIdAndBookmarkIdPairs = new ComposableListSolutions<JSON>();

        for (int counter = 0; counter < Math.min(videos.size(), bookmarks.size()); counter++) {
            // Insert code here to create a {"videoId" : videoId, "bookmarkId" : bookmarkId} JSON 
            // using json() and add it to the videoIdAndBookmarkIdPairs list.
            videoIdAndBookmarkIdPairs.add(json("videoId", videos.get(counter).id, "bookmarkId", bookmarks.get(counter).id));
        }

         return videoIdAndBookmarkIdPairs;
    }

    /*
    Exercise 21: Implement zip

    Let's add a static zip() function to the ComposableList type. 
    The zip function accepts a combiner binary function, traverses each list at the same time, and calls 
    the combiner function on the current item on the left-hand-side and right-hand-side. The zip function 
    requires an item from each list in order to call the combiner function, therefore the list returned by 
    zip will only be as large as the smallest input list.

    ComposableList.zip(
        ComposableList.of(1,2,3), 
        ComposableList.of(4,5,6), 
        (x,y) -> x + y) is structurally equivalent to ComposableList.of(5,7,9)
     */
    public static <T0,T1,R> ComposableList<R> zip(ComposableList<T0> left, ComposableList<T1> right, BiFunction<T0,T1, R> combinerFunction) {
        ComposableListSolutions<R> results = new ComposableListSolutions<R>();

        for (int counter = 0; counter < Math.min(left.size(), right.size()); counter++) {
            // Add code here to apply the combinerFunction to the left and right-hand items in the 
            // respective lists, and add the result to the results List
            results.add(combinerFunction.apply(left.get(counter), right.get(counter)));
        }

        return results;
    }

    /*
    Exercise 22: Combine videos and bookmarks by index

    Let's repeat exercise 20, but this time let's use our new zip() function. For each video and bookmark pair, 
    create a {"videoId" : videoId, "bookmarkId" : bookmarkId} JSON pair.
    */

    public static ComposableList<JSON> exercise22() {
        ComposableListSolutions<Video> videos = ComposableListSolutions.of(
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
        
        ComposableListSolutions<Bookmark> bookmarks = ComposableListSolutions.of(
            new Bookmark(470, 23432),
            new Bookmark(453, 234324),
            new Bookmark(445, 987834)
        );

        //... finish this expression
        return ComposableListSolutions.zip(
                videos.map(v -> v.id), 
                bookmarks.map(b -> b.id), 
                (v, b) -> json("videoId", v, "bookmarkId", b));
    }

    /*
    Exercise 23: Retrieve each video's id, title, middle interesting moment time, and smallest box art url.

    This is a variation of the problem we solved earlier. This time each video has an interesting moments 
    collection, each representing a time during which a screenshot is interesting or representative of the 
    title as a whole. Notice that both the boxarts and interestingMoments lists are located at the same depth 
    in the tree. Retrieve the time of the middle interesting moment and the smallest box art url simultaneously 
    with zip(). Return an {id, title, time, url} JSON object for each video.
     */
    public static ComposableList<JSON> exercise23() {
        ComposableListSolutions<MovieList> movieLists = ComposableListSolutions.of(
            new MovieList(
                "New Releases",
                ComposableListSolutions.of(
                    new Video(
                        70111470,
                        "Die Hard",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                        ),
                        ComposableListSolutions.of(
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
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                            new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 54654754),
                            new InterestingMoment("Middle", 6575665)
                        )
                    )
                )
            ),
            new MovieList(
                "Instant Queue",
                ComposableListSolutions.of(
                    new Video(
                        65432445,
                        "The Chamber",
                        4.0,
                        null,
                        ComposableListSolutions.of(
                            new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                        ),
                        ComposableListSolutions.of(
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
                        ComposableListSolutions.of(
                            new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                            new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                            new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                        ),
                        ComposableListSolutions.of(
                            new InterestingMoment("End", 45632456),
                            new InterestingMoment("Start", 234534),
                            new InterestingMoment("Middle", 3453434)
                        )
                    )
                )
            )
        );

        //------------ COMPLETE THIS EXPRESSION --------------
        return movieLists.
            concatMap(movieList -> {
                return movieList.videos.<JSON>concatMap(video -> {
                    ComposableList<BoxArt> smallestBoxArt = video.boxarts.reduce((smallest, box) -> {
                        int smallestSize = smallest.height * smallest.width;
                        int boxSize = box.height * box.width;
                        if(boxSize < smallestSize) {
                            return box;
                        } else {
                            return smallest;
                        }
                    });
                    
                    ComposableList<InterestingMoment> moment = video.interestingMoments.filter(m -> m.type == "Middle");
                    return ComposableListSolutions.zip(smallestBoxArt, moment, (s, m) -> {
                        return json("id", video.id, "title", video.title, "time", m.time, "url", s.url);                        
                    });
                });
            });
    }

    // This function can be used to build JSON objects within an expression
    private static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

    // Static list builder method to allow us to easily build lists
    @SafeVarargs
    public static <T> ComposableListSolutions<T> of(T... args) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        for (T value : args) {
            results.add(value);
        }
        return results;
    }
    
    public static <T> ComposableListSolutions<T> of(List<T> list) {
        ComposableListSolutions<T> results = new ComposableListSolutions<T>();
        for (T value : list) {
            results.add(value);
        }
        return results;
    }
}
