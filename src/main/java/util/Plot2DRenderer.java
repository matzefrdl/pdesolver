package util;

import galerkin.elements.Element;
import galerkin.elements.Triangle;
import galerkin.meshes.Mesh2D;
import maths.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class Plot2DRenderer extends DrawingPanel {

    private Plot plot;

    private Mesh2D mesh;

    private Rectangle clip;
    private int N ;
    private BufferedImage img;

    public Plot2DRenderer(Plot plot, Mesh2D mesh, String plotName) {
        super(plotName);
        this.plot = plot;
        this.mesh = mesh;
        this.N = (int) Math.sqrt(mesh.getNumKnots());
        this.img = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.DARK_GRAY);
        clip = new Rectangle((int) (this.getWidth() * 0.125f), (int) (this.getHeight() * 0.125f), (int) (this.getWidth() * 0.75f), (int) (this.getHeight() * 0.75f));
        if(clip.width != clip.height) {
            int size = Math.min(clip.width, clip.height);
            clip.width = size;
            clip.height = size;
            clip.x = (this.getWidth() - size) / 2;
            clip.y = (this.getHeight() - size) / 2;
        }
        g.drawRect(clip.x, clip.y, clip.width, clip.height);
        g.setClip(clip);
        Matrix data = plot.getMatrix();
        Color[] ydata = yconvertToIntegers(data);
        for(int x = 0; x < N; x++) {
            for(int y = 0; y < N; y++) {
                int i = x * N + y;
                img.setRGB(x,y, ydata[i].getRGB());
            }
        }
        g.drawImage(img, clip.x+1, clip.y+1, clip.width-1, clip.height-1, null);
    }

    private Color map(float t) {
        Color[] colors = {new Color(0, 0, 0),
                            new Color(1.0f, 0, 0),
                            new Color(1.0f, 1.0f, 0),
                        new Color(1.0f, 1.0f, 1.0f)};
        float dis = 1.0f / (colors.length-1);
        int i = (int) (t / dis);
        if(i == colors.length-1) return colors[i];
        Color c0 = colors[i];
        Color c1 = colors[i+1];
        float mt = (t - i * dis) / dis;
        return lerp(c0,c1,mt);
    }

    private int lerp(int a, int b, float t) {
        return (int) (a * (1.0f - t) + b * t);
    }

    private Color lerp(Color c0, Color c1, float t) {
        return new Color(lerp(c0.getRed(), c1.getRed(), t),
                lerp(c0.getGreen(), c1.getGreen(), t),
                lerp(c0.getBlue(), c1.getBlue(), t));
    }
    private Color[] yconvertToIntegers(Matrix data) {
        Color[] idata = new Color[data.getRowCount()];
        for(int i = 0; i < data.getRowCount(); i++) {
            float y = data.get(i, 0);
            float t = Math.max(0, (plot.getA() - y) / (plot.getA() - plot.getB()));
            t = Math.min(1, t);
            idata[i] = map(t);//new Color(t,t,t);//Color.getHSBColor(t * 0.9f, 1.0f, 1.0f);
        }
        return idata;
    }
}
