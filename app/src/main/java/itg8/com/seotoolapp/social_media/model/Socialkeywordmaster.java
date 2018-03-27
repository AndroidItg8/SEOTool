
package itg8.com.seotoolapp.social_media.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Socialkeywordmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sociallinkmaster_id")
    @Expose
    private String sociallinkmasterId;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("posturl")
    @Expose
    private String posturl;
    @SerializedName("liveurl")
    @Expose
    private String liveurl;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Socialkeywordmaster> CREATOR = new Creator<Socialkeywordmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Socialkeywordmaster createFromParcel(Parcel in) {
            Socialkeywordmaster instance = new Socialkeywordmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.sociallinkmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.keyword = ((String) in.readValue((String.class.getClassLoader())));
            instance.posturl = ((String) in.readValue((String.class.getClassLoader())));
            instance.liveurl = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Socialkeywordmaster[] newArray(int size) {
            return (new Socialkeywordmaster[size]);
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
     *     The sociallinkmasterId
     */
    public String getSociallinkmasterId() {
        return sociallinkmasterId;
    }

    /**
     * 
     * @param sociallinkmasterId
     *     The sociallinkmaster_id
     */
    public void setSociallinkmasterId(String sociallinkmasterId) {
        this.sociallinkmasterId = sociallinkmasterId;
    }

    /**
     * 
     * @return
     *     The keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 
     * @param keyword
     *     The keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 
     * @return
     *     The posturl
     */
    public String getPosturl() {
        return posturl;
    }

    /**
     * 
     * @param posturl
     *     The posturl
     */
    public void setPosturl(String posturl) {
        this.posturl = posturl;
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
        dest.writeValue(sociallinkmasterId);
        dest.writeValue(keyword);
        dest.writeValue(posturl);
        dest.writeValue(liveurl);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
