package com.rale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.util.List;


public class App
{
    public static void main( String[] args ) throws IOException {

        System.setProperty("webdriver.chrome.driver", "C:\\dev\\repos\\padawan-learner\\selenium-example\\src\\main\\java\\drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        WebDriver browser = new ChromeDriver();

        browser.get("https://www.azlyrics.com/");

        System.out.println(browser.getTitle());

        // Choose the song
        browser.findElement(By.name("q")).sendKeys("home phillips phillips" + Keys.ENTER);

        browser.findElement(By.partialLinkText("1.")).click();

        String pageSource = browser.getPageSource();

        Document doc = Jsoup.parse(pageSource);

        List<String> lyrics = doc.getElementsByTag("div").eachText();

        for (String element: lyrics){
            System.out.println(element);
            System.out.println();
            System.out.println("---------------");
        }

        browser.close();

    }
}