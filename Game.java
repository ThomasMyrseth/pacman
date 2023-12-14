package packman;


public class Game {
    private Tile[][] board; // denne boarden har ikke sammenheng med fxml-board-pane
    private int height;
    private int width;

    private Tile packman;
    private Tile oldTile;
    private int packmanX;
    private int packmanY;
    private int points;
    
    private boolean isGameOver = false;

    // konstruktør lager kun spillebrettet
    Game(int height, int width) {
        this.height=height;
        this.width=width;
        board = new Tile[height][width];
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                board[i][j] = new Tile(i, j); // standard type =2
            }
        }

        packmanX=7;
        packmanY=7;
    }

    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
    public Tile[][] getBoard() {
        return board;
    }
    public void setBoard(Tile[][] board) {
        this.board = board;
    }
    public Tile getPackman() {
        return packman;
    }
    public void setPackman(Tile packman) {
        this.packman = packman;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    public Tile getTile(int y, int x) {
        if (x<0) {
            x=14;
        }
        else if (x>14) {
            x=0;
        }
        else if (y<0) {
            y=14;
        }
        else if (y>14) {
            y=0;
        }
        return board[y][x];
    }
    public int getPackamnY() {
        return this.packmanY;
    }
    public int getPackamnX() {
        return this.packmanX;
    }
    public boolean validatePackmanMove(int dy, int dx) {
        int y = dy + packmanY;
        int x = dx + packmanX;

        if(dy>1 || dy<-1) {
            System.out.println("for stort hopp i y retning!");
            return false;
        }
        else if(dx>1 || dx<-1) {
            System.out.println("for stort hopp i x retning!");
            return false;
        }
        
        else if (y<0 || y>14) {
            System.out.println("spawn til andre siden av opp/ned");
            return true;
        }
        else if (x<0 || x>14) {
            System.out.println("spawn til andre siden av høyre/venstre");
            return true;
        }
        else if (getTile(y, x).getType()==0) {
            System.out.println("vegg");
            return false;
        }
        else {return true;}
    }

    public void movePackman(int dy, int dx) {
        if (validatePackmanMove(dy, dx)) {
            oldTile = getTile(packmanY, packmanX);
           // oldTile.setType(1); // vanlig rute uten poeng

            packmanX+=dx;
            packmanY+=dy;

        
            if (dy==1 && packmanY>14) { //ned
                packmanY=0;
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(6);
            }
            else if (dy==-1 && packmanY<0) { //opp
                packmanY=14;
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(4);
            }
            else if (dx==1 && packmanX>14) { //høyre
                packmanX=0;
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(5);
            }
            else if (dx==-1 && packmanX<0) { //venstre
                packmanX=14;
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(3);
            }
            else if (getTile(packmanY, packmanX).getType()==1) { // vanlig rute
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(1);
            }
            else if (getTile(packmanY, packmanX).getType()==2) {
                getTile(packmanY, packmanX).setType(7);
                oldTile.setType(1);
                points++;
            }
            else {
                throw new IllegalArgumentException("movePackman metode");
            }
        }
    }

    public int getPoints() {
        return this.points;
    }

    public boolean hasWon() {
        if (points>=139) {
            setGameOver(true);
        }
        return points>=139;
    }
    public Tile getOldTile() {
        return this.oldTile;
    }
}
