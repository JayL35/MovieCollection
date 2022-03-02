import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortString(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String temp = listToSort.get(j);
      String tempTitle = temp;

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();

    // arraylist to hold search results
    ArrayList<String> results = new ArrayList<String>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String actors = movies.get(i).getCast();
      String[] actorList = actors.split("\\|");
      for (String input : actorList)
      {
        String lower = input.toLowerCase();
        if (lower.indexOf(searchTerm) != -1)
        {
          results.add(input);
        }
      }
    }
    sortString(results);

    for (int i = 0; i < results.size(); i++)
    {
      for (int j = i + 1; j < results.size(); j++)
      {
        if (results.get(i).equals(results.get(j)))
        {
          results.remove(j);
          j--;
        }
      }
    }

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String cast = results.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + cast);
    }

    System.out.println("Which cast would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String actor = results.get(choice - 1);
    ArrayList<Movie> castMovies = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++)
    {
      String casts = movies.get(i).getCast();
      String[] actorList2 = casts.split("\\|");
      for (String check : actorList2)
      {
        if (check.equals(actor)) {
          castMovies.add(movies.get(i));
        }
      }
    }
    sortResults(castMovies);

    for (int i = 0; i < castMovies.size(); i++)
    {
      Movie movies = castMovies.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + movies.getTitle());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice2 = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = castMovies.get(choice2 - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();
      String movieWords = movies.get(i).getKeywords();
      movieWords = movieWords.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1 || movieWords.indexOf(searchTerm) != - 1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    ArrayList<String> results = new ArrayList<String>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String genre = movies.get(i).getGenres();
      String[] genres = genre.split("\\|");
      for (String input : genres)
      {
        results.add(input);
      }
    }
    sortString(results);

    for (int i = 0; i < results.size(); i++)
    {
      for (int j = i + 1; j < results.size(); j++)
      {
        if (results.get(i).equals(results.get(j)))
        {
          results.remove(j);
          j--;
        }
      }
    }
    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String genre3 = results.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + genre3);
    }


    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();
    String getGenre = results.get(choice - 1);
    ArrayList<Movie> allMovies = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++)
    {
      String genre2 = movies.get(i).getGenres();
      if (genre2.indexOf(getGenre) != -1)
      {
        allMovies.add(movies.get(i));
      }
    }
    sortResults(allMovies);

    // now, display them all to the user
    for (int i = 0; i < allMovies.size(); i++)
    {
      String title = allMovies.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice2 = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = allMovies.get(choice2 - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRated()
  {
//    ArrayList<Movie> results = new ArrayList<Movie>();
//
//    // search through ALL movies in collection
//    for (int i = 0; i < movies.size(); i++)
//    {
//      Double rating = movies.get(i).getUserRating();
//      if (results.size() == 50)
//      {
//        for (int j = 0; j < results.size() - 1; j++)
//        {
//          if (results.get(j).getUserRating() < rating)
//          {
//            results.set(j, movies.get(i));
//            results.remove(results.size() - 1);
//          }
//          j = results.size();
//        }
//      }
//      else
//      {
//        for (int j = 0; j < results.size() - 1; j++)
//        {
//          if (results.get(j).getUserRating() < rating)
//          {
//            results.set(j, movies.get(i));
//            j = results.size();
//          }
//          else
//          {
//            results.add(movies.get(i));
//          }
//        }
//      }
//    }
//
//    // now, display them all to the user
//    for (int i = 0; i < results.size(); i++)
//    {
//      String movies = results.get(i).getTitle();
//      Double ratings = results.get(i).getUserRating();
//
//      // this will print index 0 as choice 1 in the results list; better for user!
//      int choiceNum = i + 1;
//
//      System.out.println("" + choiceNum + ". " + movies + ": " + ratings);
//    }
//
//    System.out.println("Which movie would you like to learn more about?");
//    System.out.print("Enter number: ");
//
//    int choice = scanner.nextInt();
//    scanner.nextLine();
//
//    Movie selectedMovie = results.get(choice - 1);
//
//    displayMovieInfo(selectedMovie);
//
//    System.out.println("\n ** Press Enter to Return to Main Menu **");
//    scanner.nextLine();

  }
  
  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] movieFromCSV = line.split(",");

        // pull out the data for this movie
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Float.parseFloat(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create Movie object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Movie object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}