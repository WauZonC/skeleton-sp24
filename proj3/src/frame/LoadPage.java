package frame;

import edu.princeton.cs.algs4.StdDraw;
import utils.ArchiveToken;
import utils.FileUtils;

public class LoadPage implements Frame {

    public LoadPage() {
    }

    @Override
    public Frame nextPage(String args) {
        return switch (args) {
            case "1" -> new GameWorld(ArchiveToken.loadArchive(1));
            case "2" -> new GameWorld(ArchiveToken.loadArchive(2));
            case "3" -> new GameWorld(ArchiveToken.loadArchive(3));
            default -> null;
        };
    }

    @Override
    public void render() {
        StdDraw.clear(StdDraw.BLACK);

        for (int i = 1; i <= 3; i++) {
            renderArchiveColumn(i);
        }

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(40,55, "Load Archive");
        StdDraw.text(40,4, "Back To Menu(Q)");
        StdDraw.show();
    }

    private void renderArchiveColumn(int index) {
        archiveBlank(45 - 15 * Math.floorMod(index - 1, 3));
        textInMiddle(45 - 15 * Math.floorMod(index - 1, 3), index, FileUtils.fileExists("Archive"+index+".txt"));//TODO
    }
    private void textInMiddle(int YCo, int index, boolean exist) {
        StdDraw.setPenColor(exist ? StdDraw.WHITE : StdDraw.DARK_GRAY);
        StdDraw.text(40,YCo, "Archive " + index + (exist ? " (type " + index +")" : " (empty)"));
        StdDraw.show();
    }

    private void archiveBlank(int Y) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(40, Y, 30,5);
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledRectangle(40,Y,29.5, 4.5);
    }

    @Override
    public Frame play() {
        render();
        boolean delHasPressed = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case '1': if (delHasPressed) {FileUtils.delArchive(1); render(); delHasPressed = false; break;}
                        else if (FileUtils.archiveExist(1)) {return nextPage("1");} break;
                    case '2': if (delHasPressed) {FileUtils.delArchive(2); render(); delHasPressed = false; break;}
                    else if (FileUtils.archiveExist(2)) {return nextPage("2");} break;
                    case '3': if (delHasPressed) {FileUtils.delArchive(3); render(); delHasPressed = false; break;}
                    else if (FileUtils.archiveExist(3)) {return nextPage("3");} break;
                    case 'd', 'D': delHasPressed = true; break;
                    case 'q', 'Q': return new MainMenu();
                }
            }
        }
    }
}
