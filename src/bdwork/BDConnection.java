package bdwork;

/**
 * Created by android on 7/19/17.
 */
public interface BDConnection {
    void init();
    void dispose();
    void createDB();
    void clearDB();
}
