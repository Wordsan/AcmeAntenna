function phonenumber_check(input, message)
{
    re = /(\+[1-9]\d{0,2}( \([1-9]\d{0,2}\))? )?\d{4,}/;
    if ($(input).val() != "" && !$(input).val().match(re)) {
        return confirm(message);
    }
}