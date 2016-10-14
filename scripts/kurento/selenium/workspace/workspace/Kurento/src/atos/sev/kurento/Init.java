package atos.sev.kurento;

import org.apache.xpath.operations.String;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Init {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--use-fake-device-for-media-stream");
		options.addArguments("--use-fake-ui-for-media-stream");
		
		try {
			WebDriver driver = new ChromeDriver(options);
			driver.get("https://62.212.64.165:8443");
			Thread.sleep(3000);  // Let the user actually see something!
			WebElement buttonStart = driver.findElement(By.id("start"));
			WebElement buttonStop = driver.findElement(By.id("stop"));
			WebElement buttonPlay = driver.findElement(By.id("play"));
			while (1>0) {
			  
			  buttonStart.click();
			  Thread.sleep(15000);
			  buttonStop.click();
			  buttonPlay.click();
			  Thread.sleep(20000);
			}
		} catch (InterruptedException e) {
			System.out.println("Error no controlado");
		}
		  //driver.quit();

	}

}
