import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {

    static ArrayList<Integer> VectorGenerator(int size)
    {
        Random random = new Random();
        ArrayList<Integer> vector = new ArrayList<Integer>();
        for(int i = 0; i < size; i++)
            vector.add(random.nextInt(1000) - 500);
        return vector;
    }

    static ArrayList<ArrayList<Integer>> MatrixGenerator(int sizeX, int sizeY)
    {
        Random random = new Random();
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        for(int i = 0; i < sizeX; i++){
            ArrayList<Integer> vector = new ArrayList<>();
            for(int j = 0; j < sizeY; j++){
                vector.add(random.nextInt(1000) - 500);
            }
            matrix.add(vector);
        }
        return matrix;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int threads_number = 3;

        int sizeOfVector = 1000;
        int columns = sizeOfVector;//change this for tests
        int rows = 10000;

        ArrayList<Integer> vector = VectorGenerator(sizeOfVector);
        ArrayList<ArrayList<Integer>> matrix = MatrixGenerator(rows, columns);

        long endTime;
        long startTime;

        startTime = System.currentTimeMillis();

        Parallel.mulParallel(threads_number, vector, matrix);

        endTime = System.currentTimeMillis();
        System.out.println("Total execution time with Thread: " + (endTime-startTime) + "ms");

        //*******************************************************************************************************

        startTime = System.currentTimeMillis();

        Parallel.mulParallel(threads_number, vector, matrix);

        endTime = System.currentTimeMillis();
        System.out.println("Total execution time with Thread: " + (endTime-startTime) + "ms");

        // *******************************************************************************************************

        startTime = System.currentTimeMillis();

        Parallel.mulParallelStream(threads_number, vector, matrix);

        endTime = System.currentTimeMillis();
        System.out.println("Total execution time with Thread: " + (endTime-startTime) + "ms");

    }
}
