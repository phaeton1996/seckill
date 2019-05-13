package com.graduation.seckill.vo;

import java.util.Date;

public class UserVo {

    private int id;

    private String userName;

    private String account;

    private int status;

    private Date createTime;

    private int role;

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", role=" + role +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserVo() {
    }

    public UserVo(int id, String userName, String account, int status, Date createTime, int role) {
        this.id = id;
        this.userName = userName;
        this.account = account;
        this.status = status;
        this.createTime = createTime;
        this.role = role;
    }
}
