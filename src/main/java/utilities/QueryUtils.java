package utilities;

public class QueryUtils {
    public static double[] doubleArrayFrom(Object[] ary)
    {
        double[] result = new double[ary.length];
        for (int i = 0; i < ary.length; i++) {
            result[i] = doubleFrom(ary[i]);
        }

        return result;
    }

    public static double doubleFrom(Object o)
    {
        if (o instanceof Integer) {
            return ((Integer) o);
        } else if (o instanceof Float) {
            return ((Float) o);
        } else if (o instanceof Double) {
            return ((Double) o);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
