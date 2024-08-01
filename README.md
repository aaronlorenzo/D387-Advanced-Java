DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS.

Western Governors University
D387 – Advanced Java

Tasks
a. Build Resource Bundles
Create resource bundles for both English and French to comply with Canadian law. Include a welcome message in each language's resource bundle.

Resource Bundle 'translation':

translation_en_us.properties

hello=Hello!
welcome=Welcome to the Landon Hotel!

translation_fr_ca.properties
hello=Bonjour!
welcome=Bienvenue à l'hôtel Landon

b. Display Welcome Messages Using Threads
Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.

Create:

internationalization.WelcomeController.java
package edu.wgu.d387_sample_code.internationalization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@CrossOrigin(origins = "http://localhost:4200") // Needed for front end
@RestController
public class WelcomeController {

    @GetMapping("/welcome")

    public ResponseEntity displayWelcome (@RequestParam("lang") String lang) { // Request the html lang parameter here
        Locale locale = Locale.forLanguageTag(lang); // create locale object based on lang parameter
        WelcomeMessage welcomeMessage = new WelcomeMessage(locale); // create welcomeMessage to pull the corresponding lang
        return new ResponseEntity (welcomeMessage.getWelcomeMessage(), HttpStatus.OK); // respond
    }
}
internationalization.WelcomeMessage.java
package edu.wgu.d387_sample_code.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeMessage implements Runnable {

    Locale locale;

    // Constructor
    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    public String getWelcomeMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);
        return bundle.getString("welcome");
    }

    @Override
    public void run() {
        System.out.println( "Thread verification: " + getWelcomeMessage() + ", ThreadID: " + Thread.currentThread().getId() );
    }
}
Modify:

D387SampleCodeApplication.java
// Create threads for the Welcome Message in French and English
WelcomeMessage welcomeMessageEnglish = new WelcomeMessage(Locale.US);
Thread englishWelcomeThread = new Thread(welcomeMessageEnglish);
englishWelcomeThread.start();

WelcomeMessage welcomeMessageFrench = new WelcomeMessage(Locale.CANADA_FRENCH);
Thread frenchWelcomeThread = new Thread(welcomeMessageFrench);
frenchWelcomeThread.start();

2.  Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.

app.component.ts
Modify at lines 70-71:
// Add prices in CAD and EUR
this.rooms.forEach(room => {
room.priceCAD = room.price;
room.priceEUR = room.price;
});


Modify at lines 120-122:
// Add price fields for CAD and EUR
priceCAD: string;
priceEUR: string;


app.component.html
Modify at lines 87-90:
<!-- Display CAD and EUR prices -->
<strong>Price: CA${{room.priceCAD}}</strong><br>
<strong>Price: EUR€{{room.priceEUR}}</strong><br>

3.  Display the time for an online live presentation held at the Landon Hotel by doing the following:

a.  Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.
Create TZConvert.java:
package edu.wgu.d387_sample_code.internationalization;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class TZConvert {

    /**
     * Converts the current time to Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC).
     *
     * @return A string containing the time in ET, MT, and UTC formatted as "HH:mm".
     */
    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime est = currentTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime mst = currentTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utc = currentTime.withZoneSameInstant(ZoneId.of("UTC"));

        return est.format(timeFormat) + " EST, " + mst.format(timeFormat) + " MST, " + utc.format(timeFormat) + " UTC";
    }
}


b.  Use the time zone conversion method from part B3a to display a message stating the time in all three times zones in hours and minutes for an online, live presentation held at the Landon Hotel. The times should be displayed as ET, MT, and UTC.
package edu.wgu.d387_sample_code.internationalization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TZConvertController {

    /**
     * Provides an announcement for the live presentation including the time in ET, MT, and UTC.
     *
     * @return A response entity containing the announcement message.
     */
    @GetMapping("/presentation")
    public ResponseEntity<String> announcePresentation() {
        String announcement = "GOOD NEWS!: There is a presentation beginning at: " + TZConvert.getTime();
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }
}

Update app.component.ts:

Add the following line to declare the announcement observable:

//  Code to announce the presentation with time zone conversions
announcePresentation$!: Observable<string>;


Add the following code to fetch the announcement from the backend:

//  Code to add conference announcement
this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', { responseType: 'text' });


Update app.component.html:

Insert the following HTML to display the presentation announcement:

<!--  - Code to add presentation announcement -->
<div class="scene" id="presentation">
  <h1>{{announcePresentation$ | async}}</h1>
</div>
<br><br>


C.  Explain how you would deploy the Spring application with a Java back end and an Angular front end to cloud services and create a Dockerfile using the attached supporting document "How to Create a Docker Account" by doing the following:

1.  Build the Dockerfile to create a single image that includes all code, including modifications made in parts B1 to B3. Commit and push the final Dockerfile to GitLab.
CREATE 
DockerFile
   FROM openJDK:17
   COPY ./D387_sample_code-0.0.2-SNAPSHOT.jar /usr/src/D387_sample_code-0.0.2-SNAPSHOT.jar
   WORKDIR /usr/src
   EXPOSE 8080
   CMD ["java", "-jar", "D387_sample_code-0.0.2-SNAPSHOT.jar"]
