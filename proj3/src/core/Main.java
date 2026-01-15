package core;

import tileengine.TERenderer;

public class Main {
    static void main(String[] args) {
        long SEED = 99;
        int width = 80;
        int height = 30;
        World world = new World(SEED, width, height);
        TERenderer ter = new TERenderer();

        ter.initialize(width, height);

        ter.renderFrame(world.getMap());

    }
}
