package core;

/**
 * 0-Up;1-Down;2-Left;3-Right
 */
public enum Orientation {
    Up,
    Down,
    Left,
    Right,
    Null;

    public static Orientation opposite(Orientation orientation) {
        return switch (orientation) {
            case Left -> Right;
            case Right -> Left;
            case Up -> Down;
            case Down -> Up;
            case Null -> Null;
        };
    }
}