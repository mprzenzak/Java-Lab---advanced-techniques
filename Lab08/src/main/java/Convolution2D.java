public class Convolution2D {
    public static native float[][] nativeConvolution(float[][] matrix, float[][] kernel);

    public static float[][] javaConvolution(float[][] matrix, float[][] kernel) {
        int matrixSize = matrix.length;
        int kernelSize = kernel.length;
        int kernelRadius = kernelSize / 2;
        float[][] result = new float[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++){
                float sum = 0;
                for (int k = 0; k < kernelSize; k++) {
                    for (int l = 0; l < kernelSize; l++) {
                        int x = i + k - kernelRadius;
                        int y = j + l - kernelRadius;
                        if (x >= 0 && x < matrixSize && y >= 0 && y < matrixSize) {
                            sum += matrix[x][y] * kernel[k][l];
                        }
                    }
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    static {
        System.loadLibrary("Convolution2D");
    }
}
