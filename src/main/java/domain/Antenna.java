package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Antenna
extends DomainEntity {
    private User user;
    private String serialNumber;
    private String model;
    private double positionLongitude;
    private double positionLatitude;
    private double rotationAzimuth;
    private double rotationElevation;
    private double signalQuality;
    private Satellite satellite;

    @ManyToOne(optional = false)
    public User getUser()
    {
        return user;
    }

    @NotNull
    @NotBlank
    public String getSerialNumber()
    {
        return serialNumber;
    }

    @NotNull
    @NotBlank
    public String getModel()
    {
        return model;
    }

    /**
     * Longitude angle of the position of the antenna.
     *
     * Positive longitude indicates EAST, negative indicates WEST.
     */
    @Range(min = -180, max = 180)
    public double getPositionLongitude()
    {
        return positionLongitude;
    }

    /**
     * Latitude angle of the position of the antenna.
     *
     * Positive latitude indicates NORTH, negative indicates SOUTH.
     */
    @Range(min = -90, max = 90)
    public double getPositionLatitude()
    {
        return positionLatitude;
    }

    /**
     * Angle indicating the horizontal rotation of the satellite, with 0 pointing at north and increasing angles indicating clockwise rotation.
     */
    @Range(min = 0, max = 360)
    public double getRotationAzimuth()
    {
        return rotationAzimuth;
    }

    /**
     * Angle indicating the vertical rotation of the satellite, with 0 pointing directly forward and increasing angles indicating upwards rotation.
     */
    @Range(min = 0, max = 90)
    public double getRotationElevation()
    {
        return rotationElevation;
    }

    @Range(min = 0, max = 100)
    public double getSignalQuality()
    {
        return signalQuality;
    }

    @Valid
    @ManyToOne(optional = false)
    public Satellite getSatellite()
    {
        return satellite;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setPositionLongitude(double positionLongitude)
    {
        this.positionLongitude = positionLongitude;
    }

    public void setPositionLatitude(double positionLatitude)
    {
        this.positionLatitude = positionLatitude;
    }

    public void setRotationAzimuth(double rotationAzimuth)
    {
        this.rotationAzimuth = rotationAzimuth;
    }

    public void setRotationElevation(double rotationElevation)
    {
        this.rotationElevation = rotationElevation;
    }

    public void setSignalQuality(double signalQuality)
    {
        this.signalQuality = signalQuality;
    }

    public void setSatellite(Satellite satellite)
    {
        this.satellite = satellite;
    }
}
