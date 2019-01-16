package phoenix.core.security.util;

public class JwtConstants {

    private static final long MINUTES = 10;
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "s3cr3tk3yl0l";
//    public static final long EXPIRATION = MINUTES * 60* 1000;
    public static final long EXPIRATION = 10000;

}
