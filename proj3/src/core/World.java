package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World {
    private final int CHUNK_SIZE = 10;
    private final int width;
    private final int height;
    private final int chunkWidth;
    private final int chunkHeight;
    private final TETile[][] map;
    private final Chunk[][] chunks;
    private final Random random;
    private final long SEED;

    public World(String initialLine, int width, int height) {
        if ((initialLine.charAt(0) != 'n' && initialLine.charAt(0) != 'N') || (initialLine.charAt(initialLine.length() - 1) != 's' && initialLine.charAt(initialLine.length() - 1) != 'S')) {
            throw new IllegalArgumentException(initialLine + " is not the correct input form");
        }
        long seed;
        try {
             seed = Long.parseLong(initialLine.substring(1, initialLine.length() - 1));
        } catch (NumberFormatException e) {
            throw new RuntimeException(initialLine + "is a Invalid Input", e);
        }
        this(seed, width, height);
    }

    public World(long seed) {
        this(seed, 800, 300);
    }

    public World(long seed, int width, int height) {
        this.SEED = seed;
        this.random = new Random(SEED);
        this.width = width;
        this.height = height;

        this.map = new TETile[width][height];
        initializeTiles(map);

        this.chunkWidth = width / 10;
        this.chunkHeight = height / 10;
        this.chunks = new Chunk[chunkWidth][chunkHeight];
        generateRooms();

        generateHallways();
    }

    private void generateHallways() {
        double P = 0.95;
        for(int xs = 0; xs < chunkWidth; xs++) {
            for (int ys = 0; ys < chunkHeight; ys++) {
                if (random.nextDouble() > P) {
                    continue;
                }
                if (xs != chunkWidth - 1) {
                    generateHallwayBetween(new int[]{xs, ys}, new int[]{xs + 1, ys});
                }
                if (ys != chunkHeight - 1) {
                    generateHallwayBetween(new int[]{xs, ys}, new int[]{xs, ys + 1});
                }
            }
        }
    }

    private void generateHallwayBetween(int[] chunk1Co, int[] chunk2Co) {
        Orientation hallwayOrientation1to2 = (chunk1Co[0] == chunk2Co[0]) ?
                                        ((chunk1Co[1] > chunk2Co[1]) ? Orientation.Down : Orientation.Up) :
                                        ((chunk1Co[0] > chunk2Co[0]) ? Orientation.Left : Orientation.Right);
        int[] startPointCo = relativeToAbsolute(chunks[chunk1Co[0]][chunk1Co[1]].setNewDoor(hallwayOrientation1to2), chunk1Co);
        int[] endPointCo = relativeToAbsolute(chunks[chunk2Co[0]][chunk2Co[1]].setNewDoor(Orientation.opposite(hallwayOrientation1to2)), chunk2Co);

        fillPath(startPointCo, endPointCo);
    }

    /**
     * fill the path between STARTPOINT and ENDPOINT
     */
    private void fillPath(int[] startPoint, int[] endPoint) {
        int x = startPoint[0];
        int y = startPoint[1];
        for (;x != endPoint[0]; x += (startPoint[0] < endPoint[0]) ? 1 : -1) {
            render(x, y, Tileset.FLOOR);
            fillWall(x, y);
        }
        for (;y != endPoint[1]; y += (startPoint[1] < endPoint[1]) ? 1 : -1) {
            render(x, y, Tileset.FLOOR);
            fillWall(x, y);
        }
        render(x, y, Tileset.FLOOR);
    }

    /**
     * fill all NOTHING tile into WALL around the point
     */
    private void fillWall(int pointX, int pointY) {
        for (int x = pointX - 1; x <= pointX + 1; x++) {
            for (int y = pointY - 1; y <= pointY + 1; y++) {
                if (x < 0 || y < 0 || x >= width || y >= height || (x == pointX && y == pointY)) {
                    continue;
                }
                if (map[x][y].equals(Tileset.NOTHING)) {
                    map[x][y] = Tileset.WALL;
                }
            }
        }
    }

    private void generateRooms() {
        for (int xs = 0; xs < chunkWidth; xs++) {
            for (int ys = 0; ys < chunkHeight; ys++) {
                chunks[xs][ys] = new Chunk(random);
                int[] roomAbsoluteCoordinates = relativeToAbsolute(chunks[xs][ys].getRoomCoordination(), xs, ys);
                int[] roomSize = chunks[xs][ys].getRoomSize();
                fill(roomAbsoluteCoordinates, roomSize);
            }
        }
    }

    /**
     * A helper method for filling the room(containing wall)
     */
    private void fill(int[] coordinates, int[] size) {
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (x == 0 || y == 0 || x == size[0] - 1 || y == size[1] - 1) {
                    map[x + coordinates[0]][y + coordinates[1]] = Tileset.WALL;
                } else {
                    map[x + coordinates[0]][y + coordinates[1]] = Tileset.FLOOR;
                }
            }
        }
    }

    /**
     * a helper method for rendering a single tile in the map by TILE
     * @param absoluteX the absoluteCoordinates of the tile
     * @param tile the designated tile type
     */
    private void render(int absoluteX, int absoluteY, TETile tile) {
        map[absoluteX][absoluteY] = tile;
    }

    /**
     * a helper method for casting the coordination in chunk to the map
     * @param relative original coordination
     * @return after coordination
     */
    private int[] relativeToAbsolute(int[] relative, int chunkX, int chunkY) {
        return new int[]{relative[0] + chunkX * CHUNK_SIZE, relative[1] + chunkY * CHUNK_SIZE};
    }
    private int[] relativeToAbsolute(int[] relative, int[] chunkCo) {
        return relativeToAbsolute(relative, chunkCo[0], chunkCo[1]);
    }

    /**
     * helper method for initializing tiles to nothing blankets
     * @param tiles
     */
    private void initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * get the current map in form of TETile
     * @return the current MAP
     */
    public TETile[][] getMap() {
        return map;
    }

}
