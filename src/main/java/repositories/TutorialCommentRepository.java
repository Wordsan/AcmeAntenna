package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tutorial;
import domain.TutorialComment;

@Repository
public interface TutorialCommentRepository
        extends JpaRepository<TutorialComment, Integer>
{
    Page<TutorialComment> findAllByTutorialOrderByCreationTimeDesc(Tutorial tutorial, Pageable pageable);
}