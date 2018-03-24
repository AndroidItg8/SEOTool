package itg8.com.seotoolapp.common;

import java.util.List;

import io.reactivex.Observable;
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
    Observable<ResponseBody> checkLogin(@Url String url, @Query("username") String username, @Query("password") String password);


    Observable<ResponseBody> checkOtp();

    @FormUrlEncoded
    @POST
    Call <List<KeyWordModel>> getKeyWordStatusList(@Url String url,
                                                   @Field("Keywordstatusmaster[datefrom]") String dateFrom,
                                                   @Field("Keywordstatusmaster[dateto]") String dateTo,
                                                   @Field("Keywordstatusmaster[projectid]") String projectId);
    @FormUrlEncoded
    @POST
    Call<List<TrafficModel>> getTrafficCategory(@Url String url);
}
