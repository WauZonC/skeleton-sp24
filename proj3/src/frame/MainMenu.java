package frame;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class MainMenu implements Frame {
    public MainMenu() {
    }

    @Override
    public Frame nextPage(String args) {
        return switch (args) {
            case "Load" -> new LoadPage();
            case "SeedInput" -> new GameWorld(getSeed());
            default -> null;
        };
    }

    /**
     * A helper method for generating a window asking for seed input
     * @return the designated seed by user
     */
    private long getSeed() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(40,30,30,15);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(40,30,29.5,14.5);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(40,38, "Type in a seed to start a new game:");
        StdDraw.text(40, 20, "Start(S)");
        StdDraw.show();
        char keyboardInput;
        StringBuilder result = new StringBuilder();
        do {
            if (StdDraw.hasNextKeyTyped()) {
                keyboardInput = StdDraw.nextKeyTyped();
                if (Character.isDigit(keyboardInput)) {
                    result.append(keyboardInput);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledRectangle(40,28,29.5, 2);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.text(40,28, result.toString());
                    StdDraw.show();
                }
                if (keyboardInput == 'S' || keyboardInput == 's') {
                    break;
                }
            }
        } while (true);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.show();
        return Long.parseLong(result.toString());
    }

    @Override
    public void render() {
        StdDraw.setCanvasSize(80 * 16, 60 * 16);

        StdDraw.setXscale(0, 80);
        StdDraw.setYscale(0, 60);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();

        Font font = new Font("Monaco", Font.BOLD, 100);
        StdDraw.setFont(font);

        StdDraw.setPenColor(255,255,255);
        StdDraw.text(40,48,"CS61B BYOW");
        StdDraw.show();

        font = new Font("Monaco", Font.BOLD, 42);
        StdDraw.setFont(font);

        StdDraw.text(40,35,"New Game(N)");
        StdDraw.text(40,25,"Load Game(L)");
        StdDraw.text(40,15,"Quit(Q)");
        StdDraw.show();
    }

    @Override
    public Frame play() {
        this.render();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'N', 'n': return nextPage("SeedInput");
                    case 'Q', 'q': System.exit(1000);
                    case 'L', 'l': return nextPage("Load");
                }
            }
        }
    }
}
