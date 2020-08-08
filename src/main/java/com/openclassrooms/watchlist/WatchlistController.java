package com.openclassrooms.watchlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchlistController {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;


    @GetMapping("watchlist")
    public ModelAndView getWatchlist() {

        watchlistItems.clear();
        watchlistItems.add(new WatchlistItem("Lion King","8.5","high","Hakuna matata!", index++));
        watchlistItems.add(new WatchlistItem( "Frozen","7.5","medium","Let it go!", index++));
        watchlistItems.add(new WatchlistItem( "Cars","7.1","low","Go go go!", index++));
        watchlistItems.add(new WatchlistItem( "Wall-E","8.4","high","You are crying!", index++));

        String viewName = "watchlist";

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("watchlistItems", watchlistItems);
        model.put("numberOfMovies", watchlistItems.size());

        return new ModelAndView(viewName , model);
    }


}
