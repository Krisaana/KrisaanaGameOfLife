import processing.core.PApplet;
public class Main extends PApplet{
    private Cell[][] cells = new Cell[50][100];
    private boolean doEvolve = false;
    final int NUM_ROWS = 50; // horizontal panels
    final int NUM_COLUMNS = 50; //  vertical panels
    final int CELL_SIZE = 20;
    public static PApplet pApplet;
    public static PApplet app;

    public Main() {
        super();
        app = this;
        pApplet = this;
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(NUM_COLUMNS * CELL_SIZE, NUM_ROWS * CELL_SIZE);
    }

    public void setup(){
        cells = new Cell[NUM_ROWS][NUM_COLUMNS];
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3});
        for (int row = 0; row < NUM_ROWS; row++){ // every panel in rows
            for (int column = 0; column < NUM_COLUMNS; column++){ // every panel in columns
                int x = column * CELL_SIZE;
                int y = row * CELL_SIZE;
                double rando = Math.random();

                CellState thisState = CellState.DEAD;
                if(rando < 0.2){ // 20%
                    thisState = CellState.ALIVE;
                }
                if(row == 0 || column == 0 || row == NUM_ROWS-1 || column == NUM_COLUMNS-1){
                    thisState = CellState.DEAD;
                }
                Cell c = new Cell(x, y, CELL_SIZE, row, column, thisState, rules);
                cells [row][column] = c;
            }
        }
    }
    public void applyRules(){
        for (int row = 1; row < cells.length -1; row++) {
            for (int column = 1; column < cells[row].length -1; column++) {
                cells[row][column].applyRules(cells);
            }
        }
    }
    public void evolve(){
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                cells[row][column].evolve();
            }
        }
    }
    public void keyPressed(){
        doEvolve = !doEvolve;
    }

    public void draw(){
        if(doEvolve == true){
            applyRules();
            evolve();
        }
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                cells[row][column].display();
            }
        }
        delay(76);
    }
    public void mouseClicked(){
        for(int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                Cell c = cells[i][j];
                c.handleMouseClicked(mouseX, mouseY);
            }
        }
    }
}

