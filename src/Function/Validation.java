package Function;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Validation {
    public static int max2(int a, int b){
        if(a>b){return a;}
        return b;
    }

    public static void msg(String s){
        
        System.out.println(s);
    }

    public static boolean isUpperCaseAlphabet(char c){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(alphabet.indexOf(c) == -1){
            return false;
        }
        return true;
    }

    public static boolean txt(String directory, String txt){
        String line;
        String[] currentLine;

        int i, j, k;
        char currentChar, c, firstOcc;

        txt = directory + txt;

        try (BufferedReader br = new BufferedReader(new FileReader(txt))){
            InputData data = new InputData();

            // Validasi baris pertama
            line = br.readLine();
            if(line == null){
                msg("Error: File .txt tidak valid");
                return false;
            }
            line.replaceAll("\\s+$", "");
            
            if(line.length() ==0){
                msg("Error: Terdapat empty line pada file .txt");
                return false;
            }
            
            // Data N M P kurang/lebih/bukan angka
            currentLine = line.split(" ");
            if(currentLine.length != 3){
                msg("Error: Format N M P tidak valid");
                return false;
            }
            try{
                data.N = Integer.parseInt(currentLine[0]);
                data.M = Integer.parseInt(currentLine[1]);
                data.P = Integer.parseInt(currentLine[2]);

                if(data.N <= 0 || data.M <= 0 || data.P <= 0){
                    msg("Error: N, M, atau P bernilai <= 0");
                    return false;
                }
                if(data.P > 26){
                    msg("Nilai P > 26");
                    return false;
                }
            } catch(Exception as){
                msg("Error: Format N M P tidak valid");
                return false;
            }
            

            // Baris 2
            data.S = br.readLine();
            if(data.S == null){
                msg("Error: File .txt tidak valid");
                return false;
            }
            line.replaceAll("\\s+$", "");
            
            if(line.length() ==0){
                msg("Error: Terdapat empty line pada file .txt");
                return false;
            }

            if(data.S.equals("DEFAULT") == false){
                msg("Error: mode permainan tidak valid");
                return false;
            }

            // Baris 3-selesai
            // Cek height setiap piece
            line = br.readLine();
            data.pieces = new Piece[data.P][8];
            data.pieces[0][0] = new Piece();
            i = 0;
            currentChar = '?';
            try{
                while(line != null){
                    line.replaceAll("\\s+$", "");

                    if(line.length() ==0){
                        msg("Error: Terdapat empty line pada file .txt");
                        return false;
                    }

                    firstOcc = '?';

                    // Validasi karakter dalam 1 baris
                    for(j=0; j<line.length(); j++){
                        c = line.charAt(j);
                        if(c != ' '){
                            currentChar = c;
                            // Validasi alfabet kapital
                            if(!isUpperCaseAlphabet(c)){
                                msg("Error: Karakter piece tidak valid");
                                return false;
                            }

                            if(currentChar != data.pieces[i][0].id){
                                // Inisialisasi id piece
                                if(data.pieces[i][0].id == '?'){
                                    data.pieces[i][0].id = currentChar;
                                }
                                if(firstOcc != currentChar){
                                    if(firstOcc == '?'){
                                        firstOcc = currentChar;
                                    } else{
                                        msg("Error: Format piece tidak valid (Karakter berbeda dalam 1 line)");
                                        return false;
                                    }
                                }
                            }
                        }
                    }

                    // Cek apakah baris saat ini merupakan piece baru atau sambungan baris sebelumnya
                    if(data.pieces[i][0].id != '?' && data.pieces[i][0].height != 0){
                        if(currentChar != data.pieces[i][0].id){
                            i++;
                            data.pieces[i][0] = new Piece();
                            data.pieces[i][0].id = currentChar;
                        }
                    }
                    data.pieces[i][0].height++;
                    data.pieces[i][0].width = max2(data.pieces[i][0].width, line.length());

                    
                    line = br.readLine();
                }
                
                if(i < data.P-1){
                    msg("Error: Jumlah piece kurang");
                    return false;
                }
            } catch(Exception as){
                if(i > data.P-1){
                    msg("Error: Jumlah piece melebihi nilai P");
                    return false;
                }
                msg("Error: Format piece tidak valid");
                return false;
            }
            // Periksa apakah id duplikat
            for(i=0;i<data.P;i++){
                for(k=i+1;k<data.P;k++){
                    if(data.pieces[i][0].id == data.pieces[k][0].id){
                        msg("Error: Karakter pada piece duplikat");
                        return false;
                    }
                }
            }
        }  catch (IOException e){
            msg("Error: File txt tidak ditemukan");
            return false;
        }

        return true;
    }

    public static boolean totalTile(InputData data){
        int i, j, k;
        int total = 0;

        // Hitung jumlah petak yang dapat diisi pada board
        for(i=0;i<data.B.height;i++){
            for(j=0;j<data.B.width;j++){
                if(data.B.state[i][j] == ' '){
                    total++;
                }
            }
        }

        for(k=0;k<data.P;k++){
            // Periksa apakah dimensi piece lebih kecil dari dimensi board
            if(
                (data.pieces[k][0].height > data.B.height &&
                data.pieces[k][0].height > data.B.width) ||
                (data.pieces[k][0].width > data.B.width &&
                data.pieces[k][0].width > data.B.height)
            ){
                return false;
            }

            // Hitung total petak setiap piece
            for(i=0;i<data.pieces[k][0].height;i++){
                for(j=0;j<data.pieces[k][0].width;j++){
                    if(data.pieces[k][0].shape[i][j] == data.pieces[k][0].id){
                        total--;
                    }
                }    
            }
        }

        if(total == 0){
            return true;
        } else{
            return false;
        }
    }

    public static InputData symmetryOptimization(InputData data){
        int i, j, k, a, b;
        boolean isDuplicate;
        Piece[][] pieces = data.pieces;

        for(i=0; i<data.P; i++){
            for(j=0; j<7; j++){
                // Jika suatu konfigurasi berukuran lebih dari suatu dimensi board, 
                // anggap selayaknya piece kosong
                if(pieces[i][j].height > data.B.height || pieces[i][j].width > data.B.width){
                    pieces[i][j].height = 0;
                    pieces[i][j].width = 0;
                }
                for(k=j+1; k<8; k++){
                    if(
                        pieces[i][j].height != 0 && 
                        pieces[i][k].height != 0 &&
                        pieces[i][j].height == pieces[i][k].height
                    ){
                        // Cek apakah kedua konfigurasi sama
                        isDuplicate = true;
                        for(a=0;a<pieces[i][k].height;a++){
                            for(b=0;b<pieces[i][k].width;b++){
                                if(pieces[i][j].shape[a][b] != pieces[i][k].shape[a][b]){
                                    isDuplicate = false;
                                }
                            }    
                        }

                        // Jika sama, perlakukan selayaknya piece kosong (berukuran 0x0)
                        if(isDuplicate){
                            pieces[i][k].height = 0;
                            pieces[i][k].width = 0;
                        }
                    }
                }
            }
        }
        return data;
    }

    public static void main(String[] args){
        boolean valid = txt("data/", "original.txt");
        System.out.println(valid);
    }
}