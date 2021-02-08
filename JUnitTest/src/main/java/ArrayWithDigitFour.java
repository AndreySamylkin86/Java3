

public class ArrayWithDigitFour {

public  int [] elementsAfterTheLastDigitFour (int [] array) throws TheArrayDoesNotСontainTheDigitFour {
boolean theArrayСontainsTheDigitFour = false;
    int [] result;
    for (int i = array.length -1; i >=0 ; i--) {
        if (array[i] == 4) {
            theArrayСontainsTheDigitFour = true;
            int resultLength = array.length - 1 - i;
            result = new int[resultLength];
            System.arraycopy(array, i + 1, result, 0, resultLength);
            return result;

        }
    }

if (!theArrayСontainsTheDigitFour)
    throw new TheArrayDoesNotСontainTheDigitFour();
    return null;

}

    public static void main(String[] args) {
//       int [] array = new int[]{1,2,3,4,5,6};
//        int [] result =   elementsAfterTheLastDigitFour(array);
//        System.out.println(Arrays.toString(result));
    }
}
