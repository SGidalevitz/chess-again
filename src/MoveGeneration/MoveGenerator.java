package MoveGeneration;
import Board.*;
import Board.Structure.*;

import java.util.ArrayList;

public class MoveGenerator {
    public static void main(String[] args) {
        Board board = new Board("r1bqkb1r/pppp1ppp/2n2n2/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4");
        //MoveGenerator.generateLegalMoves(board, PieceColor.BLACK).forEach(System.out::println);
        BoardRenderer renderer = new BoardRenderer(board);

    }
    public static ArrayList<MoveData> generateLegalMoves(Board board) {
        return generateLegalMoves(board, board.activeColor);
    }
    private static ArrayList<MoveData> generateLegalMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        moves.addAll(generatePawnMoves(board, color));
        moves.addAll(generateKnightMoves(board, color));
        moves.addAll(generateBishopMoves(board, color));
        moves.addAll(generateRookMoves(board, color));
        moves.addAll(generateQueenMoves(board, color));
        moves.addAll(generateKingMoves(board, color));
        return moves;
    }
    private static ArrayList<MoveData> generatePawnMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> pawns = board.getPieces(color, PieceType.PAWN);
        for (int i = 0; i < pawns.size(); i++) {
            int origin = pawns.get(i);
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            int direction = color == PieceColor.WHITE ? 1 : -1;
            int forwardSquare = Board.getIndex(rank + direction, file);
            if (board.types[forwardSquare] == PieceType.EMPTY) {
                moves.add(new MoveData(new Move(origin, forwardSquare), MoveType.QUIET));
                int doubleForwardSquare = Board.getIndex(rank + 2 * direction, file);
                if (rank == (color == PieceColor.WHITE ? 1 : 6) && board.types[doubleForwardSquare] == PieceType.EMPTY) {
                    moves.add(new MoveData(new Move(origin, doubleForwardSquare), MoveType.DOUBLE_PAWN_PUSH));
                }
            }

            // Captures
            for (int j = -1; j <= 1; j += 2) {
                int diagonalSquare = Board.getIndex(rank + direction, file + j);
                if (board.types[diagonalSquare] != PieceType.EMPTY && board.colors[diagonalSquare] != color) {
                    moves.add(new MoveData(new Move(origin, diagonalSquare), MoveType.CAPTURE));
                }
            }
        }
        return moves;
    }
    private static ArrayList<MoveData> generateKnightMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> knights = board.getPieces(color, PieceType.KNIGHT);
        for (int origin : knights) {
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            for (int j = -2; j <= 2; j++) {
                if (j == 0) {
                    continue;
                }
                int k = 3 - Math.abs(j);
                int[] files = {file + j, file - j};
                for (int fileOffset : files) {
                    if (fileOffset < 0 || fileOffset >= Board.NUM_ROWS) {
                        continue;
                    }
                    int[] ranks = {rank + k, rank - k};
                    for (int rankOffset : ranks) {
                        if (rankOffset < 0 || rankOffset >= Board.NUM_ROWS) {
                            continue;
                        }
                        int destination = Board.getIndex(rankOffset, fileOffset);
                        if (board.types[destination] == PieceType.EMPTY || board.colors[destination] != color) {
                            moves.add(new MoveData(new Move(origin, destination), board.colors[destination] == color ? MoveType.CAPTURE : MoveType.QUIET));
                        }
                    }
                }
            }
        }
        return moves;
    }
    private static ArrayList<MoveData> generateBishopMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> bishops = board.getPieces(color, PieceType.BISHOP);
        for (int origin : bishops) {
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            for (int i = -1; i <= 1; i += 2) {
                for (int j = -1; j <= 1; j += 2) {
                    for (int k = 1; k < Board.NUM_ROWS; k++) {
                        int rankOffset = rank + i * k;
                        int fileOffset = file + j * k;
                        if (rankOffset < 0 || rankOffset >= Board.NUM_ROWS || fileOffset < 0 || fileOffset >= Board.NUM_ROWS) {
                            break;
                        }
                        int destination = Board.getIndex(rankOffset, fileOffset);
                        if (board.types[destination] == PieceType.EMPTY) {
                            moves.add(new MoveData(new Move(origin, destination), MoveType.QUIET));
                        } else if (board.colors[destination] != color) {
                            moves.add(new MoveData(new Move(origin, destination), MoveType.CAPTURE));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return moves;
    }
    private static ArrayList<MoveData> generateRookMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> rooks = board.getPieces(color, PieceType.ROOK);
        for (int origin : rooks) {
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            for (int i = -1; i <= 1; i += 2) {
                for (int j = 0; j < Board.NUM_ROWS; j++) {
                    int rankOffset = rank + i * j;
                    int fileOffset = file + i * j;
                    if (rankOffset < 0 || rankOffset >= Board.NUM_ROWS || fileOffset < 0 || fileOffset >= Board.NUM_ROWS) {
                        break;
                    }
                    int destination = Board.getIndex(rankOffset, fileOffset);
                    if (board.types[destination] == PieceType.EMPTY) {
                        moves.add(new MoveData(new Move(origin, destination), MoveType.QUIET));
                    } else if (board.colors[destination] != color) {
                        moves.add(new MoveData(new Move(origin, destination), MoveType.CAPTURE));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return moves;
    }
    private static ArrayList<MoveData> generateQueenMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> queens = board.getPieces(color, PieceType.QUEEN);
        for (int origin : queens) {
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    for (int k = 1; k < Board.NUM_ROWS; k++) {
                        int rankOffset = rank + i * k;
                        int fileOffset = file + j * k;
                        if (rankOffset < 0 || rankOffset >= Board.NUM_ROWS || fileOffset < 0 || fileOffset >= Board.NUM_ROWS) {
                            break;
                        }
                        int destination = Board.getIndex(rankOffset, fileOffset);
                        if (board.types[destination] == PieceType.EMPTY) {
                            moves.add(new MoveData(new Move(origin, destination), MoveType.QUIET));
                        } else if (board.colors[destination] != color) {
                            moves.add(new MoveData(new Move(origin, destination), MoveType.CAPTURE));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return moves;
    }
    private static ArrayList<MoveData> generateKingMoves(Board board, PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<Integer> kings = board.getPieces(color, PieceType.KING);
        for (int origin : kings) {
            int rank = Board.getRank(origin);
            int file = Board.getFile(origin);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    int rankOffset = rank + i;
                    int fileOffset = file + j;
                    if (rankOffset < 0 || rankOffset >= Board.NUM_ROWS || fileOffset < 0 || fileOffset >= Board.NUM_ROWS) {
                        continue;
                    }
                    int destination = Board.getIndex(rankOffset, fileOffset);
                    if (board.types[destination] == PieceType.EMPTY || board.colors[destination] != color) {
                        moves.add(new MoveData(new Move(origin, destination), board.colors[destination] == color ? MoveType.CAPTURE : MoveType.QUIET));
                    }
                }
            }
        }
        // get castling moves
        for (int i = 0; i < 2; i++) {
            boolean kingside = i == 0;
            boolean queenside = !kingside;
            if (color == PieceColor.WHITE) {
                if (kingside && board.castlingRights.whiteKingside) {
                    if (board.types[5] == PieceType.EMPTY && board.types[6] == PieceType.EMPTY) {
                        moves.add(new MoveData(new Move(4, 6), MoveType.KING_CASTLE));
                    }
                } else if (queenside && board.castlingRights.whiteQueenside) {
                    if (board.types[1] == PieceType.EMPTY && board.types[2] == PieceType.EMPTY && board.types[3] == PieceType.EMPTY) {
                        moves.add(new MoveData(new Move(4, 2), MoveType.QUEEN_CASTLE));
                    }
                }
            } else {
                if (kingside && board.castlingRights.blackKingside) {
                    if (board.types[61] == PieceType.EMPTY && board.types[62] == PieceType.EMPTY) {
                        moves.add(new MoveData(new Move(60, 62), MoveType.KING_CASTLE));
                    }
                } else if (queenside && board.castlingRights.blackQueenside) {
                    if (board.types[57] == PieceType.EMPTY && board.types[58] == PieceType.EMPTY && board.types[59] == PieceType.EMPTY) {
                        moves.add(new MoveData(new Move(60, 58), MoveType.QUEEN_CASTLE));
                    }
                }
            }
        }

        return moves;
    }
}
