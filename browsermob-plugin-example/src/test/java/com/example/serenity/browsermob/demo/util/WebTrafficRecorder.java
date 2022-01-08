package com.example.serenity.browsermob.demo.util;

import static net.lightbody.bmp.proxy.CaptureType.REQUEST_CONTENT;
import static net.lightbody.bmp.proxy.CaptureType.REQUEST_HEADERS;
import static net.lightbody.bmp.proxy.CaptureType.RESPONSE_HEADERS;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.cucumber.java.Scenario;
import lombok.Getter;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.serenitybdd.core.di.WebDriverInjectors;
import net.thucydides.browsermob.fixtureservices.BrowserMobFixtureService;
import net.thucydides.core.fixtureservices.FixtureProviderService;
import net.thucydides.core.fixtureservices.FixtureService;

/**
 * Utility class to capture web traffic and save them to HTTP archive files.
 */
@Getter
@Component
public class WebTrafficRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebTrafficRecorder.class);
    private static final Path CAPTURED_TRAFFIC_FOLDER = Paths.get("target/webtraffic");
    private static final String WHITESPACE_REGEX = "\\s";
    private static final String UNDERSCORE = "_";
    private static final String TARGET_HTTP_ARCHIVE_FILE_NAME_TEMPLATE = "http_archive_demo_%s%s.har";

    private BrowserMobProxy proxy;

    public void setUpProxyFromSerenity() {
        final FixtureProviderService classpathFixtureProviderService = WebDriverInjectors.getInjector().getInstance(FixtureProviderService.class);
        final List<FixtureService> fixtureServices = classpathFixtureProviderService.getFixtureServices();

        for (FixtureService fixtureService : fixtureServices) {

            if (fixtureService instanceof BrowserMobFixtureService) {
                final BrowserMobFixtureService service = (BrowserMobFixtureService) fixtureService;
                proxy = service.getProxyServer();

                proxy.enableHarCaptureTypes(
                    REQUEST_HEADERS,
                    REQUEST_CONTENT,
                    RESPONSE_HEADERS
                );
                proxy.newHar("demo_traffic_capture");
                LOGGER.info("Proxy was setup from the Serenity BrowserMob plugin.");
            }
        }
    }

    public void saveHttpArchiveToFile(final Scenario scenario) {
        try {
            final Har capturedHttpArchive = proxy.getHar();

            final String targetHttpArchiveFileName = String.format(
                TARGET_HTTP_ARCHIVE_FILE_NAME_TEMPLATE,
                scenario.getName(),
                scenario.getLine()
            )
                .replaceAll(WHITESPACE_REGEX, UNDERSCORE);
            Files.createDirectories(CAPTURED_TRAFFIC_FOLDER);
            capturedHttpArchive.writeTo(CAPTURED_TRAFFIC_FOLDER.resolve(Paths.get(targetHttpArchiveFileName)).toFile());

            LOGGER.info("Saving HTTP Archive was successful: {}", targetHttpArchiveFileName);
        } catch (Exception e) {
            LOGGER.warn("Can't save HTTP archive file. Exception: {}", e.getMessage());
        }
    }
}
