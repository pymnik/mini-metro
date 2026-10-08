///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.mini.metro;
//
///**
// *
// * @author lasse
// */
//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Rectangle2D;
//
//public class ShapeRenderer {
//
//    private ShapeRenderer() {
//    }
//
//    // method to draw, determined by type, center, size, colors fill/outline 
//    // g is the paintbrush
//    public static void draw(Graphics2D g, StationType type, double cx, double cy,
//            double size, Color fill, Color outline, float strokeWidth) {
//        Shape shape = createShape(type, cx, cy, size);
//        g.setColor(fill);
//        g.fill(shape);
//        g.setColor(outline);
//        g.setStroke(new BasicStroke(strokeWidth));
//        g.draw(shape);
//    }
//
//    //create a certain shape so it can be drawn
//    public static Shape createShape(StationType type, double cx, double cy, double size) {
//        double r = size / 2;
//        switch (type) {
//            case CIRCLE:
//                return new Ellipse2D.Double(cx - r, cy - r, size, size);
//            case SQUARE:
//                return new Rectangle2D.Double(cx - r, cy - r, size, size);
//            default:
//                throw new IllegalArgumentException("Unkown type: " + type);
//        }
//    }
//}
