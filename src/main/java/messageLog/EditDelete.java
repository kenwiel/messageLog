package messageLog;

import java.awt.*;

public enum EditDelete {
    EDIT(Color.YELLOW),
    DELETE(Color.RED);

    private Color color;

    EditDelete(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
