package frame;

import core.World;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.ArchiveToken;


public class GameWorld implements Frame {
    private final int WIDTH = 80;
    private final int HEIGHT = 60;
    private final World world;
    private final int TILE_SIZE = 16;
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    private final int HUD_HEIGHT = 48;
    private final int[] AvatarCo;
    private final TETile[][] map;
    private final TERenderer ter;
    private int[][] flowers;
    private int curFlowerCount;
    private final int INITIAL_FLOWER = 5;
    private final int fromArchive;

    public GameWorld(long Seed) {
        this.world = new World(Seed, WIDTH, HEIGHT);
        this.map = world.getMap();
        this.ter = new TERenderer();
        this.WINDOW_WIDTH = WIDTH * TILE_SIZE;
        this.WINDOW_HEIGHT = HEIGHT * TILE_SIZE + HUD_HEIGHT;
        ter.initialize(WINDOW_WIDTH / TILE_SIZE , WINDOW_HEIGHT / TILE_SIZE, 0, HUD_HEIGHT / TILE_SIZE);

        AvatarCo = new int[]{0,0};
        do {
            AvatarCo[0] = world.getRandom().nextInt(WIDTH);
            AvatarCo[1] = world.getRandom().nextInt(HEIGHT);
        } while (world.getMap()[AvatarCo[0]][AvatarCo[1]] != Tileset.FLOOR);

        this.curFlowerCount = this.INITIAL_FLOWER;
        this.flowers = new int[curFlowerCount][2];
        for (int cnt = 0; cnt < curFlowerCount; cnt++) {
            do {
                flowers[cnt][0] = world.getRandom().nextInt(WIDTH);
                flowers[cnt][1] = world.getRandom().nextInt(HEIGHT);
            } while (world.getMap()[flowers[cnt][0]][flowers[cnt][1]] != Tileset.FLOOR);
        }

        this.fromArchive = 0;
        //TODO: Set Doors
    }

    public GameWorld(ArchiveToken token) {
        this.world = new World(token.SEED(), WIDTH, HEIGHT);
        this.map = world.getMap();
        this.ter = new TERenderer();
        this.WINDOW_WIDTH = WIDTH * TILE_SIZE;
        this.WINDOW_HEIGHT = HEIGHT * TILE_SIZE + HUD_HEIGHT;
        ter.initialize(WINDOW_WIDTH / TILE_SIZE , WINDOW_HEIGHT / TILE_SIZE, 0, HUD_HEIGHT / TILE_SIZE);

        this.AvatarCo = token.AvatarCo();

        this.curFlowerCount = token.flowerCount();
        this.flowers = token.flowers();

        this.fromArchive = token.archiveIndex();

        //TODO: Set Doors
    }

    @Override
    public Frame nextPage(String args) {
        return switch (args) {
            case "win" -> new Ending(Ending.EndingState.Win);
            case "lose" -> new Ending(Ending.EndingState.Lost);
            default -> null;
        };
    }

    @Override
    public void render() {
        TETile[][] tempMap = new TETile[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            System.arraycopy(map[x], 0, tempMap[x], 0, map[0].length);
        }

        renderFlowers(tempMap);

        tempMap[AvatarCo[0]][AvatarCo[1]] = Tileset.AVATAR;

        ter.renderFrame(tempMap);

        renderGoal(curFlowerCount);
    }

    @Override
    public Frame play() {
        render();

        boolean ColonHasTyped = false;
        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'w', 'W': tryMove(0, 1); ColonHasTyped = false; break;
                    case 'a', 'A': tryMove(-1, 0); ColonHasTyped = false; break;
                    case 's', 'S': tryMove(0, -1); ColonHasTyped = false; break;
                    case 'd', 'D': tryMove(1,0); ColonHasTyped = false; break;
                    case ':': ColonHasTyped = true; break;
                    case 'q': if (ColonHasTyped) {
                        ArchiveToken.saveArchive(ArchiveToken.toToken(this));
                        System.exit(0);
                    } break;
                    default: ColonHasTyped = false; break;
                }
                gainFlower();
                render();
            }
            if (curFlowerCount <= 0) {
                return nextPage("win");
            }
        }
    }

    /**
     * a helper method for rendering flowers
     * @param tiles
     */
    private void renderFlowers(TETile[][] tiles) {
        for (int i = 0; i < curFlowerCount; i++) {
            tiles[flowers[i][0]][flowers[i][1]] = Tileset.FLOWER;
        }
    }

    private void gainFlower() {
        for (int i = 0; i < curFlowerCount; i++) {
            if (AvatarCo[0] == flowers[i][0] && AvatarCo[1] == flowers[i][1]) {
                delFlower(i);
                curFlowerCount--;
            }
        }
    }

    private void delFlower(int line) {
        int[][] newFlowers = new int[curFlowerCount - 1][2];
        int cnt = 0;
        for (int j = 0; j < curFlowerCount; j++) {
            if (j != line) {
                newFlowers[cnt] = flowers[j];
                cnt++;
            }
        }
        this.flowers = newFlowers;
    }

    private void renderHUD() {
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledRectangle((double) (WINDOW_WIDTH / TILE_SIZE) / 2, (double) (HUD_HEIGHT / TILE_SIZE) / 2, (double) (WINDOW_WIDTH / TILE_SIZE) / 2, (double) (HUD_HEIGHT / TILE_SIZE) / 2);
        StdDraw.show();
    }

//    private void renderMouse() {
//        if (valid((int) StdDraw.mouseX(), (int) StdDraw.mouseY())) {
//            String description = map[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
//            StdDraw.setPenColor(StdDraw.WHITE);
//            StdDraw.text(20, (double) (HUD_HEIGHT / TILE_SIZE) / 2, "Mouse: " + description);
//        } else {
//            StdDraw.text(20, (double) (HUD_HEIGHT / TILE_SIZE) / 2, "Mouse: " );
//        }
//        StdDraw.show();
//    }

    private void renderGoal(int left) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(63, 1.5, "Goal: ");
        for (int i = 0; i < left; i++) {
            Tileset.FLOWER.draw(65 + 2 * i, 1);
        }
        StdDraw.show();
    }

    private void tryMove(int deltaX, int deltaY) {
        if (canMove(deltaX, deltaY)) {
            AvatarCo[0] += deltaX;
            AvatarCo[1] += deltaY;
        }
    }

    /**
     *
     * @param deltaX
     * @param deltaY
     */
    private boolean canMove(int deltaX, int deltaY) {
        return canStay(map[AvatarCo[0] + deltaX][AvatarCo[1] + deltaY]) && valid(AvatarCo[0] + deltaX, AvatarCo[1] + deltaY);
    }

    private boolean valid(int x, int y) {
        return x < map.length && x >= 0 && y >=0 && y < map[0].length;
    }
    private boolean canStay(TETile tile) {
        return tile == Tileset.FLOOR || tile == Tileset.UNLOCKED_DOOR || tile == Tileset.FLOWER;
    }

    public World getWorld() {
        return world;
    }

    public int[] getAvatarCo() {
        return AvatarCo;
    }

    public int getCurFlowerCount() {
        return curFlowerCount;
    }

    public int[][] getFlowers() {
        return flowers;
    }
}
