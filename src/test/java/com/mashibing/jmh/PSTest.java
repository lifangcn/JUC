package com.mashibing.jmh;

import org.openjdk.jmh.annotations.Benchmark;

public class PSTest {
    @Benchmark
    public void testForEach() {
        PS.foreach();
    }
}