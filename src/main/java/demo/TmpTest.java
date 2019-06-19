package demo;

/**
 * @author huishen
 * @date 2019-04-30 17:21
 */
public class TmpTest {

    public static void main(String[] args) {
        TmpTest tmpTest = new TmpTest();
        int number = tmpTest.test();
        System.out.println(number);
    }

    public int test() {
        int sum = 11;
        int number = -1;
        // List<Integer> numbers = new ArrayList<>(16);
        int[] numbers = new int[12];
        for (int i = 1; i <= 11; i++) {
            if (1 == i) {
                numbers[i] = 1;
                number = 1;
            } else if (2 == i) {
                numbers[i] = 1;
                number = 1;
            } else if (5 == i) {
                numbers[i] = 1;
                number = 1;
            } else if (i < 5) {
                number = Math.min(numbers[i - 1] + 1, numbers[i - 2] + 1);
                numbers[i] = number;
            } else {
                number = Math.min(Math.min(numbers[i - 1] + 1, numbers[i - 2] + 1), numbers[i - 5] + 1);
                numbers[i] = number;
            }
        }
        return number;
    }

}
