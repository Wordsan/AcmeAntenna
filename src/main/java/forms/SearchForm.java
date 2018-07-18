package forms;

import validators.NullOrNotBlank;

public class SearchForm {
    private String terms;

    @NullOrNotBlank
    public String getTerms()
    {
        return terms;
    }

    public void setTerms(String terms)
    {
        this.terms = terms;
    }
}
