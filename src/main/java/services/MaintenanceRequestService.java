package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Antenna;
import domain.Handyworker;
import domain.MaintenanceRequest;
import domain.User;
import repositories.MaintenanceRequestRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class MaintenanceRequestService {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HandyworkerService handyworkerService;
    @PersistenceContext private EntityManager entityManager;
    @Autowired private Validator validator;


    public MaintenanceRequest create()
    {
        final MaintenanceRequest res = new MaintenanceRequest();
        final User u = this.userService.findPrincipal();
        res.setUser(u);
        final Date now = new Date();
        res.setCreationTime(now);
        return res;
    }

    public Collection<MaintenanceRequest> findAll()
    {
        final Collection<MaintenanceRequest> res = this.maintenanceRequestRepository.findAll();
        return res;
    }

    public MaintenanceRequest findOne(final int Id)
    {
        final MaintenanceRequest res = this.maintenanceRequestRepository.findOne(Id);
        Assert.notNull(res);

        return res;
    }

    public MaintenanceRequest save(final MaintenanceRequest maintenanceRequest)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(maintenanceRequest.getUser());
        CheckUtils.checkIsPrincipal(maintenanceRequest.getAntenna().getUser());

        Assert.notNull(maintenanceRequest);
        Assert.notNull(maintenanceRequest.getUser());
        Assert.notNull(maintenanceRequest.getDescription());
        Assert.isTrue(!maintenanceRequest.getDescription().isEmpty());
        Assert.notNull(maintenanceRequest.getAntenna());
        Assert.notNull(maintenanceRequest.getHandyworker());
        final MaintenanceRequest res = this.maintenanceRequestRepository.save(maintenanceRequest);
        Assert.notNull(res);

        final User u = res.getUser();

        final Handyworker h = res.getHandyworker();
        final Antenna a = res.getAntenna();
        h.getRequests().add(res);
        u.getRequests().add(res);
        a.getRequests().add(res);

        Assert.isTrue(u.getRequests().contains(res));
        Assert.isTrue(h.getRequests().contains(res));
        Assert.isTrue(a.getRequests().contains(res));

        return res;
    }

    public MaintenanceRequest service(final MaintenanceRequest maintenanceRequest)
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);

        Assert.notNull(maintenanceRequest);
        Assert.isNull(maintenanceRequest.getDoneTime());
        final Handyworker worker = this.handyworkerService.getPrincipal();
        Assert.isTrue(worker.equals(maintenanceRequest.getHandyworker()));
        final Date now = new Date();
        maintenanceRequest.setDoneTime(now);
        final MaintenanceRequest res = this.maintenanceRequestRepository.save(maintenanceRequest);
        Assert.notNull(res);


        return res;
    }

    public void delete(final MaintenanceRequest maintenanceRequest)
    {
        Assert.notNull(maintenanceRequest);
        Assert.isTrue(maintenanceRequest.getId() != 0);

        this.maintenanceRequestRepository.delete(maintenanceRequest);
    }

    public MaintenanceRequest bindForService(MaintenanceRequest maintenanceRequest, BindingResult binding)
    {
        entityManager.detach(maintenanceRequest);
        MaintenanceRequest oldMaintenanceRequest = maintenanceRequestRepository.findOne(maintenanceRequest.getId());
        Assert.notNull(oldMaintenanceRequest);
        oldMaintenanceRequest.setResultsDescription(maintenanceRequest.getResultsDescription());
        oldMaintenanceRequest.setDoneTime(new Date());
        validator.validate(oldMaintenanceRequest, binding);
        if (binding.hasErrors()) entityManager.detach(oldMaintenanceRequest);
        return oldMaintenanceRequest;
    }

    public boolean hasPendingRequest(Handyworker principal, Antenna antenna)
    {
        return !maintenanceRequestRepository.findPendingRequests(principal, antenna).isEmpty();
    }
}
