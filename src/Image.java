import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Image {
    public static void create(Board B, String filename){
        int i, j, a, b;
        int width, height;
        int cellSize = 40;
        int borderSize = 5;
        
        
        width = B.width;
        height = B.height;

        String[] colors = {
            "#808080", "#8b4513", "#228b22", "#808000", "#000080", "#9acd32",
            "#8fbc8f", "#800080", "#ff0000", "#ff8c00", "#ffff00", "#7cfc00",
            "#8a2be2", "#00ff7f", "#4169e1", "#dc143c", "#00ffff", "#0000ff",
            "#ff00ff", "#db7093", "#f0e68c", "#ff1493", "#ffa07a", "#ee82ee",
            "#87cefa", "#ffe4e1"
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

        // Generating the image
        a = 0;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(i=borderSize;i<imgHeight;i+= cellSize+borderSize){
            b = 0;
            for(j=borderSize;j<imgWidth;j+= cellSize+borderSize){
                g2d.setColor(Color.decode(colorMap.get(B.state[a][b])));
                g2d.fillRect(j, i, cellSize, cellSize);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString(Character.toString(B.state[a][b]), j+ cellSize/2 - 8, i + cellSize/2 + 8);
                b++;
            }    
            a++;
        }

        // Save
        filename.replace(".txt", "");
        File outputFile = new File("data/output/" + filename + ".png");
        outputFile.getParentFile().mkdirs();
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image disimpan sebagai " + filename + ".png");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
