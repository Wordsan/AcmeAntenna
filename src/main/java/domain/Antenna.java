package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
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

    @Valid
    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public User getUser()
    {
        return user;
    }

    @NotBlank
    @NotNull
    public String getSerialNumber()
    {
        return serialNumber;
    }

    @NotBlank
    @NotNull
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

    @Transient
    public String getPositionForDisplay()
    {
        return String.format("%.4f°%s %.4f°%s",
                      Math.abs(getPositionLatitude()),
                      getPositionLatitude() > 0 ? "N" : "S",
                      Math.abs(getPositionLongitude()),
                      getPositionLongitude() > 0 ? "E" : "W"
                      );
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
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
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
