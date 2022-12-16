package pl.witoldbrzezinski.libraryapp.book;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class IsbnValidatorTest {

    private final IsbnValidator isbnValidator = new IsbnValidator();
    @ParameterizedTest
    @ValueSource(
            strings = {
                    "8386858613",
                    "83-86858-61-3",
                    "9788304036277",
                    "97-88304036-27-7",
                    "9780606170970",
                    "060632349X"
            })
    void shouldReturnTrueWhenIsbnIsValid(String isbn){
    assertTrue(isbnValidator.isValid(isbn));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(
            strings = {
                    "8386858614",
                    "83-86858-61-9",
                    "9788304036275",
                    "97-88304036-27-6",
                    "9780606",
                    "XYZABCDEFA",
                    "XYZABCDEFAUJH",
                    "9780606978060697806069780606"
            })
    void shouldReturnFalseWhenIsbnIsInvalid(String isbn){
        assertFalse(isbnValidator.isValid(isbn));
    }



}