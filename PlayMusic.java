import javax.sound.sampled.AudioInputStream;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;
import java.io.FileInputStream;
public class PlayMusic
{
	PlayMusic()
	{
		AudioInputStream audioInputStream=null;
		SourceDataLine sourceDataLine=null;
		try
		{
			byte[] musicByte=new byte[4];
			audioInputStream=AudioSystem.getAudioInputStream(new File(File.separator+"/Users"+File.separator+"River"+File.separator+"Desktop"+File.separator+"音效.wav"));
			AudioFormat audioFormat=audioInputStream.getFormat();
			sourceDataLine=AudioSystem.getSourceDataLine(audioFormat);
			sourceDataLine.open(audioFormat);//打开指定音频格式的行
			sourceDataLine.start();//允许执行音频数据输出
			while(audioInputStream.read(musicByte)!=-1)
			{
				ByteBuffer.allocateDirect(4);//分配新的直接字节缓冲区,静态方法
				sourceDataLine.write(ByteBuffer.wrap(musicByte).array(),0,ByteBuffer.wrap(musicByte).array().length);//将音频数据写入混频器
			}
			sourceDataLine.drain();
			System.out.println(audioFormat.getChannels());
			System.out.println(audioFormat.getFrameRate());
			System.out.println(audioFormat.getFrameSize());
			System.out.println(audioFormat.getSampleRate());
			System.out.println(audioFormat.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sourceDataLine.close();
			try
			{
				audioInputStream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	PlayMusic(File file)
	{
		FileInputStream fileInputStream=null;
		SourceDataLine sourceDataLine=null;
		try
		{
			byte[] musicByte=new byte[8];
			fileInputStream=new FileInputStream(file);
			AudioFormat audioFormat=new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100,32,2,8,172.265625f,true);
			DataLine.Info dataLineInfo=new DataLine.Info(SourceDataLine.class,audioFormat);
			Port.Info portInfo=new Port.Info(((SourceDataLine)AudioSystem.getLine(dataLineInfo)).getClass(),"output",true);
			sourceDataLine=(SourceDataLine)AudioSystem.getLine(portInfo);
			sourceDataLine.open();
			sourceDataLine.start();
			while(fileInputStream.read(musicByte)!=-1)
			{
				ByteBuffer.allocate(8);
				sourceDataLine.write(ByteBuffer.wrap(musicByte).array(),0,ByteBuffer.wrap(musicByte).array().length);
			}
			sourceDataLine.drain();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sourceDataLine.close();
			try
			{
				fileInputStream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) 
	{
		PlayMusic playMusic=new PlayMusic(new File(File.separator+"Users"+File.separator+"River"+File.separator+"Desktop"+File.separator+"心之焰.aifc"));
		System.out.println(AudioSystem.getMixerInfo());
	}

}
