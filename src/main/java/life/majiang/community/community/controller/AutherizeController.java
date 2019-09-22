package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccesTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.provider.GitgubProvid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

@Controller
public class AutherizeController {
    @Autowired
    private GitgubProvid gitgubProvid;

    @Value("${github.setClient.id}")
    private String ClientID;
    @Value("${github.setClient.secret}")
    private String ClientSecret;
    @Value("${github.setRedirect.uri}")
    private String SetRedirectUri;
    //获取
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state) throws IOException {
        AccesTokenDTO accesTokenDTO = new AccesTokenDTO();
        accesTokenDTO.setClient_id(ClientID);
        accesTokenDTO.setClient_secret(ClientSecret);
        accesTokenDTO.setCode(code);
        accesTokenDTO.setRedirect_uri(SetRedirectUri);
        accesTokenDTO.setState(state);
        String accessToken = gitgubProvid.grtAccessToken(accesTokenDTO);
        GithubUser user = gitgubProvid.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
