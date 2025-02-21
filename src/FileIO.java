import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static void writeOutputTxt(String filename, InputData data, long searchTime){
        try {
            int i, j;
            Board B = data.B;

            FileWriter writer = new FileWriter(filename.replace(".txt", "-solution.txt"));
            writer.write("Solusi untuk file " + filename.replace("-solution.txt", ".txt") + "\n");
            writer.write("\n");

            if(B.status == -1){
                writer.write("Tidak ada solusi yang ditemukan\n");
            } else{
                for(i=0;i<B.height;i++){
                    for(j=0;j<B.width;j++){
                            writer.write(B.state[i][j]);
                    }
                    writer.write("\n");
                }
            }

            writer.write("\n");
            writer.write("Waktu pencarian (ms) : "+ searchTime +"\n");
            writer.write("Banyak kasus ditinjau: "+ B.total_case +"\n");

            writer.close();
            System.out.println("Solusi berhasil disimpan dalam file " + filename);
        } catch (IOException e) {
            System.out.println("Terjadi error saat penulisan file");
        }
    }

    public static void writeOutputCLI(String filename, InputData data, long searchTime){
        System.out.println("Alternatif solusi:");
        if(data.B.status == -1){
            System.out.println("Tidak ada solusi yang ditemukan");
        } else{
            data.B.printState();
        }

        System.out.println();
        System.out.println("Waktu pencarian (ms) : "+ searchTime);
        System.out.println("Banyak kasus ditinjau: "+ data.B.total_case);
    }

    public static void main(String[] args) {
        InputData data = readInputTxt("data/input/", "original.txt");
        System.out.println("------------");
        long start, end, exec;
        
        start = System.nanoTime();
        data.B  = Solve.BruteForce(data, 0, 0);
        end = System.nanoTime();
        exec = (end - start)/1000000;

        writeOutputCLI("original.txt", data, exec);
        Image.create(data.B, "original-solution.txt");
        // writeOutputTxt("original.txt", data, exec);
    }
}