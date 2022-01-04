package pl.adrian_komuda.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import pl.adrian_komuda.App;
import pl.adrian_komuda.model.CustomLocations;
import pl.adrian_komuda.utilities.ConvertingCountryNames;
import pl.adrian_komuda.utilities.custom_exceptions.ApiException;
import pl.adrian_komuda.weather_client.WeatherClient;
import pl.adrian_komuda.weather_client.my_dtos.City;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class AddDeleteLocationViewControllerTest {

    @InjectMocks
    AddDeleteLocationViewController addDeleteLocationViewController;

    @Mock
    TextField countryTextField;

    @Mock
    TextField cityTestField;

    @Mock
    ConvertingCountryNames convertingCountryNames;

    @Mock
    WeatherClient weatherClient;

    @BeforeEach
    public void runAppToTests(FxRobot fxRobot) throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents();

        fxRobot.clickOn("#addDeleteLocale");
        BorderPane borderPane = fxRobot.lookup("#borderPane").tryQueryAs(BorderPane.class).get();
        FXMLLoader loader = new FXMLLoader();
//        loader.set
    }

    @AfterEach
    public void tearDown(FxRobot fxRobot) throws Exception {
        FxToolkit.hideStage();
        fxRobot.release(new KeyCode[]{});
        fxRobot.release(new MouseButton[]{});
    }

    @Test
    void afterTypingProperCountryAndCityNameLocaleShouldBeAdded(FxRobot fxRobot) throws ApiException {
//        when(countryTextField.getText()).thenReturn(getProperCountryNameInEnglish());
//        when(cityTestField.getText()).thenReturn(getProperCityNameInEnglish());
//        ConvertingCountryNames convertingCountryNames = mock(ConvertingCountryNames.class);
//        WeatherClient weatherClient = mock(WeatherClient.class);
//        ConvertingCountryNames convertingCountryNames = mock(ConvertingCountryNames.class);
//        WeatherClient weatherClient = mock(WeatherClient.class);
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
        MockedStatic<CustomLocations> customLocationsMockedStatic = mockStatic(CustomLocations.class);
//        customLocationsMockedStatic.when(() -> CustomLocations.addLocale(any(TreeView.class), any(String.class), any(City.class)))
//                .thenReturn()

        // given + when

        // then
        fxRobot.clickOn("#applyButton");
        customLocationsMockedStatic.verify(() -> CustomLocations.addLocation(any(TreeView.class), any(String.class), any(City.class)));
        customLocationsMockedStatic.verify(CustomLocations::saveLocationsToFile);



    }

    private String getProperCountryNameInEnglish() { return "Japan"; }
    private String getProperCityNameInEnglish() { return "Nagoya"; }

}