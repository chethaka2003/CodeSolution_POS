package Services;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class keyBoardService {

    //This class going to handle all the keyboard enters

    public static void addShortcuts(Scene scene, Runnable runnable) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    runnable.run();
                }
            }
        });
    }
}
