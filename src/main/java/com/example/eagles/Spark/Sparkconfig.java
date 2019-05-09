package com.example.eagles.Spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sparkconfig {

    //test

    @Bean
    public SparkConf conf() {
        SparkConf conf = new SparkConf().setAppName("eagles").setMaster("local");

        return conf.set("spark.testing.memory", "2147480000");
    }

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(conf());
    }
}