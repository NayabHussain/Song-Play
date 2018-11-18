package com.example.demogradle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SongPlayServiceImpl implements SongPlayService {

	@Override
	public void playSong() {

		List<Song> songList = new ArrayList<>();

		Song song1 = new Song();
		song1.setDurationInseconds(4);
		song1.setSongName("Despacito");

		Song song2 = new Song();
		song2.setDurationInseconds(5);
		song2.setSongName("Waka Waka");

		Song song3 = new Song();
		song3.setDurationInseconds(6);
		song3.setSongName("anarkali disco chali");

		Song song4 = new Song();
		song4.setDurationInseconds(10);
		song4.setSongName("Dhoom Machale");

		Song song5 = new Song();
		song5.setDurationInseconds(6);
		song5.setSongName("Dilwale");

		songList.add(song1);
		songList.add(song2);
		songList.add(song3);
		songList.add(song4);
		songList.add(song5);

		AtomicInteger songCount = new AtomicInteger(0);

		AtomicBoolean fromPrevious = new AtomicBoolean(false);

		AtomicInteger nextSongNumberTobePlayed = new AtomicInteger(0);

		setNumberToSong(songList);

		songMenu(songList, songCount, null, fromPrevious, nextSongNumberTobePlayed);

	}

	private void songMenu(List<Song> songList, AtomicInteger songCount, Song songTobePlayed, AtomicBoolean fromPrevious,
			AtomicInteger nextSongNumberTobePlayed) {
		System.out.println("Select Options");
		System.out.println("1. Play All Songs");
		System.out.println("2. Next Song");
		System.out.println("3. Previous  Song");

		Scanner sc = new Scanner(System.in);

		int option = sc.nextInt();

		switch (option) {
		case 1:
			playAll(songList, songCount);

			break;

		case 2:
			playNext(songList, songCount, fromPrevious, nextSongNumberTobePlayed);

			break;

		case 3:
			playPrevious(songList, songCount, songTobePlayed, fromPrevious, nextSongNumberTobePlayed);

			break;

		case 4:
			System.exit(0);

		default:
			break;
		}
	}

	private void playPrevious(List<Song> songList, AtomicInteger songCount, Song songTobePlayed, AtomicBoolean fromPrevious,
			AtomicInteger nextSongNumberTobePlayed) {
		try {
			Song song = songList.stream().filter(s -> s.getSongNumber().equals(songTobePlayed.getPrevoiusSongNumber()))
					.findAny().orElse(null);
			fromPrevious.set(true);
			nextSongNumberTobePlayed.set(songTobePlayed.getSongNumber());

			playSong(song);
			
			songMenu(songList, songCount, songTobePlayed, fromPrevious, nextSongNumberTobePlayed);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void playNext(List<Song> songList, AtomicInteger songCount, AtomicBoolean fromPrevious,
			AtomicInteger nextSongNumberTobePlayed) {

		int songNumber = generateRandomNumberFromList(songList);

		if (songNumber != 0) {

			Integer songNo = songNumber;
			
			

			if (!songList.isEmpty()) {

				Song previousSong = songList.stream().filter(s -> s.isPlayed()).findAny().orElse(null);

				Song songTobePlayed = null;
				if (!fromPrevious.get()) {
					songTobePlayed = songList.stream().filter(s -> s.getSongNumber().equals(songNo)).findAny().get();
				} else {
					fromPrevious.set(false);
					songTobePlayed = songList.stream().filter(s -> s.getSongNumber().equals(nextSongNumberTobePlayed.get()))
							.findAny().get();
				}
				playSong(songTobePlayed);

				if (previousSong != null) {
					songList.stream().filter(s -> s.getSongNumber().equals(previousSong.getSongNumber())).findAny()
							.get().setPlayed(false);
					songTobePlayed.setPrevoiusSongNumber(previousSong.getSongNumber());
					previousSong.setNextSongNumber(songTobePlayed.getSongNumber());

				}

				songMenu(songList, songCount, songTobePlayed, fromPrevious, nextSongNumberTobePlayed);

			}
		}

	}

	private void playAll(List<Song> songList, AtomicInteger songCount) {
		// assigning number to each song
		setNumberToSong(songList);

		int songNumber = generateRandomNumberFromList(songList);

		// 100ms = 1secs

		if (songNumber != 0) {
			System.err.println("Song Number" + songCount.incrementAndGet());
			playSongAccordingToNumber(songList, songNumber, songCount);
		} else {

			System.out.println("All songs are played");

			System.err.println("*******-------------**********");

		}
	}

	private void setNumberToSong(List<Song> songList) {

		AtomicInteger count = new AtomicInteger(0);
		for (Song song : songList) {
			song.setSongNumber(count.incrementAndGet());
		}

	}

	private int generateRandomNumberFromList(List<Song> songList) {
		List<Song> sortedList = new ArrayList<>();
		if (!songList.isEmpty()) {
			sortedList = songList.stream().filter(s -> !s.isPlayed()).sorted(Comparator.comparing(Song::getSongNumber))
					.collect(Collectors.toList());

			int min = sortedList.get(0).getSongNumber();
			int max = sortedList.get(sortedList.size() - 1).getSongNumber();

			return getRandomNumber(min, max);
		} else {
			return 0;
		}

	}

	private int getRandomNumber(int min, int max) {

		int diff = max - min;
		Random rn = new Random();
		int i = rn.nextInt(diff + 1);
		i += min;

		return i;
	}

	private void playSongAccordingToNumber(List<Song> songList, int songNumber, AtomicInteger songCount) {

		Integer songNo = songNumber;

		if (!songList.isEmpty()) {
			Song songTobePlayed = songList.stream().filter(s -> s.getSongNumber().equals(songNo)).findAny().get();
			playSong(songTobePlayed);
			songList.remove(songTobePlayed);
			playAll(songList, songCount);

		}

	}

	private void playSong(Song songTobePlayed) {

		try {

			System.err.println("**************");
			System.out.println(songTobePlayed.getSongName() + " Starts");
			System.err.println("**************");

			songTobePlayed.setPlayed(true);

			int timerforEachSong = songTobePlayed.getDurationInseconds();

			System.out.println("song will be played for " + songTobePlayed.getDurationInseconds() + " seconds");
			for (int i = 0; i < songTobePlayed.getDurationInseconds(); i++) {
				Thread.sleep(1000);

				System.err.println("remaining " + timerforEachSong-- + " seconds");

			}
			Thread.sleep(1000);
			System.out.println(songTobePlayed.getSongName() + " ends");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
