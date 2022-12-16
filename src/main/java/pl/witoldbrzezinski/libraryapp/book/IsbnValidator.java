package pl.witoldbrzezinski.libraryapp.book;

import org.springframework.stereotype.Component;

@Component
public class IsbnValidator {

  private static final int NEW_ISBN_LENGTH = 13;
  private static final int OLD_ISBN_LENGTH = 10;
  private static final int LAST_INDEX_NEW = 12;
  public static final int LAST_INDEX_OLD = 9;

  private boolean validateNewIsbn13(String isbn) {
    if (isbn == null) {
      return false;
    }
    if (isbn.length() != NEW_ISBN_LENGTH) {
      return false;
    }
    try {
      int total = 0;
      for (int i = 0; i < LAST_INDEX_NEW; i++) {
        int digit = Integer.parseInt(isbn.substring(i, i + 1));
        total += (i % 2 == 0) ? digit : digit * 3;
      }
      int checksum = 10 - (total % 10);
      if (checksum == 10) {
        checksum = 0;
      }
      return checksum == Integer.parseInt(isbn.substring(LAST_INDEX_NEW));
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  private boolean validateOldIsbn10(String isbn) {
    if (isbn == null) {
      return false;
    }
    if (isbn.length() != OLD_ISBN_LENGTH) {
      return false;
    }
    try {
      int total = 0;
      for (int i = 0; i < LAST_INDEX_OLD; i++) {
        int digit = Integer.parseInt(isbn.substring(i, i + 1));
        total += ((10 - i) * digit);
      }
      String checksum = Integer.toString((11 - (total % 11)) % 11);
      if ("10".equals(checksum)) {
        checksum = "X";
      }
      return checksum.equals(isbn.substring(LAST_INDEX_OLD));
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  private String replaceAllHyphens(String isbn) {
    if (isbn == null) {
      return "";
    }
    return isbn.replaceAll("-", "");
  }

  boolean isValid(String isbn) {
    String parsedIsbn = replaceAllHyphens(isbn);
    if (parsedIsbn.length() == 13) {
      return validateNewIsbn13(parsedIsbn);
    } else if (parsedIsbn.length() == 10) {
      return validateOldIsbn10(parsedIsbn);
    } else {
      return false;
    }
  }
}
