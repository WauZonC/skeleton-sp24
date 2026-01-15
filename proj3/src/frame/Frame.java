package frame;

public interface Frame {
    Frame nextPage(String pageName);
    void render();
}