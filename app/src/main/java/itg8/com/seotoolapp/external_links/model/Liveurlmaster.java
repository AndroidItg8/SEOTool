
package itg8.com.seotoolapp.external_links.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Liveurlmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("exlinkmaster_id")
    @Expose
    private String exlinkmasterId;
    @SerializedName("liveurl")
    @Expose
    private String liveurl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Liveurlmaster> CREATOR = new Creator<Liveurlmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Liveurlmaster createFromParcel(Parcel in) {
            Liveurlmaster instance = new Liveurlmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.exlinkmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.liveurl = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.session = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Liveurlmaster[] newArray(int size) {
            return (new Liveurlmaster[size]);
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
     *     The exlinkmasterId
     */
    public String getExlinkmasterId() {
        return exlinkmasterId;
    }

    /**
     * 
     * @param exlinkmasterId
     *     The exlinkmaster_id
     */
    public void setExlinkmasterId(String exlinkmasterId) {
        this.exlinkmasterId = exlinkmasterId;
    }

    /**
     * 
     * @return
     *     The liveurl
     */
    public String getLiveurl() {
        return liveurl;
    }

    /**
     * 
     * @param liveurl
     *     The liveurl
     */
    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The session
     */
    public String getSession() {
        return session;
    }

    /**
     * 
     * @param session
     *     The session
     */
    public void setSession(String session) {
        this.session = session;
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
        dest.writeValue(exlinkmasterId);
        dest.writeValue(liveurl);
        dest.writeValue(status);
        dest.writeValue(session);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
