# Draw-from-the_air
This repository is about an app, where you can paint from the air using the camera of a smart device. The fingertip of a human index finger is tracked in three-dimensional space and its movement is shown as a line drawn there in a 2D view. Here is an example of the result:

![WhatsApp-Video-2021-08-10-at-12 56 24_1_](https://user-images.githubusercontent.com/33716855/136687730-5e57ec97-a87a-444b-bbc0-95f9b116f721.gif)


# Use of technologies
The hand tracking for this project was used the Hand tracking solution from Mediapipe (https://google.github.io/mediapipe/) in Android Studio. The line from this tracked coordinates was created with With OpenGL ES (https://developer.android.com/guide/topics/graphics/opengl).

# How to install 
Mediapipe must be installed for this project. There are several ways to install it in Android Studio. On the one hand, Mediapipe must be installed for the respective operating system. Please note that the installation is not possible under native Windows (see: https: //google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu). Alternatively, all Windows users can install via a virtual machine with a Linux operating system. To do this, the installation guide (https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu) and possibly the associated troubleshooting (https://google.github.io/mediapipe/getting_started/troubleshooting.html) for the Mediapipe installation under Debian or Ubuntu has to be done. The Mediapipe project is not included in this repository. Furthermore, Mediapipe must be integrated into Android Studio, so that in this case the hand tracking application can be executed. This is done under the guidance of Mediapipe: https://google.github.io/mediapipe/getting_started/android.html, or under ’Using MediaPipe withBazel’: https://gitee.com/chenpingv587/mediapipe/blob/master/mediapipe/docs/install.md. After this is done, you have to change some original files from Mediapipe with those who are named in the next Step 'Structure'. 

# Structure  
If you have installed Mediapipe and open this folder there are a couple files wich are different from the original Mediapipe folder. To demonstrate, I have marked them in the following folder structure: 

![Screen_Ordnerstruktur](https://user-images.githubusercontent.com/33716855/140933264-28b1fd82-c8fe-4bbf-861c-b69e929c67ad.jpg)

![Screen_Ordnerstruktur_2](https://user-images.githubusercontent.com/33716855/136833982-86e4dad6-925e-4d5a-a1d0-7f717b6f9160.jpg)


You can find this marked files in this Repo folder called 'Changed_and_added_files'. You have to replace and add them to the original Mediapipe folder.

The OpenGlView and the ParticleRenderer file is about to draw the line with OpenGL ES. They was added and had to split because in order to display the drawing view of OpenGL in the open camera from Mediapipe, a GLView was created in the layout of the activity Main XML file:

![Screen_XML](https://user-images.githubusercontent.com/33716855/140933689-89eaefe8-c1af-4e1e-b4ea-faeda4bdbd76.jpeg)

An GLSurfaceView expects an default constructor, which the original construction of the OpenGL ES Activity Class does not have. Only the inner class 'MyGLSurfaceView' has the required constructor. Because of that this part was outsourced.  

The tracked 3D coordinates from Mediapipe are transferred to OpenGL ES in real time. The processing and sending of the live coordinates happens in the MainActivity file of the handtrackinggpu folder. In the Particlerenderer class the coordinates are received and processed to the corresponding drawing line. 

 
# How to use the App 
If you run the app like in the Mediapipe installation guide for android Studio described, you have to note the following: 
-Currently the drawing is only possible to one Hand at the same time. If you take two hands in the camera the calibration of the fingerpoints will be 
overstrained because Mediapipe dont make a dirfference between the index from the left or the right hand.
-The drawing could be difficult sometimes. This are the reasons: 
1. The line is very slim. The width of the glLine in OpenGL ES is at maximum (see at the ParticleRender Class).
2. The current status of this project is that you draw instant and without dropping. There is only a refresh button added, where you can delete the drawed line if you push it (see the green button on the first gif).
3. The drawing with the finger in the air could takes getting used to.
5. The drawing is addicted to the correct Hand tracking of mediapipe.
6. The sorroundings of the camera view is important. For example if you show your face, mediapipe will analyse ist und sometimes tracked as hand.
You get a good result when you lie down your smart device and onyl show one Hand in the camera, during you stretch the index Finger.

#  3D drawing:
Although 3D coordinates are processed, The drawing is currently viewed from above. The virtual camera position of OpenGL ES would probably have to be adapted for a corresponding three-dimensional display. Another possibility to represent 3D is the addition of augmented reality.

# Rights: 
This project is considered open source and can therefore be freely expanded or be used for inspiration.
