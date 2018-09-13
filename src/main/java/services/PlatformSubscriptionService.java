package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Platform;
import domain.PlatformSubscription;
import exceptions.CreditCardExpiredException;
import exceptions.OverlappingPlatformSubscriptionException;
import repositories.PlatformSubscriptionRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class PlatformSubscriptionService {
    @Autowired private PlatformSubscriptionRepository repository;
    @Autowired private UserService userService;
    @PersistenceContext private EntityManager entityManager; // For Lucene.

    public List<PlatformSubscription> findAllForPrincipal()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        return repository.findAllByUserOrderByStartDateDesc(userService.getPrincipal());
    }

    public List<PlatformSubscription> findAllForPrincipalAndPlatform(Platform platform)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        return repository.findAllByUserAndPlatformOrderByStartDateDesc(userService.getPrincipal(), platform);
    }

    public PlatformSubscription create(PlatformSubscription platformSubscription) throws OverlappingPlatformSubscriptionException, CreditCardExpiredException
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);
        CheckUtils.checkIsPrincipal(platformSubscription.getUser());
        CheckUtils.checkNotExists(platformSubscription);

        if (platformSubscription.getCreditCard().isExpired()) throw new CreditCardExpiredException();

        // Check for overlapping subscriptions.
        List<PlatformSubscription> overlapping = repository.findOverlapping(platformSubscription.getUser(), platformSubscription.getPlatform(), platformSubscription.getStartDate(), platformSubscription.getEndDate());
        if (overlapping.size() > 0) {
            throw new OverlappingPlatformSubscriptionException(overlapping.get(0));
        }

        assignNewGeneratedKeycode(platformSubscription);

        return repository.save(platformSubscription);
    }

    private void assignNewGeneratedKeycode(PlatformSubscription platformSubscription)
    {
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder sb = new StringBuilder(PlatformSubscription.KEYCODE_LENGTH);
        for (int i = 0; i < PlatformSubscription.KEYCODE_LENGTH; i++) {
            sb.append(PlatformSubscription.KEYCODE_ALPHABET.charAt(secureRandom.nextInt(PlatformSubscription.KEYCODE_ALPHABET.length())));

        }

        platformSubscription.setKeyCode(sb.toString());
    }

}