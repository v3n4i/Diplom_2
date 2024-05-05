public class Tokens {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String refreshToken;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;

    public Tokens() {

    }

    public Tokens(boolean success, String accessToken, String refreshToken) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
