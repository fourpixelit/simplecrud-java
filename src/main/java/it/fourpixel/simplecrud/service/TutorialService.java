package it.fourpixel.simplecrud.service;

import it.fourpixel.simplecrud.entity.Tutorial;
import it.fourpixel.simplecrud.repository.TutorialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Transactional
    public Tutorial save(String title, String  description) {
        return tutorialRepository.saveAndFlush(new Tutorial(title, description));
    }

    @Transactional
    public boolean notExists(long id) {
        return !tutorialRepository.existsById(id);
    }

    @Transactional
    public void delete(long id) {
        tutorialRepository.deleteById(id);
    }

    @Transactional
    public void update(long id, String title, String description) {
        tutorialRepository.updateTitleAndDescription(id, title, description);
    }

    @Transactional
    public void publish(long id) {
        tutorialRepository.publish(id);
    }

    @Transactional
    public Optional<Tutorial> get(long id) {
        return tutorialRepository.findById(id);
    }
}
