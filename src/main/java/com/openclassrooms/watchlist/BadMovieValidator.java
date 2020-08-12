package com.openclassrooms.watchlist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BadMovieValidator implements ConstraintValidator<BadMovie, WatchlistItem> {
    @Override
    public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {

        return !(Double.valueOf(value.getRating()) <= 6 && value.getComment().length() < 15);
    }
}
