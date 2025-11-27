package src;

public class Line3D {
    
    // ----- pos -----
    private final Point3D point;
    private final Vector3D dir;

    // ==========================================================
    // >>>>> Construtor (Ponto + Vetor)
    // ==========================================================
    public Line3D(Point3D point, Vector3D dir){
        if (dir.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("O vetor diretor não pode ser nulo para definir uma reta.");
        }
        this.point = point;
        this.dir = dir.normalize();
    }

    // ==========================================================
    // >>>>> Construtor (dois pontos distintos)
    // ==========================================================
    public Line3D(Point3D p1, Point3D p2){
        Vector3D v = p1.toVector().subtract(p2.toVector()); 
        
        if (v.magnitude() < SpatialBase.getEpsilon()) {
            throw new IllegalArgumentException("Os dois pontos devem ser distintos para definir uma reta.");
        }
        
        this.point = p1;
        this.dir = v.normalize(); // Normaliza o vetor diretor
    }

    // ==========================================================
    // >>>>> Ponto parametrico da Reta
    // ==========================================================
    public Point3D pointAt(double t){
        Vector3D tv = this.dir.scale(t);  
        return this.point.add(tv);
    }

    
    // ==========================================================
    // >>>>> Verifica se o ponto está sobre a reta
    // ==========================================================
    public boolean contains(Point3D P) {
        return this.distanceTo(P) < SpatialBase.getEpsilon();
    }
    
    // ==========================================================
    // >>>>> Angulo entre retas
    // ==========================================================
    public double angleTo(Line3D other) {
        return this.dir.angleTo(other.dir);
    }

    // ==========================================================
    // >>>>> Verifica paralelismo
    // ==========================================================
    public boolean isParallel(Line3D other) {
        // O produto vetorial de vetores paralelos resulta em um vetor nulo.
        Vector3D crossProduct = this.dir.cross(other.dir);
        return crossProduct.magnitude() < SpatialBase.getEpsilon();
    }

    // ==========================================================
    // >>>>> Verifica Ortogonalidade
    // ==========================================================
    public boolean isOrthogonal(Line3D other) {
        // O produto escalar de vetores perpendiculares é zero.
        double dotProduct = this.dir.dot(other.dir);
        return Math.abs(dotProduct) < SpatialBase.getEpsilon();
    }

    // ==========================================================
    // >>>>> Ditancias entre a reta e um ponto
    // ==========================================================
    public double distanceTo(Point3D p){
        Vector3D P_minus_P0 = p.subtract(this.point);
        Vector3D crossp =  P_minus_P0.cross(this.dir);
        return crossp.magnitude();
    }

    // ==========================================================
    // >>>>> Distancia entre duas retas
    // ==========================================================
    public double distanceTo(Line3D other) {
        Vector3D P1P2 = other.point.subtract(this.point);
        
        if (this.isParallel(other)) {
            return other.distanceTo(this.point); // caso paralelo
        } else {
            // caso reversas
            Vector3D normal = this.dir.cross(other.dir);
            double normalMagnitude = normal.magnitude();
            
            // Se a magnitude for nula, as retas são paralelas ou incidentes (caso já coberto). 
            if (normalMagnitude < SpatialBase.getEpsilon()) {
                // Se o produto vetorial é quase zero, as retas são paralelas ou se cruzam
                // Se cruzarem, a distância é 0. Se forem paralelas, a distância já foi calculada.
                // Aqui, podemos retornar a distância do ponto para a reta, pois elas não são puramente reversas.
                return other.distanceTo(this.point);
            }

            // 5. Produto Escalar Triplo: |(P1P2 . n)|
            double numerator = Math.abs(P1P2.dot(normal));
            
            return numerator / normalMagnitude;
        }
    }

    // ==========================================================
    // >>>>> Getter
    // ==========================================================
    public Point3D getPoint() { return point; }
    public Vector3D getDirection() { return dir; }


    // ==========================================================
    // >>>>> retorna a reta como Sting
    // ==========================================================
    @Override
    public String toString() {
        return "Line3D{P0=" + point + ", v=" + dir + "}";
    }


}
