/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class Encoding {
    @JsonbProperty("SystemPreset")    
    private String systemPreset;
    @JsonbProperty("IgnoreCea708ClosedCaptions")    
    private boolean ignoreCea708ClosedCaptions;
    @JsonbProperty("AdMarkerSource")    
    private String adMarkerSource;
    @JsonbProperty("VideoStream")    
    private VideoStream videoStream;
    @JsonbProperty("AudioStreams")    
    private AudioStream[] audioStreams;

    public String getSystemPreset() { return systemPreset; }
    public void setSystemPreset(String value) { this.systemPreset = value; }

    public boolean getIgnoreCea708ClosedCaptions() { return ignoreCea708ClosedCaptions; }
    public void setIgnoreCea708ClosedCaptions(boolean value) { this.ignoreCea708ClosedCaptions = value; }

    public String getAdMarkerSource() { return adMarkerSource; }
    public void setAdMarkerSource(String value) { this.adMarkerSource = value; }

    public VideoStream getVideoStream() { return videoStream; }
    public void setVideoStream(VideoStream value) { this.videoStream = value; }

    public AudioStream[] getAudioStreams() { return audioStreams; }
    public void setAudioStreams(AudioStream[] value) { this.audioStreams = value; }
}
