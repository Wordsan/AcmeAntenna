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
public class Antenna extends DomainEntity {

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
    @NotNull
    // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public User getUser()
    {
        return this.user;
    }

    @NotBlank
    public String getSerialNumber()
    {
        return this.serialNumber;
    }

    @NotBlank
    public String getModel()
    {
        return this.model;
    }

    /**
     * Longitude angle of the position of the antenna.
     * <p>
     * Positive longitude indicates EAST, negative indicates WEST.
     */
    @Range(min = -180, max = 180)
    public double getPositionLongitude()
    {
        return this.positionLongitude;
    }

    /**
     * Latitude angle of the position of the antenna.
     * <p>
     * Positive latitude indicates NORTH, negative indicates SOUTH.
     */
    @Range(min = -90, max = 90)
    public double getPositionLatitude()
    {
        return this.positionLatitude;
    }

    @Transient
    public String getPositionForDisplay()
    {
        // \u00B0 is the degree sign. There seems to be something wrong with the java source file encoding,
        // which is supposedly set to utf8, but I can't find the problem.
        return String.format("%.4f\u00B0%s %.4f\u00B0%s", Math.abs(this.getPositionLatitude()), this.getPositionLatitude() > 0 ? "N" : "S", Math.abs(this.getPositionLongitude()), this.getPositionLongitude() > 0 ? "E" : "W");
    }

    /**
     * Angle indicating the horizontal rotation of the satellite, with 0 pointing at north and increasing angles indicating clockwise rotation.
     */
    @Range(min = 0, max = 360)
    public double getRotationAzimuth()
    {
        return this.rotationAzimuth;
    }

    /**
     * Angle indicating the vertical rotation of the satellite, with 0 pointing directly forward and increasing angles indicating upwards rotation.
     */
    @Range(min = 0, max = 90)
    public double getRotationElevation()
    {
        return this.rotationElevation;
    }

    @Range(min = 0, max = 100)
    public double getSignalQuality()
    {
        return this.signalQuality;
    }

    @ManyToOne(optional = false)
    @NotNull
    // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public Satellite getSatellite()
    {
        return this.satellite;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public void setSerialNumber(final String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public void setModel(final String model)
    {
        this.model = model;
    }

    public void setPositionLongitude(final double positionLongitude)
    {
        this.positionLongitude = positionLongitude;
    }

    public void setPositionLatitude(final double positionLatitude)
    {
        this.positionLatitude = positionLatitude;
    }

    public void setRotationAzimuth(final double rotationAzimuth)
    {
        this.rotationAzimuth = rotationAzimuth;
    }

    public void setRotationElevation(final double rotationElevation)
    {
        this.rotationElevation = rotationElevation;
    }

    public void setSignalQuality(final double signalQuality)
    {
        this.signalQuality = signalQuality;
    }

    public void setSatellite(final Satellite satellite)
    {
        this.satellite = satellite;
    }

}
