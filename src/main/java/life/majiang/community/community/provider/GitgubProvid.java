package life.majiang.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccesTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GitgubProvid {
    public String grtAccessToken(AccesTokenDTO accesTokenDTO) throws IOException {
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();  //请求到的数据
                String token = string.split("&")[0].split("=")[1];
                //System.out.println(string);
                return token;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;

    }

    //获取用户名
    public GithubUser getUser(String accesTokenDTO){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accesTokenDTO)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
