public class Cell {
    private CellState cellState;
    private Rules rules;
    int x;
    int y;
    int size;
    int row;
    int column;

    /**
     * Constructor
     * @param x
     * @param y
     * @param size
     * @param row
     * @param column
     * @param cellState
     * @param rules
     */
    public Cell(int x, int y, int size, int row, int column, CellState cellState, Rules rules){
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
    }

    /**
     * Method: Cell changes state
     * @param _x
     * @param _y
     */
    public void handleMouseClicked(int _x, int _y){
        if(_x > getX() && _x < getWidth() + getX() && _y > getY() && _y < getHeight() + getY()) {
            if (cellState == CellState.ALIVE) { //if white
                cellState = CellState.DEAD;
            } else if(cellState == CellState.DEAD){
                cellState = CellState.ALIVE;
            }
        }
    }

    /**
     * Method: displays each cell as black or white
     */
    public void display () {
        if(cellState == CellState.ALIVE){
            Main.app.fill(0);
        }
        if(cellState == CellState.DEAD){
            Main.app.fill(255);
        }
        Main.app.rect(x, y, size, size);
    }

    /**
     * Method: counts number of live cells surrounding each cell
     * @param cellArray
     * @return
     */
    private int countLiveNeighbors(Cell[][] cellArray){
        int numAlive = 0;
        for(int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if(cellArray[row + i][column + j].cellState == CellState.ALIVE || cellArray[row + i][column + j].cellState == CellState.WILL_DIE){
                    numAlive ++;
                }
            }
        }
        if (cellArray[row][column].cellState == CellState.ALIVE || cellArray[row][column].cellState == CellState.WILL_DIE){
            numAlive --;
        }
        return numAlive;
    }

    /**
     * Method: applies GOL rules
     * @param cells
     */
    public void applyRules(Cell[][] cells){
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }

    /**
     * Method: revives or kills cells
     */
    public void evolve(){
        if (cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHeight(){
        return size;
    }
    public int getWidth(){
        return size;
    }

}


