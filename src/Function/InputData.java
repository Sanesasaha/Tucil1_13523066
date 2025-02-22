package Function;

public class InputData {
    public int N, M, P;
    public String S;
    
    public Board B;
    public Piece[][] pieces;

    public void printPieces(){
        for(int i = 0; i < P; i++){
            pieces[i][0].printShape();
            System.out.println();
        }
    }
}
