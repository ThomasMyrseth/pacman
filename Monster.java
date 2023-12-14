package packman;

import java.util.Random;

public class Monster {
   private int monsterX;
   private int monsterY;
   private int previousType;
   private int nextTileType;
   private Game game;
   private int dy;
   private int dx;
   private Random random;
   private int counter=0;
   private int counterSpeed=1;

   Monster(int monsterY, int monsterX, Game game) {
        if (monsterY>14 || monsterY<0) {
            throw new IllegalArgumentException("ugyldig MonsterY verdi");
        }
        this.monsterY=monsterY;

        if (monsterX>14 || monsterX<0) {
            throw new IllegalArgumentException("ugyldig MonsterX verdi");
        }
        this.monsterX=monsterX;

        this.game=game;

        this.previousType=2;
        this.nextTileType=2;

        this.dy=0;
        this.dx=1;

        random = new Random();
   }

   public void setGame(Game game) {
        this.game=game;
   }
   public Game getGame() {
        return this.game;
   }
   public void setPreviousType(int type) {
        this.previousType=type;
   }
   public int getPreviousType() {
        return this.previousType;
   }
   
   public boolean validateMonsterMove(int dy, int dx) {
        int y = dy + monsterY;
        int x = dx + monsterX;

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
        else if (game.getTile(y, x).getType()==0) {
            System.out.println("vegg");
            return false;
        }
        else if (game.getTile(y, x).getType()==8) {
            System.out.println("møtte på et annet monster");
            return false;
        }
        else {return true;}
    }

    public void setNextTileType(int dy, int dx) {
        nextTileType = game.getTile(dy+monsterY, dx+monsterX).getType();
    }
    public int getNextTileType() {
        return this.nextTileType;
    }


    public void moveMonster(int dy, int dx) {
        counterSpeed++;
        counter++;
        moveByCounter();
        if (counterSpeed%3==0) {
            if (validateMonsterMove(dy, dx)) {
                game.getTile(monsterY, monsterX).setType(getNextTileType());
                setNextTileType(dy, dx);
                monsterX+=dx;
                monsterY+=dy; //monster står nå på setNextTileType
                game.getTile(monsterY, monsterX).setType(8);
            }
            else {
                keepMoving();
            }
        }
    }

    public void keepMoving() {
        if (! validateMonsterMove(dy, dx)) {
            int direction = random.nextInt(4)+1;

            switch (direction) {
                case(1):
                    dy=1;
                    dx=0;
                    break;
                case(2):
                    dy=-1;
                    dx=0;
                    break;
                case(3):
                    dy=0;
                    dx=1;
                    break;
                case(4):
                    dy=0;
                    dx=-1;
                    break;
            }
        }
        else {
            moveMonster(dy, dx);
        }
    }

    public void moveByCounter() {
        if (counter%8==0) {
            int direction = random.nextInt(4)+1;
            switch (direction) {
                case(1):
                    dy=1;
                    dx=0;
                    break;
                case(2):
                    dy=-1;
                    dx=0;
                    break;
                case(3):
                    dy=0;
                    dx=1;
                    break;
                case(4):
                    dy=0;
                    dx=-1;
                    break;
            }
            counter=0;
            moveMonster(dy, dx);
        }
    }

    public boolean isDead() {
        if (game.getPackamnX()==monsterX && game.getPackamnY()==monsterY) {
            game.setGameOver(true);
            return true;
        }
        else {
            return false;
        }
    }

    public int getCounter() {
        return this.counter;
    }
}
