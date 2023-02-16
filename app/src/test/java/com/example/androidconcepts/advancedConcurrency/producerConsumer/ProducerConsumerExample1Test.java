package com.example.androidconcepts.advancedConcurrency.producerConsumer;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.example1.WrongProducerConsumerExample1;
import com.example.androidconcepts.advancedConcurrency.producerConsumer.example2.CorrectProducerConsumerExample2;

import org.junit.Test;

public class ProducerConsumerExample1Test {

    @Test
    public void wrongProducerConsumerTest() throws Exception {
        WrongProducerConsumerExample1 SUT = new WrongProducerConsumerExample1();
        SUT.startExecution();
    }

    @Test
    public void correctProducerConsumerTest() throws Exception {
        CorrectProducerConsumerExample2 SUT = new CorrectProducerConsumerExample2();
        SUT.startExecution();
    }
}