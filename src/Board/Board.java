package Board;

import Board.Creation.*;
import Board.Structure.*;
import MoveGeneration.Move;
import MoveGeneration.MoveData;
import MoveGeneration.MoveType;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_SQUARES = NUM_ROWS * NUM_ROWS;
    public PieceType[] types;
    public PieceColor[] colors;
    public PieceColor activeColor;
    public CastlingRights castlingRights;
    public boolean targetSquareIsPresent;
    public int enPassantTargetSquare;
    public int halfmoveClock;
    public int fullmoveNumber;
    private int whiteKingLocation;
    private int blackKingLocation;
    public boolean latestMoveIsPresent;
    public MoveData latestMove;

    // Only to be used by Board.BoardCreation.FenParser
    public Board() {}

    // Typical for board creation
    public Board(String fen) {
        this(FenParser.parseFen(fen));
    }
    public static final Board STARTING_POSITION = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    public Board(Board other) {
        this.types = other.types;
        this.colors = other.colors;
        this.activeColor = other.activeColor;
        this.castlingRights = other.castlingRights;
        this.targetSquareIsPresent = other.targetSquareIsPresent;
        this.enPassantTargetSquare = other.enPassantTargetSquare;
        this.halfmoveClock = other.halfmoveClock;
        this.fullmoveNumber = other.fullmoveNumber;
    }

    public static int getIndex(int rank, int file) {
        return rank * 8 + file;
    }
    public static void printBoard(Board board) {
        for (int rank = NUM_ROWS - 1; rank >= 0; rank--) {
            for (int file = 0; file < NUM_ROWS; file++) {
                int index = getIndex(rank, file);
                if (board.types[index] == PieceType.EMPTY) {
                    System.out.print(".");
                    continue;
                }
                System.out.print(PieceData.getChar(index, board.colors, board.types));
            }
            System.out.println();
        }
    }
}

    /* TODO: Board.Board construction
      * From FEN
       * Board.Board
       * Active color
       * Castling rights
       * En passant target square
       * Halfmove clock
       * Fullmove number
      * From existing types and colors array
      * From existing types and colors array as well as other standard data
      * From other Board.Board class
     */
