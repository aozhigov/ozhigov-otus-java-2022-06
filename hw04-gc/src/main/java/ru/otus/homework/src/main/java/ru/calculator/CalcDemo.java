package ru.calculator;


/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/


import java.time.LocalDateTime;

public class CalcDemo {
    public static void main(String[] args) {
        ru.calculator.Summator summator = new ru.calculator.Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < 100_000_000; idx++) {
            summator.calc(new ru.calculator.Data(idx));

            if (idx % 10_000_000 == 0) {
                System.out.println(LocalDateTime.now() + " current idx:" + idx);
            }
        }

        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        long delta = System.currentTimeMillis() - startTime;
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
