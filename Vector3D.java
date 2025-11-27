package src;

public class Vector3D extends SpatialBase {

    
    // ==========================================================
    // >>>>> Construtor
    // ==========================================================

    public Vector3D(double x, double y, double z){
        super(x,y,z);
    }

    // ==========================================================
    // >>>>> Soma de vetores
    // ==========================================================
    public Vector3D add(Vector3D v){
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    // ==========================================================
    // >>>>> Subtracao de vetores
    // ==========================================================
    public Vector3D subtract(Vector3D v){
        return new Vector3D( x - v.x , y - v.y, z - v.z);
    }

    // ==========================================================
    // >>>>> Produto Escalar (dot)
    // ==========================================================
    public double dot(Vector3D v){
        return x * v.x + y * v.y + z *v.z;
    }

    // ==========================================================
    // >>>>> Produto Vetorial (cross)
    // ==========================================================
    public Vector3D cross(Vector3D v){
        return new Vector3D(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    // ==========================================================
    // >>>>> Norma
    // ==========================================================
    public double magnitude() {
        return Math.hypot(x, Math.hypot(y, z));
    }

    // ==========================================================
    // >>>>> Norma ao quadrado (para fugir de calculo de raiz)
    // ==========================================================
    public double magnitudeSquared() {
        return x*x + y*y + z*z;
    }

    // ==========================================================
    // >>>>> Normaliza Vetor
    // ==========================================================
    public Vector3D normalize() {
        double m = magnitude();
        if (m < EPSILON) return new Vector3D(0, 0, 0); // eivtar divisão por 0
        return new Vector3D(x / m, y / m, z / m);
    }

    // ==========================================================
    // >>>>> Distancia entre vetores
    // ==========================================================
    public double distanceTo(Vector3D v) {
        return this.subtract(v).magnitude();
    }

    // ==========================================================
    // >>>>> Multiplica por escalar
    // ==========================================================
    public Vector3D scale(double s) {
        return new Vector3D(x * s, y * s, z * s);
    }

    // ==========================================================
    // >>>>> Angulo Entre vetores
    // ==========================================================
    public double angleTo(Vector3D v){
        double dot = this.dot(v); // u.v
        double mag = this.magnitude() * v.magnitude(); // ∣∣u∣∣⋅∣∣v∣∣
        if(mag < EPSILON) return 0;
        double cos = dot/mag; // (u.v) / (∣∣u∣∣⋅∣∣v∣∣)

        // evitar erros de arredondamento de ponto flutuante
        cos = Math.max(-1.0, Math.min(1.0, cos));

        return Math.acos(cos);

    }

    // ==========================================================
    // >>>>> Projeta um vetor em outro
    // ==========================================================
    public Vector3D projectOnto(Vector3D v){
        double magSquared = v.magnitudeSquared();

        if (magSquared < EPSILON) return new Vector3D(0, 0, 0);

        double scale =  this.dot(v) / magSquared;
        return v.scale(scale);

    }

    // ==========================================================
    // >>>>> Retorna o vetor como um ponto
    // ==========================================================
    public Point3D toPoint(){
        return new Point3D(x, y, z);
    }


    // ==========================================================
    // >>>>> Reflexão Vetorial (Reflete este vetor na normal 'n')
    // ==========================================================
    public Vector3D reflect(Vector3D n) {
        Vector3D nomalized_n = n.normalize(); // vetor n necessita ser unitario (normalizado)
        double dot_un = this.dot(nomalized_n); // u . n
        Vector3D scaled_n = nomalized_n.scale(2.0 * dot_un); // 2 * (u . n) * n
        return this.subtract(scaled_n); // u - 2 * (u . n) * n
    }

    // ==========================================================
    // >>>>> Igualdade entre os Vetores
    // ==========================================================
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

    // ==========================================================
    // >>>>> HashCode
    // ==========================================================
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


    // ==========================================================
    // >>>>> Retorna vetor como uma string
    // ==========================================================
    @Override
    public String toString(){
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }
}
