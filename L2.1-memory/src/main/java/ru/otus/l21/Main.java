package ru.otus.l21;

import java.util.function.Supplier;

/**
 * Для разных вриантов можно протестировать в двух вариантах
 *      с опцией -XX:+UseCompressedOops
 *      и с опцией -XX:-UseCompressedOops
 */
@SuppressWarnings({"RedundantStringConstructorCall"})
public class Main {
    public static void main(String... args) throws InterruptedException {
        ObjectSizeMeter objectSizeMeter = new ObjectSizeMeter();

        objectSizeMeter.setObjectsSampleSize(10_000_000);
        objectSizeMeter.setIterationNumber(5);

        System.out.println("Starting to calculate size of objects...");
        calculateObjectSize(objectSizeMeter,"empty object", Object::new);
        calculateObjectSize(objectSizeMeter,"class with 1 int", MyClassWithOneInt::new);
        calculateObjectSize(objectSizeMeter,"class with 1 long", MyClassWithOneLong::new);
        calculateObjectSize(objectSizeMeter,"class with 2 long", MyClassWithTwoLong::new);
        calculateObjectSize(objectSizeMeter,"class with 3 long", MyClassWithThreeLong::new);
        calculateObjectSize(objectSizeMeter,"class with 1 byte", MyClassWithOneByte::new);
        calculateObjectSize(objectSizeMeter,"class with 4 bytes", MyClassWithFourBytes::new);
        calculateObjectSize(objectSizeMeter,"class with 5 bytes", MyClassWithFiveBytes::new);
        calculateObjectSize(objectSizeMeter,"class with 1 reference", MyClassWithOneReference::new);
        calculateObjectSize(objectSizeMeter,"class with 2 references", MyClassWithTwoReferences::new);
        calculateObjectSize(objectSizeMeter,"class with 3 references",MyClassWithThreeReferences::new);
        calculateObjectSize(objectSizeMeter,"class with 4 references",MyClassWithFourReferences::new);


        System.out.println();
        System.out.println("Starting to calculate size of arrays...");

        calculateObjectSize(objectSizeMeter,"empty array",()-> new boolean[0]);
        calculateObjectSize(objectSizeMeter,"array with 1 boolean",()-> new boolean[1]);
        calculateObjectSize(objectSizeMeter,"array with 2 boolean",()-> new boolean[2]);
        calculateObjectSize(objectSizeMeter,"array with 8 boolean",()-> new boolean[8]);
        calculateObjectSize(objectSizeMeter,"array with 9 boolean",()-> new boolean[9]);
        calculateObjectSize(objectSizeMeter,"array with 16 boolean",()-> new boolean[16]);
        calculateObjectSize(objectSizeMeter,"array with 17 boolean",()-> new boolean[17]);

        objectSizeMeter.setObjectsSampleSize(1_000_000);
        calculateObjectSize(objectSizeMeter,"array with 100 boolean",()-> new boolean[100]);

        objectSizeMeter.setObjectsSampleSize(100_000);
        calculateObjectSize(objectSizeMeter,"array with 1000 boolean",()-> new boolean[1000]);

        objectSizeMeter.setObjectsSampleSize(10_000_000);


        System.out.println();
        System.out.println("Starting to calculate size of strings...");
        calculateObjectSize(objectSizeMeter,"empty string",()-> new String(""));
        calculateObjectSize(objectSizeMeter,"empty string without string pool",()-> new String(new char[0]));
        calculateObjectSize(objectSizeMeter,"string with 10 chars",()-> new String(new char[10]));

        objectSizeMeter.setObjectsSampleSize(1_000_000);
        calculateObjectSize(objectSizeMeter,"string with 100 chars",()-> new String(new char[100]));

        objectSizeMeter.setObjectsSampleSize(100_000);
        calculateObjectSize(objectSizeMeter,"string with 1000 chars",()-> new String(new char[1000]));
    }

    private static void calculateObjectSize(ObjectSizeMeter objectSizeMeter, String objectName, Supplier objectConstructor){
        System.out.print("\tCalculating size of " + objectName + "... ");
        try {
            long size = objectSizeMeter.calculate(objectConstructor);
            System.out.println(size + " bytes");
        }catch(Exception ex){
            System.out.print("error occurred during size calculation: ");
            System.out.println(ex.getMessage());
        }
    }

    private static class MyClassWithOneInt {
        private int i = 0;
    }
    private static class MyClassWithOneLong {
        private long i1 = 0;
    }
    private static class MyClassWithTwoLong {
        private long i1 = 0;
        private long i2 = 0;
    }
    private static class MyClassWithThreeLong {
        private long i1 = 0;
        private long i2 = 0;
        private long i3 = 0;
    }

    private static class MyClassWithOneByte {
        private byte i = 0;
    }
    private static class MyClassWithFourBytes {
        private byte i = 0;
        private byte i2 = 0;
        private byte i3 = 0;
        private byte i4 = 0;
    }
    private static class MyClassWithFiveBytes {
        private byte i = 0;
        private byte i2 = 0;
        private byte i3 = 0;
        private byte i4 = 0;
        private byte i5 = 0;
    }

    private static class MyClassWithOneReference {
        private Object i1;
    }
    private static class MyClassWithTwoReferences {
        private Object i1;
        private Object i2;
    }
    private static class MyClassWithThreeReferences {
        private Object i1;
        private Object i2;
        private Object i3;
    }
    private static class MyClassWithFourReferences {
        private Object i1;
        private Object i2;
        private Object i3;
        private Object i4;
    }
}
