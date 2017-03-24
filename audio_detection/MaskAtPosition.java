package com.musicg.experiment.testdetection;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;
import com.musicg.wave.WaveFileManager;
import com.musicg.wave.WaveHeader;

public class MaskAtPosition {
	public static void main(String[] args) {
		Wave testClip = new Wave("audio_work/cock_a_koel.wav");
		Wave pattern1 = new Wave("audio_work/koel.wav");
		
		FingerprintSimilarity fingerprintSimilarity = testClip.getFingerprintSimilarity(pattern1);
		double clipTimePosition = fingerprintSimilarity.getsetMostSimilarTimePosition();
		
		saveMaskedSample(testClip, clipTimePosition, pattern1);
	}
	
	public static void saveMaskedSample(Wave testClip, double clipTimePosition, Wave pattern) {
		final WaveHeader waveHeader = testClip.getWaveHeader();
		
		
		int sampleRate = waveHeader.getSampleRate();
		int bitsPerSample = waveHeader.getBitsPerSample();
		int channels = waveHeader.getChannels();
		
		int startSamplePosition = (int) (sampleRate * bitsPerSample / 8 * channels * clipTimePosition);
		
		final WaveHeader sampleHeader = pattern.getWaveHeader();
		long patternSampleSize = sampleHeader.getChunkSize();
		
		byte[] testClipData = testClip.getBytes();
		byte[] maskedClipData = Arrays.copyOf(testClipData, testClipData.length);
		byte[] blankPattern = new byte[(int)patternSampleSize];
		System.arraycopy(blankPattern, 0, maskedClipData, (int)(startSamplePosition + patternSampleSize), 1024);
		InputStream is = new ByteArrayInputStream(maskedClipData);
		Wave finalWave = new Wave(testClip.getWaveHeader(), maskedClipData);
		WaveFileManager waveFileManager = new WaveFileManager(finalWave);
		waveFileManager.saveWaveAsFile("output_audio/cock_a_koel_muted.wav");
		
		
	}
}
