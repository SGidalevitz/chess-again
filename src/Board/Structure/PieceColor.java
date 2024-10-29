package Board.Structure;
public enum PieceColor {
    EMPTY, WHITE, BLACK;

    public PieceColor opposite() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            default -> EMPTY;
        };
    }
}
