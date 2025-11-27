package src;

public class Line3D {
    
    // --- pos ---
    private Point3D point;
    private Vector3D dir;

    // >>>>> construtor ponto + vetor <<<<<
    public Line3D(Point3D point, Vector3D dir){
        if (dir.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("O vetor diretor não pode ser nulo para definir uma reta.");
        }
        this.point = point;
        this.dir = dir.normalize();
    }

    // >>>>> construtor dois pontos distintos <<<<<
    public Line3D(Point3D p1, Point3D p2){
        Vector3D v = p1.toVector().subtract(p2.toVector());
        if (dir.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("O vetor diretor não pode ser nulo para definir uma reta.");
        }
        
        this.point = p1;
        this.dir = v.normalize();

    }

    // --- getters ---
    public Point3D getPoint() { return point; }
    public Vector3D getDirection() { return dir; }

    
    @Override
    public String toString() {
        return "Line3D{P0=" + point + ", v=" + dir + "}";
    }

}
