package com.musicg.experiment.test;

import com.musicg.wave.Wave;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.fingerprint.FingerprintManager;

public class Test2 {
	public static void main(String[] args) {

		Wave wave1 = new Wave("audio_work/cock_a_koel.wav");
		Wave wave2 = new Wave("audio_work/koel.wav");

		FingerprintSimilarity fingerprintSimilarity = wave1
				.getFingerprintSimilarity(wave2);
		System.out.println("Similarity: " + fingerprintSimilarity.getSimilarity());
		System.out.println("Score: " + fingerprintSimilarity.getScore());
		System.out.println("Most similar frame position: " + fingerprintSimilarity.getMostSimilarFramePosition());
		System.out.println("Most similar time position: " + fingerprintSimilarity.getsetMostSimilarTimePosition());
	}
}
