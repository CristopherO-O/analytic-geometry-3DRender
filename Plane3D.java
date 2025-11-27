package src;

public class Plane3D {
    
    // ----- pos -----
    private final Point3D point;
    private final Vector3D normal;
    private final double d;

    // ==========================================================
    // >>>>> Construtor 1: Ponto + Vetor Normal
    // ==========================================================
    public Plane3D(Point3D point, Vector3D normal) {
        if (normal.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("O vetor normal não pode ser nulo.");
        }
        this.point = point;
        this.normal = normal.normalize();
        
        // Calcula a constante d = - (n . P0)
        this.d = -this.normal.dot(this.point.toVector());
    }

    // ==========================================================
    // >>>>> Construtor 2: Três Pontos Distintos
    // ==========================================================
    public Plane3D(Point3D p1, Point3D p2, Point3D p3) {
        //Cria dois vetores no plano (v1 e v2)
        Vector3D v1 = p2.subtract(p1); // P2 - P1
        Vector3D v2 = p3.subtract(p1); // P3 - P1
        
        //Calcula o vetor normal
        Vector3D normalVector = v1.cross(v2);

        if (normalVector.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Os três pontos são colineares e não definem um plano.");
        }
        
        //Normaliza e define o ponto de passagem
        this.normal = normalVector.normalize();
        this.point = p1;
        
        //Calcula a constante d
        this.d = -this.normal.dot(this.point.toVector());
    }

    // ==========================================================
    // >>>>> Calcula a menor distância assinada (signed distance)
    // ==========================================================
    public double distanceTo(Point3D P) {
        // A equação do plano é n.P + d = 0. 
        // A distância assinada é o resultado desta expressão para o ponto P.
        return this.normal.dot(P.toVector()) + this.d;
    }

    // ==========================================================
    // >>>>> Verifica se a reta Contem um ponto
    // ==========================================================
    public boolean contains(Point3D P) {
        // Se a distância do ponto ao plano for próxima de zero, ele pertence ao plano.
        return Math.abs(this.distanceTo(P)) < SpatialBase.getEpsilon();
    }

    // ==========================================================
    // >>>>> Paralelismo
    // ==========================================================
    // >>>>> Verifica Paralelismo com Outro Plano <<<<<
    public boolean isParallel(Plane3D other) {
        // Planos são paralelos se seus vetores normais são paralelos (produto vetorial nulo).
        Vector3D crossProduct = this.normal.cross(other.normal);
        return crossProduct.magnitude() < SpatialBase.getEpsilon();
    }

    // >>>>> Verifica Paralelismo com uma Reta <<<<<
    public boolean isParallel(Line3D line) {
        // Um plano é paralelo a uma reta se o vetor normal (n) é ortogonal ao vetor diretor da reta (v).
        double dotProduct = this.normal.dot(line.getDirection());
        return Math.abs(dotProduct) < SpatialBase.getEpsilon();
    }

    // ==========================================================
    // >>>>> projeta ponto no plano
    // ==========================================================
    public Point3D project(Point3D P) {
        double signedDistance = this.distanceTo(P);
        Vector3D correctionVector = this.normal.scale(-signedDistance);
        return P.add(correctionVector);
    }

    // ==========================================================
    // >>>>> encontra ponto de interseção com uma reta
    // ==========================================================
    public Point3D intersectionWith(Line3D line) {
        Vector3D v = line.getDirection();
        double dot_vn = v.dot(this.normal);
        
        if (Math.abs(dot_vn) < SpatialBase.getEpsilon()) {
            // Se a reta é paralela e passa pelo ponto do plano, ela está contida no plano.
            if (this.contains(line.getPoint())) {
                return line.getPoint(); 
            }
            // Reta paralela e fora do plano (sem interseção)
            return null; 
        }
        
        Vector3D P0_minus_P = this.point.subtract(line.getPoint()); 
        double t = P0_minus_P.dot(this.normal) / dot_vn;
        return line.pointAt(t);
    }

    // ==========================================================
    // >>>>> Getters e toString
    // ==========================================================
    
    public Point3D getPoint() { return point; }
    public Vector3D getNormal() { return normal; }
    public double getD() { return d; }

    @Override
    public String toString() {
        return String.format("Plane3D{n=(%.2f, %.2f, %.2f), d=%.2f, P0=%s}",
                             normal.getX(), normal.getY(), normal.getZ(), d, point.toString());
    }

}
