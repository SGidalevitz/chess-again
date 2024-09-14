package Board.StructureTests;

import Board.Structure.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class PieceDataTest {
    char[] testChars = {'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k'};
    PieceColor[] testColors = {PieceColor.WHITE, PieceColor.WHITE, PieceColor.WHITE, PieceColor.WHITE, PieceColor.WHITE, PieceColor.WHITE, PieceColor.BLACK, PieceColor.BLACK, PieceColor.BLACK, PieceColor.BLACK, PieceColor.BLACK, PieceColor.BLACK};
    PieceType[] testTypes = {PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.KING, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.KING};
    @Test
    public void testGetWhitePawn() {
        PieceData pieceData = PieceData.getPieceData(testChars[0]);
        assertEquals(testColors[0], pieceData.color);
        assertEquals(testTypes[0], pieceData.type);
    }
    @Test
    public void testGetWhiteKnight() {
        PieceData pieceData = PieceData.getPieceData(testChars[1]);
        assertEquals(testColors[1], pieceData.color);
        assertEquals(testTypes[1], pieceData.type);
    }
    @Test
    public void testGetWhiteBishop() {
        PieceData pieceData = PieceData.getPieceData(testChars[2]);
        assertEquals(testColors[2], pieceData.color);
        assertEquals(testTypes[2], pieceData.type);
    }
    @Test
    public void testGetWhiteRook() {
        PieceData pieceData = PieceData.getPieceData(testChars[3]);
        assertEquals(testColors[3], pieceData.color);
        assertEquals(testTypes[3], pieceData.type);
    }
    @Test
    public void testGetWhiteQueen() {
        PieceData pieceData = PieceData.getPieceData(testChars[4]);
        assertEquals(testColors[4], pieceData.color);
        assertEquals(testTypes[4], pieceData.type);
    }
    @Test
    public void testGetWhiteKing() {
        PieceData pieceData = PieceData.getPieceData(testChars[5]);
        assertEquals(testColors[5], pieceData.color);
        assertEquals(testTypes[5], pieceData.type);
    }
    @Test
    public void testGetBlackPawn() {
        PieceData pieceData = PieceData.getPieceData(testChars[6]);
        assertEquals(testColors[6], pieceData.color);
        assertEquals(testTypes[6], pieceData.type);
    }
    @Test
    public void testGetBlackKnight() {
        PieceData pieceData = PieceData.getPieceData(testChars[7]);
        assertEquals(testColors[7], pieceData.color);
        assertEquals(testTypes[7], pieceData.type);
    }
    @Test
    public void testGetBlackBishop() {
        PieceData pieceData = PieceData.getPieceData(testChars[8]);
        assertEquals(testColors[8], pieceData.color);
        assertEquals(testTypes[8], pieceData.type);
    }
    @Test
    public void testGetBlackRook() {
        PieceData pieceData = PieceData.getPieceData(testChars[9]);
        assertEquals(testColors[9], pieceData.color);
        assertEquals(testTypes[9], pieceData.type);
    }
    @Test
    public void testGetBlackQueen() {
        PieceData pieceData = PieceData.getPieceData(testChars[10]);
        assertEquals(testColors[10], pieceData.color);
        assertEquals(testTypes[10], pieceData.type);
    }
    @Test
    public void testGetBlackKing() {
        PieceData pieceData = PieceData.getPieceData(testChars[11]);
        assertEquals(testColors[11], pieceData.color);
        assertEquals(testTypes[11], pieceData.type);
    }


    @Test
    public void testGetCharWhitePawn() {
        char piece = testChars[0];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[0], testTypes[0])));
    }
    @Test
    public void testGetCharWhiteKnight() {
        char piece = testChars[1];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[1], testTypes[1])));
    }
    @Test
    public void testGetCharWhiteBishop() {
        char piece = testChars[2];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[2], testTypes[2])));
    }
    @Test
    public void testGetCharWhiteRook() {
        char piece = testChars[3];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[3], testTypes[3])));
    }
    @Test
    public void testGetCharWhiteQueen() {
        char piece = testChars[4];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[4], testTypes[4])));
    }
    @Test
    public void testGetCharWhiteKing() {
        char piece = testChars[5];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[5], testTypes[5])));
    }
    @Test
    public void testGetCharBlackPawn() {
        char piece = testChars[6];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[6], testTypes[6])));
    }
    @Test
    public void testGetCharBlackKnight() {
        char piece = testChars[7];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[7], testTypes[7])));
    }
    @Test
    public void testGetCharBlackBishop() {
        char piece = testChars[8];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[8], testTypes[8])));
    }
    @Test
    public void testGetCharBlackRook() {
        char piece = testChars[9];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[9], testTypes[9])));
    }
    @Test
    public void testGetCharBlackQueen() {
        char piece = testChars[10];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[10], testTypes[10])));
    }
    @Test
    public void testGetCharBlackKing() {
        char piece = testChars[11];
        assertEquals(piece, PieceData.getChar(new PieceData(testColors[11], testTypes[11])));
    }





    @Test
    public void testGetPieceDataWithArrays() {
        PieceColor[] colors = {PieceColor.WHITE, PieceColor.BLACK, PieceColor.BLACK};
        PieceType[] types = {PieceType.KING, PieceType.BISHOP, PieceType.PAWN};
        assertEquals(new PieceData(PieceColor.WHITE, PieceType.KING), PieceData.getPieceData(0, colors, types));
        assertEquals(new PieceData(PieceColor.BLACK, PieceType.BISHOP), PieceData.getPieceData(1, colors, types));
        assertEquals(new PieceData(PieceColor.BLACK, PieceType.PAWN), PieceData.getPieceData(2, colors, types));
    }
    @Test
    public void testGetCharWithArrays() {
        PieceColor[] colors = {PieceColor.WHITE, PieceColor.BLACK, PieceColor.BLACK};
        PieceType[] types = {PieceType.KING, PieceType.BISHOP, PieceType.PAWN};
        assertEquals('K', PieceData.getChar(0, colors, types));
        assertEquals('b', PieceData.getChar(1, colors, types));
        assertEquals('p', PieceData.getChar(2, colors, types));
    }

}
