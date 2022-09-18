package com.example.sllbt.ux;

public class Song {

	private final String title;
	private final String url;
	private final int indice;
	
	public Song (String title, String url, int indice) {
		this.title = title;
		this.url = url;
		this.indice = indice;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public int getIndice() {
		return indice;
	}
}
