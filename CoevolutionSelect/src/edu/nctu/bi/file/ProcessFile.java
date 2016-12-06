package edu.nctu.bi.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nctu.bi.entity.Data;
import edu.nctu.bi.entity.IndexObject;

public class ProcessFile {
	
	public static List<Data> openFile(String filePath) {
		List<Data> dataList = new ArrayList<Data>();
		
		try {
			/** Open file */
			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			Data data = null;
			/** Read file */
			while (br.ready()) {
				
				String str = br.readLine();
				if(str.indexOf(">")==0){
					data = new Data();
					/** Species */
					data.setMark(str);
					int endIndex = str.indexOf("/");
					String species = str.substring(1, endIndex);
					data.setSpecies(species);
				}else{
					/** Squence */
					String squence = str;
					if(!squence.equals("")) {
						char[] squenceArray = squence.toCharArray();
						data.setSquence(squenceArray);
						dataList.add(data);
					}
				}
			}
			
			/** Close file */
			br.close();
			fr.close();
		} catch (Exception e) {
			/** Exception error */
			e.printStackTrace();
		}
		/** Return Data List */
		return dataList;
	}
	
	public static void writeFile_allData(List<Data> dataList, String index1, String index2, boolean hasStar, boolean noBothDash) {
		try {
			String head = "";
			if(hasStar){ head="##"; }
			FileWriter fw = new FileWriter(head+"Species-squence_"+index1+"_"+index2+".txt", false);
			for(Data data:dataList) {
				if(noBothDash==true && data.getAtom1()=='-' && data.getAtom2()=='-'){
					continue;
				}
				
				fw.write(data.getMark() + "_" + data.getAtom1() + "_" + data.getAtom2() + "\n");
				char[] array = data.getSquence();
				for(char atom:array) {
					fw.write(atom);
				}
				fw.write("\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeFile_onlyMark(List<Data> dataList, String index1, String index2, boolean hasStar) {
		try {
			String head = "";
			if(hasStar){ head="##"; }
			FileWriter fw = new FileWriter(head+"Species-mark_"+index1+"_"+index2+".txt", false);
			for(Data data:dataList) {
				fw.write(data.getMark() + "_" + data.getAtom1() + "_" + data.getAtom2() + "\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<IndexObject> openIndexFile(String filePath) {
		List<IndexObject> indexList = new ArrayList<IndexObject>();
		try {
			/** Open file */
			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			/** Read file */
			while (br.ready()) {
				String[] data = br.readLine().split("\t");
				if(data.length==0) { break; }					
				
				//System.out.println(data[0]+":"+data[1]+":"+data[2]+":"+data[3]);
				IndexObject temp = new IndexObject();
				temp.setIndex1(Integer.valueOf(data[1]));
				temp.setIndex2(Integer.valueOf(data[3]));
				indexList.add(temp);
			}
			br.close();
			fr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return indexList;
	}
	
}
