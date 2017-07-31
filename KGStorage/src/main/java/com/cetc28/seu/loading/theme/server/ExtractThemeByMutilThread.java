package com.cetc28.seu.loading.theme.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc28.seu.loading.theme.output.HbaseWritter;
import com.cetc28.seu.loading.theme.parser.ObjectParser;


public class ExtractThemeByMutilThread implements Runnable{
	private HashMap<String,String> themeNameAndClassPartition;
	private int pageSize;
	private int numPage;
	

	public ExtractThemeByMutilThread(HashMap<String, String> themeNameAndClassPartition, int pageSize, int numPage) {
		super();
		this.themeNameAndClassPartition = themeNameAndClassPartition;
		this.pageSize = pageSize;
		this.numPage = numPage;
	}
	public ExtractThemeByMutilThread(int pageSize, int numPage) {
		super();
		this.pageSize = pageSize;
		this.numPage = numPage;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int localnumPage;
		HbaseWritter writter=new HbaseWritter();
		ObjectParser oParser=new ObjectParser(writter);
		for(Map.Entry<String,String> entry : themeNameAndClassPartition.entrySet()){
			int ipage=1;
			localnumPage=numPage;
			inner:
			for(;ipage<localnumPage;ipage++){
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
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					
				}
			}
		}	
		
	}
	private List<Object> extract(String key, Class<?> forName, int ipage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}


	public HashMap<String, String> getThemeNameAndClassPartition() {
		return themeNameAndClassPartition;
	}
	
	public void setThemeNameAndClassPartition(HashMap<String,String> themeNameAndClassPartition){
		this.themeNameAndClassPartition=themeNameAndClassPartition;
	}
	
	

}
