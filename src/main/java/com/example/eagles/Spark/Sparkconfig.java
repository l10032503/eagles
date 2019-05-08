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
        return new SparkConf().setAppName("eagles").setMaster("local");
    }

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(conf());
    }
}
