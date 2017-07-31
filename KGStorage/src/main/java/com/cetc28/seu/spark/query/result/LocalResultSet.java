package com.cetc28.seu.spark.query.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class LocalResultSet extends ResultSet {

	private static final long serialVersionUID = 7256652389358789457L;
	private List<Result> results;

	// private JavaPairRDD<String, Result> resultRDD = null;

	public LocalResultSet() {
		this.results = new ArrayList<>();
		this.askColumns = new ArrayList<>();
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@Override
	public void show(int num) { // TODO Auto-generated method stub
		for (int i = 0; i < num; i++) {
			if (results.size() == 0) {
				System.out.println("Null");
				break;
			} else if (i < results.size()) {
				Result result = results.get(i);
				String key = Bytes.toString(result.getRow());
				for (Cell cell : result.rawCells())
					System.out.println(key + ": " + Bytes.toString(CellUtil.cloneFamily(cell)) + " "
							+ Bytes.toString(CellUtil.cloneQualifier(cell)) + " "
							+ Bytes.toString(CellUtil.cloneValue(cell)));

			}
		}
	}

	// public void show(int num) {
	// // TODO Auto-generated method stub
	// if (resultMap.size() == 0 || resultMap == null) {
	// System.out.println("Null");
	// } else {
	// int count = 0;
	// for (Entry<String, Result> entry : resultMap.entrySet()) {
	// if (count < num) {
	// Result result = entry.getValue();
	// String key = Bytes.toString(result.getRow());
	// for (Cell cell : result.rawCells())
	// System.out.println(key + ": " +
	// Bytes.toString(CellUtil.cloneFamily(cell)) + " "
	// + Bytes.toString(CellUtil.cloneQualifier(cell)) + " "
	// + Bytes.toString(CellUtil.cloneValue(cell)));
	//
	// }
	// count++;
	// }
	// }
	//
	// }

	/*
	 * public void show() { for(Result result : results) { String key =
	 * Bytes.toString(result.getRow()); for(Cell cell : result.rawCells())
	 * System.out.println(key+" "+Bytes.toString(CellUtil.cloneFamily(cell))+" "
	 * +Bytes.toString(CellUtil.cloneQualifier(cell))+" "+Bytes.toString(
	 * CellUtil.cloneValue(cell))); } }
	 * 
	 * public void show(int num) { for(int i = 0; i < num; i++) { if(i <=
	 * results.size()) { Result result = results.get(i) ; String key =
	 * Bytes.toString(result.getRow()); for(Cell cell : result.rawCells())
	 * System.out.println(key+" "+Bytes.toString(CellUtil.cloneFamily(cell))+" "
	 * +Bytes.toString(CellUtil.cloneQualifier(cell))+" "+Bytes.toString(
	 * CellUtil.cloneValue(cell)));
	 * 
	 * } } }
	 */
	/*
	 * public void show() { if (resultRDD.isEmpty() && resultRDD == null) { for
	 * (Result result : this.results) { for (Cell cell : result.rawCells()) {
	 * System.out .println(Bytes.toString(result.getRow()) + " " +
	 * Bytes.toString(CellUtil.cloneFamily(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneQualifier(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneValue(cell))); } } } else { long start =
	 * System.currentTimeMillis(); JavaRDD<Map<String, String>> results =
	 * resultRDD .map(new Function<Tuple2<String, Result>, Map<String,
	 * String>>() {
	 * 
	 * private static final long serialVersionUID = -7232265844466542327L;
	 * 
	 * @Override public Map<String, String> call(Tuple2<String, Result> v1)
	 * throws Exception { Map<String, String> hashMap = new HashMap<String,
	 * String>(); for (Cell cell : v1._2.rawCells()) { String key =
	 * Bytes.toString(v1._2.getRow()); String value =
	 * Bytes.toString(CellUtil.cloneFamily(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneQualifier(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneValue(cell)); hashMap.put(value, key); }
	 * return hashMap; } }); // 本地一条数据从第二次开始0.6s左右 Map<String, String> re =
	 * results.first(); long end = System.currentTimeMillis();
	 * System.out.println("get time: " + (end - start)); for (Entry<String,
	 * String> entry : re.entrySet()) { System.out.println(entry.getKey() + " "
	 * + entry.getValue()); } } }
	 */

	/*
	 * public void show(int num) { if (resultRDD.isEmpty() && resultRDD == null)
	 * { for (Result result : this.results) { for (Cell cell :
	 * result.rawCells()) { System.out .println(Bytes.toString(result.getRow())
	 * + " " + Bytes.toString(CellUtil.cloneFamily(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneQualifier(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneValue(cell))); } } } else { long start =
	 * System.currentTimeMillis(); JavaRDD<Map<String, String>> results =
	 * resultRDD .map(new Function<Tuple2<String, Result>, Map<String,
	 * String>>() {
	 * 
	 * private static final long serialVersionUID = -7232265844466542327L;
	 * 
	 * @Override public Map<String, String> call(Tuple2<String, Result> v1)
	 * throws Exception { Map<String, String> hashMap = new HashMap<String,
	 * String>(); for (Cell cell : v1._2.rawCells()) { String key =
	 * Bytes.toString(v1._2.getRow()); String value =
	 * Bytes.toString(CellUtil.cloneFamily(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneQualifier(cell)) + " " +
	 * Bytes.toString(CellUtil.cloneValue(cell)); hashMap.put(value, key); }
	 * return hashMap; } }); // 本地一条数据从第二次开始0.6s左右 // Map<String, String> re =
	 * results.first(); // 本地多条数据在19-20s左右 List<Map<String, String>> resultStr =
	 * results.take(num); long end = System.currentTimeMillis();
	 * System.out.println("get time: " + (end - start)); for (Map<String,
	 * String> str : resultStr) {
	 * 
	 * for (Entry<String, String> ele : str.entrySet()) {
	 * System.out.println(ele.getValue() + " : " + ele.getKey()); }
	 * 
	 * } } }
	 */
}
