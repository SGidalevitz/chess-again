package MoveGeneration;
import Board.Board;
import Structure.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardRenderer extends JPanel implements MouseListener {
    public BoardRenderer(Board board) {
        super();
        this.board = board;
        addMouseListener( this);
        selectedPiecePos = -1;
        somethingSelected = false;
        initGraphics();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = lightSquare;
        for (int rank = Board.NUM_ROWS - 1; rank >= 0; rank--) {
            for (int file = 0; file < Board.NUM_ROWS; file++) {
                g.setColor((7 - rank + file) % 2 == 0 ? lightSquare : darkSquare);
                int pos = Board.getIndex(rank, file);
                if (board.latestMoveIsPresent) {
                    if (pos == board.latestMove.move().origin() || pos == board.latestMove.move().destination()) {
                        g.setColor(getLatestMoveColor(g.getColor()));
                    }
                }
                if (selectedPiecePos != -1) {}

                for (int i = 0; possibleMoves != null && i < possibleMoves.length; i++) {
                    g.setColor(possibleMoves[i].move().destination() == pos ? (pos / 8 + pos % 8) % 2 == 0 ? possibleMoveLight : possibleMoveDark : g.getColor());
                }
                g.fillRect(file * SQUARE_DIMENSION, (7 - rank) * SQUARE_DIMENSION, SQUARE_DIMENSION, SQUARE_DIMENSION);
                if (board.types[pos] != PieceType.EMPTY) {
                    Image piece = new ImageIcon(getPathForPiece(board.types[pos], board.colors[pos])).getImage();
                    g.drawImage(piece,file * SQUARE_DIMENSION, (7 - rank) * SQUARE_DIMENSION, SQUARE_DIMENSION, SQUARE_DIMENSION, null);
                }
            }
        }

    }
    public String getPathForPiece(PieceType pieceType, PieceColor pieceColor) {
        return "assets/Pieces/" +
        switch (pieceColor) {
            case WHITE -> "White";
            case BLACK -> "Black";
            case EMPTY -> throw new IllegalArgumentException("Empty pieceColor call to getPathForPiece.");
        } +
        switch (pieceType) {
            case KING -> "King";
            case QUEEN -> "Queen";
            case ROOK -> "Rook";
            case BISHOP -> "Bishop";
            case KNIGHT -> "Knight";
            case PAWN -> "Pawn";
            case EMPTY -> throw new IllegalArgumentException(("Empty PieceType call to getPathForPiece."));
        } +
        ".png";
    }
    private Color getLatestMoveColor(Color color) {
        if (color.equals(lightSquare)) return latestMoveLight;
        if (color.equals(darkSquare)) return latestMoveDark;
        throw new IllegalArgumentException("invalid color for getLatestMoveColor");

    }
    public void initGraphics() {
        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(this);
            frame.setSize(BoardRenderer.SQUARE_DIMENSION * Board.NUM_ROWS + 400, BoardRenderer.SQUARE_DIMENSION * Board.NUM_ROWS + 28);
            frame.setVisible(true);
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / SQUARE_DIMENSION;
        int y = 7 - e.getY() / SQUARE_DIMENSION;
        int pos = Board.getIndex(y, x);
        if (somethingSelected) {
            for (MoveData move : possibleMoves) {
                if (move.move().destination() == pos) {
                    board.makeMove(move);
                    selectedPiecePos = -1;
                    somethingSelected = false;
                    possibleMoves = null;
                    repaint();
                    return;
                }
            }
            return;
        }
        if (board.types[pos] != PieceType.EMPTY && board.colors[pos] == board.activeColor) {
            selectedPiecePos = pos;
            somethingSelected = true;
            possibleMoves = MoveGenerator.generateLegalMoves(board).stream().filter(move -> move.move().origin() == selectedPiecePos).toArray(MoveData[]::new);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public static final int SQUARE_DIMENSION = 75;
    private Board board;
    private static final Color lightSquare = new Color(245, 226, 203);
    private static final Color darkSquare = new Color(169, 128, 110);
    public static final Color possibleMoveLight = new Color(212, 100, 100);
    public static final Color possibleMoveDark = new Color(175, 75, 75);
    public static final Color latestMoveLight = new Color(192, 164, 110);
    public static final Color latestMoveDark = new Color(194,174, 107);
    public static final JFrame frame = new JFrame("Chess Board");
    public static final Font font = new Font("Monospaced", Font.BOLD, 24);
    private int selectedPiecePos; // -1 if nothing is selected
    public static MoveData[] possibleMoves;
    private boolean somethingSelected;
}
