package src.gui;

import javax.swing.*;
import java.awt.*;
import src.core.Point3D;
import src.core.Vector3D;
import src.entities.Line3D;
import src.entities.Plane3D;
import src.entities.Scene;
import src.camera.Camera;

/**
 * Canvas that renders 3D scene entities using camera projection.
 */
public class RenderCanvas extends JPanel {

    private Camera camera;
    private Scene scene;

    
    private int[] selectedIndices = new int[0]; 
    
    private Point3D tempStart = null;
    private Point3D tempEnd = null;
    private Timer tempTimer = null;

    
    
    
    /**
     * Creates a render canvas with camera and scene.
     * @param camera camera used for projection
     * @param scene scene containing points, lines, planes, vectors
     */
    public RenderCanvas(Camera camera, Scene scene) {
        this.camera = camera;
        this.scene = scene;
        setBackground(new Color(40,44,52));
    }

    
    
    
    
    
    
    /**
     * Set list selection indices and repaint canvas.
     * @param indices selected entity indices
     */
    public void setSelectedIndices(int[] indices) {
        this.selectedIndices = (indices != null) ? indices : new int[0];
        repaint(); 
    }

    
    
    
    
    /**
     * Check if index is currently selected.
     * @param index candidate entity index
     * @return true if selected
     */
    private boolean isIndexSelected(int index) {
        for (int i : selectedIndices) {
            if (i == index) return true;
        }
        return false;
    }

    
    
    
    /**
     * Show a temporary line on canvas between two points.
     * @param p1 start point
     * @param p2 end point
     * @param durationMs duration in milliseconds
     */
    public void showTemporaryLine(Point3D p1, Point3D p2, int durationMs) {
        this.tempStart = p1;
        this.tempEnd = p2;
        repaint(); 

        if (tempTimer != null && tempTimer.isRunning()) tempTimer.stop();

        tempTimer = new Timer(durationMs, e -> {
            tempStart = null;
            tempEnd = null;
            repaint(); 
        });
        tempTimer.setRepeats(false); 
        tempTimer.start();
    }

    @Override
    
    
    
    /**
     * Paints all scene entities and temporary highlights.
     * @param g graphics context
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double scale = 1.0;

        drawAxis(g2, new Point3D(5,0,0), new Color(220,80,80), "X", centerX, centerY, scale);
        drawAxis(g2, new Point3D(0,5,0), new Color(80,200,120), "Y", centerX, centerY, scale);
        drawAxis(g2, new Point3D(0,0,5), new Color(80,140,255), "Z", centerX, centerY, scale);

        int currentIndex = 0;

        
        for(Point3D p : scene.getPoints()){
            double[] proj = camera.project(p);
            int x = centerX + (int)(proj[0] * scale);
            int y = centerY - (int)(proj[1] * scale);
            
            
            if (isIndexSelected(currentIndex)) {
                g2.setColor(Color.YELLOW);
                g2.fillOval(x - 6, y - 6, 12, 12); 
            } else {
                g2.setColor(new Color(120,200,255));
                g2.fillOval(x - 4, y - 4, 8, 8); 
            }
            currentIndex++;
        }

        
        for(Line3D line : scene.getLines()){
            Point3D pStart = line.pointAt(-100);
            Point3D pEnd = line.pointAt(100);
            
            if (isIndexSelected(currentIndex)) {
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(3.0f)); 
            } else {
                g2.setColor(new Color(230,230,230));
                g2.setStroke(new BasicStroke(1.0f)); 
            }
            renderLine(g2, pStart, pEnd, centerX, centerY, scale);
            g2.setStroke(new BasicStroke(1.0f)); 
            currentIndex++;
        }

        
        for(Plane3D plane : scene.getPlanes()){
            boolean isSelected = isIndexSelected(currentIndex);
            drawPlaneGrid(g2, plane, centerX, centerY, scale, isSelected);
            currentIndex++;
        }

        
        for(Vector3D v : scene.getVectors()) {
            Color color;
            if (isIndexSelected(currentIndex)) {
                color = Color.YELLOW;
                g2.setStroke(new BasicStroke(3.0f)); 
            } else {
                color = new Color(255, 180, 50);
                g2.setStroke(new BasicStroke(1.0f));
            }
            
            drawVector(g2, new Point3D(0, 0, 0), v, centerX, centerY, scale, color);
            g2.setStroke(new BasicStroke(1.0f)); 
            currentIndex++;
        }

        
        if (tempStart != null && tempEnd != null) {
            g2.setColor(new Color(255, 80, 80)); 
            drawDashedLine(g2, tempStart, tempEnd, centerX, centerY, scale);
        }
    }

    
    
    
    /**
     * Draws a plane grid at the plane location for visualization.
     */
    private void drawPlaneGrid(Graphics2D g2, Plane3D plane, int cx, int cy, double s, boolean isSelected){
        Vector3D n = plane.getNormal();
        Vector3D v1;
        if(Math.abs(n.getX()) < 0.9) v1 = n.cross(new Vector3D(1,0,0)).normalize();
        else v1 = n.cross(new Vector3D(0,1,0)).normalize();
        Vector3D v2 = n.cross(v1).normalize();

        int gridSize = 5;
        int resolution = 1;
        
        if (isSelected) {
            g2.setColor(new Color(255, 255, 0, 100)); 
            g2.setStroke(new BasicStroke(2.0f));
        } else {
            g2.setColor(new Color(120,140,200,60)); 
            g2.setStroke(new BasicStroke(1.0f));
        }

        for(int i = -gridSize; i <= gridSize; i += resolution){
            Point3D pStart1 = plane.getPoint().add(v1.scale(i)).add(v2.scale(-gridSize));
            Point3D pEnd1 = plane.getPoint().add(v1.scale(i)).add(v2.scale(gridSize));
            Point3D pStart2 = plane.getPoint().add(v2.scale(i)).add(v1.scale(-gridSize));
            Point3D pEnd2 = plane.getPoint().add(v2.scale(i)).add(v1.scale(gridSize));

            renderLine(g2, pStart1, pEnd1, cx, cy, s);
            renderLine(g2, pStart2, pEnd2, cx, cy, s);
        }
        g2.setStroke(new BasicStroke(1.0f)); 
    }

    
    
    
    /**
     * Renders a line segment projected from 3D endpoints.
     */
    private void renderLine(Graphics2D g2, Point3D p1, Point3D p2, int cx, int cy, double s){
        double[] proj1 = camera.project(p1);
        double[] proj2 = camera.project(p2);
        g2.drawLine(cx + (int)(proj1[0]*s), cy - (int)(proj1[1]*s), cx + (int)(proj2[0]*s), cy - (int)(proj2[1]*s));
    }

    
    
    
    /**
     * Draws a dashed line between two projected 3D points.
     */
    private void drawDashedLine(Graphics2D g2, Point3D p1, Point3D p2, int cx, int cy, double s) {
        Stroke oldStroke = g2.getStroke(); 
        float[] dashPattern = {5f, 5f};
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
        renderLine(g2, p1, p2, cx, cy, s);
        g2.setStroke(oldStroke); 
    }

    
    
    
    /**
     * Draws coordinate axis line and label.
     */
    private void drawAxis(Graphics2D g2, Point3D end, Color color, String label, int cx, int cy, double s){
        double[] p0 = camera.project(new Point3D(0,0,0));
        double[] p1 = camera.project(end);
        g2.setColor(color);
        g2.drawLine(cx + (int)(p0[0]*s), cy - (int)(p0[1]*s), cx + (int)(p1[0]*s), cy - (int)(p1[1]*s));
        g2.drawString(label, cx + (int)(p1[0]*s), cy - (int)(p1[1]*s));
    }

    
    
    
    /**
     * Draws a 3D vector from origin in projected coordinates.
     */
    private void drawVector(Graphics2D g2, Point3D origin, Vector3D vector, int cx, int cy, double s, Color color) {
        g2.setColor(color);
        Point3D endPoint = origin.add(vector); 
        double[] pStart = camera.project(origin);
        double[] pEnd = camera.project(endPoint);
        int x1 = cx + (int)(pStart[0] * s); int y1 = cy - (int)(pStart[1] * s);
        int x2 = cx + (int)(pEnd[0] * s); int y2 = cy - (int)(pEnd[1] * s);
        
        g2.drawLine(x1, y1, x2, y2);
        
        int arrowSize = 8;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        
        g2.drawLine(x2, y2, x3, y3);
        g2.drawLine(x2, y2, x4, y4);
    }

    
    
    
    /**
     * Returns the render camera.
     * @return current Camera
     */
    public Camera getCamera() { 
        return camera; 
    }
}

