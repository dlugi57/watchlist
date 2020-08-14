package com.openclassrooms.watchlist;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.repository.WatchListRepository;
import com.openclassrooms.watchlist.service.MovieRatingService;
import com.openclassrooms.watchlist.service.WatchlistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistServiceTest {
    @Mock
    private WatchListRepository watchlistRepositoryMock;
    @Mock
    private MovieRatingService movieRatingServiceMock;

    @InjectMocks
    WatchlistService watchlistService;

    @Test
    public void getWatchlistItemsReturnsAllItemFromRepository(){

        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "" , 1);
        WatchlistItem item2 = new WatchlistItem("Star Treck", "8.8", "M" , "" , 2);
        List<WatchlistItem> mockItems = Arrays.asList(item1, item2);

        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();

        //Assert
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getTitle().equals("Star Wars"));
        assertTrue(result.get(1).getTitle().equals("Star Treck"));

    }

    @Test
    public void testGetWatchlistItemsRatingFormOmdbServiceOverrideTheValueInItems() {

        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "" , 1);
        List<WatchlistItem> mockItems = Arrays.asList(item1);

        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        when(movieRatingServiceMock.getMovieRating(any(String.class))).thenReturn("10");

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();

        //Assert
        assertTrue(result.get(0).getRating().equals("10"));
    }
@Test
    public void testGetWatchlistItemsSizeReturnsSizeOfWatchlistItemsList(){
        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "" , 1);
        WatchlistItem item2 = new WatchlistItem("Star Treck", "8.8", "M" , "" , 2);
        List<WatchlistItem> mockItems = Arrays.asList(item1, item2);
        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        //Act
        Integer result = watchlistService.getWatchlistItemsSize();

        //Assert
        assertTrue(result.equals(2));
    }
}
