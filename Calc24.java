import java.util.ArrayList;
import java.util.List;

// calc 24 use backtracking
// todo: handle fractional numbers, e.g. 3, 3, 8, 8
class Calc {
    public static boolean calculate(int[] numbers, int target, List<NumberOp> ops) {
        int[] used = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            used[i] = 1;
            ops.add(new NumberOp(numbers[i], 0));
            if (calculate(numbers, used, numbers.length-1, numbers[i], target, ops)) {
                return true;
            }
            ops.remove(ops.size()-1);
            used[i] = 0;
        }
        return false;
    }
    public static String listToString(List<NumberOp> ops) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (NumberOp op : ops) {
            if (!first) {
                switch (op.operation) {
                    case 0: sb.append('+'); break;
                    case 1: sb.append('-'); break;
                    case 2: sb.append('*'); break;
                    case 3: sb.append('/'); break;
                    default: throw new AssertionError();
                }
            }
            sb.append(op.number);
            first = false;
        }
        return sb.toString();
    }
    static class NumberOp {
        int number;
        // 0, 1, 2, 3
        // +, -, *, /
        int operation;
        public NumberOp(int number, int operation) { this.number = number; this.operation = operation; }
    }
    private static boolean calculate(int[] numbers, int[] used, int rem, int curr, int target, List<NumberOp> ops) {
        if (rem == 0) {
            return (curr == target);
        }
        for (int i = 0; i < numbers.length; i++) {
            if (used[i] == 1) continue;
            used[i] = 1;
            int val = numbers[i];
            // plus
            ops.add(new NumberOp(val, 0));
            if (calculate(numbers, used, rem-1, curr + val, target, ops)) {
                return true;
            }
            ops.remove(ops.size() - 1);
            // minus
            ops.add(new NumberOp(val, 1));
            if (calculate(numbers, used, rem-1, curr - val, target, ops)) {
                return true;
            }
            ops.remove(ops.size() - 1);
            // mult
            ops.add(new NumberOp(val, 2));
            if (calculate(numbers, used, rem-1, curr * val, target, ops)) {
                return true;
            }
            ops.remove(ops.size() - 1);
            // div
            ops.add(new NumberOp(val, 3));
            if (curr % val == 0 && calculate(numbers, used, rem-1, curr / val, target, ops)) {
                return true;
            }
            ops.remove(ops.size() - 1);
            used[i] = 0;
        }
        return false;
    }
}
public class Main {
    public static void main(String[] args) {
        int target = 24;
        List<Calc.NumberOp> ops = new ArrayList<>();
        {
            int[] input = {1, 2, 3, 4};
            ops.clear();
            System.out.println(Calc.calculate(input, target, ops));
            System.out.println(Calc.listToString(ops));
        }
        {
            int[] input = {1, 2, 2, 1};
            ops.clear();
            System.out.println(Calc.calculate(input, target, ops));
            System.out.println(Calc.listToString(ops));
        }
        {
            int[] input = {3, 3, 8, 8};
            ops.clear();
            System.out.println(Calc.calculate(input, target, ops));
            System.out.println(Calc.listToString(ops));
        }
        {
            int[] input = {5, 3, 7, 8};
            ops.clear();
            System.out.println(Calc.calculate(input, target, ops));
            System.out.println(Calc.listToString(ops));
        }
    }
}
