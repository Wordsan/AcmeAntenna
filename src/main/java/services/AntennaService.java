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

    @Autowired
    private AntennaRepository repository;
    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;


    public Antenna create(final Antenna submittedAntenna)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
        CheckUtils.checkNotExists(submittedAntenna);

        CheckUtils.checkFalse(submittedAntenna.getSatellite().getDeleted());

        return this.repository.save(submittedAntenna);
    }

    public Antenna update(final Antenna submittedAntenna)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
        CheckUtils.checkExists(submittedAntenna);

        // Ensure we get the old antenna and not the same one we submitted.
        this.entityManager.detach(submittedAntenna);
        final Antenna oldAntenna = this.repository.findOne(submittedAntenna.getId());
        Assert.isTrue(submittedAntenna != oldAntenna);

        CheckUtils.checkExists(oldAntenna);
        CheckUtils.checkIsPrincipal(oldAntenna.getUser());

        CheckUtils.checkTrue(!submittedAntenna.getSatellite().getDeleted() || (submittedAntenna.getSatellite().equals(oldAntenna.getSatellite())));

        return this.repository.save(submittedAntenna);
    }

    public Antenna getByIdForEdit(final int id)
    {
        return this.getByIdForShow(id);
    }

    public void delete(final int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        final Antenna antenna = this.getById(id);
        CheckUtils.checkIsPrincipal(antenna.getUser());
        this.repository.delete(antenna);
    }

    public List<Antenna> findAllForPrincipal()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        final User user = this.userService.getPrincipal();
        return this.repository.findAllByUserOrderBySerialNumberAsc(user);
    }

    public List<Antenna> findAll()
    {

        return this.repository.findAll();
    }

    public Antenna getByIdForShow(final int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        final Antenna antenna = this.getById(id);
        CheckUtils.checkIsPrincipal(antenna.getUser());
        return antenna;
    }

    private Antenna getById(final int id)
    {
        final Antenna antenna = this.repository.findOne(id);
        if (antenna == null) {
            throw new ResourceNotFoundException();
        }
        return antenna;
    }

}
