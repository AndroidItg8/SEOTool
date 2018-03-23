package itg8.com.seotoolapp.common;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetroController {

    @GET
    Observable<ResponseBody> checkLogin(@Url String url, @Query("username") String username, @Query("password") String password);


    Observable<ResponseBody> checkOtp();
}
