
package src.gui;

import javax.swing.*;
import java.awt.*;
import src.core.Point3D;
import src.core.Vector3D;
import src.entities.Line3D;
import src.entities.Plane3D;
import src.entities.Scene;
import src.camera.Camera;

public class RenderCanvas extends JPanel {

    private Camera camera;
    private Scene scene;

    public RenderCanvas(Camera camera, Scene scene) {

        this.camera = camera;
        this.scene = scene;

        setBackground(new Color(40,44,52));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        double scale = 750.0 / camera.getRadius();

        Color axisX = new Color(220,80,80);
        Color axisY = new Color(80,200,120);
        Color axisZ = new Color(80,140,255);

        drawAxis(g2, new Point3D(5,0,0), axisX, "X", centerX, centerY, scale);
        drawAxis(g2, new Point3D(0,5,0), axisY, "Y", centerX, centerY, scale);
        drawAxis(g2, new Point3D(0,0,5), axisZ, "Z", centerX, centerY, scale);

        g2.setColor(new Color(120,200,255));

        for(Point3D p : scene.getPoints()){

            double[] proj = camera.projectOrtographic(p);

            int x = centerX + (int)(proj[0] * scale);
            int y = centerY - (int)(proj[1] * scale);

            g2.fillOval(x - 4, y - 4, 8, 8);
        }

        g2.setColor(new Color(230,230,230));

        for(Line3D line : scene.getLines()){

            Point3D pStart = line.pointAt(-100);
            Point3D pEnd = line.pointAt(100);

            double[] projStart = camera.projectOrtographic(pStart);
            double[] projEnd = camera.projectOrtographic(pEnd);

            g2.drawLine(
                    centerX + (int)(projStart[0] * scale),
                    centerY - (int)(projStart[1] * scale),
                    centerX + (int)(projEnd[0] * scale),
                    centerY - (int)(projEnd[1] * scale)
            );
        }

        for(Plane3D plane : scene.getPlanes()){

            drawPlaneGrid(g2, plane, centerX, centerY, scale);
        }
    }

    private void drawPlaneGrid(Graphics2D g2, Plane3D plane, int cx, int cy, double s){

        Vector3D n = plane.getNormal();

        Vector3D v1;

        if(Math.abs(n.getX()) < 0.9)
            v1 = n.cross(new Vector3D(1,0,0)).normalize();
        else
            v1 = n.cross(new Vector3D(0,1,0)).normalize();

        Vector3D v2 = n.cross(v1).normalize();

        int gridSize = 5;
        int resolution = 1;

        g2.setColor(new Color(120,140,200,60));

        for(int i = -gridSize; i <= gridSize; i += resolution){

            Point3D pStart1 = plane.getPoint().add(v1.scale(i)).add(v2.scale(-gridSize));
            Point3D pEnd1 = plane.getPoint().add(v1.scale(i)).add(v2.scale(gridSize));

            Point3D pStart2 = plane.getPoint().add(v2.scale(i)).add(v1.scale(-gridSize));
            Point3D pEnd2 = plane.getPoint().add(v2.scale(i)).add(v1.scale(gridSize));

            renderLine(g2, pStart1, pEnd1, cx, cy, s);
            renderLine(g2, pStart2, pEnd2, cx, cy, s);
        }
    }

    private void renderLine(Graphics2D g2, Point3D p1, Point3D p2, int cx, int cy, double s){

        double[] proj1 = camera.projectOrtographic(p1);
        double[] proj2 = camera.projectOrtographic(p2);

        g2.drawLine(
                cx + (int)(proj1[0]*s),
                cy - (int)(proj1[1]*s),
                cx + (int)(proj2[0]*s),
                cy - (int)(proj2[1]*s)
        );
    }

    private void drawAxis(Graphics2D g2, Point3D end, Color color, String label, int cx, int cy, double s){

        double[] p0 = camera.projectOrtographic(new Point3D(0,0,0));
        double[] p1 = camera.projectOrtographic(end);

        g2.setColor(color);

        g2.drawLine(
                cx + (int)(p0[0]*s),
                cy - (int)(p0[1]*s),
                cx + (int)(p1[0]*s),
                cy - (int)(p1[1]*s)
        );

        g2.drawString(label,
                cx + (int)(p1[0]*s),
                cy - (int)(p1[1]*s)
        );
    }
}

