package com.example.androidconcepts.advancedConcurrency.producerConsumer;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.example1.WrongProducerConsumerExample1;
import com.example.androidconcepts.advancedConcurrency.producerConsumer.example2.CorrectProducerConsumerExample2;
import com.example.androidconcepts.advancedConcurrency.producerConsumer.example3.BQProducerConsumerExample3;
import com.example.androidconcepts.advancedConcurrency.producerConsumer.example4.BQDishRackExample4;
import com.example.androidconcepts.advancedConcurrency.producerConsumer.example5.BQDishRackExample5;

import org.junit.Test;

public class ProducerConsumerTest {

    @Test
    public void wrongProducerConsumerTest() throws Exception {
        WrongProducerConsumerExample1 SUT = new WrongProducerConsumerExample1();
        SUT.startExecution();
    }

    @Test
    public void correctProducerConsumerTest() {
        CorrectProducerConsumerExample2 SUT = new CorrectProducerConsumerExample2();
        SUT.startExecution();
    }

    @Test
    public void BlockingQueueProducerConsumerTest() {
        BQProducerConsumerExample3 SUT = new BQProducerConsumerExample3();
        SUT.startExecution();
    }

    @Test
    public void BQDishRackExample4Test() {
        BQDishRackExample4 SUT = new BQDishRackExample4();
        SUT.startExecution();
    }

    @Test
    public void BQDishRackExample5Test() {
        BQDishRackExample5 SUT = new BQDishRackExample5();
        SUT.startExecution();
    }
}