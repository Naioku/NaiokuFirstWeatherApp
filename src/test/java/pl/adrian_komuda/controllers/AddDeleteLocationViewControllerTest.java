package pl.adrian_komuda.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import pl.adrian_komuda.App;
import pl.adrian_komuda.model.CustomLocations;
import pl.adrian_komuda.utilities.ConvertingCountryNames;
import pl.adrian_komuda.utilities.ErrorMessages;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.views.ViewFactory;
import pl.adrian_komuda.weather_client.WeatherClient;
import pl.adrian_komuda.weather_client.my_dtos.City;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class AddDeleteLocationViewControllerTest {

    ConvertingCountryNames convertingCountryNames = mock(ConvertingCountryNames.class);
    WeatherClient weatherClient = mock(WeatherClient.class);
    CustomLocations customLocations = mock(CustomLocations.class);

    @BeforeEach
    public void runAppToTests(FxRobot fxRobot) throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App::new);
        FxToolkit.showStage();
        ViewFactory.init(
                convertingCountryNames,
                weatherClient,
                customLocations
        );
        WaitForAsyncUtils.waitForFxEvents();

        fxRobot.clickOn("#addDeleteLocation");
    }

    @AfterEach
    public void tearDown(FxRobot fxRobot) throws Exception {
        FxToolkit.hideStage();
        fxRobot.release(new KeyCode[]{});
        fxRobot.release(new MouseButton[]{});
    }

    @Test
    void withNoExceptionThrownLocationShouldBeAdded(FxRobot fxRobot) throws ApiException {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willReturn("Proper ISO code");
        given(weatherClient.getCityInfo(any(String.class), any(String.class))).willReturn(new City() {
            @Override
            public String getName() {
                return "Name";
            }

            @Override
            public float getLatitude() {
                return 0;
            }

            @Override
            public float getLongitude() {
                return 0;
            }
        });

        // when
        fxRobot.clickOn("#applyButton");

        // then
        verify(customLocations).addLocation(any(), any(), any());
        verify(customLocations).saveLocationsToFile();
    }

    @Test
    void whenIllegalArgumentExceptionThrownLocationShouldNotBeAdded(FxRobot fxRobot) {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willThrow(IllegalArgumentException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        verify(customLocations, never()).addLocation(any(), any(), any());
        verify(customLocations, never()).saveLocationsToFile();
    }

    @Test
    void whenIllegalArgumentExceptionThrownErrorLabelShouldHaveProperValue(FxRobot fxRobot) {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willThrow(IllegalArgumentException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        Label errorLabel = fxRobot.lookup("#errorLabel").tryQueryAs(Label.class).get();
        assertThat(errorLabel.getText()).isEqualTo(ErrorMessages.WEATHER_API_TYPO_IN_ADDING_LOCATION);
    }

    @Test
    void whenArrayIndexOutOfBoundsExceptionLocationShouldNotBeAdded(FxRobot fxRobot) throws ApiException {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willReturn("ISO code");
        given(weatherClient.getCityInfo(any(String.class), any(String.class))).willThrow(ArrayIndexOutOfBoundsException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        verify(customLocations, never()).addLocation(any(), any(), any());
        verify(customLocations, never()).saveLocationsToFile();
    }

    @Test
    void whenArrayIndexOutOfBoundsExceptionThrownErrorLabelShouldHaveProperValue(FxRobot fxRobot) throws ApiException {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willReturn("ISO code");
        given(weatherClient.getCityInfo(any(String.class), any(String.class))).willThrow(ArrayIndexOutOfBoundsException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        Label errorLabel = fxRobot.lookup("#errorLabel").tryQueryAs(Label.class).get();
        assertThat(errorLabel.getText()).isEqualTo(ErrorMessages.WEATHER_API_TYPO_IN_ADDING_CITY);
    }

    @Test
    void whenApiExceptionLocationShouldNotBeAdded(FxRobot fxRobot) throws ApiException {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willReturn("ISO code");
        given(weatherClient.getCityInfo(any(String.class), any(String.class))).willThrow(ApiException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        verify(customLocations, never()).addLocation(any(), any(), any());
        verify(customLocations, never()).saveLocationsToFile();
    }

    @Test
    void whenApiExceptionThrownErrorLabelShouldHaveProperValue(FxRobot fxRobot) throws ApiException {
        // given
        given(convertingCountryNames.convertNameToISO(any(String.class))).willReturn("ISO code");
        given(weatherClient.getCityInfo(any(String.class), any(String.class))).willThrow(ApiException.class);

        // when
        fxRobot.clickOn("#applyButton");

        // then
        Label errorLabel = fxRobot.lookup("#errorLabel").tryQueryAs(Label.class).get();
        assertThat(errorLabel.getText()).isEqualTo(ErrorMessages.WEATHER_API_COULD_NOT_LOAD_CITY_DATA);
    }

//    @Test
//    void dasldskfjlsdfk(FxRobot fxRobot) {
//        // given
//        // when
//        fxRobot.clickOn("#countryTextField");
//        fxRobot.write("Japan");
//        fxRobot.clickOn("#cityTextField");
//        fxRobot.write("Nagoya");
//        TreeView treeView = fxRobot.lookup("#tableView").tryQueryAs(TreeView.class).get();
//        ObservableList<TreeItem<String>> countryTreeItem = treeView.getRoot().getChildren();
//    }
}