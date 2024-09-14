package Board.Creation;

import Board.*;
import Board.Structure.*;
public final class FenParser {
    public static Board parseFen(String fen) {
        Board board = new Board();
        String[] components = fen.split(" ");
        parseBoard(components[0], board);
        parseColor(components[1], board);
        parseCastlingRights(components[2], board);
        parseEnPassantTargetSquare(components[3], board);
        parseHalfmoveClock(components[4], board);
        parseFullmoveNumber(components[5], board);
        return board;
    }
    private static void parseBoard(String fen, Board board) {
        PieceColor[] colors = new PieceColor[Board.NUM_SQUARES];
        PieceType[] types = new PieceType[Board.NUM_SQUARES];
        emptyArrs(colors, types);
        String[] ranks = fen.split("/");
        for (int rank = Board.NUM_ROWS - 1; rank >= 0; rank--) {
            int fileIndex = 0;
            for (char c : ranks[Board.NUM_ROWS - rank - 1].toCharArray()) {
                if (Character.isDigit(c)) {
                    fileIndex += c - '0';
                    continue;
                }
                PieceData data = PieceData.getPieceData(c);
                int index = Board.getIndex(rank, fileIndex);
                colors[index] = data.color;
                types[index] = data.type;
                fileIndex++;
            }
        }
        board.colors = colors;
        board.types = types;
    }
    private static void parseColor(String color, Board board) {
        board.activeColor = color.equals("w") ? PieceColor.WHITE : PieceColor.BLACK;
    }
    private static void parseCastlingRights(String castlingRights, Board board) {
        board.castlingRights = new CastlingRights(castlingRights);
    }
    private static void parseEnPassantTargetSquare(String enPassantTargetSquare, Board board) {
        if (enPassantTargetSquare.equals("-")) {
            board.targetSquareIsPresent = false;
            return;
        }
        int rank = enPassantTargetSquare.charAt(0) - 'a';
        int file = enPassantTargetSquare.charAt(1) - '1';
        board.enPassantTargetSquare = Board.getIndex(rank, file);
        board.targetSquareIsPresent = true;
    }
    private static void parseHalfmoveClock(String halfMoveClock, Board board) {
        board.halfmoveClock = Integer.parseInt(halfMoveClock);
    }
    private static void parseFullmoveNumber(String fullMoveNumber, Board board) {
        board.fullmoveNumber = Integer.parseInt(fullMoveNumber);
    }




    public static void emptyArrs(PieceColor[] colors, PieceType[] types) {
        for (int rank = 0; rank < Board.NUM_ROWS; rank++) {
            for (int file = 0; file < Board.NUM_ROWS; file++) {
                int index = Board.getIndex(rank, file);
                colors[index] = PieceColor.EMPTY;
                types[index] = PieceType.EMPTY;
            }
        }
    }
}


/* From FEN
       * Board.Board
       * Player to move
       * Castling rights
       * En passant target square
       * Halfmove clock
       * Fullmove number

 */