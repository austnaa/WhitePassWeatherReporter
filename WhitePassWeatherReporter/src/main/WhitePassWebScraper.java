/**
 * Austn Attaway
 * White Pass Ski Area Weather Report Web Scraper
 * Works as of February 1, 2021
 */
package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Scanner;

/**
 * Runs a console based application that reports the current weather report
 * at White Pass Ski Area.
 *
 * Information is web scraped from skiwhitepass.com/snow-report
 *
 * @author Austn Attaway
 * @version February 1, 2021
 */
public class WhitePassWebScraper {

    /** The URL to the website information is scraped from. */
    private static final String URL = "https://skiwhitepass.com/snow-report";

    /**
     * Runs the program.
     *
     * @param theArgs the command line arguments (unused)
     */
    public static void main(final String[] theArgs) {
        try {
            final Document document = Jsoup.connect(URL).get();
            StringBuilder stringBuilder = new StringBuilder();

            // append the console header
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(  "* * * * * * * * * * * * * * * * * * *");
            stringBuilder.append("\n*                                   *");
            stringBuilder.append("\n*  White Pass Ski Area Snow Report  *");
            stringBuilder.append("\n*                                   *");
            stringBuilder.append("\n* * * * * * * * * * * * * * * * * * *");
            stringBuilder.append(System.lineSeparator());

            // append the report information
            stringBuilder.append("\n  Report for ");
            final String date = document.select("p:nth-of" + "-type(1)").get(0).text();
            stringBuilder.append(date);
            final String lastUpdated = document.select("p:nth-of-type(1)" +
                    ".text-right").text();
            stringBuilder.append("\n  ");
            stringBuilder.append(lastUpdated);

            // append the weather update
            // Example: "Snowing lightly" or "Blue skies"
            stringBuilder.append("\n\n  Weather:\n    ");
            final String latestWeather =
                    document.select("h2:nth-of-type(1)").get(0).text();
            final String last12Snow =
                    document.select("h2:nth-of-type(1)").get(2).text();
            final String last24Snow =
                    document.select("h2:nth-of-type(2)").get(2).text();
            final String last36Snow =
                    document.select("h2:nth-of-type(3)").get(1).text();

            // append the recent snowfall data
            stringBuilder.append(latestWeather);
            stringBuilder.append("\n    Last 12 hours: ");
            stringBuilder.append(last12Snow);
            stringBuilder.append("\n    Last 24 hours: ");
            stringBuilder.append(last24Snow);
            stringBuilder.append("\n    Last 36 hours: ");
            stringBuilder.append(last36Snow);

            // append the temperatures at the summit and base
            final Scanner tempScanner = new Scanner(document.select("h2:nth-of-type(1)").get(1).text());
            stringBuilder.append("\n\n  Temperatures:");
            stringBuilder.append("\n    Summit temp: ");
            stringBuilder.append(tempScanner.next());
            tempScanner.next();
            stringBuilder.append("\n    Base temp:   ");
            stringBuilder.append(tempScanner.next());

            // append the snow depths at the summit and base
            final String depthText =
                    document.select("h2:nth-of-type(4)").text();
            Scanner depthScanner = new Scanner(depthText);
            stringBuilder.append("\n\n  Snow depths:");
            stringBuilder.append("\n    Summit depth: ");
            stringBuilder.append(depthScanner.next());
            depthScanner.next();
            stringBuilder.append("\n    Base depth:   ");
            stringBuilder.append(depthScanner.next());

            // append the footer
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("\n* * * * * * * * * * * * * * * * * * *");
            stringBuilder.append("\n*                                   *");
            stringBuilder.append("\n*    It's getting better faster!    *");
            stringBuilder.append("\n*                                   *");
            stringBuilder.append("\n* * * * * * * * * * * * * * * * * * *");

            System.out.println(stringBuilder);

        } catch(final Exception theException) {
            System.out.println("An error occurred trying to access website.");
        }
    }

}
