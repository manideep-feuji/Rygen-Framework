package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    private static final Path SCREENSHOT_DIRECTORY =
            Paths.get("target", "screenshots");

    private ScreenshotUtil() {
    }

    public static Path capture(WebDriver driver, String name) {
        if (driver == null) {
            logger.warn("Screenshot skipped because the WebDriver is null.");
            return null;
        }

        if (!(driver instanceof TakesScreenshot)) {
            logger.warn("Screenshot skipped because the WebDriver does not support screenshots.");
            return null;
        }

        try {
            Files.createDirectories(SCREENSHOT_DIRECTORY);

            String safeName = sanitizeName(name);
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            Path destination = SCREENSHOT_DIRECTORY.resolve(
                    timestamp + "_" + safeName + ".png");

            Path screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(screenshot, destination);
            logger.info("Screenshot saved: {}", destination.toAbsolutePath());
            return destination;
        } catch (IOException | RuntimeException e) {
            logger.error("Unable to capture screenshot for '{}'.", name, e);
            return null;
        }
    }

    private static String sanitizeName(String name) {
        String value = name == null || name.isBlank() ? "screenshot" : name.trim();
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
