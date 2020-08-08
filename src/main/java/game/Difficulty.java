package game;

public enum Difficulty {
    BEGINNER(10),
    INTERMEDIATE(40),
    ADVANCE(99);

    Difficulty(int mines) {
        this.mines = mines;
    }

    private final int mines;

    public int getMines(){
        return mines;
    }
}
