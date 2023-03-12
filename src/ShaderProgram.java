	import static org.lwjgl.opengl.GL44.*;
	import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;
public final class ShaderProgram {
	private int _programId=0;
	private int _vertexShaderId;
	private int _fragmentShaderId;
	private Map<String,Integer> _uniformsMap=new HashMap<>();
public ShaderProgram(String fsShader,String vsShader)  {
	try {
	_programId=glCreateProgram();
	System.out.print(Paths.get(fsShader).toAbsolutePath()+"\n");
	
	_vertexShaderId= CreateShader(new String(Files.readAllBytes(Paths.get(vsShader).toAbsolutePath())),GL_VERTEX_SHADER);
	_fragmentShaderId=CreateShader(new String(Files.readAllBytes(Paths.get(fsShader).toAbsolutePath())),GL_FRAGMENT_SHADER);
	LinkShader();
	}catch(Exception ex ){
	System.out.print("ERR:"+ex.toString());
	}
	
}
public <T> void SetUniform(String uniformName, T value) {
    // Dump the matrix into a float buffer
    try (MemoryStack stack = MemoryStack.stackPush()) {
     
        if(value instanceof Matrix4f) {
        	   FloatBuffer fb;
        	fb= stack.mallocFloat(16);
              ((Matrix4f)value).get(fb);
        	 glUniformMatrix4fv(_uniformsMap.get(uniformName), false, fb);
        }else if(value instanceof Vector4f){
        	Vector4f vector=(Vector4f)value;
        	 glUniform4f(_uniformsMap.get(uniformName),vector.x,vector.y,vector.z,vector.w);
        }
    }
}
public void CreateUniform(String uniformName) throws Exception {
    int uniformLocation = glGetUniformLocation(_programId,uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" +
                uniformName);
        }
        _uniformsMap.put(uniformName, uniformLocation);
}
private int CreateShader(String shaderCode, int shaderType) throws Exception {
    int shaderId = glCreateShader(shaderType);
    if (shaderId == 0) {
    	 throw new Exception("Error creating shader. Type: " + shaderType);
    }

    glShaderSource(shaderId, shaderCode);
    glCompileShader(shaderId);
    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
    	 throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
    }
    glAttachShader(_programId, shaderId);
    return shaderId;
}
private void LinkShader() throws Exception {
    glLinkProgram(_programId);
    if (glGetProgrami(_programId, GL_LINK_STATUS) == 0) {
        throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(_programId, 1024));
    }

    if (_vertexShaderId != 0) {
        glDetachShader(_programId, _vertexShaderId);
    }
    if (_fragmentShaderId != 0) {
        glDetachShader(_programId, _fragmentShaderId);
    }

    glValidateProgram(_programId);
    if (glGetProgrami(_programId, GL_VALIDATE_STATUS) == 0) {
        System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(_programId, 1024));
    }
}

public void Use() {
    glUseProgram(_programId);
}

public void UnUse() {
    glUseProgram(0);
}
}
