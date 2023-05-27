package util;

import maths.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

public class PlotRenderer extends DrawingPanel {

    private Plot plot;

    public PlotRenderer(Plot plot, String plotName) {
        super(plotName);
        this.plot = plot;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.DARK_GRAY);
        Rectangle clip = new Rectangle((int) (this.getWidth() * 0.125f), (int) (this.getHeight() * 0.125f), (int) (this.getWidth() * 0.75f), (int) (this.getHeight() * 0.75f));
        g.drawRect(clip.x, clip.y, clip.width, clip.height);
        g.setClip(clip);

        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        Matrix data = plot.getMatrix();
        int[] xdata = xconvetToIntegers(data);
        int[] ydata = yconvertToIntegers(data);
        g.setColor(Color.BLUE);
        for(int i = 0; i < xdata.length-1; i++) {
            g.drawLine(xdata[i],ydata[i],xdata[i+1],ydata[i+1]);
        }
        g.setClip(null);
        g.setColor(Color.DARK_GRAY);
        g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 1.5f));
        int h = g.getFontMetrics().getHeight();
        g.drawString("0", clip.x - g.getFontMetrics().charWidth('0') - 10, clip.y + clip.height / 2 + h/4);
        g.drawString("1", clip.x + clip.width + 10, clip.y + clip.height / 2 + h/4);
        String min = "" + plot.getA();
        String max = "" + plot.getB();
        g.drawString(min, clip.x + clip.width / 2 - g.getFontMetrics().charsWidth(min.toCharArray(), 0, min.length()) / 2, clip.y + clip.height + h);
        g.drawString(max, clip.x + clip.width / 2 - g.getFontMetrics().charsWidth(max.toCharArray(), 0, max.length()) / 2, clip.y - g.getFontMetrics().getAscent());
    }

    private int[] yconvertToIntegers(Matrix data) {
        int[] idata = new int[data.getRowCount()];
        for(int i = 0; i < data.getRowCount(); i++) {
            float y = data.get(i, 0);
            float t = (plot.getA() - y) / (plot.getA() - plot.getB());
            idata[i] = (int) (t * this.getHeight() * 0.75f + this.getHeight() * 0.125f);
        }
        return idata;
    }

    private int[] xconvetToIntegers(Matrix data) {
        int[] idata = new int[data.getRowCount()];
        for(int i = 0; i < data.getRowCount(); i++) {
            idata[i] = (int) (((float) i / (float) (data.getRowCount()-1)) * (this.getWidth() * 0.75f) + this.getWidth() * 0.125f);
        }
        return idata;
    }
}
