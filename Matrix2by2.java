/**
 * Matrix2by2 class represents a 2x2 transformation matrix.
 * Used for image transformations like rotation.
 */
public class Matrix2by2 {
    private double[][] matrix;
    
    /**
     * Constructor that creates a matrix with given values
     * @param a00 element at row 0, column 0
     * @param a01 element at row 0, column 1
     * @param a10 element at row 1, column 0
     * @param a11 element at row 1, column 1
     */
    public Matrix2by2(double a00, double a01, double a10, double a11) {
        matrix = new double[2][2];
        matrix[0][0] = a00;
        matrix[0][1] = a01;
        matrix[1][0] = a10;
        matrix[1][1] = a11;
    }
    
    /**
     * Default constructor creates an identity matrix
     */
    public Matrix2by2() {
        this(1, 0, 0, 1); // Identity matrix
    }
    
    /**
     * Copy constructor creates a copy of another matrix
     * @param other the matrix to copy
     */
    public Matrix2by2(Matrix2by2 other) {
        this(other.matrix[0][0], other.matrix[0][1],
             other.matrix[1][0], other.matrix[1][1]);
    }
    
    // Getters for individual elements
    public double getA00() { return matrix[0][0]; }
    public double getA01() { return matrix[0][1]; }
    public double getA10() { return matrix[1][0]; }
    public double getA11() { return matrix[1][1]; }
    
    // Setters for individual elements
    public void setA00(double value) { matrix[0][0] = value; }
    public void setA01(double value) { matrix[0][1] = value; }
    public void setA10(double value) { matrix[1][0] = value; }
    public void setA11(double value) { matrix[1][1] = value; }
    
    /**
     * Sets all elements of the matrix at once
     * @param a00 element at row 0, column 0
     * @param a01 element at row 0, column 1
     * @param a10 element at row 1, column 0
     * @param a11 element at row 1, column 1
     */
    public void setMatrix(double a00, double a01, double a10, double a11) {
        matrix[0][0] = a00;
        matrix[0][1] = a01;
        matrix[1][0] = a10;
        matrix[1][1] = a11;
    }
    
    /**
     * Sets this matrix to be a rotation matrix for given angle (in degrees)
     * @param angleDegrees the rotation angle in degrees
     */
    public void setRotationMatrix(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        
        matrix[0][0] = cos;
        matrix[0][1] = -sin;
        matrix[1][0] = sin;
        matrix[1][1] = cos;
    }
    
    /**
     * Multiplies this matrix by a scalar
     * @param scalar the scalar to multiply by
     * @return a new scaled matrix
     */
    public Matrix2by2 multiply(double scalar) {
        return new Matrix2by2(
            matrix[0][0] * scalar, matrix[0][1] * scalar,
            matrix[1][0] * scalar, matrix[1][1] * scalar
        );
    }
    
    /**
     * Multiplies this matrix by another matrix
     * @param other the other matrix
     * @return a new matrix that is the product
     */
    public Matrix2by2 multiply(Matrix2by2 other) {
        return new Matrix2by2(
            matrix[0][0] * other.matrix[0][0] + matrix[0][1] * other.matrix[1][0],
            matrix[0][0] * other.matrix[0][1] + matrix[0][1] * other.matrix[1][1],
            matrix[1][0] * other.matrix[0][0] + matrix[1][1] * other.matrix[1][0],
            matrix[1][0] * other.matrix[0][1] + matrix[1][1] * other.matrix[1][1]
        );
    }
    
    /**
     * Multiplies this matrix by a vector
     * @param vector the vector to multiply
     * @return a new transformed vector
     */
    public Vector1by2 multiply(Vector1by2 vector) {
        return new Vector1by2(
            matrix[0][0] * vector.getRow() + matrix[0][1] * vector.getCol(),
            matrix[1][0] * vector.getRow() + matrix[1][1] * vector.getCol()
        );
    }
    
    /**
     * Static method to multiply a vector by a matrix
     * @param vector the vector
     * @param matrix the matrix
     * @return the transformed vector
     */
    public static Vector1by2 multiply(Vector1by2 vector, Matrix2by2 matrix) {
        return matrix.multiply(vector);
    }
    
    /**
     * Returns a string representation of the matrix
     * @return string in matrix format
     */
    @Override
    public String toString() {
        return "[[" + matrix[0][0] + ", " + matrix[0][1] + "],\n [" + 
               matrix[1][0] + ", " + matrix[1][1] + "]]";
    }
    
    /**
     * Checks if this matrix equals another object
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matrix2by2 other = (Matrix2by2) obj;
        return Math.abs(matrix[0][0] - other.matrix[0][0]) < 0.0001 &&
               Math.abs(matrix[0][1] - other.matrix[0][1]) < 0.0001 &&
               Math.abs(matrix[1][0] - other.matrix[1][0]) < 0.0001 &&
               Math.abs(matrix[1][1] - other.matrix[1][1]) < 0.0001;
    }
}
