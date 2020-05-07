/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author DuyDL2
 */
public class Main {

    MainFrame frame;
    Music m = new Music();
    AudioStream as = null;
    AudioPlayer ap = AudioPlayer.player;

    public Main() {
        frame = new MainFrame();
        MyTimeCount timeCount = new MyTimeCount();
        timeCount.start();
        new Thread(frame).start();
    }

    public static void main(String[] args) {
        new Main();
    }

    class MyTimeCount extends Thread {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (frame.isPause()) {
                    if (frame.isResume()) {
                        frame.time--;
                    }
                } else {
                    frame.time--;
                }
                if (frame.time == 10) {
                    as = m.warningMusic();
                    ap.start(as);
                }
                if (frame.time == 0) {
                    as = m.loseMusic();
                    ap.start(as);
                    if (frame.showDialogNewGame(
                            "Full time\nDo you want play again?", "Lose", 1) == true) {
                        ap.stop(as);
                    };
                }
            }
        }
    }
}