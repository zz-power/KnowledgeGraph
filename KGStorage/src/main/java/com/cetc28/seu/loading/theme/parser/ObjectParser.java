package com.cetc28.seu.loading.theme.parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.cetc28.seu.loading.theme.model.Property;
import com.cetc28.seu.loading.theme.output.ParserWriter;






/**
 * @author yaosheng
 */
public class ObjectParser extends Parser{
	private long id=0;
	private String themeName;
	private ParserWriter writer;
	
	public ObjectParser(ParserWriter writer) {
		super();
		this.writer = writer;
	}
	
	/**
	 * @param obj
	 * @param type
	 * @param parent
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void dfs(Object obj,Class<?> type,long parent) throws IllegalArgumentException, IllegalAccessException{
		
		Field[] attrs=type.getDeclaredFields();
		HashMap<String,String> arrayChild=new HashMap<>();
		HashMap<String,String> childObj=new HashMap<>();
		HashMap<String,String> basePro=new HashMap<>();
		long cur=id;
		for(Field attr : attrs){//遍历所有属性
			attr.setAccessible(true);
			Class<?> attrtype = attr.getType();
			String attrname=attr.getName();
			Object attrValue=attr.get(obj);
			if(attrValue==null) return;
			if(isArray(attrtype)){//如果是数组
				if(!isBaseArray(attrtype)){//如果是对象数组
					Object[] arrayValue=(Object[]) attrValue;
					String value="";
					for(Object everyObj : arrayValue){
						id++;
						dfs(everyObj,everyObj.getClass(),cur);
						String idTheme=String.valueOf(id)+themeName;
						value=value+idTheme+" ";
					}
					arrayChild.put(attrname, value);
				}else{//如果是基础类型的数组
					String value="";
					for(int i=0;i<Array.getLength(attrValue);i++){
						value=value+Array.get(attrValue, i)+" ";
					}
					basePro.put(attrname, value);
				}
			}else if(isList(attrtype)){//如果是List
				ParameterizedType pt = (ParameterizedType) attr.getGenericType() ;
				Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
				List<?> listValue=(List<?>) attrValue;
				if(isObject(clz)){//list中保存的是对象
					for(Object listObj : listValue){
						id++;
						dfs(listObj,clz,cur);
					}
				}
				else{//保存的是基础类型
					String value="";
					for(Object ev : listValue){
						value=value+String.valueOf(ev)+" ";
					}
					basePro.put(attrname, value);
				}
			}else if(isObject(attrtype)){// 该属性是对象
				Object child=attr.get(obj);
				id++;
				childObj.put(attrname, String.valueOf(id)+themeName);
				dfs(child,attrtype,cur);
			}
			else {
				basePro.put(attrname, String.valueOf(attrValue));
			}
		}
		Property prop=new Property(basePro, String.valueOf(id)+themeName, childObj, arrayChild,Long.toString(cur)+themeName,type.getSimpleName());
		writer.writer(prop);
	}
	
	public void extractTreeObj(List<Object> objs,Class<?> type,String themeName) throws IllegalArgumentException, IllegalAccessException{
		this.themeName=themeName;
		for(Object obj : objs){
			dfs(obj,type,-1);
			id++;
		}
	}
	public void writerToHbase(Property prop,String themeName){
		System.out.println("oid : " +prop.getOid()+" parentid : " +prop.getParent()+" type : "+prop.getType());
		
		for(Map.Entry<String, String>  basePro : prop.getBasePro().entrySet()){
			System.out.println("base prop 'key : "+basePro.getKey()+" , "+basePro.getValue());
		}
		for(Map.Entry<String, String> objectProp : prop.getChildObj().entrySet()){
			System.out.println("child prop 'key : "+objectProp.getKey()+" , "+objectProp.getValue());
		}
		for(Entry<String, String> arrayProp : prop.getArrayObj().entrySet()){
			System.out.println("array prop 'key : "+arrayProp.getKey()+" , "+arrayProp.getValue());
		}
		System.out.println("\n\n");
	}
	public void updateToHbase(Property prop,String themeName){
		// 1. 查出 Hbase 中相同的prop 
		// 2. 修改 该 prop中的  sameAs 列
		// 3. 将该prop写入 Hbase
		System.out.println("need updated object : ");
		System.out.println("oid : " +prop.getOid()+" parentid : " +prop.getParent()+" type : "+prop.getType());
		for(Map.Entry<String, String>  basePro : prop.getBasePro().entrySet()){
			System.out.println("base prop 'key : "+basePro.getKey()+" , "+basePro.getValue());
		}
		for(Map.Entry<String, String> objectProp : prop.getChildObj().entrySet()){
			System.out.println("child prop 'key : "+objectProp.getKey()+" , "+objectProp.getValue());
		}
		for(Entry<String, String> arrayProp : prop.getArrayObj().entrySet()){
			System.out.println("array prop 'key : "+arrayProp.getKey()+" , "+arrayProp.getValue());
		}
		System.out.println("\n\n");
	}
	
}
