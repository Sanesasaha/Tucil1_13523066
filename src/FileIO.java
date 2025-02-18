import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
    
    public static int max2(int a, int b){
        if(a>b){return a;}
        return b;
    }

    public static InputData readInputTxt(String directory, String txt){
        InputData data = new InputData();

        String line;
        String[] currentLine;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int i = 0; 
        char currentChar = '?';
        int j, k;

        txt = directory + txt;

        try (BufferedReader br = new BufferedReader(new FileReader(txt))){
            line = br.readLine();
            if(line == null){
                data.N = -1;
                return data;
            }
            currentLine = line.split(" ");
            
            // TODOLIST: Validasi -> nilai -1, >26, bukan angka, kosong?, bonus custom?, alphabet valid?
            
            data.N = Integer.parseInt(currentLine[0]);
            data.M = Integer.parseInt(currentLine[1]);
            data.P = Integer.parseInt(currentLine[2]);
            data.pieces = new Piece[data.P][8];
            data.S = br.readLine();
            
            

            if(data.S.equals("DEFAULT") == false){
                data.N = -1;
                return data;
            }

            // Cek height setiap piece
            line = br.readLine();
            data.pieces[0][0] = new Piece();

            while(line != null){
                for(j=0; j<line.length(); j++){
                    char c = line.charAt(j);
                    if(c != ' '){
                        currentChar = c;
                        break;
                    }
                }
            
                if(currentChar != data.pieces[i][0].id){
                    if(data.pieces[i][0].id != '?'){i++;}
                    data.pieces[i][0] = new Piece();
                    data.pieces[i][0].id = currentChar;
                }
                data.pieces[i][0].height++;
                data.pieces[i][0].width = max2(data.pieces[i][0].width, line.length());

                line = br.readLine();
            }
        }  catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(data.M);
        System.out.println(data.N);
        System.out.println(data.P);
        System.out.println(data.S);
        for(i=0;i<data.P;i++){
            System.out.print("[" + data.pieces[i][0].height + ", " + data.pieces[i][0].width + "] ");
        }

        // Pembacaan bentuk setiap piece (TODOLIST: handle kasus aneh2)
        try (BufferedReader br = new BufferedReader(new FileReader(txt))){
            line = br.readLine();
            line = br.readLine();

            for(i=0; i<data.P; i++){
                data.pieces[i][0].shape = new char[data.pieces[i][0].height][data.pieces[i][0].width];
                for(j=0; j<data.pieces[i][0].height; j++){
                    line = br.readLine();

                    for(k=0;k<line.length();k++){
                        data.pieces[i][0].shape[j][k] = line.charAt(k);
                    }
                    for(k=line.length();k<data.pieces[i][0].width;k++){
                        data.pieces[i][0].shape[j][k] = ' ';
                    }

                    
                    // cek bagian paling "atas kiri" dari piece
                    if(j==0){
                        for(k=0;k<line.length();k++){
                            if(line.charAt(k) == data.pieces[i][0].id){
                                data.pieces[i][0].top_left_index = k;
                                break;
                            }
                        }
                    }
                }
            }
        }  catch (IOException e){
            e.printStackTrace();
        }
        
        // Simpan semua variasi rotasi dan pencerminan pada pieces
        // TODOLIST: boleh prune yang simetrinya sama?
        for(i=0; i<data.P; i++){
            for(j=1;j<4;j++){
                data.pieces[i][j] = data.pieces[i][j-1].rotate90();
            }
            data.pieces[i][4] = data.pieces[i][j-1].mirror();
            for(j=5;j<8;j++){
                data.pieces[i][j] = data.pieces[i][j-1].rotate90();
            }
        }

        return data;
    }

    public static void main(String[] args) {
        InputData data = readInputTxt("data/", "tes.txt");
    }
}