package packman;

public class Tile {
    int type = 2;
    int x;
    int y;

    Tile(int y, int x) {
        this.y=y;
        this.x=x;
    }

    public int getType() {
        return this.type;
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public void setType(int type) {
        if (type<0 || type>9) {
            throw new IllegalArgumentException("Ikke gydig type for tile");
        }
        this.type=type;
    }
    public boolean isType(int type) {
        return this.type == type;
    }
}
