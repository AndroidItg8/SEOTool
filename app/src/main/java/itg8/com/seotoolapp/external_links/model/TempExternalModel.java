package itg8.com.seotoolapp.external_links.model;

/**
 * Created by Android itg 8 on 3/27/2018.
 */

public class TempExternalModel {
    private String date;
    private String count;
    private String sessions;

    public TempExternalModel(String date, String count, String sessions) {
        this.date = date;
        this.count = count;
        this.sessions = sessions;
    }

    public String getDate() {
        return date;
    }

    public String getCount() {
        return count;
    }

    public String getSessions() {
        return sessions;
    }
}
