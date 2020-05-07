/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author DuyDL2
 */
public class Music {

    public AudioStream startMusic() {

        AudioStream BGM = null;
        AudioData MD;

        try {
            InputStream test = new FileInputStream("D:\\musicpokemon.wav");
            BGM = new AudioStream(test);

            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }

    public AudioStream winningMusic() {
        AudioStream BGM = null;
        AudioData MD;

        try {
            InputStream test = new FileInputStream("D:\\winning.wav");
            BGM = new AudioStream(test);

            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }

    public AudioStream loseMusic() {
        AudioStream BGM = null;
        AudioData MD;

        try {
            InputStream test = new FileInputStream("D:\\lose.wav");
            BGM = new AudioStream(test);

            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }

    public AudioStream warningMusic() {
        AudioStream BGM = null;
        AudioData MD;

        try {
            InputStream test = new FileInputStream("D:\\warning.wav");
            BGM = new AudioStream(test);

            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
}
