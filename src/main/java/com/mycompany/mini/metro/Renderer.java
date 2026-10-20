/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.List;

/**
 *
 * @author nikit
 */
public class Renderer extends JPanel {

    private final Color[] lineColors = {new Color(0xE03134),
        new Color(0xF6BF16), new Color(0x1678D1)};
    private WorldState world;
    private Mechanics mechanics;
    private MiniMetroGUI frame;
    private int selectedLine;
    private int passengerRadius = 5;
    private static final double[][] slots = {
        {-3, -3},
        {3, -3},
        {-3, 3},
        {3, 3}
    };

    public Renderer(WorldState world, Mechanics mechanics, MiniMetroGUI frame) {
        this.world = world;
        this.mechanics = mechanics;
        this.frame = frame;
        setFocusable(true);
        setOpaque(false);

        // On mouse press call handleClick
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void setGame(WorldState world, Mechanics mechanics) {
        this.world = world;
        this.mechanics = mechanics;
    }

    public void handleClick(int x, int y) {
        mechanics.addStationToLine(this.selectedLine, mechanics.findStationAt(x, y));
    }

    public void setSelectedLine(int i) {
        this.selectedLine = i;
    }

    public void clearLine() {
        mechanics.clearSelectedLine(selectedLine);
    }

    public void pause() {
        mechanics.pause();
        if (mechanics.getPause()) {
            frame.getPauseButton().setText("Resume");
        } else {
            frame.getPauseButton().setText("Pause");
        }
    }

    public void speed2x() {
        mechanics.speed2x();
        if (mechanics.getSpeed2x()) {
            frame.getSpeedButton().setText("Speed 2x");
        } else {
            frame.getSpeedButton().setText("Speed 1x");
        }
    }

    // This paints the scene each time repaint() is called
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // erase previous picture

        Graphics2D g2 = (Graphics2D) g;
        // smooth edges for shapes and lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // smooth text
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // draw strokes at their exact (decimal) position instead of rounding to pixels
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        drawLines(g2);
        drawStations(g2);
        drawText(g2);
        drawPassengers(g2);
        drawTrains(g2);
        drawPassengerTrain(g2);

        frame.getTimeLabel().setText(Statistics.formatTime(world.getElapsed()));
        frame.getDeliveredLabel().setText(world.getStatistics().getDelivered() + "");
        frame.getWaitingLabel().setText(world.getStatistics().getWaiting() + "");
        frame.getOnTrainsLabel().setText(world.getStatistics().getOnTrains() + "");
        mechanics.findMostCrowded();
        frame.getMostCrowdedLabel().setText(world.getStatistics().getMostCrowded() + "");
    }

    private void drawLines(Graphics2D g2) {
        g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (MetroLine line : world.getLines()) {
            g2.setColor(lineColors[line.getNumber()]);
            for (int i = 0; i < line.getRoute().size() - 1; i++) {
                Station a = line.getRoute().get(i);
                Station b = line.getRoute().get(i + 1);
                g2.drawLine(a.getX(), a.getY(),
                        b.getX(), b.getY());
            }
        }
    }

    private void drawStations(Graphics2D g2) {
        for (Station station : world.getStations()) {
            StationShape shape = station.getShape();
            double x = station.getX();
            double y = station.getY();
            double sizeShape = mechanics.getStationSize();

            draw(g2, shape, x, y, sizeShape, Color.WHITE, Color.BLACK, 4);
        }

    }

    public static void draw(Graphics2D g, StationShape type, double cx,
            double cy,
            double size, Color fill, Color outline, float strokeWidth) {
        Shape shape = createShape(type, cx, cy, size);
        g.setColor(fill);
        g.fill(shape);
        g.setColor(outline);
        g.setStroke(new BasicStroke(strokeWidth));
        g.draw(shape);
    }

    //create a certain shape so it can be drawn
    public static Shape createShape(StationShape type, double cx, double cy,
            double size) {
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

    private void drawText(Graphics2D g2) {
        for (Station station : world.getStations()) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2.drawString(station.getName(),
                    station.getX() - mechanics.getStationSize() + 3,
                    station.getY() + mechanics.getStationSize() + 10);
        }
    }

    private void drawPassengers(Graphics2D g2) {
        for (int i = 0; i < world.getStations().size(); i++) {
            Station station = world.getStationById(i);
            List<Passenger> waiting = station.getWaiting();
            for (int j = 0; j < waiting.size(); j++) {
                draw(g2, waiting.get(j).getDestination(),
                        station.getX() + (8 * (j + 2)),
                        station.getY() - 5,
                        4.0,
                        Color.DARK_GRAY,
                        Color.DARK_GRAY,
                        2);
            }
        }
    }

    private void drawTrains(Graphics2D g2) {
        for (Train train : world.getTrains()) {
            draw(
                    g2,
                    StationShape.SQUARE,
                    train.getX(),
                    train.getY(),
                    17.5,
                    lineColors[train.getMetroLineIndex()],
                    Color.BLACK,
                    (float) 2.0
            );
        }
    }
    //math to for position in the train

    private void drawPassengerTrain(Graphics2D g2) {
        for (int i = 0; i < world.getTrains().size(); i++) {
            Train train = world.getTrains().get(i);
            List<Passenger> passengerTrain = train.getPassengers();
            for (int j = 0; j < passengerTrain.size(); j++) {
                draw(
                        g2,
                        passengerTrain.get(j).getDestination(),
                        train.getX() + slots[j][0],
                        train.getY() + slots[j][1],
                        4.0,
                        Color.DARK_GRAY,
                        Color.DARK_GRAY,
                        1);
            }
        }
    }
}
