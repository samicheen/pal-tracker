package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        final TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeEntry);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        final TimeEntry fetchedTimeEntry = timeEntryRepository.find(timeEntryId);
        BodyBuilder status = ObjectUtils.isEmpty(fetchedTimeEntry) ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK);
        return status.body(fetchedTimeEntry);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        final List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryList);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
        final TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
        BodyBuilder status = ObjectUtils.isEmpty(updatedTimeEntry) ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                : ResponseEntity.status(HttpStatus.OK);
        return status.body(updatedTimeEntry);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return ResponseEntity.noContent().build();
    }
}
