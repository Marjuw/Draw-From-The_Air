# Draw-From-The_Air
This Repository is about an App, where you can paint from the air using the camera of a smart device. The fingertip of a human index finger is tracked in three-dimensional space and its movement is shown as a line drawn there in a 2D view. Here is an Example of the result:

![WhatsApp-Video-2021-08-10-at-12 56 24_1_](https://user-images.githubusercontent.com/33716855/136687730-5e57ec97-a87a-444b-bbc0-95f9b116f721.gif)


# Use of technologies
The hand tracking for this project was used the Hand tracking solution from Mediapipe (https://google.github.io/mediapipe/) in Android Studio. The line from this tracked coordinates was created with With OpenGL ES (https://developer.android.com/guide/topics/graphics/opengl).

# How to Install 
Mediapipe must be installed for this project. There are several ways to install it in Android Studio. On the one hand, Mediapipe must be installed for the respective operating system. Please note that the installation is not possible under native Windows (see: https: //google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu). Alternatively, all Windows users can install via a virtual machine with a Linux operating system.To do this, the installation guide (https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu) and possibly the associated troubleshooting (https://google.github.io/mediapipe/getting_started/troubleshooting.html) for the Mediapipe installation under Debian or Ubuntu has to be done. The Mediapipe project folder is already included in this repository. There you will also find the additionally created OpenGL classes, which is why a download of the original Mediapipe folder is replaced by this. Furthermore, Mediapipe must be integrated into Android Studio, so that in this case the hand tracking application can be executed. This is done under the guidance of Mediapipe: https://google.github.io/mediapipe/getting_started/android.html, or under ’Using MediaPipe withBazel’: https://gitee.com/chenpingv587/mediapipe/blob/master/mediapipe/docs/install.md. 

# Structure  
If you have installed Mediapipe and open this folder in Android Studio there are a couple files wich are different from the original Mediapipe folder. To demonstrate, I have marked them in the following folder structure: 

![Screen_Ordnerstruktur](https://user-images.githubusercontent.com/33716855/136826917-05ae551c-7c39-46c7-aa81-42aeffe3ff17.jpg)

![Screen_Ordnerstruktur_2](https://user-images.githubusercontent.com/33716855/136833982-86e4dad6-925e-4d5a-a1d0-7f717b6f9160.jpg)


The OpenGlView and the ParticleRenderer file is about to draw the line with OpenGL ES. They had to split because in order to display the drawing view of OpenGL in the open camera from Mediapipe, a GLView was created in the layout of the activity Main XML file (ausführlicher erklären): 

![Screen_XML](https://user-images.githubusercontent.com/33716855/136933920-66498d6c-cd8d-4fab-bd1a-ba6e57204db5.jpeg)

The tracked 3D coordinates from Mediapipe are transferred to OpenGL ES in real time. The processing and sending of the live coordinates happens in the MainActivity file of the handtrackinggpu folder. In the Particlerenderer class the coordinates are received and processed to the corresponding drawing line.

 
# How to Use the App 
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

#  What did I learn?

#  Vision:
-Zwar werden 3D Koordinaten verarbeitet, jedoch müsste für eine entsprechende dreidimensionale Darstellung in einem AR Kontext vermutlich die Kameraposition von OpenGL ES angepasst werden.
-Some of the challenges you faced and features you hope to implement in the future.

Aktuell ohne virtuelle Kamera spielt die Z Koordinate keine Rolle (ausprobiert mit Linien, die ihre Z Koordinate ändern. Die Linien verändert sich nicht). Wenn die virtuelle GL Kamera aktiviert wäre und z funktioniert, müsste diese sich dann wahrscheinlich in der Z Achse mit den Z Kooridnate des Fingers mitbewegen, damit die Linie überhaupt zu sehen wäre. Dann wäre allerdings nur eine Linie zu sehen wo sie aktuell gemalt wird und alles was in der z Achse davor liegt. Sobald der Finger wieder näher kommt würden alle dahinterliegenden Zeichnungen nicht dargestellt werden, also nicht mehr im virtuellen Raum zu sehen...
Also müsste die virtuelle Kamera von der anderen Seite (die Seite wie auch die echte Kamera ist) betrachten. Nur würden dann die Zeichnungen spiegelverkerhrt dargestellt werden. Hierfür müssten die Zeichnungen spiegelverkehrt berechnet werden, damit der Nutzer der App diese wiederrum richtig sieht. 
-Bei gesetzter Kamera ist die Z Achse bei getesteten Linien dann die nähe oder entfernung der Linie. Die Linie könnte dementsprechend größer (länger)  oder kleiner (kürzer) erschienen, da sie weiter weg oder näher an der Kamera dran ist. 
-Dieses Phänomen zeigt sich auch bei der Live Übertragung wieder (vgl. mit z und ohne Z Koordinaten). Allerdings schwer zu erkennen, da zusätzlich Kamera Position nicht richtig.
