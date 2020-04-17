package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @program: tensquare_parent
 * @description: 创建token, 解析token
 **/
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    //从配置文件中获取
    private long expire; //有效时间
    private String signName; //“盐”

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    /**
     * 用户登陆成功后签发token
     * @return
     */
    public String createJwt(String id, String username, String role){
        long now = System.currentTimeMillis();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)  //jti: jwt的唯一身份标识
                .setSubject(username)  //sub: jwt所面向的用户
                .setIssuedAt(new Date(now))  //iat: jwt的签发时间
                .setExpiration(new Date(now+expire)) //exp: jwt的过期时间
                .signWith(SignatureAlgorithm.HS256, signName);//设置 “盐” head头部设置加密方式
        jwtBuilder.claim("role", role); //设置角色信息
        String token = jwtBuilder.compact();//生成token
        return token;
    }

    /**
     * 解析token是否合法
     * @param token
     * @return
     */
    public Claims parseJwt(String token){
        Claims claims =  Jwts.parser()
                .setSigningKey(signName)  //设置签名  如果签名跟服务端签发token不一致会解析失败
                .parseClaimsJws(token)
                .getBody(); //获取载荷信息
        return claims;
    }
}
