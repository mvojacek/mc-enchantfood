package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class ImageUtils
{
    public static BufferedImage fromResourceLocation(ResourceLocation loc) throws IOException
    {
        BufferedImage img;
        try (InputStream is = ImageUtils.class.getClassLoader().getResourceAsStream(String.format("assets/%s/%s", loc.getResourceDomain(), loc.getResourcePath()))
        )
        {
            img = ImageIO.read(is);
        }
        return img;
    }

    public static BufferedImage fromResourceLocationSilent(ResourceLocation loc)
    {
        try
        {
            return fromResourceLocation(loc);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadTCMResource(String resource)
    {
        return fromResourceLocationSilent(ResourceHelper.getResourceLocation("securepm", resource));
    }

    public static BufferedImage fromFile(File file) throws IOException
    {
        BufferedImage img;
        img = ImageIO.read(file);
        return img;
    }

    public static BufferedImage fillConsecutiveColor(BufferedImage in, int baseColor, int replacementColor, int startX, int startY)
    {
        int width = in.getWidth();
        int height = in.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(in, 0, 0, null);
        g.dispose();

        HelperFillConsecutiveColor.CoordinateRegistry registry = new HelperFillConsecutiveColor.CoordinateRegistry(width, height);
        Deque<int[]> deque = new ArrayDeque<>();

        out.setRGB(startX, startY, replacementColor);
        deque.add(new int[]{startX, startY});

        while (!deque.isEmpty())
        {
            int[] current = deque.pollLast();

            registry.consumeAllFreeAdjacent(current[0], current[1], (consX, consY, consReg) ->
            {
                if (consX > consReg.maxIndexX || consY > consReg.maxIndexY) return;
                if (out.getRGB(consX, consY) != baseColor) return;
                out.setRGB(consX, consY, replacementColor);
                consReg.visitCoord(consX, consY);
                deque.add(new int[]{consX, consY});
            });
        }

        return out;
    }

    public static BufferedImage dye(BufferedImage image, Color color)
    {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return dyed;
    }

    public static BufferedImage dye(BufferedImage image, int color)
    {
        return dye(image, fromInt(color));
    }

    public static int fromColor(Color c)
    {
        return (c.getAlpha() & 255) << 24 | (c.getRed() & 255) << 16 | (c.getGreen() & 255) << 8 | c.getBlue() & 255;
    }

    public static Color fromInt(int i)
    {
        return new Color(i >> 16 & 255, i >> 8 & 255, i & 255, i >> 24 & 255);
    }

    public static int scaleColor(int color, float scalar)
    {
        Color clr = fromInt(color);
        int r = (int) (clr.getRed() * scalar);
        int g = (int) (clr.getGreen() * scalar);
        int b = (int) (clr.getBlue() * scalar);
        int a = clr.getAlpha();
        return fromColor(new Color(r, g, b, a));
    }

    public static int brighter(int color)
    {
        return fromColor(fromInt(color).brighter());
    }

    public static int darker(int color)
    {
        return fromColor(fromInt(color).darker());
    }

    public static BufferedImage replacePixels(BufferedImage in, int color, int[]... pixelPairs)
    {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = out.createGraphics();
        graphics.drawImage(in, 0, 0, null);
        graphics.dispose();
        for (int[] pair : pixelPairs)
            out.setRGB(pair[0], pair[1], color);
        return out;
    }

    public static BufferedImage crop(BufferedImage in, int x, int y, int endX, int endY)
    {
        BufferedImage out = new BufferedImage(endX - x, endY - y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = out.createGraphics();
        graphics.drawImage(in, 0, 0, endX - x, endY - y, x, y, endX, endY, null);
        graphics.dispose();
        return out;
    }

    public static BufferedImage overlay(BufferedImage bg, BufferedImage fg)
    {
        BufferedImage out = new BufferedImage(bg.getWidth(), bg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = out.createGraphics();
        graphics.drawImage(bg, 0, 0, null);
        graphics.drawImage(fg, 0, 0, null);
        graphics.dispose();
        return out;
    }

    private static class HelperFillConsecutiveColor
    {
        private static final int[][] adjacentMasks = new int[][]{
                new int[]{1, 0}, new int[]{-1, 0},
                new int[]{0, 1}, new int[]{0, -1}};

        private interface CoordsConsumer
        {
            void accept(int x, int y, CoordinateRegistry registry);
        }

        private static class CoordinateRegistry
        {
            private final int maxIndexX;
            private final int maxIndexY;
            private int[][] visitedCoords;
            private int[] nextFreeY;

            public CoordinateRegistry(int resX, int resY)
            {
                maxIndexX = resX - 1;
                maxIndexY = resY - 1;
                visitedCoords = new int[resX][resY];
                nextFreeY = new int[resX];
            }

            public boolean hasVisited(int x, int y)
            {
                if (x > maxIndexX || x < 0) return false;
                for (int i = 0, end = Math.min(nextFreeY[x] + 1, maxIndexY); i < end; i++)
                {
                    if (y == visitedCoords[x][i]) return true;
                }
                return false;
            }

            public boolean visitCoord(int x, int y)
            {
                if (x > maxIndexX || x < 0 || y > maxIndexY || y < 0) return false;
                if (hasVisited(x, y)) return false;
                visitedCoords[x][nextFreeY[x]] = y;
                nextFreeY[x]++;
                return false;
            }

            public void consumeAllFreeAdjacent(int x, int y, CoordsConsumer consumer)
            {
                for (int[] i : adjacentMasks)
                {
                    int x2 = i[0] + x;
                    int y2 = i[1] + y;
                    if (x2 > maxIndexX || x2 < 0 || y2 > maxIndexY || y2 < 0 || hasVisited(x2, y2)) continue;
                    consumer.accept(x2, y2, this);
                }
            }
        }
    }
}
