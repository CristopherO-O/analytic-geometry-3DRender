package src.core;

/**
 * Representa um ponto no espaco 3D.
 * 
 * Diferente de um vetor, um ponto indica uma posicao fixa no espaco.
 * Pode ser utilizado em operacoes geometricas como distancia,
 * deslocamento e conversao para vetor.
 */
public class Point3D  extends SpatialBase{

    
    /**
     * Construtor do ponto 3D.
     *
     * @param x coordenada no eixo x
     * @param y coordenada no eixo y
     * @param z coordenada no eixo z
     */
    public Point3D(double x, double y, double z) {
        super(x,y,z);
    }
    
    /**
     * Calcula a distancia euclidiana entre este ponto e outro ponto 3D.
     *
     * @param other ponto de referencia para o calculo
     * @return distancia entre os dois pontos
     */
    public double distanceTo(Point3D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    /**
     * Converte este ponto em um vetor com as mesmas coordenadas.
     *
     * @return vetor com os mesmos valores de x, y e z
     */
    public Vector3D toVector() {
        return new Vector3D(x, y, z);
    }

    /**
     * Retorna um novo ponto resultante da soma deste ponto com um vetor.
     *
     * @param v vetor a ser somado
     * @return novo ponto deslocado pelas coordenadas do vetor
     */
    public Point3D add(Vector3D v) {
        return new Point3D(this.x + v.getX(), this.y + v.getY(), this.z + v.getZ());
    }

    /**
     * Calcula o vetor resultante da subtracao entre este ponto e outro ponto.
     *
     * @param other ponto a ser subtraido
     * @return vetor que vai de other ate este ponto
     */
    public Vector3D subtract(Point3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Compara este ponto com outro considerando uma tolerancia (EPSILON).
     *
     * @param obj objeto a ser comparado
     * @return true se as coordenadas forem aproximadamente iguais
     */
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
    
    /**
     * Gera um hashcode baseado nas coordenadas do ponto,
     * considerando uma tolerancia (EPSILON).
     *
     * @return hashcode do ponto
     */
    @Override 
    public int hashCode() {
        
        
        long qx = (long) Math.floor(x / EPSILON);
        long qy = (long) Math.floor(y / EPSILON);
        long qz = (long) Math.floor(z / EPSILON);

        
        int result = 17;
        result = 31 * result + (int) (qx ^ (qx >>> 32));
        result = 31 * result + (int) (qy ^ (qy >>> 32));
        result = 31 * result + (int) (qz ^ (qz >>> 32));
        return result;
    }

    /**
     * Retorna uma representacao em string do ponto no formato:
     * Point(x, y, z)
     *
     * @return string com as coordenadas do ponto
     */
    @Override
    public String toString(){
        return "Point(" + x + ", " + y + ", " + z + ")";
    }
}
