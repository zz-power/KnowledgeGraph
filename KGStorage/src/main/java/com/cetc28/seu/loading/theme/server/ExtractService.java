package com.cetc28.seu.loading.theme.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cetc28.seu.loading.theme.output.HbaseWritter;
import com.cetc28.seu.loading.theme.parser.ObjectParser;
import com.cetc28.seu.loading.theme.parser.SchemaParser;
import com.cetc28.seu.loading.theme.parser.XMLParser;




public class ExtractService {
	private String path="allTheme";
	/**
	 * 上午9:59:12
	 * TODO 使用单线程抽取所有主题数据
	 */
	public void extractAllThema(){
		
		HashMap<String,String> themeNameAndClass=new HashMap<>();
		XMLParser xmlParser=new XMLParser();
		xmlParser.patchParse(path, themeNameAndClass);
		HbaseWritter writter=new HbaseWritter();
		ObjectParser oParser=new ObjectParser(writter);
		int pageSize=1000;
		int numPage;
		for(Map.Entry<String,String> entry : themeNameAndClass.entrySet()){
			int ipage=1;
			numPage=1000;
			inner:
			for(;ipage<numPage;ipage++){
				try {
					List<Object> datas=this.extract(entry.getKey(),Class.forName(entry.getValue()),ipage,pageSize);
					if(datas!=null && datas.size()!=0){
						oParser.extractTreeObj(datas, Class.forName(entry.getValue()), entry.getKey());
						if(ipage>=numPage){
							numPage=numPage*2;
							continue inner;
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}	
	}
	/**
	 * 
	 * @param patitionNum 
	 * 上午9:59:44
	 * TODO 划分主题数据，多线程运行
	 */
	public void extractByPatition(int patitionNum){
		//1. 划分主题
		//2. 多线程运行
		HashMap<String,String> themeNameAndClass=new HashMap<>();
		XMLParser xmlParser=new XMLParser();
		xmlParser.patchParse(path, themeNameAndClass);
		int nums=themeNameAndClass.size();
		int partitionSize=nums/patitionNum;
		Set<Entry<String, String>> entries = themeNameAndClass.entrySet();
		Iterator<Entry<String, String>> itr=entries.iterator();
		for(int i=0;i<patitionNum;i++){
			HashMap<String,String> mapPartition=new HashMap<>();
			int curNum=0;
			for(;curNum<partitionSize;curNum++){
				Entry<String, String> curEntry=itr.next();
				mapPartition.put(curEntry.getKey().trim(),curEntry.getValue().trim());
			}
			ExtractThemeByMutilThread et=new ExtractThemeByMutilThread(mapPartition, 1000, 1000);
			new Thread(et).start();
		}
	}
	
	public  void extractAllSchema() throws ClassNotFoundException{//抽取所有主题数据
		HashMap<String,String> maps=new HashMap<String,String>();
		XMLParser xp=new XMLParser();
		xp.patchParse(path,maps);
		HbaseWritter hw=new HbaseWritter();
		SchemaParser sp=new SchemaParser(hw);
		for( Entry<String, String> map : maps.entrySet()){
			sp.setThemeName(map.getKey());
			String className=map.getValue();
			sp.dfs(Class.forName(className),className, " ");
		}
	}
	
	
	private List<Object> extract(String key, Class<?> forName, int ipage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
