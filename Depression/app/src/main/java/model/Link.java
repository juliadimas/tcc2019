package model;

import java.util.Date;

/**
 * Created by feliz on 05/11/2019.
 */

public class Link {
    private String url;
    private boolean verificado = false;
    private Date data;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Link(String url, boolean verificado, Date data) {
        this.url = url;
        this.verificado = verificado;
        this.data = data;
    }

    public Link() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return url;
    }
}
