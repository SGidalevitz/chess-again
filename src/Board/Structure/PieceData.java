package Board.Structure;

public class PieceData {


    public PieceColor color;
    public PieceType type;
    public PieceData(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }
    public static PieceData getPieceData(int index, PieceColor[] colors, PieceType[] types) {
        return new PieceData(colors[index], types[index]);
    }
    public static PieceData getPieceData(char piece) {
        return KEY[PIECES.indexOf(piece)];
    }

    public static char getChar(PieceData piece) {
        int index = piece.color.ordinal() * 6 - 6 + piece.type.ordinal() - 1;
        return PIECES.charAt(index);
    }
    public static char getChar(int index, PieceColor[] colors, PieceType[] types) {
        return getChar(getPieceData(index, colors, types));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PieceData pieceData = (PieceData) obj;
        return color == pieceData.color && type == pieceData.type;
    }





    public static final PieceData[] KEY = {
            new PieceData(PieceColor.WHITE, PieceType.PAWN),
            new PieceData(PieceColor.WHITE, PieceType.KNIGHT),
            new PieceData(PieceColor.WHITE, PieceType.BISHOP),
            new PieceData(PieceColor.WHITE, PieceType.ROOK),
            new PieceData(PieceColor.WHITE, PieceType.QUEEN),
            new PieceData(PieceColor.WHITE, PieceType.KING),
            new PieceData(PieceColor.BLACK, PieceType.PAWN),
            new PieceData(PieceColor.BLACK, PieceType.KNIGHT),
            new PieceData(PieceColor.BLACK, PieceType.BISHOP),
            new PieceData(PieceColor.BLACK, PieceType.ROOK),
            new PieceData(PieceColor.BLACK, PieceType.QUEEN),
            new PieceData(PieceColor.BLACK, PieceType.KING)
    };
    public static final String PIECES = "PNBRQKpnbrqk";
}
