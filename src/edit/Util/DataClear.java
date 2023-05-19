package edit.Util;

import edit.Action.Draw;
import edit.Action.Sticker;

public class DataClear {
    public DataClear() {
        Sticker.draggedImageID = 0;
        Draw.drawPane.getChildren().clear();
    }
}
