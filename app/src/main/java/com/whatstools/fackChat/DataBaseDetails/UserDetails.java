package com.whatstools.fackChat.DataBaseDetails;

public class UserDetails {
    private byte[] bytes;
    private int uid;
    private String uname;
    private String uonline;
    private String ustatus;
    private String utyping;

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUtyping() {
        return this.utyping;
    }

    public void setUtyping(String utyping) {
        this.utyping = utyping;
    }

    public String getUonline() {
        return this.uonline;
    }

    public void setUonline(String uonline) {
        this.uonline = uonline;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getUstatus() {
        return this.ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }
}
