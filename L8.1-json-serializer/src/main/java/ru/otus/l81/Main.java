package ru.otus.l81;


import ru.otus.l81.jsonserializer.JsonSerializer;

import java.util.*;

/**
 *
 */
public class Main {

    public static void main(String... args) throws Exception{
        System.out.println(JsonSerializer.objectToJson(new ComplexObject()));
    }

    static class ComplexObject{
        int number = 5;
        long bigNumber = 10000000L;
        float pi = 3.14f;
        double doublePi = 6.283185;
        String name = "John Doe";

        transient String whoKilledKennedy = "Lee Harvey Oswald";

        int[] fibonacciNumbers = new int[]{1,1,2,3,5,8,13};
        String[] cardinalDirection = new String[]{"North","East","South","West"};

        List<Object> someList = new ArrayList<>(Arrays.asList(1,1,2,3,"North",5L,42, new ComplexObject.InnerObject()));

        Map<String,Object> theGreatHouses = new HashMap<>();

        InnerObject innerObject = new InnerObject();

        {
            theGreatHouses.put("House Lannister","Hear me roar!");
            theGreatHouses.put("House Stark","Winter is coming");
            theGreatHouses.put("House Greyjoy","We Do Not Sow");
            theGreatHouses.put("House Targaryen","Fire and Blood");
        }

        class InnerObject{
            int answerToTheUltimateQuestionOfLifeTheUniverseAndEverything = 42;
        }
    }

}
