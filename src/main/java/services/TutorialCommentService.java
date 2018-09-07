package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.transaction.Transactional;

import domain.Tutorial;
import domain.TutorialComment;
import exceptions.ResourceNotFoundException;
import repositories.TutorialCommentRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class TutorialCommentService {
    @Autowired private TutorialCommentRepository repository;

    public Page<TutorialComment> findAllForTutorial(Tutorial tutorial, Pageable pageable)
    {
        CheckUtils.checkAuthenticated();
        return repository.findAllByTutorialOrderByCreationTimeDesc(tutorial, pageable);
    }

    public void delete(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        repository.delete(id);
    }

    public TutorialComment create(TutorialComment tutorialComment)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(tutorialComment.getUser());
        CheckUtils.checkNotExists(tutorialComment);

        tutorialComment.setCreationTime(new Date());

        return repository.save(tutorialComment);
    }

    public TutorialComment getByIdForDelete(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        TutorialComment result = repository.findOne(id);
        if (result == null) throw new ResourceNotFoundException();
        return result;
    }
}