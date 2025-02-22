package Function;

public class Piece {
    public int width = 0; 
    public int height = 0;
    
    public char id = '?';
    public boolean isPlaced = false;

    public int top_left_index = 0;
    public char[][] shape;

    public void stats(){
        System.out.println("width  :" + width);
        System.out.println("height :" + height);
        System.out.println("id     :" + id);
        System.out.println("tli    :" + top_left_index);
    }

    public void printShape(){
        int i, j;
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                System.out.print(shape[i][j]);
            }
            System.out.println();
        }
    }

    // clockwise rotation
    public Piece rotate90(){
        int i, j;
        Piece NP = new Piece();
        NP.width = height;
        NP.height = width;
        NP.id = id;

        NP.shape = new char[NP.height][NP.width];
        for(i=0; i<NP.width; i++){
            for(j=0; j<NP.height; j++){
                NP.shape[j][NP.width - 1 - i] = shape[i][j];
            }
        }

        for(i=0; i<NP.width; i++){
            if(NP.shape[0][i] == NP.id){
                NP.top_left_index = i;
                break;
            }
        }

        return NP;
    }

    public Piece mirror(){
        int i, j;
        Piece NP = new Piece();
        NP.width = width;
        NP.height = height;
        NP.id = id;

        NP.shape = new char[NP.height][NP.width];
        for(i=0; i<NP.height; i++){
            for(j=0; j<NP.width; j++){
                NP.shape[i][NP.width - 1 - j] = shape[i][j];
            }
        }

        for(i=0; i<NP.width; i++){
            if(NP.shape[0][i] == NP.id){
                NP.top_left_index = i;
                break;
            }
        }

        return NP;
    }
}
