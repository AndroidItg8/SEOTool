
package itg8.com.seotoolapp.social_media.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sociallinkmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("projectmaster_id")
    @Expose
    private String projectmasterId;
    @SerializedName("dateof")
    @Expose
    private String dateof;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Sociallinkmaster> CREATOR = new Creator<Sociallinkmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Sociallinkmaster createFromParcel(Parcel in) {
            Sociallinkmaster instance = new Sociallinkmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.projectmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateof = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Sociallinkmaster[] newArray(int size) {
            return (new Sociallinkmaster[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The projectmasterId
     */
    public String getProjectmasterId() {
        return projectmasterId;
    }

    /**
     * 
     * @param projectmasterId
     *     The projectmaster_id
     */
    public void setProjectmasterId(String projectmasterId) {
        this.projectmasterId = projectmasterId;
    }

    /**
     * 
     * @return
     *     The dateof
     */
    public String getDateof() {
        return dateof;
    }

    /**
     * 
     * @param dateof
     *     The dateof
     */
    public void setDateof(String dateof) {
        this.dateof = dateof;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(projectmasterId);
        dest.writeValue(dateof);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
