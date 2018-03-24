
package itg8.com.seotoolapp.keyword.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keywordstatusmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("projectmaster_id")
    @Expose
    private String projectmasterId;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("pageno")
    @Expose
    private String pageno;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("dateof")
    @Expose
    private String dateof;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Keywordstatusmaster> CREATOR = new Creator<Keywordstatusmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Keywordstatusmaster createFromParcel(Parcel in) {
            Keywordstatusmaster instance = new Keywordstatusmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.projectmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.keyword = ((String) in.readValue((String.class.getClassLoader())));
            instance.pageno = ((String) in.readValue((String.class.getClassLoader())));
            instance.rank = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateof = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Keywordstatusmaster[] newArray(int size) {
            return (new Keywordstatusmaster[size]);
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
     *     The pageno
     */
    public String getPageno() {
        return pageno;
    }

    /**
     * 
     * @param pageno
     *     The pageno
     */
    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    /**
     * 
     * @return
     *     The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
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
        dest.writeValue(keyword);
        dest.writeValue(pageno);
        dest.writeValue(rank);
        dest.writeValue(dateof);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
