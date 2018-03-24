
package itg8.com.seotoolapp.traffic.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trafficmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("projectmaster_id")
    @Expose
    private String projectmasterId;
    @SerializedName("trafficcategorymaster_id")
    @Expose
    private String trafficcategorymasterId;
    @SerializedName("contof")
    @Expose
    private String contof;
    @SerializedName("dateof")
    @Expose
    private String dateof;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Trafficmaster> CREATOR = new Creator<Trafficmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Trafficmaster createFromParcel(Parcel in) {
            Trafficmaster instance = new Trafficmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.projectmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.trafficcategorymasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.contof = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateof = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Trafficmaster[] newArray(int size) {
            return (new Trafficmaster[size]);
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
     *     The trafficcategorymasterId
     */
    public String getTrafficcategorymasterId() {
        return trafficcategorymasterId;
    }

    /**
     * 
     * @param trafficcategorymasterId
     *     The trafficcategorymaster_id
     */
    public void setTrafficcategorymasterId(String trafficcategorymasterId) {
        this.trafficcategorymasterId = trafficcategorymasterId;
    }

    /**
     * 
     * @return
     *     The contof
     */
    public String getContof() {
        return contof;
    }

    /**
     * 
     * @param contof
     *     The contof
     */
    public void setContof(String contof) {
        this.contof = contof;
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
        dest.writeValue(trafficcategorymasterId);
        dest.writeValue(contof);
        dest.writeValue(dateof);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
