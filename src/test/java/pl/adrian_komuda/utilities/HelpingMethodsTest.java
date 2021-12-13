package pl.adrian_komuda.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HelpingMethodsTest {

    @BeforeEach
    void setDefaultLocale() {
        Locale.setDefault(new Locale("en"));
    }

    /**
     * Format HH:mm
     * Timezone offset not included
     */
    @Test
    void givenUNIXSecondsShouldReturnHourInString() {
        // given
        int seconds = 1639386382; // Monday, Dec 13, 2021 09:06:22.000 AM

        // when
        String result = HelpingMethods.convertUNIXSecondsToHourString(seconds);

        // then
        assertThat(result).isEqualTo("09:06");
    }

    /**
     * Format MM.dd (EEE)
     * Timezone offset not included
     */
    @Test
    void givenUNIXSecondsShouldReturnDateInString() {
        // given
        int seconds = 1639386382; // Monday, Dec 13, 2021 09:06:22.000 AM

        // when
        String result = HelpingMethods.convertUNIXSecondsToDate(seconds);

        // then
        assertThat(result).isEqualTo("12.13 (Mon)");
    }

    @Test
    void givenVelocityInMetersPerSecondShouldReturnVelocityInKilometersPerHour() {
        // given
        float velocity = 10;

        // when
        float result = HelpingMethods.mPerSecToKmPerH(10);

        // then
        assertThat(result).isEqualTo(36F);
    }
}