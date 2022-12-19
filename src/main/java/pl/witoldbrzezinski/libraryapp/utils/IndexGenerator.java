package pl.witoldbrzezinski.libraryapp.utils;

public class IndexGenerator {
  private static final int MIN_INT = 10000;
  private static final int MAX_INT = 99999;

  public static String generateRandomIndex() {
    int random = MIN_INT + (int) (Math.random() * ((MAX_INT - MIN_INT) + 1));
    return Integer.toString(random);
  }
}
