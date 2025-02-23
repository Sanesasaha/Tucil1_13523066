package Function;

public class Board {
    public int width = -1;
    public int height = -1;
    public int status = 0; // -1 : no solution, 0 : still searching, 1 : found

    public int total_case = 0;
    public char[][] state;

    public void initiate(){
        int i,j;
        state = new char[height][width];
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                state[i][j] = ' ';
            }
        }
    }

    public void printState(){
        int i, j;
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                if(state[i][j] == ' '){
                    System.out.print(".");
                } else{
                    System.out.print(state[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Menempatkan piece P dengan tli pada posisi [row][col]
    public void place(Piece P, int row, int col){
        int i, j;

        // validasi dimensi awal
        if(row + P.height > height || col - P.top_left_index < 0 || col + P.width - P.top_left_index - 1 >= width){
            // total_case++;
            return;
        }
        
        // validasi apakah seluruh petak dapat ditempati
        for(i=0;i<P.height;i++){
            for(j=0;j<P.width;j++){
                if(P.shape[i][j] != ' ' && state[row + i][col + j - P.top_left_index] != ' '){
                    // total_case ++;
                    return;
                }
            }
        }
        
        // Simpan Piece
        for(i=0;i<P.height;i++){
            for(j=0;j<P.width;j++){
                if(P.shape[i][j] != ' ' && state[row + i][col + j - P.top_left_index] == ' '){
                state[row + i][col + j - P.top_left_index] = P.id;
                }
            }
        }
    }

    public void pop(Piece P, int row, int col){
        int i, j;
        for(i=0;i<P.height;i++){
            for(j=0;j<P.width;j++){
                if(P.shape[i][j] != ' ' && state[row + i][col + j - P.top_left_index] == P.id){
                    state[row + i][col + j - P.top_left_index] = ' ';
                }
            }
        }
    }
}
