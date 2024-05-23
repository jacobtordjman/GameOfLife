package Ex5;

public class GOLMatrix {
    private boolean[][] world;
    private int generations;
    private final int size;

    public GOLMatrix(int n){
        if (n<3)
            n = 3;
        world = new boolean[n][n];
        generations = 0;
        size=n;
    }
     public void flipCell(int row, int col){
        world[row][col]=!world[row][col];
     }

    public boolean[][] getWorld() {
        return world;
    }

    public int getGenerations() {
        return generations;
    }

    public void setWorld(boolean[][] world) {
        this.world = world;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public void clearWorld(){
        setWorld(new boolean[size][size]);
        setGenerations(0);
    }

    int countLiveNeighbors(boolean[][] temp, int i, int j) {
        int liveNeighbors = 0;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int g = j - 1; g <= j + 1; g++) {
                if (k == i && g == j)
                    continue;
                if (k<0||k>=size||g<0||g>=size)
                    continue;
                if (temp[k][g])
                    liveNeighbors++;
                }
            }
        return liveNeighbors;
        }

    public void nextGeneration() {
        // Make a copy of the world array
        boolean[][] temp = world;
        int[][] neighbors = new int[size][size];
        boolean allDead = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                neighbors[i][j] = countLiveNeighbors(temp, i, j);
                if (world[i][j]) {
                    allDead = false;
                }
            }
        }
        if (!allDead) {
            setGenerations(generations + 1);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (world[i][j]) {
                    switch (neighbors[i][j]) {
                        case 2, 3:
                            continue;
                        default:
                            world[i][j] = false;
                    }
                } else {
                    if (neighbors[i][j] == 3)
                        world[i][j] = true;
                }
            }
        }
    }


}
