package com.amator.htprinter.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AmatorLee on 2018/5/4.
 */
@Entity
public class UserBean {
    @Id
    private Long id;
    private String username;
    private String password;
    private String nick;




    @Generated(hash = 1315144946)
    public UserBean(Long id, String username, String password, String nick) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nick = nick;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
