public class OneAndFour {
    public boolean arrayContainsTheDigitOneAndFour(int[] array) {
        boolean arrayContainsTheDigitOne = false;
        boolean arrayContainsTheDigitFour = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1)
                arrayContainsTheDigitOne = true;
            if (array[i] == 4)
                arrayContainsTheDigitFour = true;
        }

        return arrayContainsTheDigitOne && arrayContainsTheDigitFour;
    }
}
