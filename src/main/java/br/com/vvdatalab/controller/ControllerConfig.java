package br.com.vvdatalab.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vvdatalab.vo.Config;

public class ControllerConfig {
	
	public Connection getConnection() throws IOException {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "10.128.132.221");
    	config.set("hbase.client.retries.number", Integer.toString(1));
    	config.set("zookeeper.session.timeout", Integer.toString(60000));
    	config.set("zookeeper.recovery.retry", Integer.toString(1));
    	config.set("zookeeper.znode.parent", "/hbase-unsecure");
    	config.set("hbase.zookeeper.property.clientport", "2181");
		
		Connection connection = ConnectionFactory.createConnection(config);
		
		return connection;
	}
	
	public String getFieldHbase(String hbaseTable, String rowkey, String cf, String qualifier) {
		Table table;
		String valueField = null;
		
		try {
			Connection connection = getConnection();
			table = connection.getTable(TableName.valueOf(Bytes.toBytes(hbaseTable)));
			
			Get get = new Get(Bytes.toBytes(rowkey));
			get.addFamily(Bytes.toBytes(cf));
			
			Result result = table.get(get);
				
			byte[] valueFieldByte = result.getValue(cf.getBytes(), qualifier.getBytes());
			valueField = new String(valueFieldByte);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return valueField;
		
	}
	
	public Config getAllFieldHbase(String hbaseTable, String rowkey,String cf) {
		Table table;
		Config config = null;
		
		try {
			Connection connection = getConnection();
			table = connection.getTable(TableName.valueOf(Bytes.toBytes(hbaseTable)));
			
			Get get = new Get(Bytes.toBytes(rowkey));
			
			Result result = table.get(get);
			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(cf.getBytes());
			
			@SuppressWarnings("unchecked")
			Map<String, String> mapString = new HashedMap();
			
			for(Entry<byte[], byte[]> map : familyMap.entrySet()) {
				String campo = new String(map.getKey());
				String valor = new String(map.getValue());
				
				mapString.put(campo, valor);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			config = objectMapper.convertValue(mapString, Config.class);
			
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return config;
	}

}
