// Copyright 2019 The MediaPipe Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.mediapipe.apps.handtrackinggpu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.mediapipe.apps.basic.OpenGLView;
import com.google.mediapipe.apps.basic.ParticleRenderer;
import com.google.mediapipe.apps.basic.R;
import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.AndroidPacketCreator;
import com.google.mediapipe.framework.Packet;
import com.google.mediapipe.framework.PacketGetter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/** Main activity of MediaPipe hand tracking app. */
public class MainActivity extends com.google.mediapipe.apps.basic.MainActivity implements View.OnClickListener {

  private static final String TAG = "MainActivity";

  private static final String INPUT_NUM_HANDS_SIDE_PACKET_NAME = "num_hands";
  private static final String OUTPUT_LANDMARKS_STREAM_NAME = "hand_landmarks";
  // Max number of hands to detect/process.
  private static final int NUM_HANDS = 1;

  boolean first_time_draw=true;
  boolean another_time_draw_1=false;
  boolean another_time_draw_2=false;

  int durchgelaufe=0;
  int draw_freuquenz=40; //draw_frequent will be use later to send coordinates to OGL for drawing only every 40th run.
            //Attention, if the frequency is too low, ticked lines appear, because the ogl is overwhelmed.
            // In addition, a frequency that is too low (e.g. 10) ensures that nothing is drawn.
            // And if frequency is too high, the drawing comes to late. Best to specify between 40-50)


  OpenGLView ogl;
  ImageView refresh;

  @Override
  protected void onCreate(Bundle savedInstanceState) {


    super.onCreate(savedInstanceState);

    refresh = findViewById(R.id.refresh_button);
    refresh.setOnClickListener(this); //So that the onClick method is executed when you click the Refresh button (see below)

    ogl = findViewById(R.id.gl_view); //To adress the GLView in the activity_main Layout



    AndroidPacketCreator packetCreator = processor.getPacketCreator();
    Map<String, Packet> inputSidePackets = new HashMap<>();
    inputSidePackets.put(INPUT_NUM_HANDS_SIDE_PACKET_NAME, packetCreator.createInt32(NUM_HANDS));
    processor.setInputSidePackets(inputSidePackets);



    // To show verbose logging, run:
    // adb shell setprop log.tag.MainActivity VERBOSE

    //  if (Log.isLoggable(TAG, Log.VERBOSE)) {  //outcommented, to follow/track the live coordinates over the loccat.

      processor.addPacketCallback(
          OUTPUT_LANDMARKS_STREAM_NAME,
          (packet) -> {
            Log.v(TAG, "Received multi-hand landmarks packet.");
            List<NormalizedLandmarkList> multiHandLandmarks =
                PacketGetter.getProtoVector(packet, NormalizedLandmarkList.parser());
            Log.v(
                TAG,
                "[TS:"
                    + packet.getTimestamp()
                    + "] "
                    + getMultiHandLandmarksDebugString(multiHandLandmarks));


          });
    }
  //}

  private String getMultiHandLandmarksDebugString(List<NormalizedLandmarkList> multiHandLandmarks) {


    if (multiHandLandmarks.isEmpty()) {
      return "No hand landmarks";
    }
    String multiHandLandmarksStr = "Number of hands detected: " + multiHandLandmarks.size() + "\n";
    int handIndex = 0;
    for (NormalizedLandmarkList landmarks : multiHandLandmarks) {
      multiHandLandmarksStr +=
           "\t#Hand landmarks for hand[" + handIndex + "]: " + landmarks.getLandmarkCount() + "\n";
      int landmarkIndex = 8; //original to 0, but now to 8 to track only the Fingertip (INDEX_FINGER_TIP)
      for (NormalizedLandmark landmark : landmarks.getLandmarkList()) {
        multiHandLandmarksStr = //Original is "=+". "=" only, if one Fingertip should be tracked.
                // Otherwise all coordinates from all Landmark Points/Indexes will be send.
                "\t\tLandmark ["
                        + landmarkIndex
                        + "]: ("
                        + landmark.getX()
                        + ", "
                        + landmark.getY()
                        + ", "
                        + landmark.getZ()
                        + ")\n";

            //Here the live coordinates are transmitted individually to OpenGL and a further line is drawn for each coordinate

            if (durchgelaufe == 0 && first_time_draw == true) { //If it will drawn the first time


                firstfingerdraw((((landmarks.getLandmark(8).getX() * 2) - 1) * -1), (((landmarks.getLandmark(8).getY() * 2) - 1) * -1), ((landmarks.getLandmark(8).getZ() * 2) + 1) * -1);
                //The X,Y und Z Coordinates will be send here from Mediapipe to OGL. To do that the different Coordinate System will be mathematically adjusted.


                first_time_draw = false;  //to draw only at the first coordinate (see Ogl drawfirsttime method comment)
                another_time_draw_1 = true;


            }

            if (durchgelaufe == draw_freuquenz && another_time_draw_1 == true) { //when the second and more times are drawn.
                // draw_frequency for comparison to ensure that coordinates are only sent to OGL every 40th iteration of this method.
                anotherfingerdraw((((landmarks.getLandmark(8).getX() * 2) - 1) * -1), (((landmarks.getLandmark(8).getY() * 2) - 1) * -1), ((landmarks.getLandmark(8).getZ() * 2) + 1) * -1);

                durchgelaufe = 0; //so that coordinates are sent to OGL every 40th run of this method and not just once.

            }


            durchgelaufe++;


       // ++landmarkIndex;  //commented out because only the fingertip of the index finger, i.e. the index 8, should be output.

      }
       //++handIndex; //outcommented, because only one hand can be used for the drawing
    }


    return multiHandLandmarksStr;
  }

  //Methods for transferring the coordinates to OGL for drawing a line in a respective color.

  public void firstfingerdraw (float x, float y, float z){

    ogl.pgl.drawfirsttime(x, y, z,.8f,0f, 0f); //in red (but cant be shwon, because of the first point)

  }
  public void anotherfingerdraw (float x, float y, float z){

    ogl.pgl.drawanothertime(x, y, z,.8f,0f, 0f); //in red

  }

  @Override
  public void onClick(View v) { //If the reset button will be clicked

     ogl.pgl.reset=1;  //delete the drawn (see OnDraw Methode from ogl.pgl)

  }

}
