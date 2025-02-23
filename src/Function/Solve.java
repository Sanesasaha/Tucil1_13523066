package Function;

public class Solve {
    public static Board BruteForce(InputData data, int row, int col){
        int i, j;
        int currentRow = row;
        int currentCol = col;
        Board board = data.B;
        Piece[][] pieces = data.pieces;
        int total_pieces = data.P;

        // Menentukan indeks paling atas kiri yang dapat diisi
        boolean found = false;
        while(currentCol<board.width && !found){
            if(board.state[currentRow][currentCol] == ' '){
                found = true;
            } else{
                currentCol++;
            }
        }
        if(!found){
            currentRow++;
        }
        while(currentRow<board.height && !found){
            currentCol = 0;
            while(currentCol<board.width && !found){
                if(board.state[currentRow][currentCol] == ' '){
                    found = true;
                } else{
                    currentCol++;
                }
            }
            if(!found){
                currentRow++;
            }
        }
        // Basis: solusi ditemukan
        if(currentRow == board.height && currentCol == board.width && board.state[currentRow-1][currentCol-1] != ' '){
            board.status = 1;
            return board;
        }
        // Mencoba meletakkan piece di dalam board
        for(i=0; i<total_pieces; i++){
            j = 0;
            while(j<8 && pieces[i][0].isPlaced == false){
                board.total_case++;
                if(pieces[i][0].isPlaced == false){
                    board.place(pieces[i][j], currentRow, currentCol);
                    // Jika berhasil diletakkan, lanjutkan ke tahap berikutnya
                    if(board.state[currentRow][currentCol] == pieces[i][j].id){
                        pieces[i][0].isPlaced = true;
                        board = BruteForce(data, currentRow, currentCol);
                        // Jika solusi sudah ditemukan, keluar dari fungsi
                        if(board.status == 1){
                            return board;
                        }
                        // Jika solusi tidak ditemukan, hapus piece yang baru saja diletakkan, tukar dengan konfigurasi lain
                        pieces[i][0].isPlaced = false;
                        board.pop(pieces[i][j], currentRow, currentCol);
                    } 
                }
                j++;
            }
        }
        // Tidak ada solusi yang ditemukan
        data.B.status = -1;
        return data.B;
    }
}
