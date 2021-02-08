import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OneAndFourTest {
    private OneAndFour oneAndFour;

    @BeforeEach
    public void init() {
        oneAndFour = new OneAndFour();
    }
    @AfterEach
    public void destroy () {
        oneAndFour = null;
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testElementsAfterTheLastDigitFour(boolean result, int[] array) {
        Assertions.assertEquals(result, oneAndFour.arrayContainsTheDigitOneAndFour(array));
    }

    public static Stream<Arguments> data() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(true, new int[]{1, 2, 3, 4, 5, 6}));
        out.add(Arguments.arguments(true, new int[]{4, 1, 1, 1, 1, 1}));
        out.add(Arguments.arguments(false, new int[]{1,1,1,1,1,1,1}));
        out.add(Arguments.arguments(false, new int[]{4, 4, 4, 4, 4, 4}));
        return out.stream();
    }
}
