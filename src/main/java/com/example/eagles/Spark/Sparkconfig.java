package com.example.eagles.Spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
public class Sparkconfig {

    @Bean
    public SparkConf conf() {
        return new SparkConf().setAppName("eagles").setMaster("local");
    }

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(conf());
    }
}
