package Creation;

import Structure.PieceColor;

public class CastlingRights {
    public boolean whiteKingside, blackKingside, whiteQueenside, blackQueenside;

    public CastlingRights(boolean whiteKingside, boolean blackKingside, boolean whiteQueenside, boolean blackQueenside) {
        this.whiteKingside = whiteKingside;
        this.blackKingside = blackKingside;
        this.whiteQueenside = whiteQueenside;
        this.blackQueenside = blackQueenside;
    }
    public CastlingRights(String color) {
         this.whiteKingside   = color.contains("K");
          this.blackKingside  = color.contains("Q");
          this.whiteQueenside = color.contains("k");
          this.blackQueenside = color.contains("q");

    }
    public void removeRights(PieceColor color) {
        switch (color) {
            case WHITE -> {
                whiteKingside = false;
                whiteQueenside = false;
            }
            case BLACK -> {
                blackKingside = false;
                blackQueenside = false;
            }
        }
    }
    public String toString() {
        return (whiteKingside ? "K" : "")
                + (blackKingside ? "Q" : "")
                + (whiteQueenside ? "k" : "")
                + (whiteKingside ? "q" : "");
    }
}
