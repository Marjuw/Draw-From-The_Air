# Draw-From-The_Air
This Repository is about an App, where you can paint from the air using the camera of a smart device. The fingertip of a human index finger is tracked in three-dimensional space and its movement is shown as a line drawn there in a 2D view. Here is an Example of the result:

![WhatsApp-Video-2021-08-10-at-12 56 24_1_](https://user-images.githubusercontent.com/33716855/136687730-5e57ec97-a87a-444b-bbc0-95f9b116f721.gif)


# Use of technologies
The hand tracking for this project was used the Hand tracking solution from Mediapipe (https://google.github.io/mediapipe/) in Android Studio. The line from this tracked coordinates was created with With OpenGL ES. (https://developer.android.com/guide/topics/graphics/opengl). The tracked 3D coordinates from Mediapipe are transferred to OpenGL ES in real time.
-Mediapipe und Open GL Beziehung erklären. 


# How to Install 
Für dieses Projekt muss Mediapipe installiert werden. Die  Installation  von  Mediapipe  in  Android  Studio  erfolgt ̈uber  mehrere  Wege.  Zum einen muss Mediapipe für das jeweilige Betriebsystem installiert werden. Zu beachten ist, dass die Installation von Mediapipe nicht unter  native  Windows  möglich  ist (siehe:https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu). Alternativ kann eine installation über eine virtuelle Mashine mit einem Linux Betriebsystem eerfolgen. Hierzu muss zuerst der Installationsguide (https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu) und evtl das dazugehorige Troubleshooting (https://google.github.io/mediapipe/getting_started/troubleshooting.html) für die Mediapipe Installation unter Debian oder Ubuntu durchgeführt werden. Desweiteren muss Mediapipe in Android Studio integriert werden, sodass in diesem Fall die Hand Tracking Anwendung ausgef ̈uhrt werden kann. Dies erfolgt unter der Anleitung von Mediapipe:https://google.github.io/mediapipe/getting_started/android.html, oder unter ’Using MediaPipe withBazel’:https://gitee.com/chenpingv587/mediapipe/blob/master/mediapipe/docs/install.md.

# How to Use the App 
 Die jeweiligen Klassen von OpenGl heißen  

#  What did I learn?

#  Vision:
-Zwar werden 3D Koordinaten verarbeitet, jedoch müsste für eine entsprechende dreidimensionale Darstellung in einem AR Kontext vermutlich die Kameraposition von OpenGL ES angepasst werden.
-Some of the challenges you faced and features you hope to implement in the future.
