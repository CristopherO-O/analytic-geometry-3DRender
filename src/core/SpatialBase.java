package src.core;

/**
 * Classe base para objetos espaciais em 3D.
 * Fornece coordenadas imutaveis (x, y, z) e uma tolerancia padrao (EPSILON)
 * para comparacoes numericas.
 */
public abstract class SpatialBase {

    
   protected static final double EPSILON = 1e-9;
   
   protected final double x, y, z;
   
   /**
    * Classe base para representacao de entidades espaciais em 3D.
    * Armazena coordenadas imutaveis x, y e z.
    *
    * @param x coordenada no eixo x
    * @param y coordenada no eixo y
    * @param z coordenada no eixo z
    */
   public SpatialBase(double x, double y, double z) {
       this.x = x;
       this.y = y;
       this.z = z;
   }
   
   // GETTERS
   public static double getEpsilon() { return EPSILON; }
   public double getX() { return x; }
   public double getY() { return y; }
   public double getZ() { return z; }


   @Override
   public abstract String toString();
}
