package com.rale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.xml.parsers.DocumentBuilderFactory;


public class App
{
    public static void main( String[] args )
    {

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

        String outPut = doc.text();

        System.out.println(outPut);

        browser.close();

    }
}