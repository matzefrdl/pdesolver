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

public abstract class DrawingPanel extends JPanel {

    public abstract void draw(Graphics g);

    public DrawingPanel(String plotName) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(plotName);
            frame.setSize(533, 533);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.add(this);
            frame.setVisible(true);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}
