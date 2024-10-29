package Board;

import Creation.*;
import Structure.*;
import MoveGeneration.MoveData;

import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.IntStream;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_SQUARES = NUM_ROWS * NUM_ROWS;
    public PieceType[] types;
    public PieceColor[] colors;
    public PieceColor activeColor;
    CastlingRights castlingRights;
    boolean targetSquareIsPresent;
    int enPassantTargetSquare;
    int halfmoveClock;
    int fullmoveNumber;
    int whiteKingLocation;
    int blackKingLocation;
    public boolean latestMoveIsPresent;
    public MoveData latestMove;
    Stack<MoveData> moveHistory;
    PieceData lastCapture;

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
        return rank * NUM_ROWS + file;
    }
    public static int getRank(int index) {
        return index / NUM_ROWS;
    }
    public static int getFile(int index) {
        return index % NUM_ROWS;
    }
    public PieceData getPiece(int index) {
        return new PieceData(colors[index], types[index]);
    }
    public void setPiece(int index, PieceData data) {
        setPiece(index, data.type, data.color);
    }
    public void setPiece(int index, PieceType type, PieceColor color) {
        types[index] = type;
        colors[index] = color;
    }
    public ArrayList<Integer> getPieces(PieceData data) {
        if (data.type == PieceType.KING) {
            return data.color == PieceColor.WHITE ? new ArrayList<>(whiteKingLocation) : new ArrayList<>(blackKingLocation);
        }
        return IntStream.range(0, NUM_SQUARES)
                .filter(i -> types[i] == data.type && colors[i] == data.color)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    public ArrayList<Integer> getPieces(PieceColor color, PieceType type) {
        return getPieces(new PieceData(color, type));
    }
    boolean positionIsValid() {
        return true;
    }
    private void makeMove(MoveData moveData) {
        MoveMaker.makeMove(this, moveData);
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
