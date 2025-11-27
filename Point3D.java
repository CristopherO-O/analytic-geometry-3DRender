package src;

public class Point3D  extends SpatialBase{

    // >>>>> Construtor <<<<<
    public Point3D(double x, double y, double z) {
        super(x,y,z);
    }

    // >>>>> distancia entre dois pontos <<<<<
    public double distanceTo(Point3D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    //  >>>>> converte ponto para vetor <<<<<
    public Vector3D toVector() {
        return new Vector3D(x, y, z);
    }
    
    
    // >>>>> Comparação de igualdade entre pontos <<<<<
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point3D other = (Point3D) obj;

        return Math.abs(this.x - other.x) < EPSILON &&
        Math.abs(this.y - other.y) < EPSILON &&
        Math.abs(this.z - other.z) < EPSILON;
    }

    // >>>>> Hashcode (Implementação Corrigida para EPSILON) <<<<<
    @Override
    public int hashCode() {
        // Usa Math.floor() para agrupar valores dentro do intervalo EPSILON (bucketing), 
        // garantindo que pontos 'iguais' pelo EPSILON tenham o mesmo hash.
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


    // >>>>> converte o ponto para uma string <<<<<
    @Override
    public String toString(){
        return "Point(" + x + ", " + y + ", " + z + ")";
    }
}
