/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 *
 * @author nikit
 */
public class Renderer extends JPanel {

    private Color[] lineColors = {new Color(0xE03134), new Color(0x1678D1), new Color(0xF6BF16)};
    private WorldState world;
    private Mechanics mechanics;
    private int selectedLine;

    public Renderer(WorldState world, Mechanics mechanics) {
        this.world = world;
        this.mechanics = mechanics;
        setFocusable(true);

        // On mouse press call handleClick
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void handleClick(int x, int y) {
        return;
    }

    // This paints the scene each time repaint() is called
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // erase previous picture
        drawLines(g);
        drawStations(g);
        drawText(g);
    }

    private void drawLines(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));

        for (MetroLine line : world.getLines()) {
            g.setColor(lineColors[line.getNumber()]);
            for (int i = 0; i < line.getRoute().size(); i++) {
                Station a = line.getRoute().get(i);
                Station b = line.getRoute().get(i + 1);
                g.drawLine((int) a.getX(), (int) a.getY(),
                        (int) b.getX(), (int) b.getY());
            }
        }
    }

    private void drawStations(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Station station : world.getStations()) {
            StationShape shape = station.getShape();
            double x = station.getX();
            double y = station.getY();
            double sizeShape = mechanics.getStationSize();

            draw(g2, shape, x, y, sizeShape, Color.WHITE, Color.BLACK, 6);
        }

    }

    public static void draw(Graphics2D g, StationShape type, double cx, double cy,
            double size, Color fill, Color outline, float strokeWidth) {
        Shape shape = createShape(type, cx, cy, size);
        g.setColor(fill);
        g.fill(shape);
        g.setColor(outline);
        g.setStroke(new BasicStroke(strokeWidth));
        g.draw(shape);
    }

    //create a certain shape so it can be drawn
    public static Shape createShape(StationShape type, double cx, double cy, double size) {
        double r = size / 2;
        switch (type) {
            case CIRCLE:
                return new Ellipse2D.Double(cx - r, cy - r, size, size);
            case SQUARE:
                return new Rectangle2D.Double(cx - r, cy - r, size, size);
            case TRIANGLE:
                Path2D t = new Path2D.Double();
                t.moveTo(cx, cy - r);        // top corner
                t.lineTo(cx + r, cy + r);    // bottom right
                t.lineTo(cx - r, cy + r);    // bottom left
                t.closePath();               // back to the top
                return t;
            case DIAMOND:
                Path2D d = new Path2D.Double();
                d.moveTo(cx, cy - r);        // top
                d.lineTo(cx + r, cy);        // right
                d.lineTo(cx, cy + r);        // bottom
                d.lineTo(cx - r, cy);        // left
                d.closePath();               // back to the top
                return d;
            default:
                throw new IllegalArgumentException("Unkown type: " + type);
        }
    }

    private void drawText(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(Station station : world.getStations()){
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2.drawString(station.getName(), (int)station.getX(), (int)station.getY() + mechanics.getStationSize() + 10);
        }
    }
}
