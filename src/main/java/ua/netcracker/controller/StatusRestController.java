package ua.netcracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.entity.Status;

import java.util.ArrayList;
import java.util.Collection;


@RestController
public class StatusRestController {

    @RequestMapping(value = "/getStatuses", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> listAllStatuses() {

        Status[] values = Status.values();

        Collection<String> statuses = new ArrayList<>();

        for (Status value : values) {
            statuses.add(value.name());
        }

        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
