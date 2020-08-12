package com.openclassrooms.watchlist;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchlistController {

    private static int index = 1;
    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();

    @GetMapping("watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        String viewName = "watchlistItemForm";
        Map<String, Object> model = new HashMap<String, Object>();

        WatchlistItem watchlistItem = findWatchlistItemById(id);

        if (watchlistItem == null) {
            model.put("watchlistItem", new WatchlistItem());
        } else {
            model.put("watchlistItem", watchlistItem);
        }


        return new ModelAndView(viewName, model);
    }

    private WatchlistItem findWatchlistItemById(Integer id) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId().equals(id)) {
                return watchlistItem;
            }
        }
        return null;
    }

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitWatchlistItemForm(@Valid WatchlistItem watchlistItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("watchlistItemForm");
        }

        WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());


        if (existingItem == null) {

            if (itemAlreadyExists(watchlistItem.getTitle())) {
                bindingResult.rejectValue("title", "", "This movie is already on your watchlist");
                return new ModelAndView("watchlistItemForm");
            }

            if (toMuchItemsInList()) {
                bindingResult.rejectValue(null, "", "You added to much movies to your list please start to watching lazy bustard");
                return new ModelAndView("watchlistItemForm");
            }



            watchlistItem.setId(index++);
            watchlistItems.add(watchlistItem);
        } else {
            existingItem.setComment(watchlistItem.getComment());
            existingItem.setPriority(watchlistItem.getPriority());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setTitle(watchlistItem.getTitle());
        }


        RedirectView redirect = new RedirectView();
        redirect.setUrl("/watchlist");
        return new ModelAndView(redirect);
    }


    @GetMapping("watchlist")
    public ModelAndView getWatchlist() {


        String viewName = "watchlist";

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("watchlistItems", watchlistItems);
        model.put("numberOfMovies", watchlistItems.size());

        return new ModelAndView(viewName, model);
    }

    private boolean itemAlreadyExists(String title) {

        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    private boolean toMuchItemsInList() {

        if (watchlistItems.size() > 2) {
            return true;
        }
        return false;
    }


}
