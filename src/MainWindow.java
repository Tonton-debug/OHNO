
	import org.lwjgl.glfw.*;
	import org.lwjgl.opengl.*;
	import org.lwjgl.system.*;
	import java.nio.*;
	import static org.lwjgl.glfw.Callbacks.*;
	import static org.lwjgl.glfw.GLFW.*;
	import static org.lwjgl.opengl.GL44.*;
	import static org.lwjgl.system.MemoryStack.*;
	import static org.lwjgl.system.MemoryUtil.*;
public class MainWindow {


		// The window handle
		private long window;
		private ShaderProgram _mainShader;
		private Buffer _mainBuffer;
		private VertexBuffer _mainBufferVertex;
		float[] vertices = new float[]{
			     0.0f,  0.5f, -2.05f,
			    -0.5f, -0.5f,-2.05f,
			     0.5f, -0.5f, -2.05f
			};
		private World _mainWorld;
		public void Run() {	
			Init();
			Loop();
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}

		private void Init() {
			
			GLFWErrorCallback.createPrint(System.err).set();
			if ( !glfwInit() )
				throw new IllegalStateException("Unable to initialize GLFW");
			glfwDefaultWindowHints(); 
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
			window = glfwCreateWindow(500, 500, "Hello World!", NULL, NULL);
			if ( window == NULL )
				throw new RuntimeException("Failed to create the GLFW window");
			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose(window, true); 
			});
			try ( MemoryStack stack = stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1); 
				IntBuffer pHeight = stack.mallocInt(1); 
				glfwGetWindowSize(window, pWidth, pHeight);
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			} 
			glfwMakeContextCurrent(window);
			glfwSwapInterval(1);
			glfwShowWindow(window);
			
		}

		private void Loop() {
			
			GL.createCapabilities();
			_mainWorld=World.Instance();
			_mainWorld.CreatePerpective((float) Math.toRadians(60.0f), 0.01f, 1000.0f, 1);
			_mainBuffer=new Buffer(GL_ARRAY_BUFFER);
		 _mainBufferVertex=new VertexBuffer(3);
			_mainShader=new ShaderProgram("standart_shader.fs","standart_shader.vs");
			try {
				_mainShader.CreateUniform("projectionMatrix");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
			
			_mainBuffer.InitFloatData(vertices);
		
			
			glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
			while ( !glfwWindowShouldClose(window) ) {
	
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
				_mainShader.SetUniform("projectionMatrix", _mainWorld.GetPerspective());
				_mainShader.Use();
				_mainBufferVertex.BindBuffer();
				_mainBuffer.BindBuffer();
				_mainBufferVertex.EnableBuffer();
				  glDrawArrays(GL_TRIANGLES, 0, 3);
				
				glfwSwapBuffers(window); 
				glfwPollEvents();
			}
		}

		public static void main(String[] args) {
			new MainWindow().Run();
		}

}
