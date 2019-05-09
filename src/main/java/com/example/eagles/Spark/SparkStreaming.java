package com.example.eagles.Spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkStreaming {

    public void makeStreaming(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("eagles");
    }

}
