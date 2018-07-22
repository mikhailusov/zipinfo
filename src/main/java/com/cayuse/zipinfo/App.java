package com.cayuse.zipinfo;

import com.cayuse.zipinfo.domain.ZipCode;
import com.cayuse.zipinfo.domain.ZipInfo;
import com.cayuse.zipinfo.service.ZipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import javax.ws.rs.NotFoundException;
import java.util.Scanner;

@ComponentScan("com.cayuse.zipinfo.service")
public class App {

    @Autowired
    private ZipInfoService zipInfoService;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        App app = context.getBean(App.class);
        app.run();
    }

    private void run() {
        System.out.print("Please enter a zip code: ");
        String zip = new Scanner(System.in).nextLine().trim();

        while (!zip.matches("\\d{5}")) {
            System.out.print("Sorry zip you entered is not valid, enter zip of 5 digits: ");
            zip = new Scanner(System.in).nextLine().trim();
        }

        try {
            ZipInfo zipInfo = zipInfoService.getInfo(new ZipCode(zip, "us"));

            System.out.printf("At the location %s, the temperature is %.0fF, the timezone is %s, and the elevation is %.2f.",
                    zipInfo.getCityName(), zipInfo.getTemperature(), zipInfo.getTimeZone(), zipInfo.getElevation());
        } catch (NotFoundException e) {
            System.out.println("Couldn't locate entered zip code");
        }
    }
}
