package exceptions;

import domain.PlatformSubscription;

public class OverlappingPlatformSubscriptionException extends Exception {
    private static final long serialVersionUID = 1L;

    private final PlatformSubscription overlappingPlatformSubscription;

    public OverlappingPlatformSubscriptionException(PlatformSubscription overlappingPlatformSubscription)
    {
        this.overlappingPlatformSubscription = overlappingPlatformSubscription;
    }

    public PlatformSubscription getOverlappingPlatformSubscription()
    {
        return overlappingPlatformSubscription;
    }
}
