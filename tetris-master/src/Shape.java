
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Studio
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int[][] coordinates;
    Socket socket;   
    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };

    int candidate;

    public Shape(Tetrominoes pieceShape) {
        this.pieceShape = pieceShape;
        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[pieceShape.ordinal()][point][0];
            coordinates[point][1] = coordsTable[pieceShape.ordinal()][point][1];
        }
        

    }

    public Shape() {

        int randomNumber = (int) (Math.random() * 7 + 1); //del 0-6 +1
        pieceShape = Tetrominoes.values()[randomNumber]; //teniendo el ordinal, saca el enum
        

        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[pieceShape.ordinal()][point][0];
            coordinates[point][1] = coordsTable[pieceShape.ordinal()][point][1];
        }
    }

    public static Shape getRandomShape() {
        return new Shape();
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public Shape rotateRight() {
        Shape rotatedShape = new Shape(pieceShape);
        for (int point = 0; point < rotatedShape.coordinates.length; point++) {
            rotatedShape.coordinates[point][0] = coordinates[point][0];
            rotatedShape.coordinates[point][1] = coordinates[point][1];
        }

        if (pieceShape != Tetrominoes.SquareShape) {

            for (int point = 0; point < rotatedShape.coordinates.length; point++) {
                int temp = rotatedShape.coordinates[point][0];
                rotatedShape.coordinates[point][0] = rotatedShape.coordinates[point][1];
                rotatedShape.coordinates[point][1] = -temp; //es -x
            }
        }
        return rotatedShape;
    }

    public int getXmin() {

        candidate = coordinates[0][0];

        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][0] < candidate) {
                candidate = coordinates[i][0];
            }
        }
        return candidate;
    }

    public int getXmax() {
        candidate = coordinates[0][0];

        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][0] > candidate) {
                candidate = coordinates[i][0];
            }
        }
        return candidate;
    }

    public int getYmin() {
        candidate = coordinates[0][1];

        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][1] < candidate) {
                candidate = coordinates[i][1];
            }
        }
        return candidate;
    }

    public int getYmax() {
        candidate = coordinates[0][1];

        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][1] > candidate) {
                candidate = coordinates[i][1];
            }
        }
        return candidate;
    }

    public void draw(Graphics g, int row, int col, int squareWidth, int squareHeight) {

        for (int point = 0; point <= 3; point++) {
            Util.drawSquare(g, row + coordinates[point][1], col + coordinates[point][0], pieceShape, squareWidth, squareHeight);
        }
    }
     public void write(String message) throws IOException {
        DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
        ostream.writeUTF(message);
        ostream.flush();
    }
    
    public String read() throws IOException {
        DataInputStream istream = new DataInputStream(socket.getInputStream());
        return istream.readUTF();
    }
    
}
