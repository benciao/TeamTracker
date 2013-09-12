package com.ecg.mts.support.teamtracker.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.ecg.mts.support.teamtracker.util.Util;

/**
 * This class provides general information about an adminuser of this
 * application. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.04.2012
 */
@Entity
public class AdminUser
{
    @Id
    private String  username;
    @Basic
    private String  password;
    @Basic
    private boolean isActive;
    @Basic
    private boolean isLogedOn;

    public AdminUser(String username, String password, boolean isActive)
    {
        this.username = username;
        this.password = Util.getMd5Encoding(password);
        this.isActive = isActive;
        this.isLogedOn = false;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = Util.getMd5Encoding(password);
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public boolean isLogedOn()
    {
        return isLogedOn;
    }

    public void setLogedOn(boolean isLogedOn)
    {
        this.isLogedOn = isLogedOn;
    }
}
