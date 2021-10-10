# Draw-From-The_Air
This Repository is about an App, where you can paint from the air using the camera of a smart device. The fingertip of a human index finger is tracked in three-dimensional space and its movement is shown as a line drawn there in a 2D view. Here is an Example of the result:

![WhatsApp-Video-2021-08-10-at-12 56 24_1_](https://user-images.githubusercontent.com/33716855/136687730-5e57ec97-a87a-444b-bbc0-95f9b116f721.gif)


# Use of technologies
The hand tracking for this project was used the Hand tracking solution from Mediapipe (https://google.github.io/mediapipe/) in Android Studio. The line from this tracked coordinates was created with With OpenGL ES. (https://developer.android.com/guide/topics/graphics/opengl). The tracked 3D coordinates from Mediapipe are transferred to OpenGL ES in real time.
-Mediapipe und Open GL Beziehung erklären. 


# How to Install 
Mediapipe must be installed for this project. There are several ways to install it in Android Studio. On the one hand, Mediapipe must be installed for the respective operating system. Please note that the installation is not possible under native Windows (see: https: //google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu). Alternatively, all Windows users can install via a virtual machine with a Linux operating system.To do this, the installation guide (https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu) and possibly the associated troubleshooting (https://google.github.io/) mediapipe / getting_started / troubleshooting.html) for the Mediapipe installation under Debian or Ubuntu has to be done. The Mediapipe project folder is already included in this repository. There you will also find the additionally created OGL classes, which is why a download of the original Mediapipe folder is replaced by this. Furthermore, Mediapipe must be integrated into Android Studio, so that in this case the hand tracking application can be executed. This is done under the guidance of Mediapipe: https://google.github.io/mediapipe/getting_started/android.html, or under ’Using MediaPipe withBazel’: https://gitee.com/chenpingv587/mediapipe/blob/master/mediapipe/docs/install.md. 

# How to Use the App 
 Die jeweiligen Klassen von OpenGl heißen  

#  What did I learn?

#  Vision:
-Zwar werden 3D Koordinaten verarbeitet, jedoch müsste für eine entsprechende dreidimensionale Darstellung in einem AR Kontext vermutlich die Kameraposition von OpenGL ES angepasst werden.
-Some of the challenges you faced and features you hope to implement in the future.
