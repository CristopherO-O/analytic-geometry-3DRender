package src.entities;

import src.core.Point3D;
import src.core.SpatialBase;
import src.core.Vector3D;

/**
 * Representa um plano no espaco 3D.
 * 
 * O plano e definido por um ponto e um vetor normal,
 * seguindo a equacao: n . P + d = 0
 */
public class Plane3D {

    private final Point3D point;
    private final Vector3D normal;
    private final double d;

    /**
     * Cria um plano a partir de um ponto e um vetor normal.
     */
    public Plane3D(Point3D point, Vector3D normal) {
        if (normal.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Vetor normal nao pode ser nulo.");
        }

        this.point = point;
        this.normal = normal.normalize();
        this.d = -this.normal.dot(point.toVector());
    }

    /**
     * Cria um plano a partir de tres pontos nao colineares.
     */
    public Plane3D(Point3D p1, Point3D p2, Point3D p3) {
        Vector3D v1 = p2.subtract(p1);
        Vector3D v2 = p3.subtract(p1);

        Vector3D normalVector = v1.cross(v2);

        if (normalVector.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Pontos colineares nao definem plano.");
        }

        this.normal = normalVector.normalize();
        this.point = p1;
        this.d = -this.normal.dot(point.toVector());
    }

    /**
     * Retorna a distancia assinada de um ponto ao plano.
     */
    public double distanceTo(Point3D p) {
        return this.normal.dot(p.toVector()) + this.d;
    }

    /**
     * Verifica se um ponto pertence ao plano.
     */
    public boolean contains(Point3D p) {
        return Math.abs(distanceTo(p)) < SpatialBase.getEpsilon();
    }

    /**
     * Verifica se dois planos sao paralelos.
     */
    public boolean isParallel(Plane3D other) {
        return this.normal.cross(other.normal).magnitude() < SpatialBase.getEpsilon();
    }

    /**
     * Verifica se uma reta e paralela ao plano.
     */
    public boolean isParallel(Line3D line) {
        return Math.abs(this.normal.dot(line.getDirection())) < SpatialBase.getEpsilon();
    }

    /**
     * Projeta um ponto no plano.
     */
    public Point3D project(Point3D p) {
        double dist = distanceTo(p);
        return p.add(this.normal.scale(-dist));
    }

    /**
     * Calcula a intersecao entre o plano e uma reta.
     * Retorna null se nao houver intersecao.
     */
    public Point3D intersectionWith(Line3D line) {
        Vector3D v = line.getDirection();
        double denom = v.dot(this.normal);

        if (Math.abs(denom) < SpatialBase.getEpsilon()) {
            // reta paralela ao plano
            if (this.contains(line.getPoint())) {
                return line.getPoint(); // reta contida no plano
            }
            return null;
        }

        Vector3D diff = this.point.subtract(line.getPoint());
        double t = diff.dot(this.normal) / denom;

        return line.pointAt(t);
    }

    // GETTERS
    public Point3D getPoint() { return point; }
    public Vector3D getNormal() { return normal; }
    public double getD() { return d; }

    /**
     * Retorna representacao textual do plano.
     */
    @Override
    public String toString() {
        return String.format(
            "Plane3D{n=(%.2f, %.2f, %.2f), d=%.2f, P0=%s}",
            normal.getX(), normal.getY(), normal.getZ(), d, point
        );
    }
}