import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Image {
    public static void create(Board B){
        int i, j, a, b;
        int width, height;
        int cellSize = 40;
        int borderSize = 5;
        
        
        width = B.width;
        height = B.height;
        String[] colors = {
            "#ff0000", "#b30000", "#00ff13", "#00b30d", "#0013ff", "#000db3", "#ff3a00", "#b32a00",
            "#00ff4e", "#00b33a", "#004eff", "#003ab3", "#ff7500", "#b35900", "#00ff89", "#00b36b",
            "#0089ff", "#0066b3", "#ffb000", "#b38a00", "#62ff00", "#49b300", "#00c4ff", "#0094b3",
            "#ffeb00", "#b3a600", "#27ff00", "#1db300", "#00feff", "#00b3b3", "#d7ff00", "#94b300",
            "#9cff00", "#75b300", "#2700ff", "#1c00b3", "#ff00eb", "#b300a9", "#ff00b0", "#b30087",
            "#ff0075", "#b3005b"
        };
        
        // Dictionary
        Map<Character, String> colorMap = new HashMap<>();
        for (i=0; i<26; i++){
            colorMap.put((char) ('A' + i), colors[i]);
        }

        int imgWidth = width*cellSize + (width+1)*borderSize;
        int imgHeight = height*cellSize + (height+1)*borderSize;
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);

        a = 0;
        for(i=borderSize;i<imgHeight;i+= cellSize+borderSize){
            b = 0;
            for(j=borderSize;j<imgWidth;j+= cellSize+borderSize){
                g2d.setColor(Color.decode(colorMap.get(B.state[a][b])));
                g2d.fillRect(j, i, cellSize, cellSize);
                b++;
            }    
            a++;
        }

        File outputFile = new File("data/output/grid_image.png");
        outputFile.getParentFile().mkdirs(); // Ensure the output directory exists
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }
}
