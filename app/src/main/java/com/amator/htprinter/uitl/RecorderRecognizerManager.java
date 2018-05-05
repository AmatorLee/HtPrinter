package com.amator.htprinter.uitl;

import com.amator.htprinter.base.Constans;

import ai.olami.android.IRecorderSpeechRecognizerListener;
import ai.olami.android.RecorderSpeechRecognizer;
import ai.olami.cloudService.APIConfiguration;

/**
 * Created by AmatorLee on 2018/5/3.
 */
public class RecorderRecognizerManager {


    private volatile static RecorderRecognizerManager sInstance = null;
    private RecorderSpeechRecognizer mRecorderSpeechRecognizer;

    public RecorderRecognizerManager(IRecorderSpeechRecognizerListener listener) {
        APIConfiguration configuration = new APIConfiguration(Constans.ALAMI_APP_ID, Constans.ALAMI_APP_SECRET, APIConfiguration.LOCALIZE_OPTION_SIMPLIFIED_CHINESE);
        mRecorderSpeechRecognizer = RecorderSpeechRecognizer.create(listener, configuration);
        // * Other optional steps: Setup some other configurations.
        //                         You can use default settings without bellow steps.
        mRecorderSpeechRecognizer.setEndUserIdentifier("Someone");
        mRecorderSpeechRecognizer.setApiRequestTimeout(3000);

        // * Advanced setting example.
        //   These are also optional steps, so you can skip these
        //   (or any one of these) to use default setting(s).
        // ------------------------------------------------------------------
        // * You can set the length of end time of the VAD in milliseconds
        //   to stop voice recording automatically.
        mRecorderSpeechRecognizer.setLengthOfVADEnd(2000);
        // * You can set the frequency in milliseconds of the recognition
        //   result query, then the recognizer client will query the result
        //   once every milliseconds you set.
        mRecorderSpeechRecognizer.setResultQueryFrequency(100);
        // * You can set audio length in milliseconds to upload, then
        //   the recognizer client will upload parts of audio once every
        //   milliseconds you set.
        mRecorderSpeechRecognizer.setSpeechUploadLength(300);
        // * Due to the different microphone sensitivity of each different device,
        //   you can set level of silence volume of the VAD
        //   to stop voice recording automatically.
        //   The recommended value is 5 to 10.
        mRecorderSpeechRecognizer.setSilenceLevelOfVADTail(5);
        // -----------------------------------------
    }

    private RecorderSpeechRecognizer.RecordState state;

    public void start() {
        state = mRecorderSpeechRecognizer.getRecordState();
        try {
            if (state == RecorderSpeechRecognizer.RecordState.STOPPED){
                mRecorderSpeechRecognizer.start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        mRecorderSpeechRecognizer.cancel();
    }

    public void release() {
        mRecorderSpeechRecognizer.release();
    }

    public void stop() {
        mRecorderSpeechRecognizer.stop();
    }

    public RecorderSpeechRecognizer.RecordState getRecordState(){
        return mRecorderSpeechRecognizer.getRecordState();
    }

}
