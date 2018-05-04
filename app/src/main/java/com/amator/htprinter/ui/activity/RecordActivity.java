package com.amator.htprinter.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.db.VoiceHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.uitl.DialogUtil;
import com.amator.htprinter.uitl.RecorderRecognizerManager;
import com.amator.htprinter.uitl.ViewUtil;
import com.skyfishjy.library.RippleBackground;

import ai.olami.android.IRecorderSpeechRecognizerListener;
import ai.olami.android.RecorderSpeechRecognizer;
import ai.olami.cloudService.APIResponse;
import ai.olami.cloudService.SpeechResult;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AmatorLee on 2018/5/3.
 */
public class RecordActivity extends BaseActivity<BasePresenterImpl> {

    @BindView(R.id.btn_record_state)
    Button mBtnState;
    @BindView(R.id.txt_voice_result)
    TextView mTxtResult;
    @BindView(R.id.img_voice)
    ImageView mImgVoice;
    @BindView(R.id.ripple_voice_container)
    RippleBackground mRippleBackground;
    @BindView(R.id.txt_change_state)
    TextView mTxtChangeState;
    @BindView(R.id.txt_record_state)
    TextView mTxtRecordState;
    @BindView(R.id.btn__cancel_record)
    Button mBtnCancel;
    @BindView(R.id.toolbar_voice_input)
    Toolbar mToolbar;
    @BindView(R.id.btn_sure_voice)
    Button mBtnSure;
    private RecorderRecognizerManager mRecorderRecognizerManager;

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_record_state, R.id.btn__cancel_record, R.id.btn_sure_voice})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_record_state) {
            mRecordState = mRecorderRecognizerManager.getRecordState();
            if (mRecordState == RecorderSpeechRecognizer.RecordState.STOPPED) {
                mRecorderRecognizerManager.onstart();
                startAnimation();
                mBtnState.setEnabled(false);
            } else if (mRecordState == RecorderSpeechRecognizer.RecordState.RECORDING) {
                mRecorderRecognizerManager.stop();
                stopAnimation();
            }
        } else if (id == R.id.btn__cancel_record) {
            mRecorderRecognizerManager.cancel();
            stopAnimation();
        } else if (id == R.id.btn_sure_voice) {
            if (android.text.TextUtils.isEmpty(mTxtResult.getText().toString())) {
                showToast(ViewUtil.getString(R.string.txt_result_null));
                return;
            }
            DialogUtil.newInstance().dialogWithConfirmAndCancel(this, "确定把\n" + mTxtResult.getText().toString() + "\n存进数据库?", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    VoiceBean bean = new VoiceBean(null,mTxtResult.getText().toString(),System.currentTimeMillis());
                    ((VoiceHandler) DbHandlerFactory.create(DbHandlerFactory.VOICE)).intsert(bean);
                    sweetAlertDialog.dismissWithAnimation();
                    finish();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecorderRecognizerManager.release();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        checkPermission();
        mRecorderRecognizerManager = new RecorderRecognizerManager(new MyListener());
        mTxtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice;
    }

    @Override
    public BasePresenterImpl initPresenter() {
        return null;
    }

    public void startAnimation() {
        if (!mRippleBackground.isRippleAnimationRunning()) {
            mRippleBackground.startRippleAnimation();
        }
        if (mImgVoice != null) {
            mImgVoice.setAnimation(createAnimation());
        }
    }

    public void stopAnimation() {
      ViewUtil.runInUIThread(new Runnable() {
          @Override
          public void run() {
              if (mRippleBackground.isRippleAnimationRunning()) {
                  mRippleBackground.stopRippleAnimation();
              }
              if (mImgVoice != null) {
                  mImgVoice.clearAnimation();
              }
          }
      });
    }

    private RecorderSpeechRecognizer.RecordState mRecordState = RecorderSpeechRecognizer.RecordState.STOPPED;
    private RecorderSpeechRecognizer.RecognizeState mRecognizeState = RecorderSpeechRecognizer.RecognizeState.STOPPED;

    private class MyListener implements IRecorderSpeechRecognizerListener {

        @Override
        public void onRecordStateChange(RecorderSpeechRecognizer.RecordState state) {
            mRecordState = state;
            if (mRecordState == RecorderSpeechRecognizer.RecordState.STOPPED) {
                onButtonHandler(true, ViewUtil.getString(R.string.txt_start));
                onRecordStateHandler(ViewUtil.getString(R.string.txt_stop));
                onCancelButtonHandler(View.INVISIBLE);
                stopAnimation();
            } else if (mRecordState == RecorderSpeechRecognizer.RecordState.INITIALIZING) {
                onRecordStateHandler(ViewUtil.getString(R.string.txt_decord_init));
                onButtonHandler(false, ViewUtil.getString(R.string.txt_decord_init));
                onCancelButtonHandler(View.INVISIBLE);
            } else if (state == RecorderSpeechRecognizer.RecordState.INITIALIZED) {
                onRecordStateHandler(ViewUtil.getString(R.string.txt_decord_init_finish));
                onCancelButtonHandler(View.INVISIBLE);
                onButtonHandler(false, "");
            } else if (state == RecorderSpeechRecognizer.RecordState.RECORDING) {
                onRecordStateHandler(ViewUtil.getString(R.string.txt_recording));
                onButtonHandler(true, ViewUtil.getString(R.string.txt_stop));
                onCancelButtonHandler(View.VISIBLE);
            } else if (state == RecorderSpeechRecognizer.RecordState.STOPPING) {
                String stop = ViewUtil.getString(R.string.txt_recording) + "...";
                onRecordStateHandler(stop);
                onButtonHandler(false, ViewUtil.getString(R.string.txt_stop));
                onCancelButtonHandler(View.VISIBLE);
            } else if (state == RecorderSpeechRecognizer.RecordState.ERROR) {
                onRecordStateHandler(ViewUtil.getString(R.string.txt_decord_error));
                onButtonHandler(false, ViewUtil.getString(R.string.txt_decord_error));
                onCancelButtonHandler(View.VISIBLE);
            }
        }

        @Override
        public void onRecognizeStateChange(RecorderSpeechRecognizer.RecognizeState state) {
            mRecognizeState = state;
            if (mRecognizeState == RecorderSpeechRecognizer.RecognizeState.STOPPED) {

                onChangeStateHandler(ViewUtil.getString(R.string.txt_stop));

            } else if (mRecognizeState == RecorderSpeechRecognizer.RecognizeState.PROCESSING) {
                onChangeStateHandler(ViewUtil.getString(R.string.txt_processing));

            } else if (mRecognizeState == RecorderSpeechRecognizer.RecognizeState.COMPLETED) {

                onChangeStateHandler(ViewUtil.getString(R.string.txt_completed));

            } else if (mRecognizeState == RecorderSpeechRecognizer.RecognizeState.ERROR) {

                onChangeStateHandler(ViewUtil.getString(R.string.txt_recognize_error));

            }
        }

        @Override
        public void onRecognizeResultChange(APIResponse response) {
            SpeechResult sttResult = response.getData().getSpeechResult();
            if (sttResult.complete()) {
                onResultHandler(sttResult.getResult());
            } else {
                if (sttResult.getStatus() == SpeechResult.STATUS_RECOGNIZE_OK) {
                    onResultHandler(sttResult.getResult());
                }
            }
        }

        @Override
        public void onRecordVolumeChange(int volumeValue) {

        }

        @Override
        public void onServerError(APIResponse response) {

        }

        @Override
        public void onError(RecorderSpeechRecognizer.Error error) {
            onErrorHandler("RecorderSpeechRecognizer.Error: " + error.name());
        }

        @Override
        public void onException(Exception e) {
            e.printStackTrace();
        }
    }

    private void onCancelButtonHandler(final int invisible) {
        ViewUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                mBtnCancel.setVisibility(invisible);
            }
        });
    }

    private void onResultHandler(final String result) {
        ViewUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                mTxtResult.setText(result);
            }
        });
    }

    private void onRecordStateHandler(final String result) {
        ViewUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                mTxtRecordState.setText(result);
            }
        });
    }

    private void onButtonHandler(final boolean isEnable, final String result) {
        ViewUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                mBtnState.setEnabled(isEnable);
                mBtnState.setText(result);
            }
        });
    }

    private void onChangeStateHandler(final String result) {
        ViewUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                mTxtChangeState.setText(result);
            }
        });
    }

    private void onErrorHandler(final String errorString) {
        new Handler(this.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),
                        errorString,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public Animation createAnimation() {
        Animation rorateAnim = AnimationUtils.loadAnimation(this,
                R.anim.printing);
        LinearInterpolator lin = new LinearInterpolator();
        rorateAnim.setInterpolator(lin);
        return rorateAnim;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecorderRecognizerManager.stop();
    }
}
