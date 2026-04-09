package frame;

public interface Frame {
    Frame nextPage(String args);
    void render();
    Frame play();
}