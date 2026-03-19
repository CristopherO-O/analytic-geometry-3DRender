package src.entities;

import src.core.Point3D;
import src.core.SpatialBase;
import src.core.Vector3D;

/**
 * Representa uma reta no espaco 3D definida por um ponto e um vetor diretor.
 * A reta e parametrizada como: P(t) = P0 + t * d
 */
public class Line3D {

    private final Point3D point;
    private final Vector3D dir;

    /**
     * Cria uma reta a partir de um ponto e um vetor diretor.
     *
     * @param point ponto pertencente a reta
     * @param dir vetor diretor (nao pode ser nulo)
     */
    public Line3D(Point3D point, Vector3D dir){
        if (dir.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Vetor diretor nao pode ser nulo.");
        }
        this.point = point;
        this.dir = dir.normalize();
    }

    /**
     * Cria uma reta a partir de dois pontos distintos.
     *
     * @param p1 primeiro ponto
     * @param p2 segundo ponto
     */
    public Line3D(Point3D p1, Point3D p2){
        Vector3D v = p2.subtract(p1); // mais intuitivo: p1 -> p2
        
        if (v.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Os pontos devem ser distintos.");
        }

        this.point = p1;
        this.dir = v.normalize();
    }

    /**
     * Retorna um ponto da reta para um parametro t.
     *
     * @param t parametro da reta
     * @return ponto correspondente a P(t)
     */
    public Point3D pointAt(double t){
        return this.point.add(this.dir.scale(t));
    }

    /**
     * Verifica se um ponto pertence a reta (com tolerancia).
     */
    public boolean contains(Point3D p) {
        return this.distanceTo(p) < SpatialBase.getEpsilon();
    }

    /**
     * Calcula o angulo entre esta reta e outra.
     */
    public double angleTo(Line3D other) {
        return this.dir.angleTo(other.dir);
    }

    /**
     * Verifica se duas retas sao paralelas.
     */
    public boolean isParallel(Line3D other) {
        return this.dir.cross(other.dir).magnitude() < SpatialBase.getEpsilon();
    }

    /**
     * Verifica se duas retas sao ortogonais.
     */
    public boolean isOrthogonal(Line3D other) {
        return Math.abs(this.dir.dot(other.dir)) < SpatialBase.getEpsilon();
    }

    /**
     * Calcula a distancia de um ponto ate a reta.
     */
    public double distanceTo(Point3D p){
        Vector3D diff = p.subtract(this.point);
        return diff.cross(this.dir).magnitude();
    }

    /**
     * Calcula a distancia entre duas retas.
     * Funciona tanto para retas paralelas quanto reversas.
     */
    public double distanceTo(Line3D other) {
        if (this.isParallel(other)) {
            return other.distanceTo(this.point);
        }

        Vector3D p1p2 = other.point.subtract(this.point);
        Vector3D normal = this.dir.cross(other.dir);

        double normalMagnitude = normal.magnitude();

        if (normalMagnitude < SpatialBase.getEpsilon()) {
            return other.distanceTo(this.point);
        }

        return Math.abs(p1p2.dot(normal)) / normalMagnitude;
    }

    // GETTERS
    public Point3D getPoint() { return point; }
    public Vector3D getDirection() { return dir; }

    /**
     * Retorna representacao textual da reta.
     */
    @Override
    public String toString() {
        return "Line3D{P0=" + point + ", d=" + dir + "}";
    }
}