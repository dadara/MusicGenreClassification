import java.io.File;
import java.io.IOException;

import net.beadsproject.beads.analysis.featureextractors.SpectralCentroid;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.audiofile.FileFormatException;
import net.beadsproject.beads.data.audiofile.OperationUnsupportedException;
import net.beadsproject.beads.data.audiofile.WavFileReaderWriter;
import net.beadsproject.beads.core.TimeStamp;

public class MainMusicGenre {

	public static void main(String args[]){
		float[][] deathMetal=null;
		WavFileReaderWriter wfwr = new WavFileReaderWriter();
		AudioContext ac;
		ac = new AudioContext();
		
		try {
			deathMetal = wfwr.readAudioFile("C:"+File.separator+"Users"+File.separator+"ramsauerd89cs"+File.separator+"OneDrive"+File.separator+"Dokumente"+File.separator+"Music"+File.separator+"Death-Metal"+File.separator+"deathMetal.wav");
		} catch (IOException | OperationUnsupportedException	| FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float[] channel1 = new float[3634560];
		float[] channel2 = new float[3634560];
		//3634560    data - - upon return, this will point to a 2D array containing all the audio data. The first dimension (data.length) is the number of channels. The second dimension (data[0].length) is the number of frames.
		int cnt = 0;
		for(int i = 0; i<deathMetal[0].length; i++){
			cnt++;
//			if(cnt<90 && cnt>80){
				System.out.println(deathMetal[0][i]);
				System.out.println(deathMetal[1][i]);
//			}		
			channel1[i] = deathMetal[0][i];
			channel2[i] = deathMetal[1][i];
		}
		
		float sampleRate = 44000.0f;
		SpectralCentroid sc = new SpectralCentroid(sampleRate);
		SpectralCentroid sc1 = new SpectralCentroid(sampleRate);

		long timeStep = 1;
		int index = 0;
		TimeStamp tsBegin = new TimeStamp(new AudioContext(), 0);
		TimeStamp tsEnd = new TimeStamp(new AudioContext(), deathMetal[0].length);
		sc.process(tsBegin, tsEnd, channel1);
		sc1.process(tsBegin, tsEnd, channel2);
		
		System.out.println("channel1 SpectralCentroid "+sc.getFeatures());
		System.out.println("channel2 SpectralCentroid "+sc1.getFeatures());
		
		float spectralRollOff = getSpectralRollOff(channel1);
		System.out.println(spectralRollOff);

	}
	
	public static float getSpectralRollOff(float[] frames){
		
		float framesSum = 0;
		
		for(int i = 0; i < frames.length; i++){
			framesSum += frames[i];
		}
		
		framesSum = framesSum/frames.length;
		
		return framesSum;
	}
}
