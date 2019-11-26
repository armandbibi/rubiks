package rubik2;

public class FaceCube implements ConstantCoords, ConstantCubieCube {

    byte[] facelets;
    byte[][] cornerFacelet;
    byte[][] cornerColor;
    byte[][] edgeColor;

    public FaceCube() {
        this("UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB");
    }

    public FaceCube(String cubeString) {
        
        facelets = new byte[cubeString.length()];
        for (int i = 0; i < cubeString.length(); i++) {
            facelets[i] = (byte) cubeString.charAt(i);
        }
        cornerFacelet = new byte[][]{ { U9, R1, F3 }, { U7, F1, L3 }, { U1, L1, B3 }, { U3, B1, R3 },
                { D3, F9, R7 }, { D1, L9, F7 }, {D7, B9, L7 }, { D9, R9, B7 }};

        cornerColor = new byte[][]{
                { U, R, F }, { U, F, L }, { U, L, B }, { U, B, R },
                {D, F, R }, {D, L, F }, {D, B, L }, { D, R, B }};
        edgeColor = new byte[][]{
                { U, R }, { U, F }, { U, L },
                { U, B }, { D, R }, {D, F },
                { D, L }, { D, B }, { F, R },
                { F, L }, { B, L }, { B, R }
        };
    }

    public byte[] getFacelets() {
        return facelets;
    }

    public byte[][] getCornerFacelet() {
        return cornerFacelet;
    }

    public byte[][] getCornerColor() {
        return cornerColor;
    }

    public byte[][] getEdgeColor() {
        return edgeColor;
    }
}
