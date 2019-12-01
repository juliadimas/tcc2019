package model;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ? on 24/11/2019.
 */

public class HumorDiario {
    private String data;
    private List<Integer> listHumores;

    public HumorDiario() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Integer> getListHumores() {
        return listHumores;
    }

    public void setListHumores(List<Integer> listHumores) {
        this.listHumores = listHumores;
    }
}
