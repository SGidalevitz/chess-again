package Board;

import Creation.CastlingRights;
import Structure.PieceColor;
import Structure.PieceData;

public class PreviousState {
    public PieceColor activeColor;
    public int enPassantTargetSquare;
    public int whiteKingLocation;
    public int blackKingLocation;
    public CastlingRights castlingRights;
    public PieceData lastCapturePiece;
    public int halfMoveClock;
    public int fullMoveNumber;
    public PreviousState(Board board) {
        saveState(board, this);
    }
    public static void revertState(Board board, PreviousState previousState) {
        board.activeColor = board.activeColor.opposite();
        board.enPassantTargetSquare = previousState.enPassantTargetSquare;
        board.whiteKingLocation = previousState.whiteKingLocation;
        board.blackKingLocation = previousState.blackKingLocation;
        board.castlingRights = new CastlingRights(previousState.castlingRights);
        board.lastCapturePiece = previousState.lastCapturePiece;
        board.halfmoveClock = previousState.halfMoveClock;
        board.fullmoveNumber--;
    }
    public static void saveState(Board board, PreviousState stateToBeSaved) {
        board.activeColor = board.activeColor.opposite();
        stateToBeSaved.enPassantTargetSquare = board.enPassantTargetSquare;
        stateToBeSaved.whiteKingLocation = board.whiteKingLocation;
        stateToBeSaved.blackKingLocation = board.blackKingLocation;
        stateToBeSaved.castlingRights = new CastlingRights(board.castlingRights);
        stateToBeSaved.lastCapturePiece = board.lastCapturePiece;
        stateToBeSaved.halfMoveClock = board.halfmoveClock;
    }
}
