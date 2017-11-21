import java.util.Arrays;

public class ConcatenarArrays {
    // Versão genérica, usar com quaisquer arrays de objetos (têm de ser do mesmo tipo)
    public static <T> T[] concat (Class<T> klass, T[]... arrays) {
        int length = 0;
        for (T[] array : arrays) { length += array.length; }
        @SuppressWarnings ("unchecked")
        T[] ret = (T[]) java.lang.reflect.Array.newInstance (klass, length);
        int destPos = 0;
        for (T[] array : arrays) {
            System.arraycopy (array, 0, ret, destPos, array.length);
            destPos += array.length;
        }
        return ret;
    }
    // Versão especializada para o tipo primitivo int
    public static int[] concat (int[]... arrays) {
        int length = 0;
        for (int[] array : arrays) { length += array.length; }
        int[] ret = new int[length];
        int destPos = 0;
        for (int[] array : arrays) {
            System.arraycopy (array, 0, ret, destPos, array.length);
            destPos += array.length;
        }
        return ret;
    }
    // Versão especializada para o tipo primitivo double
    public static double[] concat (double[]... arrays) {
        int length = 0;
        for (double[] array : arrays) { length += array.length; }
        double[] ret = new double[length];
        int destPos = 0;
        for (double[] array : arrays) {
            System.arraycopy (array, 0, ret, destPos, array.length);
            destPos += array.length;
        }
        return ret;
    }
    public static void main (String[] args) {
        Integer[] arr1 = new Integer[10];
        for (int i = 0; i < arr1.length; ++i) arr1[i] = i;
        Integer[] arr2 = new Integer[15];
        for (int i = 0; i < arr2.length; ++i) arr2[i] = i + 100;
        Integer[] arr3 = new Integer[30];
        for (int i = 0; i < arr3.length; ++i) arr3[i] = i + 1000;
        Integer[] arr = ConcatenarArrays.concat (Integer.class, arr1, arr2, arr3);
        System.out.println (arr.length);
        System.out.println (Arrays.asList (arr));
        int[] intArr = ConcatenarArrays.concat (new int[10], new int[15], new int[30]);
        System.out.println (intArr.length);
    }
}