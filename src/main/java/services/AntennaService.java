package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Antenna;
import domain.User;
import exceptions.ResourceNotFoundException;
import repositories.AntennaRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class AntennaService {
    @Autowired private AntennaRepository repository;
    @Autowired private UserService userService;

    @PersistenceContext private EntityManager entityManager;

    public Antenna create(Antenna submittedAntenna)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
        CheckUtils.checkNotExists(submittedAntenna);

        CheckUtils.checkFalse(submittedAntenna.getSatellite().getDeleted());

        return repository.save(submittedAntenna);
    }

    public Antenna update(Antenna submittedAntenna)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
        CheckUtils.checkExists(submittedAntenna);

        // Ensure we get the old antenna and not the same one we submitted.
        entityManager.detach(submittedAntenna);
        Antenna oldAntenna = repository.findOne(submittedAntenna.getId());
        Assert.isTrue(submittedAntenna != oldAntenna);

        CheckUtils.checkExists(oldAntenna);
        CheckUtils.checkIsPrincipal(oldAntenna.getUser());

        CheckUtils.checkTrue(!submittedAntenna.getSatellite().getDeleted() || (submittedAntenna.getSatellite().equals(oldAntenna.getSatellite())));

        return repository.save(submittedAntenna);
    }

    public Antenna getByIdForEdit(int id)
    {
        return getByIdForShow(id);
    }

    public void delete(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        Antenna antenna = getById(id);
        CheckUtils.checkIsPrincipal(antenna.getUser());
        repository.delete(antenna);
    }

    public List<Antenna> findAllForPrincipal()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        User user = userService.getPrincipal();
        return repository.findAllByUserOrderBySerialNumberAsc(user);
    }

    public Antenna getByIdForShow(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        Antenna antenna = getById(id);
        CheckUtils.checkIsPrincipal(antenna.getUser());
        return antenna;
    }

    private Antenna getById(int id)
    {
        Antenna antenna = repository.findOne(id);
        if (antenna == null) throw new ResourceNotFoundException();
        return antenna;
    }
}