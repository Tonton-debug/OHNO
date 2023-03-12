import static org.lwjgl.openal.AL10.*;
public class AudioSource  {
private AudioBuffer _audioBuffer;
private boolean _isLoop;
private int _sourceId;
private boolean _isPlay=false;
public AudioSource(String filePath,boolean isLoop) {
	_isLoop=isLoop;
	System.out.print("\nD:"+filePath);
	_audioBuffer=new AudioBuffer(filePath);
	GenSourceId();
}
private void GenSourceId() {
	_sourceId=alGenSources();
	alSourcei(_sourceId,AL_BUFFER,_audioBuffer.GetIdBuffer());
	alSourcei(_sourceId,AL_POSITION,0);
	alSourcei(_sourceId,AL_LOOPING,_isLoop==true?1:0);
	alSourcef(_sourceId,AL_GAIN,1.0f);
	
}
public void Play() {
	alSourcePlay(_sourceId);
}
}

