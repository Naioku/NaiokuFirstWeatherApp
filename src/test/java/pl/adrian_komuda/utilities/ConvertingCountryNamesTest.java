package pl.adrian_komuda.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/*
 * Polish localization -> names of country in Polish language
 */
class ConvertingCountryNamesTest {

    // private
    ConvertingCountryNames convertingCountryNames;

    @BeforeEach
    void setLocalization() {
        Locale.setDefault(new Locale("en"));
        convertingCountryNames = new ConvertingCountryNames();
    }

    @Tag("MyLogic")
    @Test
    void givenLocalNameInProperLocalizationShouldReturnISOName() {
        // given
        // convertingCountryNames
        String countryName = "Japan";
        String CountryISOName = "JP"; //countryISOName

        // when
        String result = convertingCountryNames.convertNameToISO(countryName);

        // then
        assertThat(result).isEqualTo(CountryISOName);
    }

    @Tag("MyLogic")
    @Test
    void givenWrongLocaleNameShouldReturnAnError() {
        // given
        // convertingCountryNames
        String countryName = "Japonia"; // name correct but in Polish, not in set locale.

        // when + then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> convertingCountryNames.convertNameToISO(countryName));
    }
}