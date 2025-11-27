package src;

public class Vector3D extends SpatialBase {

    
    // >>>>> Construtor <<<<<
    public Vector3D(double x, double y, double z){
        super(x,y,z);
    }

    // >>>>> Soma Vetor <<<<<
    public Vector3D add(Vector3D v){
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    // >>>>> Subtrai Vetor <<<<<
    public Vector3D subtract(Vector3D v){
        return new Vector3D( x - v.x , y - v.y, z - v.z);
    }

    // >>>>> Produto Escalar (dot) <<<<<
    public double dot(Vector3D v){
        return x * v.x + y * v.y + z *v.z;
    }

    // >>>>> Produto Vetorial (cross) <<<<<
    public Vector3D cross(Vector3D v){
        return new Vector3D(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    // >>>>> Norma <<<<<
    public double magnitude() {
        return Math.hypot(x, Math.hypot(y, z));
    }


    // >>>>> Normalizar <<<<<
    public Vector3D normalize() {
        double m = magnitude();
        if (m < EPSILON) return new Vector3D(0, 0, 0); // eivtar divisão por 0
        return new Vector3D(x / m, y / m, z / m);
    }


    // >>>>> Multiplicação por escalar <<<<<
    public Vector3D scale(double s) {
        return new Vector3D(x * s, y * s, z * s);
    }

    // >>>>> Angulo entre vetores (em radianos) <<<<<
    public double angleTo(Vector3D v){
        double dot = this.dot(v); // u.v
        double mag = this.magnitude() * v.magnitude(); // ∣∣u∣∣⋅∣∣v∣∣
        if(mag < EPSILON) return 0;
        double cos = dot/mag; // (u.v) / (∣∣u∣∣⋅∣∣v∣∣)

        // evitar erros de arredondamento de ponto flutuante
        cos = Math.max(-1.0, Math.min(1.0, cos));

        return Math.acos(cos);

    }

    // >>>>> Projecao Vetorial <<<<<
    public Vector3D projectOnto(Vector3D v){
        double magSquared = v.x*v.x + v.y*v.y + v.z*v.z;

        if (magSquared < EPSILON) return new Vector3D(0, 0, 0);

        double scale =  this.dot(v) / magSquared;
        return v.scale(scale);

    }

    // >>>>> Comparação de igualdade entre vetores <<<<<
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Vector3D other = (Vector3D) obj;
 
        return Math.abs(this.x - other.x) < EPSILON &&
            Math.abs(this.y - other.y) < EPSILON &&
            Math.abs(this.z - other.z) < EPSILON;
    }

    // >>>>> Hashcode (Implementação Corrigida para EPSILON) <<<<<
    @Override
    public int hashCode() {
        // Usa Math.floor() para agrupar valores dentro do intervalo EPSILON (bucketing), 
        // garantindo que vetores 'iguais' pelo EPSILON tenham o mesmo hash.
        long qx = (long) Math.floor(x / EPSILON);
        long qy = (long) Math.floor(y / EPSILON);
        long qz = (long) Math.floor(z / EPSILON);

        // Combina os longos de forma padrão (similar ao Objects.hash)
        int result = 17;
        result = 31 * result + (int) (qx ^ (qx >>> 32));
        result = 31 * result + (int) (qy ^ (qy >>> 32));
        result = 31 * result + (int) (qz ^ (qz >>> 32));
        return result;
    }


    // >>>>> retorna o vetor como uma String <<<<<
    @Override
    public String toString(){
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }
}
