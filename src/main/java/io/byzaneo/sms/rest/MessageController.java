package io.byzaneo.sms.rest;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.byzaneo.one.billing.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
public class MessageController {

    public MessageController(@Value("${sms.account-sid}") String accountSid,
                             @Value("${sms.auth-token}") String authToken) {
        Twilio.init(accountSid, authToken);
    }


    @Price(value = "0.0999", description = "Send a SMS")
    @PostMapping(path = "/messages/{fromNumber}/{toNumber}",
                 consumes = TEXT_PLAIN_VALUE,
                 produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> send(@PathVariable String fromNumber,
                                       @PathVariable String toNumber,
                                       @RequestBody String text) {
        try {
            return ok(Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    text)
                    .create()
                    .getSid());
        } catch (Exception e) {
            return status(BAD_REQUEST).body(e.getMessage());
        }
    }
}
