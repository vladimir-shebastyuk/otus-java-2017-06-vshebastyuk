package ru.otus.l51.testframework;

/**
 * Вспомогательные методы для тестирования условий в тестах
 */
public class Assert {
    private Assert(){}

    public static void assertEquals(Object expected, Object result){
        if(result != null){
            if(!result.equals(expected)){
                fail(expected, result);
            }
        }else if(expected != null){
            fail(expected, result);
        }
    }

    public static void assertTrue(boolean expression){
        if(!expression){
            fail();
        }
    }

    public static void assertTrue(String message, boolean expression){
        if(!expression){
            fail(message);
        }
    }

    private static void fail(){
        throw new AssertionError();
    }

    private static void fail(String message){
        throw new AssertionError(message);
    }

    private static void fail(Object expected, Object result){
        throw new AssertionError(String.format("expected:<%s> but was:<%s>",expected,result));
    }
}
