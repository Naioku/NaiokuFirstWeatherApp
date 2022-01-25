package pl.adrian_komuda.controllers;

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
import pl.adrian_komuda.views.ViewFactory;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class FontSizePickerTest {

    private Slider slider;

    @BeforeEach
    public void runAppToTests(FxRobot fxRobot) throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents();

        fxRobot.clickOn("#optionsButton");
        slider = fxRobot.lookup("#fontSizeSlider").tryQueryAs(Slider.class).get();
    }

    @AfterEach
    public void tearDown(FxRobot fxRobot) throws Exception {
        FxToolkit.hideStage();
        fxRobot.release(new KeyCode[]{});
        fxRobot.release(new MouseButton[]{});
    }

    @Test
    void afterSettingUpSliderShouldHaveMinValue0(FxRobot fxRobot) {
        // then
        assertThat(slider.getMin()).isEqualTo(0);
    }

    @Test
    void afterSettingUpSliderShouldHaveMaxValue2(FxRobot fxRobot) {
        // then
        assertThat(slider.getMax()).isEqualTo(2);
    }

    @Test
    void afterSettingUpSliderValueShouldBeConjugatedWithValueFromViewFactory(FxRobot fxRobot) {
       // then
        assertThat(slider.getValue()).isEqualTo(ViewFactory.getFontSize().ordinal());
    }

    @Test
    void afterSettingUpSliderMajorTickUnitShouldBe1(FxRobot fxRobot) {
        // then
        assertThat(slider.getMajorTickUnit()).isEqualTo(1);
    }

    @Test
    void afterSettingUpSliderMinorTickCountShouldBe1(FxRobot fxRobot) {
        // then
        assertThat(slider.getMinorTickCount()).isEqualTo(0);
    }

    @Test
    void afterSettingUpSliderBlockIncrementShouldBe1(FxRobot fxRobot) {
        // then
        assertThat(slider.getBlockIncrement()).isEqualTo(1);
    }

    @Test
    void afterSettingUpSliderSnapToTicksShouldBeEnabled(FxRobot fxRobot) {
        // then
        assertThat(slider.isSnapToTicks()).isEqualTo(true);
    }

    @Test
    void afterSettingUpSliderShowTickMarksShouldBeEnabled(FxRobot fxRobot) {
        // then
        assertThat(slider.isShowTickLabels()).isEqualTo(true);
    }

    @Test
    void afterSettingUpSliderShowTickLabelsShouldBeEnabled(FxRobot fxRobot) {
       // then
        assertThat(slider.isShowTickLabels()).isEqualTo(true);
    }
}