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

