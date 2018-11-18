package com.example.demogradle;

public class Song {

	private String songName;
	private Integer songNumber;
	private Integer durationInseconds;
	private Integer prevoiusSongNumber;
	private Integer nextSongNumber;
	private boolean played;

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public Integer getSongNumber() {
		return songNumber;
	}

	public void setSongNumber(Integer songNumber) {
		this.songNumber = songNumber;
	}

	public Integer getDurationInseconds() {
		return durationInseconds;
	}

	public void setDurationInseconds(Integer durationInseconds) {
		this.durationInseconds = durationInseconds;
	}

	public Integer getPrevoiusSongNumber() {
		return prevoiusSongNumber;
	}

	public void setPrevoiusSongNumber(Integer prevoiusSongNumber) {
		this.prevoiusSongNumber = prevoiusSongNumber;
	}

	public Integer getNextSongNumber() {
		return nextSongNumber;
	}

	public void setNextSongNumber(Integer nextSongNumber) {
		this.nextSongNumber = nextSongNumber;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}


}
