package src;

public abstract class SpatialBase {

    // tolerancia para erros de ponto flutuante
    protected static final double EPSILON = 1e-9;

    // --- Pos ---
    protected double x, y, z;

    // >>>>> Construtor <<<<<
    public SpatialBase(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // --- Getters ---
    public static double getEpsilon() { return EPSILON; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    // --- Setters ---
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }

    @Override
    public abstract String toString();
}