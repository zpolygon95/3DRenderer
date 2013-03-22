package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zachary
 */
public class ImageFactory
{
    static byte[] GIF_HEADER = {0x47, 0x49, 0x46, 0x38, 0x39, 0x61};
    
    public static Color[][] bufferedImageToColorArray(BufferedImage image)
    {
        Color[][] colors = new Color[image.getWidth()][image.getHeight()];
        for(int x = 0; x < colors.length; x++)
        {
            for(int y = 0; y < colors[0].length; y++)
            {
                colors[x][y] = new Color(image.getRGB(x, y));
            }
        }
        return colors;
    }
    
    public static byte[] getGIFColorTable(Color[][] imageData)
    {
        ArrayList<Color> colors = new ArrayList();
        for (int x = 0; x < imageData.length; x++)
        {
            for (int y = 0; y < imageData.length; y++)
            {
                if (!colors.contains(imageData[x][y]))
                {
                    colors.add(imageData[x][y]);
                }
            }
        }
        byte[] colorTable = new byte[colors.size() * 3];
        for (int i = 0; i < colorTable.length / 3; i++)
        {
            colorTable[i * 3] = (byte)colors.get(i).getRed();
            colorTable[(i + 1) * 3] = (byte)colors.get(i).getGreen();
            colorTable[(i + 2) * 3] = (byte)colors.get(i).getBlue();
        }
        return colorTable;
    }
    /**
     * writes a .gif image file to a specified location in memory
     * @param path the location to write the image, in the windows file format
     * @param imageData the color of each individual pixel, with the first dimension being horizontal position and the second being vertical
     */
    public static void exportGIF(String path, Color[][] imageData)
    {
        File file = new File(path);//create file to be written to
        FileOutputStream fOut;
        try
        {
            fOut = new FileOutputStream(file);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ImageFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        int imageWidth = imageData.length;
        int imageHeight = imageData[0].length;
        byte canvasWidthLSB = (byte)imageWidth;
        byte canvasWidthMSB = (byte)((imageWidth - canvasWidthLSB)/0xFF);
        byte canvasHeightLSB = (byte)imageHeight;
        byte canvasHeightMSB = (byte)((imageHeight - canvasHeightLSB)/0xFF);
        byte LSDPackedByte = (byte) 0b11110111;
        byte backgroundIndex = (byte) 0;
        byte aspectRatio = (byte) 0;
        byte[] logicalScreenDescriptor = {canvasWidthLSB, canvasWidthMSB, canvasHeightLSB, canvasHeightMSB, LSDPackedByte, backgroundIndex, aspectRatio};
        
    }
    
    /**
     * writes an animated .gif image file to a specified location in memory
     * @param path the location to write the image, in the windows file format
     * @param imageData the color of each individual pixel, with the first dimension being horizontal position, the second being vertical position, and the third being the frame selector
     */
    public static void exportGIF(String path, Color[][][] imageData)
    {
        
    }
}
