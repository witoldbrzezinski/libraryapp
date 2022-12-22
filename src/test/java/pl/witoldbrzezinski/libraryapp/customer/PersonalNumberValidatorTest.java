package pl.witoldbrzezinski.libraryapp.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PersonalNumberValidatorTest {
    private final PersonalNumberValidator personalNumberValidator = new PersonalNumberValidator();

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "85121139822",
                    "93091379186",
                    "83111697583",
                    "85041568728",
                    "00242758345",
                    "90112568721"
            })
    void shouldSetCorrectGenderForFemale(String personalNumber) {
        assertEquals(Gender.FEMALE, personalNumberValidator.getGender(personalNumber));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "01282636631",
                    "02270248391",
                    "96110948697",
                    "80110699738",
                    "79071891914",
                    "90051618411"
            })
    void shouldSetCorrectGenderForMale(String personalNumber) {
        assertEquals(Gender.MALE, personalNumberValidator.getGender(personalNumber));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "90051618411",
                    "88071425591",
                    "77091398297",
                    "00281718478",
                    "83111697583",
                    "85041568728"
            })
    void shouldReturnTrueWhenPersonalNumberIsValid(String personalNumber) {
        assertTrue(personalNumberValidator.isValid(personalNumber));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(
            strings = {
                    "90051618412123",
                    "zzzzzzzzzz",
                    "770913982",
                    "1234",
                    "831116ąć7583",
                    "xxxxxxxxxxx"
            })
    void shouldReturnFalseWhenPersonalNumberIsInvalid(String personalNumber) {
        assertFalse(personalNumberValidator.isValid(personalNumber));
    }

    @Test
    void shouldReturnCorrectDateOfBirthFromPersonalNumber() {
        String personalNumber = "89091206218";
        LocalDate expectedDate = LocalDate.of(1989, 9, 12);
        LocalDate actualDate = personalNumberValidator.getBirthDate(personalNumber);
        assertEquals(expectedDate, actualDate);
    }

}