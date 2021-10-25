package it.fourpixel.simplecrud.repository;

import it.fourpixel.simplecrud.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    @Modifying
    @Query("UPDATE Tutorial set title = :title, description = :description where id = :id")
    void updateTitleAndDescription(@Param("id") long id,
                                   @Param("title") String title,
                                   @Param("description") String description);
    @Modifying
    @Query("UPDATE Tutorial set published = true where id = :id")
    void publish(long id);
}
