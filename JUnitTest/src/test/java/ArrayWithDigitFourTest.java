import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class ArrayWithDigitFourTest {
    private ArrayWithDigitFour arrayWithDigitFour;

    @BeforeEach
    public void init() {
        arrayWithDigitFour = new ArrayWithDigitFour();
    }
    @AfterEach
    public void destroy () {
        arrayWithDigitFour = null;
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testElementsAfterTheLastDigitFour(int[] result, int[] array) {
        Assertions.assertArrayEquals(result, arrayWithDigitFour.elementsAfterTheLastDigitFour(array));
    }

    public static Stream<Arguments> data() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{5, 6}, new int[]{1, 2, 3, 4, 5, 6}));
        out.add(Arguments.arguments(new int[]{1, 1, 1, 1, 1}, new int[]{4, 1, 1, 1, 1, 1}));
        out.add(Arguments.arguments(new int[]{3, 2, 1}, new int[]{6, 5, 4, 3, 2, 1}));
        out.add(Arguments.arguments(new int[]{}, new int[]{4, 4, 4, 4, 4, 4}));
        return out.stream();
    }

    @Test
    public void testException() {
        Assertions.assertThrows(TheArrayDoesNotСontainTheDigitFour.class, () -> {

            arrayWithDigitFour.elementsAfterTheLastDigitFour(new int[]{5, 6});
        });
    }
}
