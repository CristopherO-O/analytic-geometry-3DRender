package src.core;

/**
 * Representa um vetor no espaco 3D.
 * Fornece operacoes vetoriais como soma, produto escalar,
 * produto vetorial, normalizacao e projecoes.
 */
public class Vector3D extends SpatialBase {

    /**
     * Cria um vetor com coordenadas x, y e z.
     *
     * @param x componente no eixo x
     * @param y componente no eixo y
     * @param z componente no eixo z
     */
    public Vector3D(double x, double y, double z){
        super(x, y, z);
    }

    /**
     * Soma este vetor com outro vetor.
     *
     * @param v vetor a ser somado
     * @return novo vetor resultante da soma
     */
    public Vector3D add(Vector3D v){
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtrai um vetor deste vetor.
     *
     * @param v vetor a ser subtraido
     * @return novo vetor resultante da subtracao
     */
    public Vector3D subtract(Vector3D v){
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Calcula o produto escalar entre este vetor e outro.
     *
     * @param v vetor para o calculo
     * @return valor do produto escalar
     */
    public double dot(Vector3D v){
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Calcula o produto vetorial entre este vetor e outro.
     *
     * @param v vetor para o calculo
     * @return vetor perpendicular resultante
     */
    public Vector3D cross(Vector3D v){
        return new Vector3D(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    /**
     * Retorna o modulo (comprimento) do vetor.
     *
     * @return magnitude do vetor
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Retorna o quadrado do modulo do vetor.
     * Evita o custo da raiz quadrada.
     *
     * @return magnitude ao quadrado
     */
    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Retorna o vetor normalizado (comprimento 1).
     *
     * @return vetor normalizado ou vetor zero se magnitude for muito pequena
     */
    public Vector3D normalize() {
        double m = magnitude();
        if (m < EPSILON) return new Vector3D(0, 0, 0);
        return new Vector3D(x / m, y / m, z / m);
    }

    /**
     * Calcula a distancia entre este vetor e outro.
     *
     * @param v vetor de referencia
     * @return distancia entre os vetores
     */
    public double distanceTo(Vector3D v) {
        return this.subtract(v).magnitude();
    }

    /**
     * Multiplica o vetor por um escalar.
     *
     * @param s valor escalar
     * @return novo vetor escalado
     */
    public Vector3D scale(double s) {
        return new Vector3D(x * s, y * s, z * s);
    }

    /**
     * Calcula o angulo entre este vetor e outro em radianos.
     *
     * @param v vetor de referencia
     * @return angulo entre os vetores
     */
    public double angleTo(Vector3D v){
        double dot = this.dot(v);
        double mag = this.magnitude() * v.magnitude();

        if (mag < EPSILON) return 0;

        double cos = dot / mag;

        // evita erro numerico
        cos = Math.max(-1.0, Math.min(1.0, cos));

        return Math.acos(cos);
    }

    /**
     * Projeta este vetor sobre outro vetor.
     *
     * @param v vetor base da projecao
     * @return vetor projetado
     */
    public Vector3D projectOnto(Vector3D v){
        double magSquared = v.magnitudeSquared();

        if (magSquared < EPSILON) return new Vector3D(0, 0, 0);

        double scale = this.dot(v) / magSquared;
        return v.scale(scale);
    }

    /**
     * Converte este vetor em um ponto com as mesmas coordenadas.
     *
     * @return ponto equivalente ao vetor
     */
    public Point3D toPoint(){
        return new Point3D(x, y, z);
    }

    /**
     * Reflete este vetor em relacao a uma normal.
     *
     * @param n vetor normal da superficie
     * @return vetor refletido
     */
    public Vector3D reflect(Vector3D n) {
        Vector3D normalizedN = n.normalize();
        double dot = this.dot(normalizedN);
        return this.subtract(normalizedN.scale(2.0 * dot));
    }

    /**
     * Compara vetores considerando uma tolerancia (EPSILON).
     *
     * @param obj objeto a ser comparado
     * @return true se forem aproximadamente iguais
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vector3D other = (Vector3D) obj;

        return Math.abs(this.x - other.x) < EPSILON &&
               Math.abs(this.y - other.y) < EPSILON &&
               Math.abs(this.z - other.z) < EPSILON;
    }

    /**
     * Gera um codigo hash baseado nas coordenadas com tolerancia.
     *
     * @return codigo hash do vetor
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
     * Retorna uma representacao em string do vetor.
     *
     * @return string no formato Vector(x, y, z)
     */
    @Override
    public String toString(){
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }
}