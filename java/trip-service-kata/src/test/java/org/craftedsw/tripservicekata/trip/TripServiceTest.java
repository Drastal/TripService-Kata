package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TripServiceTest {

    TripService tripService;
    private User loggedInUser;

    private static final User UNUSED_USER = null;
    private static final User GUEST = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();

    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_LONDON = new Trip();

    @Before
    public void init() {
        tripService = new TestableTripService();
    }


    @Test(expected = UserNotLoggedInException.class)
    public void shouldThrowUserNotLoggedInException_whenUserIsNotLoggedIn() {
        // Given
        loggedInUser = GUEST;
        // When
        tripService.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void shouldReturnEmptyTrips_whenUsersAreNotFriends() {
        // Given
        loggedInUser = REGISTERED_USER;
        User friend = new User();
        friend.addTrip(TO_BRAZIL);
        friend.addFriend(ANOTHER_USER);

        // When
        List<Trip> trips = tripService.getTripsByUser(friend);

        // Then
        assertThat(trips).isEmpty();
    }

    @Test
    public void shouldReturnTrips_whenUsersAreFriends() {
        // Given
        loggedInUser = REGISTERED_USER;
        User friend = new User();
        friend.addTrip(TO_BRAZIL);
        friend.addTrip(TO_LONDON);
        friend.addFriend(ANOTHER_USER);
        friend.addFriend(loggedInUser);

        // When
        List<Trip> trips = tripService.getTripsByUser(friend);

        // Then
        assertThat(trips).containsExactlyInAnyOrder(TO_BRAZIL, TO_LONDON);
    }

    private class TestableTripService extends TripService {
        @Override
        User getLoggedInUser() {
            return loggedInUser;
        }
        @Override
        List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }

}
