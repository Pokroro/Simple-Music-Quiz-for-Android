package com.example.sllbt;

public class Song {

	protected String title;
	protected String url;
	
	public Song (String title, String url) {
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Song [title=" + title + ", url=" + url + "]";
	}
	
}
