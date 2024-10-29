package Board;

import Structure.PieceColor;
import Structure.PieceType;
import MoveGeneration.Move;
import MoveGeneration.MoveData;
import MoveGeneration.MoveType;

public class MoveMaker {

    static void makeMove(Board board, MoveData moveData) {
        makeMoveNoChecks(board, moveData);
        if (!board.positionIsValid()) {
            unmakeMove(board);
        }
        else {
            applyPostMoveUpdates(board, moveData);
        }
    }
    private static void makeMoveNoChecks(Board board, MoveData moveData) {
        Move move = moveData.move();
        MoveType type = moveData.type();
        switch (type) {
            case QUIET, DOUBLE_PAWN_PUSH, CAPTURE -> {
                applyMove(board, move);
            }

            case KING_CASTLE -> {
                int kingStart = move.origin();
                int kingEnd = move.destination();
                assert kingStart == (board.colors[kingStart] == PieceColor.WHITE ? 4 : 60);
                assert kingEnd == (board.colors[kingStart] == PieceColor.WHITE ? 6 : 62);
                int rookStart = board.colors[kingStart] == PieceColor.WHITE ? 7 : 63;
                int rookEnd = board.colors[kingStart] == PieceColor.WHITE ? 5 : 61;
                board.setPiece(kingEnd, board.getPiece(kingStart));
                board.setPiece(kingStart, PieceType.EMPTY, PieceColor.EMPTY);
                board.setPiece(rookEnd, board.getPiece(rookStart));
                board.setPiece(rookStart, PieceType.EMPTY, PieceColor.EMPTY);

            }
            case QUEEN_CASTLE -> {
                int kingStart = move.origin();
                int kingEnd = move.destination();
                assert kingStart == (board.colors[kingStart] == PieceColor.WHITE ? 4 : 60);
                assert kingEnd == (board.colors[kingStart] == PieceColor.WHITE ? 2 : 58);
                int rookStart = board.colors[kingStart] == PieceColor.WHITE ? 0 : 56;
                int rookEnd = board.colors[kingStart] == PieceColor.WHITE ? 3 : 59;
                board.setPiece(kingEnd, board.getPiece(kingStart));
                board.setPiece(kingStart, PieceType.EMPTY, PieceColor.EMPTY);
                board.setPiece(rookEnd, board.getPiece(rookStart));
                board.setPiece(rookStart, PieceType.EMPTY, PieceColor.EMPTY);
            }
            case EN_PASSANT -> {
                int start = move.origin();
                int end = move.destination();
                board.setPiece(end, board.getPiece(start));
                board.setPiece(start, PieceType.EMPTY, PieceColor.EMPTY);
                board.setPiece(board.enPassantTargetSquare, PieceType.EMPTY, PieceColor.EMPTY);
            }
            case PROMOTION, PROMOTION_CAPTURE -> {
                int start = move.origin();
                int end = move.destination();
                board.setPiece(start, board.lastCapture);
                board.setPiece(end, PieceType.PAWN, board.activeColor);
            }
        }
    }
    private static void applyMove(Board board, Move move) {
        int start = move.origin();
        int end = move.destination();
        board.types[end] = board.types[start];
        board.colors[end] =board.colors[start];
        board.types[start] = PieceType.EMPTY;
        board.colors[start] = PieceColor.EMPTY;
    }
    private static void unmakeMove(Board board) {
        MoveData lastMoveData = board.moveHistory.pop();
        Move move = lastMoveData.move();
        MoveType type = lastMoveData.type();
        switch (type) {
            case QUIET, DOUBLE_PAWN_PUSH, CAPTURE -> {
                int start = move.origin();
                int end = move.destination();
                board.types[start] = board.types[end];
                board.colors[start] = board.colors[end];
                try {
                    board.types[end] = board.lastCapture.type;
                    board.colors[end] = board.lastCapture.color; // Should always be the opposite color
                }
                catch (NullPointerException e) {
                    throw new RuntimeException("No last capture to undo");
                }
            }
            case KING_CASTLE -> {
                int kingStart = move.origin();
                int kingEnd = move.destination();
                int rookStart = board.colors[kingStart] == PieceColor.WHITE ? 7 : 63;
                int rookEnd = board.colors[kingStart] == PieceColor.WHITE ? 5 : 61;
                board.setPiece(kingStart, board.getPiece(kingEnd));
                board.setPiece(kingEnd, PieceType.EMPTY, PieceColor.EMPTY);
                board.setPiece(rookStart, board.getPiece(rookEnd));
                board.setPiece(rookEnd, PieceType.EMPTY, PieceColor.EMPTY);
            }
            case QUEEN_CASTLE -> {
                int kingStart = move.origin();
                int kingEnd = move.destination();
                int rookStart = board.colors[kingStart] == PieceColor.WHITE ? 0 : 56;
                int rookEnd = board.colors[kingStart] == PieceColor.WHITE ? 3 : 59;
                board.setPiece(kingStart, board.getPiece(kingEnd));
                board.setPiece(kingEnd, PieceType.EMPTY, PieceColor.EMPTY);
                board.setPiece(rookStart, board.getPiece(rookEnd));
                board.setPiece(rookEnd, PieceType.EMPTY, PieceColor.EMPTY);
            }
            case EN_PASSANT -> {
                int start = move.origin();
                int end = move.destination();
                board.setPiece(start, board.getPiece(end));
                board.setPiece(end, board.lastCapture);
                board.setPiece(board.enPassantTargetSquare, PieceType.PAWN, board.activeColor.opposite());
            }
            // TODO: Implement choosable promotion piece
            case PROMOTION, PROMOTION_CAPTURE -> {
                int start = move.origin();
                int end = move.destination();
                board.setPiece(start, board.getPiece(end));
                board.setPiece(end, PieceType.QUEEN, board.activeColor);
            }
        }


    }
    private static void applyPostMoveUpdates(Board board, MoveData moveData) {
        board.activeColor = board.activeColor.opposite();
        board.fullmoveNumber++;
        board.lastCapture = board.getPiece(moveData.move().destination());
        board.moveHistory.push(moveData);
        Move move = moveData.move();
        int position = move.destination();
        MoveType type = moveData.type();

        switch (moveData.type()) {

            case MoveType.DOUBLE_PAWN_PUSH -> {
                board.enPassantTargetSquare = board.colors[position] == PieceColor.WHITE ? move.destination() - 8 : move.destination() + 8;
                board.targetSquareIsPresent = true;
            }
            case MoveType.CAPTURE -> {
                board.halfmoveClock = 0;
            }
        }
        if (board.types[position] == PieceType.KING) {
            if (board.colors[position] == PieceColor.WHITE) {
                board.whiteKingLocation = position;
            }
            else {
                board.blackKingLocation = position;
            }
        }
        switch (board.types[position]) {
            case PAWN -> {
                board.halfmoveClock = 0;
            }
            case ROOK -> {
                int origin = move.origin();
                if (board.colors[position] == PieceColor.WHITE) {
                    if (origin == 0) board.castlingRights.whiteQueenside = false;
                    else if (origin == 7) board.castlingRights.whiteKingside = false;
                }
                else {
                    if (origin == 56) board.castlingRights.blackQueenside = false;
                    else if (origin == 63) board.castlingRights.blackKingside = false;
                }
            }
            case KING -> {
                switch (board.colors[position]) {
                    case WHITE -> board.whiteKingLocation = position;
                    case BLACK -> board.blackKingLocation = position;
                }
                if (type != MoveType.KING_CASTLE && type != MoveType.QUEEN_CASTLE) {
                    board.castlingRights.removeRights(board.activeColor);
                }
            }
        }

    }






}
