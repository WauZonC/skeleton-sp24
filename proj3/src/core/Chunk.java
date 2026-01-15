package core;

import java.util.Random;

/**
 * core class for room generate & door generate.
 * take 10 as designated chunk size.
 */
public class Chunk {
    private static final int CHUNK_SIZE = 10;
    private final int roomRelativeXAxis;
    private final int roomRelativeYAxis;
    private final int roomWidth;
    private final int roomHeight;
    private final Random random;
    private final Doors doors;

    /**
     * initialize of the chunk object
     * set the x, yaxis of the room by random
     * set the width,height of the room by random
     * initial the Doors object of the chunk
     * @param r the general random machine of the world
     */
    public Chunk(Random r) {
        this.random = r;

        roomHeight = 4 + (1 + random.nextInt(3)) * (1 + random.nextInt(2));
        roomWidth = 4 + (1 + random.nextInt(3)) * (1 + random.nextInt(2));

        roomRelativeYAxis = (roomHeight == 10) ? 0 : random.nextInt(CHUNK_SIZE - roomHeight);
        roomRelativeXAxis = (roomWidth == 10) ? 0 : random.nextInt(CHUNK_SIZE - roomWidth);

        //set the doors attribute
        doors = new Doors();
    }

    /**
     * @return {roomRelativeXAxis, roomRelativeYAxis}
     */
    public int[] getRoomCoordination() {
        return new int[]{roomRelativeXAxis, roomRelativeYAxis};
    }

    /**
     * @return {roomWidth, roomHeight}
     */
    public int[] getRoomSize() {
        return new int[]{roomWidth, roomHeight};
    }
    /**
     * initialize a new door in the 'orientation' side of the room by random
     * @param orientation the expected orientation would like to be set a door
     * @throws IllegalStateException when the door in that orientation has already been set
     * @return the coordination of the new door(relative) -- (0-X axis; 1-Y axis)
     */
    public int[] setNewDoor(Orientation orientation) {
        if (doors.hasSet(orientation)) {
            throw new IllegalStateException("The door in this orientation has already been set");
        }
        doors.setNewDoor(orientation,
                switch (orientation) {
                    case Left -> roomRelativeXAxis;
                    case Right -> roomRelativeXAxis + roomWidth - 1;
                    case Up, Down -> roomRelativeXAxis + 1 + random.nextInt(roomWidth - 2);
                    case Null -> throw new IllegalStateException("Illegal value input");
        },
                switch (orientation) {
                    case Up -> roomRelativeYAxis + roomHeight - 1;
                    case Down -> roomRelativeYAxis;
                    case Left, Right -> roomRelativeYAxis + 1 + random.nextInt(roomHeight - 2);
                    case Null -> throw new IllegalStateException("Illegal value input");
        }
                );

        return getDoorCoordination(orientation);
    }

    /**
     * return coordination of the door(relative) in that ORIENTATION if exists
     * @param orientation the expected orientation
     * @throws IllegalStateException when the door in ORIENTATION does not exist
     * @return the coordination of the door(relative) in that ORIENTATION
     */
    public int[] getDoorCoordination(Orientation orientation) {
        if (!doors.hasSet(orientation)) {
            throw new IllegalStateException("The door does not exist");
        }
        return new int[]{doors.getDoorRelativeXAxis(orientation), doors.getDoorRelativeYAxis(orientation)};
    }


    private class Doors {
        /** 0-Up;1-Down;2-Left;3-Right */
        private final int[] doorXAxis;
        /** 0-Up;1-Down;2-Left;3-Right */
        private final int[] doorYAxis;
        /** 0-Up;1-Down;2-Left;3-Right */
        private final boolean[] hasSet;

        /**
         * 0-Up;1-Down;2-Left;3-Right
         */
        public Doors() {
            doorXAxis = new int[4];
            doorYAxis = new int[4];
            hasSet = new boolean[]{false, false, false, false};
        }

        public boolean hasSet(Orientation orientation) {
            return hasSet[orientation.ordinal()];
        }
        /**
         * a method for setting the door
         * @param orientation the orientation expected to be set
         * @param XAxis the relative xAxis Coordination of the new door
         * @param YAxis the relative yAxis Coordination of the new door
         */
        public void setNewDoor(Orientation orientation, int XAxis, int YAxis) {
            doorXAxis[orientation.ordinal()] = XAxis;
            doorYAxis[orientation.ordinal()] = YAxis;
            hasSet[orientation.ordinal()] = true;
        }

        /**
         * a method for getting the door's relative xAxis Coordination
         * @param orientation the orientation
         * @return the relative xAxis
         */
        public int getDoorRelativeXAxis(Orientation orientation) {
            return doorXAxis[orientation.ordinal()];
        }

        /**
         * a method for getting the door's relative yAxis Coordination
         * @param orientation the orientation
         * @return the relative yAxis
         */
        public int getDoorRelativeYAxis(Orientation orientation) {
            return doorYAxis[orientation.ordinal()];
        }
    }
}
