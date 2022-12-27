package pl.witoldbrzezinski.libraryapp.customer;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PersonalNumberValidator {

  boolean isValid(String personalNumber) {
    if (personalNumber == null || !personalNumber.matches("[0-9]{11}")) {
      return false;
    } else {
      int[] weightsOfDigits = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
      int checkSum = 0;
      for (int i = 0; i < weightsOfDigits.length; i++) {
        checkSum +=
            weightsOfDigits[i] * Integer.parseInt(Character.toString(personalNumber.charAt(i)));
      }
      int modulo = checkSum % 10;
      int checkDigit;
      if (modulo == 0) {
        checkDigit = 0;
      } else {
        checkDigit = 10 - modulo;
      }
      return Integer.parseInt(Character.toString(personalNumber.charAt(10))) == checkDigit;
    }
  }

  LocalDate getBirthDate(String personalNumber) {
    int year = Integer.parseInt(personalNumber.substring(0, 2));
    int month = Integer.parseInt(personalNumber.substring(2, 4));
    int day = Integer.parseInt(personalNumber.substring(4, 6));
    if (month > 20) {
      month -= 20;
      year += 2000;
    } else {
      year += 1900;
    }
    return LocalDate.of(year, month, day);
  }

  Gender getGender(String personalNumber) {
    int number = Integer.parseInt(Character.toString(personalNumber.charAt(9)));
    if (number % 2 == 0) {
      return Gender.FEMALE;
    } else {
      return Gender.MALE;
    }
  }
}
