import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
public class MainWindow {


		// The window handle
		public static long window;
		private World _mainWorld;
		private GameObject _earth;
		private int wave=1;
		private int tick=500;
		private int max_tick=500;
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
			 GLFW.glfwMakeContextCurrent(window);
			
			glfwSwapInterval(1);
			glfwShowWindow(window);
			
		}
		private void CheckKey() {
			if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
				World.Instance().GetPlayer().Move(new Vector3f(0.1f,0,0));
			}
			if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
				World.Instance().GetPlayer().Move(new Vector3f(-0.1f,0,0));
			
			}
			if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
				World.Instance().GetPlayer().Shot();
			
		}
		private void InitMainShader() {
			ShaderProgram _mainShader=new ShaderProgram("standart_shader.fs","standart_shader.vs");
			try {
				_mainShader.CreateUniform("projectionMatrix");
				_mainShader.CreateUniform("worldMatrix");
				_mainShader.CreateUniform("color");
			} catch (Exception e) {
			
				System.out.print(e.toString());
			}
			World.Instance().CreateMainShader(_mainShader);
		}
		private void InitWorld() {
			_mainWorld=World.Instance();
			_mainWorld.CreatePerpective((float) Math.toRadians(90.0f), 0.01f, 1000.0f, 1);		
			
		}
		private void CreateAL() {
			long device = ALC10.alcOpenDevice((ByteBuffer) null);
			    ALCCapabilities deviceCaps = ALC.createCapabilities(device);
			    long context = ALC10.alcCreateContext(device, (IntBuffer) null);
			    ALC10.alcMakeContextCurrent(context);
			    AL.createCapabilities(deviceCaps);
		}
		private void InitEarth() {
			_earth=new PlaneObject(World.Instance().GetMainShader());
			_earth.SetRotate(new Vector3f(-90,0,0));
			_earth.SetPosition(new Vector3f(0,-3,5));
			_earth.SetScale(new Vector3f(10,1000,1));
			_earth.SetColor(new Vector4f(0,0.7f,0,1));
		}
	    private static double getRandom(int min, int max) {
	        if (min >= max) {
	            throw new IllegalArgumentException("Invalid range [" + min + ", " + max + "]");
	        }
	        return min + Math.random() * (max - min);
	    }
		private void CreateWave() {
				System.out.print("CREATE WAVE");
				Random random = new Random();
				for(int count=1;count<5+(wave*2);count++) {

					Soldier soldier=new Soldier(EnemyType.Soldier,(int)getRandom(1,5),((float)getRandom(1,2))/10);
					double min=-4;
					double max=4;
					soldier.GetGameObject().SetPosition(new Vector3f((float)getRandom(-4,4),-1.5f,-200+(int)getRandom(1,15)));
					soldier.GetGameObject().SetColor(new Vector4f(random.nextFloat(),random.nextFloat(),random.nextFloat(),1));
					World.Instance().AddEnemy(soldier);
				}
				wave+=1;
				tick=max_tick;
				if(max_tick>100)
					max_tick-=10;
				System.out.print("End");
		}
		private void Loop() {
			 GL.createCapabilities();
			CreateAL();
			InitWorld();
			InitMainShader();
			InitEarth();
			CreateWave();
			_mainWorld.CreateNewPlayer();
			glClearColor(0.3f, 0.3f, 1.0f, 0.0f);
			glEnable(GL_DEPTH_TEST);
			glDepthFunc(GL_LESS); 

			while ( !glfwWindowShouldClose(window) ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
				_earth.Draw();
				
				World.Instance().GetPlayer().Draw();
				World.Instance().UpdateWorld();
				glfwSwapBuffers(window); 
				glfwPollEvents();
				CheckKey();
				tick-=1;
				if(tick<=0)
					CreateWave();
				
			}
		}

		public static void main(String[] args) {
			new MainWindow().Run();
		}

}
