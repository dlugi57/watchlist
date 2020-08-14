package com.openclassrooms.watchlist.service;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Service
public class WatchlistService {

    WatchListRepository watchListRepository;
    MovieRatingService movieRatingService;

    @Autowired
    public WatchlistService(WatchListRepository watchListRepository, MovieRatingService movieRatingService) {
        super();
        this.watchListRepository = watchListRepository;
        this.movieRatingService = movieRatingService;
    }

    public List<WatchlistItem> getWatchlistItems() {
        List<WatchlistItem> watchlistItems = watchListRepository.getList();

        for (WatchlistItem watchlistItem : watchlistItems) {
            String rating = movieRatingService.getMovieRating(watchlistItem.getTitle());

            if (rating != null) {
                watchlistItem.setRating(rating);
            }

        }

        return watchlistItems;
    }

    public int getWatchlistItemsSize() {
        return watchListRepository.getList().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        return watchListRepository.findWatchlistItemById(id);
    }

    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
        WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());


        if (existingItem == null) {

            if (watchListRepository.findWatchlistItemByTitle(watchlistItem.getTitle()) != null) {
                throw new DuplicateTitleException();
            }

            watchListRepository.addItem(watchlistItem);
        } else {
            existingItem.setComment(watchlistItem.getComment());
            existingItem.setPriority(watchlistItem.getPriority());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setTitle(watchlistItem.getTitle());
        }
    }

}
