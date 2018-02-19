package br.com.vvdatalab.main;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import br.com.vvdatalab.controller.ControllerConfig;
import br.com.vvdatalab.vo.Config;

public class AppMain {
	
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Ingestion").setMaster("yarn-cluster");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		
		ControllerConfig cconfig = new ControllerConfig();
		String fieldHbase = cconfig.getFieldHbase("ingestion:properties", "compra", "config", "banco");
		
	    Config allFieldHbase = cconfig.getAllFieldHbase("ingestion:properties", "compra", "config");
	
	    System.out.println(allFieldHbase);
	}
}
