package com.sehack1.myday;

public class Post {
	private String caption;
	private int idImg;
	
	public Post(String caption, Integer idImg){
		this.caption = caption;
		this.idImg = idImg;
	}
	
	//called a getter, retrieves the caption of the String
	public String getCaption(){
		return caption;
	}
	
	//called a setter, sets the caption
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	//you always need a getter and a setter per member of the class
	public int getIdImg(){
		return idImg;
	}
	
	public void setIdImg(int idImg){
		this.idImg = idImg;
	}
	
	
}
