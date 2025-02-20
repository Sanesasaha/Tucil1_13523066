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

                    // Periksa apakah id duplikat
                    for(k=0;k<i;k++){
                        if(data.pieces[i][0].id == data.pieces[k][0].id){
                            msg("Error: Karakter pada piece duplikat");
                            return false;
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

        }  catch (IOException e){
            msg("Error: File txt tidak ditemukan");
            return false;
        }

        // setiap input distrip kanan
        // row kosong skip aja...


        // waktu displit ngga jadi 3
        // waktu displit, ada yang bukan angka
        // dimensi <=0
        // jumlah piece > 26

        // baris 2 bukan DEFAULT

        // baris piece ada yang 1 baris beda huruf
        // jumlah piece ngga sama kaya yg dibilang di baris 1
        // ada karakter yang bukan abjad kapital
        // ada karakter yang udah kepake di piece lain



        // [Early validation]

        // kalau ada piece yang gk mungkin disimpen di state awal, gk ada solusi
        // hapus simetri yang duplikat
        // hapus konfigurasi yang "ngga mungkin disimpen"
        // cek jumlah tile dari semua piece, kalau != dimensi board, gk ada solusi
        // rata kirii

        return true;
    }

    public static void main(String[] args){
        boolean valid = txt("data/", "original.txt");
    }
}