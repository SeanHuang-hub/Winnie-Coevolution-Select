package edu.nctu.bi.entity;

public class Data {
	String mark;
	String species;
	char[] squence;
	char atom1;
	char atom2;
	double similarity;
	
	public String getSpecies() {
		return species;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public char[] getSquence() {
		return squence;
	}
	public void setSquence(char[] squence) {
		this.squence = squence;
	}
	public char getAtom1() {
		return atom1;
	}
	public void setAtom1(char atom1) {
		this.atom1 = atom1;
	}
	public char getAtom2() {
		return atom2;
	}
	public void setAtom2(char atom2) {
		this.atom2 = atom2;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
}
