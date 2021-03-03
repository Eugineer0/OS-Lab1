import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Parallel {
    private static List<Integer> vector;

    private static int mulLines(List<Integer> line)
    {
        int result = 0;

        for(int j = 0; j < vector.size(); j++)
        {
            result += line.get(j) * vector.get(j);
        }

        return result;
    }

    private static class MulThread extends Thread {
        private ArrayList<Integer> part_matrix;
        private List<Integer> matrix_line;

        MulThread(List<Integer> matrix_line) {
           this.matrix_line = matrix_line;
            part_matrix = new ArrayList<>();
        }

        @Override
        public void run() {
            for(int i = 0; i < matrix_line.size() / vector.size(); i++) {
                part_matrix.add(mulLines(matrix_line.subList(i * vector.size(), (i + 1) * vector.size())));
            }
        }

        public ArrayList<Integer> getProduct() {
            return part_matrix;
        }
    }

    static ArrayList<Integer> mulParallel(int threads_number, ArrayList<Integer> vec, ArrayList<ArrayList<Integer>> matrix) throws InterruptedException {
        vector = vec;

        ArrayList<Integer> linear_matrix = new ArrayList<>();
        for(ArrayList<Integer> matrix_line: matrix)
        {
            linear_matrix.addAll(matrix_line);
        }

        int columns = vec.size();
        int rows = matrix.size();

        if(rows < threads_number)
        {
            threads_number = rows;
        }

        int space = rows / threads_number * columns;
        ArrayList<MulThread> threads = new ArrayList<>();

        for(int i = 0; i < threads_number; i++)
        {
            if(i == threads_number - 1)
            {
                threads.add(new MulThread(linear_matrix.subList(i * space, linear_matrix.size())));
            }
            else
            {
                threads.add(new MulThread(linear_matrix.subList(i * space, (i + 1) * space)));
            }

            threads.get(i).start();
        }

        ArrayList<Integer> result_matrix = new ArrayList<>();
        for(MulThread thread: threads)
        {
            thread.join();

            result_matrix.addAll(thread.getProduct());
        }

        return result_matrix;
    }

    static ArrayList<Integer> mulParallelStream(int threads_number, ArrayList<Integer> vec, ArrayList<ArrayList<Integer>> matrix) throws ExecutionException, InterruptedException {
        vector = vec;

        ForkJoinPool customThreadPool = new ForkJoinPool(threads_number);
        return customThreadPool.submit(() -> matrix.parallelStream().map((x) -> mulLines(x))).get().collect(Collectors.toCollection(ArrayList::new));
    }
}
