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

