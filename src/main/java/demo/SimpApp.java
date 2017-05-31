package demo;

import lombok.Builder;

/**
 * Created by huishen on 17/2/15.
 */
@Builder
public class SimpApp {
    private String appId;
    private String appName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpApp simpApp = (SimpApp) o;

        return appId.equals(simpApp.appId);

    }

    @Override
    public int hashCode() {
        return appId.hashCode();
    }
}
