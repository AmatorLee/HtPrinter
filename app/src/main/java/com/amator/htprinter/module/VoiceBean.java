package com.amator.htprinter.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AmatorLee on 2018/5/2.
 */
@Entity
public class VoiceBean {

    @Id
    private Long id;
    private String message;
    private long time;



    @Generated(hash = 485990618)
    public VoiceBean(Long id, String message, long time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    @Generated(hash = 1719036352)
    public VoiceBean() {
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
