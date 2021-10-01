package pl.adrian_komuda.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import pl.adrian_komuda.Model.ColorTheme;
import pl.adrian_komuda.Model.FontSize;
import pl.adrian_komuda.Views.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsViewController extends BaseController implements Initializable {

    @FXML
    private Slider fontSizeSlider;

    @FXML
    private ChoiceBox<ColorTheme> colorThemeChoiceBox;

    public OptionsViewController(String fxmlName) {
        super(fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpFontSizePicker();
        setUpColorThemePicker();
    }

    private void setUpFontSizePicker() {
        fontSizeSlider.setMin(0);
        fontSizeSlider.setMax(FontSize.values().length - 1);
        fontSizeSlider.setValue(ViewFactory.getFontSize().ordinal());
        fontSizeSlider.setMajorTickUnit(1);
        fontSizeSlider.setMinorTickCount(0);
        fontSizeSlider.setBlockIncrement(1);
        fontSizeSlider.setSnapToTicks(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
    }

    private void setUpColorThemePicker() {
        colorThemeChoiceBox.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        colorThemeChoiceBox.setValue(ViewFactory.getColorTheme());
    }

    @FXML
    void applyChangesButton() {
        ViewFactory.setColorTheme(colorThemeChoiceBox.getValue());
        ViewFactory.setFontSize(FontSize.values()[(int)(fontSizeSlider.getValue())]);
        ViewFactory.updateStyles();
    }
}
