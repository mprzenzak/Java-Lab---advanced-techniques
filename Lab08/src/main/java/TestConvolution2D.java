import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestConvolution2D {
    public static void main(String[] args) {
        int size = 500;
        float[][] matrix = new float[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextFloat();
            }
        }

        float[][] kernel = {
                {1, 0},
                {0, 1}
        };

        // Measure Java convolution time
        long startJava = System.nanoTime();
        float[][] resultJava = Convolution2D.javaConvolution(matrix, kernel);
        long endJava = System.nanoTime();
        long durationJava = TimeUnit.NANOSECONDS.toMillis(endJava - startJava);

        // Measure native convolution time
        long startNative = System.nanoTime();
        float[][] resultNative = Convolution2D.nativeConvolution(matrix, kernel);
        long endNative = System.nanoTime();
        long durationNative = TimeUnit.NANOSECONDS.toMillis(endNative - startNative);

        System.out.println("Java Convolution:");
        printMatrix(resultJava);

        System.out.println("\nNative Convolution:");
        printMatrix(resultNative);

        System.out.println("Java Convolution time: " + durationJava + " ms");
        System.out.println("Native Convolution time: " + durationNative + " ms");
    }

    private static void printMatrix(float[][] matrix) {
        for (float[] row : matrix) {
            for (float element : row) {
                System.out.printf("%.1f ", element);
            }
            System.out.println();
        }
    }
}

