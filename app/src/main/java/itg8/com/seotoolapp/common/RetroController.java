package itg8.com.seotoolapp.common;

import java.util.List;

import io.reactivex.Observable;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetroController {

    @GET
    Observable<ResponseBody> checkLogin(@Url String url,
                                        @Query("username") String username,
                                        @Query("password") String password);


    Observable<ResponseBody> checkOtp();

    @FormUrlEncoded
    @POST
    Call <List<KeyWordModel>> getKeyWordStatusList(@Url String url,
                                                   @Field("Keywordstatusmaster[datefrom]") String dateFrom,
                                                   @Field("Keywordstatusmaster[dateto]") String dateTo,
                                                   @Field("Keywordstatusmaster[projectid]") String projectId);
    @FormUrlEncoded
    @POST
    Call<List<TrafficModel>> getTrafficBetweenDate(@Url String url,
                                                   @Field("Trafficmaster[datefrom]") String dateFrom,
                                                   @Field("Trafficmaster[dateto]") String dateTo,
                                                   @Field("Trafficmaster[projectid]") String projectId
                                                   );
    @FormUrlEncoded
    @POST
    Call<List<ExternalLinksModel>> getExternalLinksBetween(@Url String url,
                                                           @Field("Exlinkmaster[datefrom]")  String dateTo,
                                                           @Field("Exlinkmaster[dateto]") String dateFrom,
                                                           @Field("Exlinkmaster[projectid]")   String projectId,
                                                           @Field("Exlinkmaster[typeof]") int type);


    @FormUrlEncoded
    @POST
    Call<ResponseBody> changePasscode(@Url String url,
                                      @Field("User[id]") String userId,
                                      @Field("User[password]") String passcode,
                                      @Field("User[cpassword]") String passcodeConfirm);
//    @FormUrlEncoded
//    @POST
//    Call<List<So>> getSocialMediaBetween(@Url String url,
//                                                       @Field("Sociallinkmaster[datefrom]")    String toDate,
//                                                       @Field("Sociallinkmaster[dateto]") String dateFrom,
//                                                       @Field("Sociallinkmaster[projectid]")   String projectId);
}
