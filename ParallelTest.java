import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelTest {
    @Test
    public void testMulParallel() throws InterruptedException {
        assertEquals(
                new ArrayList<Integer>(Arrays.asList(34,59,26)) ,
                Parallel.mulParallel(8, new ArrayList<Integer>(Arrays.asList(3,5,7)),
                        new ArrayList<ArrayList<Integer>> (Arrays.asList(new ArrayList<Integer> (Arrays.asList(1, 2, 3)),
                                new ArrayList<Integer> (Arrays.asList(2,5,4)), new ArrayList<Integer>(Arrays.asList(3,2,1)))
                        )
                )
        );
    }
    @Test
    public void testmulParallelStream() throws InterruptedException {
        assertEquals(
                new ArrayList<Integer>(Arrays.asList(34,59,26)) ,
                Parallel.mulParallel(4, new ArrayList<Integer>(Arrays.asList(3,5,7)),
                        new ArrayList<ArrayList<Integer>> (Arrays.asList(new ArrayList<Integer> (Arrays.asList(1, 2, 3)),
                                new ArrayList<Integer> (Arrays.asList(2,5,4)), new ArrayList<Integer>(Arrays.asList(3,2,1)))
                        )
                )
        );
    }

}