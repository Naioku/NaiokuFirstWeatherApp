package pl.adrian_komuda.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
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
import pl.adrian_komuda.model.ColorTheme;
import pl.adrian_komuda.views.ViewFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ColorThemePickerTest {

    ChoiceBox<ColorTheme> colorThemeChoiceBox;

    @BeforeEach
    public void runAppToTests(FxRobot fxRobot) throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents();

        fxRobot.clickOn("#optionsButton");
        colorThemeChoiceBox = (ChoiceBox<ColorTheme>) fxRobot.lookup("#colorThemeChoiceBox").tryQueryAs(ChoiceBox.class).get();
    }

    @AfterEach
    public void tearDown(FxRobot fxRobot) throws Exception {
        FxToolkit.hideStage();
        fxRobot.release(new KeyCode[]{});
        fxRobot.release(new MouseButton[]{});
    }

    @Test
    void afterSettingUpColorThemePickerItemsInListShouldBeConjugatedWithColorThemeEnumValues(FxRobot fxRobot) {
        // then
        assertThat(colorThemeChoiceBox.getItems()).isEqualTo(FXCollections.observableArrayList(ColorTheme.values()));
    }

    @Test
    void afterSettingUpColorThemePickerValueShouldBeConjugatedWithViewFactoryValue(FxRobot fxRobot) {
        // then
        assertThat(colorThemeChoiceBox.getValue()).isEqualTo(ViewFactory.getColorTheme());
    }

}