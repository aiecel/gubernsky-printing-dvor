package com.aiecel.gubernskytypography.bot.api.keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBuilder {
    public static final int MAX_ROWS = 10;
    public static final int MAX_COLUMNS = 5;
    public static final int MAX_BUTTONS = 40;

    private final Button[][] buttons = new Button[MAX_COLUMNS][MAX_ROWS];
    private int buttonCount;

    public KeyboardBuilder add(Button button) {
        //find first empty row
        for (int row = 0; row < buttons[0].length; row++) {
            if (isRowEmpty(row)) {
                //add button to this row
                return add(button, row, 0);
            }
        }

        //if there's no empty rows then find first non empty place
        for (int row = 0; row < buttons[0].length; row++) {
            for (int column = 0; column < buttons.length; column++) {
                if (buttons[column][row] == null) {
                    //add button to this row
                    return add(button, row, column);
                }
            }
        }

        return this;
    }

    public KeyboardBuilder add(Button button, int row, int column) {
        if (buttonCount < MAX_BUTTONS) {
            buttons[column][row] = button;
            buttonCount++;
        }
        return this;
    }

    public Keyboard build() {
        List<List<Button>> allButtons = new ArrayList<>();

        for (int row = 0; row < buttons[0].length; row++) {
            if (!isRowEmpty(row)) {
                List<Button> rowButtons = new ArrayList<>();
                for (Button[] button : buttons) {
                    if (button[row] != null) {
                        rowButtons.add(button[row]);
                    }
                }
                allButtons.add(rowButtons);
            }
        }

        return new Keyboard(allButtons);
    }

    private boolean isRowEmpty(int row) {
        for (Button[] button : buttons) {
            if (button[row] != null) return false;
        }
        return true;
    }
}
