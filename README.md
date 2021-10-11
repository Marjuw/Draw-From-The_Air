# Draw-From-The_Air
This Repository is about an App, where you can paint from the air using the camera of a smart device. The fingertip of a human index finger is tracked in three-dimensional space and its movement is shown as a line drawn there in a 2D view. Here is an Example of the result:

![WhatsApp-Video-2021-08-10-at-12 56 24_1_](https://user-images.githubusercontent.com/33716855/136687730-5e57ec97-a87a-444b-bbc0-95f9b116f721.gif)


# Use of technologies
The hand tracking for this project was used the Hand tracking solution from Mediapipe (https://google.github.io/mediapipe/) in Android Studio. The line from this tracked coordinates was created with With OpenGL ES (https://developer.android.com/guide/topics/graphics/opengl).

# How to Install 
Mediapipe must be installed for this project. There are several ways to install it in Android Studio. On the one hand, Mediapipe must be installed for the respective operating system. Please note that the installation is not possible under native Windows (see: https: //google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu). Alternatively, all Windows users can install via a virtual machine with a Linux operating system.To do this, the installation guide (https://google.github.io/mediapipe/getting_started/install.html#installing-on-debian-and-ubuntu) and possibly the associated troubleshooting (https://google.github.io/mediapipe/getting_started/troubleshooting.html) for the Mediapipe installation under Debian or Ubuntu has to be done. The Mediapipe project folder is already included in this repository. There you will also find the additionally created OpenGL classes, which is why a download of the original Mediapipe folder is replaced by this. Furthermore, Mediapipe must be integrated into Android Studio, so that in this case the hand tracking application can be executed. This is done under the guidance of Mediapipe: https://google.github.io/mediapipe/getting_started/android.html, or under ’Using MediaPipe withBazel’: https://gitee.com/chenpingv587/mediapipe/blob/master/mediapipe/docs/install.md. 

# Structure and how to Use the App 
If you have installed Mediapipe and open this folder in Android Studio there are a couple files wich are different from the original Mediapipe folder. To demonstrate, I have marked them in the following folder structure: 
![WhatsApp Image 2021-10-11 at 17 35 32](https://user-images.githubusercontent.com/33716855/136821724-90cc9b52-8869-4f60-a734-80f7d248f076.jpeg)

The tracked 3D coordinates from Mediapipe are transferred to OpenGL ES in real time. In order to display the drawing view of OpenGL in the open camera from Mediapipe, a GLView was created in the layout of the activity Main XML file.

 -Mediapipe und Open GL Beziehung erklären. 
 
 
 Allgemeiner Stand:
-malen nur auf einer Hand gleichzeitig möglich, sonst ist Kalibrierung zu Index 8 überfordert (Mediapipe unterschiedet nicht zwischen Index der linken und rechten Hand)
-Instant malen ohne absetzen aktuell nur (evtl zukünftig nur dann, wenn nur der Zeigefinger ausgestreckt wird)
-draw reset durch Button
-Malen noch schwierig, da:
1. sehr dünne Linie
2. Malen ohne absetzen 
3. Finger in der Luft bewegen (meistens kleine bewegungen schon einfluss) 
4. Kamera verfolgt auf y an rändern ungenauer (bzw. nicht gleichmäßig)
5. Evtl Live Werte von mediapipe in angegebener Frequenz nicht immer ganz optimiert 
6. Wenn Mediapipe andere Objekte wie z.b das Gesicht ständig analysiert und ausversehen kurz als Hand sieht
=> Am besten das Smartphone abstellen und nur die Hand in die Kamera halten, während der Index-Finger ausgestreckt ist
7. Beide Hände funktionieren nicht, da der Index der Fingerspitze daurhaft springt und nicht zwischen rechter und linker Hand unterschiedet
-linie Dicker ? (Width max =1.0, ansonsten muss man mit eigenen Rechtecken Arbeiten und dort die größe manuell eingeben)
-malen in 3D (OGL stellt 3D nicht dar?): 
Aktuell ohne virtuelle Kamera spielt die Z Koordinate keine Rolle (ausprobiert mit Linien, die ihre Z Koordinate ändern. Die Linien verändert sich nicht). Wenn die virtuelle GL Kamera aktiviert wäre und z funktioniert, müsste diese sich dann wahrscheinlich in der Z Achse mit den Z Kooridnate des Fingers mitbewegen, damit die Linie überhaupt zu sehen wäre. Dann wäre allerdings nur eine Linie zu sehen wo sie aktuell gemalt wird und alles was in der z Achse davor liegt. Sobald der Finger wieder näher kommt würden alle dahinterliegenden Zeichnungen nicht dargestellt werden, also nicht mehr im virtuellen Raum zu sehen...
Also müsste die virtuelle Kamera von der anderen Seite (die Seite wie auch die echte Kamera ist) betrachten. Nur würden dann die Zeichnungen spiegelverkerhrt dargestellt werden. Hierfür müssten die Zeichnungen spiegelverkehrt berechnet werden, damit der Nutzer der App diese wiederrum richtig sieht. 
-Bei gesetzter Kamera ist die Z Achse bei getesteten Linien dann die nähe oder entfernung der Linie. Die Linie könnte dementsprechend größer (länger)  oder kleiner (kürzer) erschienen, da sie weiter weg oder näher an der Kamera dran ist. 
-Dieses Phänomen zeigt sich auch bei der Live Übertragung wieder (vgl. mit z und ohne Z Koordinaten). Allerdings schwer zu erkennen, da zusätzlich Kamera Position nicht richtig.

#  What did I learn?

#  Vision:
-Zwar werden 3D Koordinaten verarbeitet, jedoch müsste für eine entsprechende dreidimensionale Darstellung in einem AR Kontext vermutlich die Kameraposition von OpenGL ES angepasst werden.
-Some of the challenges you faced and features you hope to implement in the future.
