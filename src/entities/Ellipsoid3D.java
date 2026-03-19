package src.entities;

import src.core.Point3D;

public class Ellipsoid3D {
    private Point3D center;
    private double radiusX;
    private double radiusY;
    private double radiusZ;

    public Ellipsoid3D(Point3D center, double radiusX, double radiusY, double radiusZ) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }


    
    public Point3D getCenter() { return center; }
    public double getRadiusX() { return radiusX; }
    public double getRadiusY() { return radiusY; }
    public double getRadiusZ() { return radiusZ; }
}
