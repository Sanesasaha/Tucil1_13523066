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

        int i = 0; 
        char currentChar = '?';
        int j, k;

        boolean isValid = Validation.txt(directory, txt);
        if(!isValid){
            System.out.println("Input teks file tidak valid!");
            return data;
        }

        txt = directory + txt;

        try (BufferedReader br = new BufferedReader(new FileReader(txt))){
            line = br.readLine();
            if(line == null){
                data.N = -1;
                return data;
            }
            currentLine = line.split(" ");
            
            data.N = Integer.parseInt(currentLine[0]);
            data.M = Integer.parseInt(currentLine[1]);
            data.P = Integer.parseInt(currentLine[2]);
            data.pieces = new Piece[data.P][8];
            data.S = br.readLine();
            data.B = new Board();
            data.B.width = data.M;
            data.B.height = data.N;
            data.B.initiate();

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

        // Pembacaan bentuk setiap piece
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
        
        // Cek apakah jumlah tile pada piece mungkin mengisi board
        isValid =  Validation.totalTile(data);
        if(!isValid){
            data.B.status = -1;
            return data;
        }

        // Simpan semua variasi rotasi dan pencerminan pada pieces
        for(i=0; i<data.P; i++){
            for(j=1;j<4;j++){
                data.pieces[i][j] = data.pieces[i][j-1].rotate90();
            }
            data.pieces[i][4] = data.pieces[i][j-1].mirror();
            for(j=5;j<8;j++){
                data.pieces[i][j] = data.pieces[i][j-1].rotate90();
            }
        }

        // Optimalisasi simetri
        data = Validation.symmetryOptimization(data);

        return data;
    }

    public static void main(String[] args) {
        InputData data = readInputTxt("data/", "original.txt");
        System.out.println("------------");
        long start, end, exec;
        
        // data.B.place(data.pieces[0][0], 0, 0);
        // data.B.place(data.pieces[1][0], 0, 1);
        // data.B.place(data.pieces[2][0], 2, 2);
        // data.B.printState();

        // data.pieces[1][1].printShape();
        // data.pieces[1][1].stats();
        
        start = System.nanoTime();
        data.B  = Solve.BruteForce(data, 0, 0);
        end = System.nanoTime();
        exec = (end - start);

        System.out.println();
        data.B.printState();
        System.out.println("status: " + data.B.status);
        System.out.println("Time (ms): " + exec/1000000);
        System.out.println("Total case: " + data.B.total_case);
    }
}