package it.fourpixel.simplecrud.resource;

import it.fourpixel.simplecrud.dto.TutorialDto;
import it.fourpixel.simplecrud.service.TutorialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("tutorials")
public class TutorialResource {

    private final TutorialService tutorialService;

    public TutorialResource(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TutorialDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tutorialService.save(dto.getTitle(), dto.getDescription()));
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable long id) {
        var fromDb = tutorialService.get(id);
        if(fromDb.isPresent()) {
            return ResponseEntity.ok(fromDb.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id,
                                 @RequestBody TutorialDto dto) {
        var response = tutorialisInvalid(id);
        if (Objects.nonNull(response)) return response;
        tutorialService.update(id, dto.getTitle(), dto.getDescription());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/publish")
    public ResponseEntity update(@PathVariable long id) {
        var response = tutorialisInvalid(id);
        if (Objects.nonNull(response)) return response;
        tutorialService.publish(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        var response = tutorialisInvalid(id);
        if (Objects.nonNull(response)) return response;
        tutorialService.delete(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity tutorialisInvalid(long modelId) {
        if (tutorialService.notExists(modelId)) {
            var error = new HashMap<String, String>();
            error.put("message", "There is no tutorial with the id informed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
        return null;
    }
}
