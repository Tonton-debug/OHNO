
import java.io.File;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Paths;

import static org.lwjgl.openal.AL11.*;
import org.lwjgl.PointerBuffer;
import org.lwjgl.openal.*;
import org.lwjgl.util.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;
public class AudioBuffer extends Buffer {
	private String _filePath;

	public AudioBuffer(String file) {
		super();
		_filePath=file;
		System.out.print("\nEXIT:"+new File(_filePath).canWrite());
		InitAudioData();
	}
	@Override
	protected int GenBuffer() {return alGenBuffers();}
	public void InitAudioData() {
		IntBuffer channelBuffer=MemoryUtil.memAllocInt(1);
		IntBuffer sampleRateBuffer=MemoryUtil.memAllocInt(1);
		ShortBuffer buffer=STBVorbis.stb_vorbis_decode_filename(_filePath, channelBuffer, sampleRateBuffer);
		int channels=channelBuffer.get();
		int samples=sampleRateBuffer.get();
	    alBufferData(GetIdBuffer(), AL_FORMAT_STEREO16,buffer, samples);
	}
}
