package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;

public class TripServiceTest {

    TripService tripService;

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
        // When
        tripService.getTripsByUser(UNUSED_USER, GUEST);
    }

    @Test
    public void shouldReturnEmptyTrips_whenUsersAreNotFriends() {
        // Given
        User friend = aUser()
                        .friendsWith(ANOTHER_USER)
                        .withTrips(TO_BRAZIL)
                        .build();

        // When
        List<Trip> trips = tripService.getTripsByUser(friend, REGISTERED_USER);

        // Then
        assertThat(trips).isEmpty();
    }

    @Test
    public void shouldReturnTrips_whenUsersAreFriends() {
        // Given
        User friend = aUser()
                        .friendsWith(ANOTHER_USER, REGISTERED_USER)
                        .withTrips(TO_BRAZIL, TO_LONDON)
                        .build();

        // When
        List<Trip> trips = tripService.getTripsByUser(friend, REGISTERED_USER);

        // Then
        assertThat(trips).containsExactlyInAnyOrder(TO_BRAZIL, TO_LONDON);
    }

    private class TestableTripService extends TripService {
        @Override
        List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }

}
