package com.example.selenium.serenity.demo.driverprovider;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.selenium.serenity.demo.util.browserplatform.JsonDeserializerForPlatform;
import com.example.selenium.serenity.demo.util.browserplatform.Platform;
import com.example.selenium.serenity.demo.util.browserplatform.Screen;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.webdriver.DriverSource;

public class CustomChromiumDriver implements DriverSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomChromiumDriver.class);

    private static final String WIDTH_DEVICE_METRIC = "width";
    private static final String HEIGHT_DEVICE_METRIC = "height";
    private static final String PIXEL_RATIO_DEVICE_METRIC = "pixelRatio";
    private static final String DEVICE_METRICS_EMULATION_PARAM = "deviceMetrics";
    private static final String USER_AGENT_EMULATION_PARAM = "userAgent";
    private static final String MOBILE_EMULATION_OPTION_NAME = "mobileEmulation";
    private static final String DESKTOP_DEFAULT_PLATFORM = "desktop";
    private static final String PLATFORM_TO_SET_PROPERTY_NAME = "platformToSet";
    private static final String PROVIDED_DRIVER_PROPERTY_NAME = "provided.driver";
    private static final String CHROME = "chrome";
    private static final String HEADLESS_PROPERTY_NAME = "headless";
    private static final String FALSE = "false";
    private static final String HEADLESS_OPTION = "headless";
    private static final String DISABLE_GPU_OPTION = "disable-gpu";

    private Platform platform;

    @Override
    public WebDriver newDriver() {
        platform = new JsonDeserializerForPlatform(Platform.class).readJsonFileToPlatform(getPlatformToSet());

        WebDriver webDriver = CHROME.equals(getChromiumBrowserToSet()) ? setUpChromeDriver() : setUpEdgeDriver();

        webDriver.manage().window().setSize(platform.getBrowserWindowSize());

        return webDriver;
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private ChromiumOptions<?> emulateMobileForChromiumBrowser(final ChromiumOptions<?> chromiumOptions) {
        final Screen screen = platform.getScreenSettings();

        final Map<String, Object> deviceMetrics = Map.of(
            WIDTH_DEVICE_METRIC, screen.getScreenWidth(),
            HEIGHT_DEVICE_METRIC, screen.getScreenHeight(),
            PIXEL_RATIO_DEVICE_METRIC, screen.getPixelRatio()
        );

        final Map<String, Object> mobileEmulation = Map.of(
            DEVICE_METRICS_EMULATION_PARAM, deviceMetrics,
            USER_AGENT_EMULATION_PARAM, platform.getUserAgent()
        );

        LOGGER.info("Setting mobile emulation for custom provided chromium driver: {}", mobileEmulation);
        return chromiumOptions.setExperimentalOption(MOBILE_EMULATION_OPTION_NAME, mobileEmulation);
    }

    private WebDriver setUpEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        EdgeOptions edgeOptions = new EdgeOptions();

        if (getHeadless()) {
            edgeOptions.addArguments(HEADLESS_OPTION);
            edgeOptions.addArguments(DISABLE_GPU_OPTION);
        }

        if (platform.isDevice()) {
            emulateMobileForChromiumBrowser(edgeOptions);
        }

        return new EdgeDriver(edgeOptions);
    }

    private WebDriver setUpChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(getHeadless());

        if (platform.isDevice()) {
            emulateMobileForChromiumBrowser(chromeOptions);
        }

        return new ChromeDriver(chromeOptions);
    }

    private String getPlatformToSet() {
        return System.getProperty(PLATFORM_TO_SET_PROPERTY_NAME, DESKTOP_DEFAULT_PLATFORM);
    }

    private String getChromiumBrowserToSet() {
        return System.getProperty(PROVIDED_DRIVER_PROPERTY_NAME, CHROME);
    }

    private Boolean getHeadless() {
        return Boolean.valueOf(System.getProperty(HEADLESS_PROPERTY_NAME, FALSE));
    }
}
