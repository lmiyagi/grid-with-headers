package br.com.leonardomiyagi.gridwithheaders;

/**
 * Created by SES\leonardom on 02/08/17.
 */

public class SampleObject {

    private int id;
    private String category;

    public SampleObject(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
