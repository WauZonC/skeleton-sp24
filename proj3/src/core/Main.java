package core;

import frame.Frame;
import frame.MainMenu;

public class Main {
    static void main(String[] args) {
        Frame frame = new MainMenu();
        while (frame != null) {
            frame = frame.play();
        }
        System.exit(0);
    }
}
