package frame;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Ending implements Frame {
    private final EndingState state;
    public Ending(EndingState state) {
        this.state = state;
    }

    @Override
    public Frame nextPage(String args) {
        return new MainMenu();
    }

    @Override
    public void render() {
        String words = (state == EndingState.Win) ? "You Win!" : "You Lost!";

        StdDraw.clear(StdDraw.BLACK);

        StdDraw.setPenColor(StdDraw.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 100);
        StdDraw.setFont(font);

        StdDraw.text(40,45,words);
        StdDraw.show();

        font = new Font("Monaco", Font.BOLD, 42);
        StdDraw.setFont(font);
        StdDraw.text(40,20,"Press <C> to Continue");
        StdDraw.show();
    }

    @Override
    public Frame play() {
        render();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'c', 'C': return nextPage("");
                    default: break;
                }
            }
        }
    }

    public enum EndingState {
        Win, Lost;
    }
}
