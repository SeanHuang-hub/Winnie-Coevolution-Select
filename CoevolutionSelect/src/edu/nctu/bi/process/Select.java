package edu.nctu.bi.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.nctu.bi.entity.Data;
import edu.nctu.bi.entity.IndexObject;
import edu.nctu.bi.file.ProcessFile;

public class Select {
	
	List<Data> dataList;
	List<IndexObject> indexList;
	Data human;
	
	public int loadFileMethod(String filePath) {
		dataList = ProcessFile.openFile(filePath);
		human = getHumanData(dataList);
		return getHumanSquenceLength(human);
	}
	
	public void loadIndexFileMethod(String filePath) {
		indexList = ProcessFile.openIndexFile(filePath);
	}
	
	public void selectIndexListMethod(boolean noBothDash){
		if(dataList.size()<=0 && indexList.size()<=0) {
			return;
		}
		
		for(IndexObject indexObject:indexList) {
			selectFileMethod(String.valueOf(indexObject.getIndex1()), String.valueOf(indexObject.getIndex2()), noBothDash);
		}
	}
	
	public void selectFileMethod(String index1, String index2, boolean noBothDash) {
		/** find index */
		int site1 = getHumanSquenceIndex(human, Integer.valueOf(index1));
		int site2 = getHumanSquenceIndex(human, Integer.valueOf(index2));
		/** set atom1 & atom2 */        
        toSetAllDataSquenceAtom(dataList, site1, site2);
        /** sort by atom1 & atom2 */
        int count = toSortAllDataAtom(human, dataList);
		
        /** save file */
		ProcessFile.writeFile_onlyMark(dataList,index1,index2,hasData(count));
		ProcessFile.writeFile_allData(dataList,index1,index2,hasData(count), noBothDash);
	}
	
	/**
	 * Judge File has Star?
	 * @param count
	 * @return
	 */
	public boolean hasData(int count) {
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sort Data List
	 * @param human
	 * @param dataList
	 * @return
	 */
	public int toSortAllDataAtom(Data human, List<Data> dataList) {
		List<Data> type1 = new ArrayList<Data>(); //both different
		List<Data> type2 = new ArrayList<Data>(); //both dash
		List<Data> type3 = new ArrayList<Data>(); //first dash
		List<Data> type4 = new ArrayList<Data>(); //second dash
		List<Data> type5 = new ArrayList<Data>(); //both same
		List<Data> type6 = new ArrayList<Data>(); //first same
		List<Data> type7 = new ArrayList<Data>(); //second same
		
		/** Classification Type */
		for(Data data:dataList) {
			switch(judgeType(human, data.getAtom1(), data.getAtom2())) {
			case 1:
				type1.add(data);
				break;
			case 2:
				type2.add(data);
				break;
			case 3:
				type3.add(data);
				break;
			case 4:
				type4.add(data);
				break;
			case 5:
				type5.add(data);
				break;
			case 6:
				type6.add(data);
				break;
			case 7:
				type7.add(data);
				break;
			}
		}
		
		/** sort Data */
		sortDataList(type1);
		sortDataList(type2);
		sortDataList(type3);
		sortDataList(type4);
		sortDataList(type5);
		sortDataList(type6);
		sortDataList(type7);
		
		/** Last clean and add Data */
		dataList.clear();
		dataList.addAll(type1);
		dataList.addAll(type6);
		dataList.addAll(type7);
		dataList.addAll(type2);
		dataList.addAll(type3);
		dataList.addAll(type4);
		dataList.addAll(type5);
		return type1.size();
	}
	
	/**
	 * Sort Data List
	 * @param dataList
	 */
	private void sortDataList(List<Data> dataList) {
		if(dataList.size()<2) {
			return;
		}
		
		/** Sort first atom (Atom1) */
		Collections.sort(dataList, new Comparator<Data>(){
			@Override
			public int compare(Data o1, Data o2) {
				return o1.getAtom1()-o2.getAtom1();
			}
		});
		
		/** Sort second atom (Atom2) */
		Collections.sort(dataList, new Comparator<Data>(){
			@Override
			public int compare(Data o1, Data o2) {
				if((o1.getAtom1()-o2.getAtom1()) != 0){
					return 0;
				}
				return o1.getAtom2()-o2.getAtom2();
			}
		});
	}
	
	/**
	 * Judge Data Type
	 * @param human
	 * @param atom1
	 * @param atom2
	 * @return
	 */
	private int judgeType(Data human, char atom1, char atom2) {
		char hAtom1 = human.getAtom1();
		char hAtom2 = human.getAtom2();
		
		if(atom1==hAtom1 && atom2==hAtom2) {
			return 5;
		}else if(atom1=='-' && atom2=='-'){
			return 2;
		}else if(atom1=='-'){
			return 3;
		}else if(atom2=='-'){
			return 4;
		}else if(atom1==hAtom1 && atom2!=hAtom2){
			return 6;
		}else if(atom1!=hAtom1 && atom2==hAtom2){
			return 7;
		}else{
			return 1;
		}
	}
	
	/**
	 * Set all data atom1 & atom2
	 * @param dataList
	 * @param site1
	 * @param site2
	 */
	public void toSetAllDataSquenceAtom(List<Data> dataList, int site1, int site2) {
		for(Data data:dataList) {
			char[] squence = data.getSquence();
			data.setAtom1(squence[site1]);
			data.setAtom2(squence[site2]);
		}
	}
	
	/**
	 * Get LYSC_HUMAN index range
	 * @param human
	 * @return
	 */
	public int getHumanSquenceLength(Data human) {
		char[] humanSquence = human.getSquence();
		int count=0;
		
		for(int i=0; i<humanSquence.length; i++) {
			char atom = humanSquence[i];
			if(atom!='-'){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * User choose LYSC_HUMAN squence index
	 * @param human
	 * @param index
	 * @return
	 */
	private int getHumanSquenceIndex(Data human, int index) {
		char[] humanSquence = human.getSquence();
		int count=0;
		
		for(int i=0; i<humanSquence.length; i++) {
			char atom = humanSquence[i];
			if(atom!='-'){
				count++;
				if(count == index){
					return i;
				}
			}
			
		}
		return -1;
	}
	
	/**
	 * Get LYSC_HUMAN Data
	 * @param dataList
	 * @return
	 */
	private Data getHumanData(List<Data> dataList) {
		Data result = null;
		
		for(Data data:dataList) {
			if(data.getSpecies().equals("LYSC_HUMAN")) {
				result = data;
			}
		}
		return result;
	}
}
