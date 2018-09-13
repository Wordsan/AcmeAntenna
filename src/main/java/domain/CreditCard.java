package domain;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

@Embeddable
public class CreditCard {
    private String holderName = "";
    private String brandName = "";
    private String number = "";
    private int expirationMonth;
    private int expirationYear;
    private int cvv;

    @NotBlank
    @Pattern(regexp = "[^#]*")
    public String getHolderName()
    {
        return holderName;
    }

    public void setHolderName(String holderName)
    {
        this.holderName = holderName;
    }

    @NotBlank
    @Pattern(regexp = "[^#]*")
    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    @Transient
    public String getObscuredNumber()
    {
        String tmp = number.replaceAll("[^0-9]", "");
        if (tmp.length() > 4) {
            return StringUtils.repeat("*", tmp.length() - 4) + tmp.substring(tmp.length() - 4);
        } else {
            return StringUtils.repeat("*", tmp.length());
        }
    }

    @NotBlank
    @CreditCardNumber
    @Pattern(regexp = "^[0-9 ]+$", message = "{org.hibernate.validator.constraints.CreditCardNumber.message}")
    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Range(min = 1, max = 12)
    public int getExpirationMonth()
    {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth)
    {
        this.expirationMonth = expirationMonth;
    }

    @Range(min = 2000, max = 2999)
    public int getExpirationYear()
    {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear)
    {
        this.expirationYear = expirationYear;
    }

    // American Express uses 4 digit CVV
    @Range(min = 0, max = 9999)
    public int getCvv()
    {
        return cvv;
    }

    public void setCvv(int cvv)
    {
        this.cvv = cvv;
    }

    @Transient
    public static String toCookieString(CreditCard creditCard)
    {
        return String.format("%s#%s#%s#%d#%d#%d", creditCard.holderName, creditCard.brandName, creditCard.number, creditCard.expirationMonth, creditCard.expirationYear, creditCard.cvv);
    }

    @Transient
    public static CreditCard fromCookieString(String cookieString)
    {
        if (cookieString == null) return null;

        String[] parts = cookieString.split("#");
        CreditCard result = new CreditCard();

        try {
            if (parts.length == 6) {
                result.setHolderName(parts[0]);
                result.setBrandName(parts[1]);
                result.setNumber(parts[2]);
                result.setExpirationMonth(Integer.parseInt(parts[3]));
                result.setExpirationYear(Integer.parseInt(parts[4]));
                result.setCvv(Integer.parseInt(parts[5]));
                return result;
            }
        } catch (NumberFormatException ignored) {}

        return null;
    }

    @Transient
    public static CreditCard visaTestCard()
    {
        CreditCard result = new CreditCard();
        result.setHolderName("VISA TEST HOLDER");
        result.setBrandName("VISA");
        result.setNumber("4111111111111111");
        result.setExpirationMonth(12);
        result.setExpirationYear(2999);
        result.setCvv(123);
        return result;
    }
}
