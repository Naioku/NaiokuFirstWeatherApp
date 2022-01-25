package pl.adrian_komuda.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HelpingMethodsTest {

    @BeforeEach
    void setDefaultLocale() {
        Locale.setDefault(new Locale("en"));
    }

    /*
     * Format HH:mm //I wonder if those comments are necessary
     * Timezone offset not included
     */
    @Tag("MyLogic")
    @Test
    void givenUNIXSecondsShouldReturnHourInString() {
        // given
        int seconds = 1639386382; // Monday, Dec 13, 2021 09:06:22.000 AM

        // when
        String result = HelpingMethods.convertUNIXSecondsToHourString(seconds);

        // then
        assertThat(result).isEqualTo("09:06");
    }

    /*
     * Format MM.dd (EEE)
     * Timezone offset not included
     */
    @Tag("MyLogic")
    @Test
    void givenUNIXSecondsShouldReturnDateInString() {
        // given
        int seconds = 1639386382; // Monday, Dec 13, 2021 09:06:22.000 AM

        // when
        String result = HelpingMethods.convertUNIXSecondsToDate(seconds);

        // then
        assertThat(result).isEqualTo("12.13 (Mon)");
    }

    @Tag("MyLogic")
    @Test
    void givenVelocityInMetersPerSecondShouldReturnVelocityInKilometersPerHour() {
        // given
        float velocity = 10; //unused variable

        // when
        float result = HelpingMethods.mPerSecToKmPerH(10);

        // then
        assertThat(result).isEqualTo(36F);
    }
}