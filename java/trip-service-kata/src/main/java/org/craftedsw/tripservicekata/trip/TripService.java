package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }
        return user.isFriendsWith(loggedInUser)
                ? tripsBy(user)
                : noTrips();
	}

    private List<Trip> noTrips() {
        return new ArrayList<>();
    }

    List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

}
