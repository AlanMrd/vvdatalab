package br.com.vvdatalab.main;

import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;

public class App {
	public static void main( String[] args ){
    	SparkConf conf = new SparkConf().setAppName("Ingestion").setMaster("yarn-cluster");
    	JavaSparkContext jsc = new JavaSparkContext(conf);
    	
//    	String version = jsc.version();
//    	
//    	System.out.println("Teste de Aplicação");
//    	System.out.println(version);
    	
    	Configuration config = HBaseConfiguration.create();
//    	config.set("hbase.zookeeper.quorum", "10.128.132.221");
//    	conf.set("zookeeper.znode.parent", "/hbase-unsecure");
    	config.set("hbase.zookeeper.quorum", "10.128.132.221");
    	config.set("hbase.client.retries.number", Integer.toString(1));
    	config.set("zookeeper.session.timeout", Integer.toString(60000));
    	config.set("zookeeper.recovery.retry", Integer.toString(1));
    	config.set("zookeeper.znode.parent", "/hbase-unsecure");
    	config.set("hbase.zookeeper.property.clientport", "2181");
    	
    	Result result = new Result();
    	
    	try (Connection connection = ConnectionFactory.createConnection(config);
				Table table = connection.getTable(TableName.valueOf(Bytes.toBytes("ingestion:properties")))){
    			Get get = new Get(Bytes.toBytes("compra"));
    			get.addFamily(Bytes.toBytes("config"));
    			result = table.get(get);
    			
    			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes("config"));
    			
    			for(Entry<byte[], byte[]> map : familyMap.entrySet()) {
    				String campo =  new String(map.getKey());
    				String value =  new String(map.getValue());
    				
    				System.out.println("Campo - " + campo);
    				System.out.println("Valor - " + value);
    			}
//    			
//    			
//    			byte[] value = result.getValue("config".getBytes(), "banco".getBytes());
//    			String banco = new String(value);
//    			System.out.println(banco);
    	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
