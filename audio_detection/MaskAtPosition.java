package com.musicg.experiment.testdetection;

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
		
		
		// int sampleRate = waveHeader.getSampleRate();
		int sampleRate = 44100;
		int bitsPerSample = waveHeader.getBitsPerSample();
		int channels = waveHeader.getChannels();
		
		int startSamplePosition = (int) (sampleRate * bitsPerSample / 8 * channels * clipTimePosition);
		
		final WaveHeader sampleHeader = pattern.getWaveHeader();
		// long patternSampleSize = sampleHeader.getSubChunk2Size();
		long patternSampleSize = (sampleHeader.getChunkSize() + 44 ) * 2;  // * 2 = 16 bit
		
		byte[] testClipData = testClip.getBytes();
		byte[] maskedClipData = Arrays.copyOf(testClipData, testClipData.length);
		byte[] blankPattern = new byte[(int)patternSampleSize];
		System.arraycopy(blankPattern, 0, maskedClipData, (int)(startSamplePosition), blankPattern.length);
		System.arraycopy(blankPattern, 0, maskedClipData, (int)(startSamplePosition + blankPattern.length), blankPattern.length);
		// InputStream is = new ByteArrayInputStream(maskedClipData);
		
		WaveHeader finalWaveHeader = testClip.getWaveHeader();
		finalWaveHeader.setSampleRate(44100);
		finalWaveHeader.setBitsPerSample(bitsPerSample);
		
		
		Wave finalWave = new Wave(finalWaveHeader, maskedClipData);
		WaveFileManager waveFileManager = new WaveFileManager(finalWave);
		waveFileManager.saveWaveAsFile("output_audio/cock_a_koel_muted.wav");
	}
}
